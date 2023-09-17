SPN {
	*prEzForOctave {
		^PitchClassSet.prMakeEZ(
			PitchClassSet.prEzOctaveLowerPart
		++ PitchClassSet.prEzCommonPart
		++ PitchClassSet.prEzOctaveHigherPart
		)
	}
	*prLilyForOctave {
		^PitchClassSet.prMake(
			PitchClassSet.prLilyOctaveLowerPart
		++ PitchClassSet.prLilyCommonPart
		++ PitchClassSet.prLilyOctaveHigherPart
		)
	}
	*prMusForOctave {
		^PitchClassSet.prMake(
			PitchClassSet.prMusicSymbolOctaveLowerPart
		++ PitchClassSet.prMusicSymbolCommonPart
		++ PitchClassSet.prMusicSymbolOctaveHigherPart
		)
	}
	*prParseOctave {
		arg aPitchClassAsSymbol, octave;
		var aPitchClassAsString, result;
		aPitchClassAsString = aPitchClassAsSymbol.asString;
		result = if (aPitchClassAsString[aPitchClassAsString.size - 1] == $h) {
			aPitchClassAsString[.. aPitchClassAsString.size - 2] ++ (octave.asInteger + 1)
		} {
			if (aPitchClassAsString[aPitchClassAsString.size - 1] == $l) {
				aPitchClassAsString[.. aPitchClassAsString.size - 2] ++ (octave.asInteger - 1)
			}
			{
				aPitchClassAsSymbol ++ octave.asInteger
			}
		};
		^result
	}
	*prMapToOctave {
		arg enharmonicQuarterToneDictionaryOfPitchClasses, midiPitch;
		var octave = (midiPitch / 12).floor - 1;
		^enharmonicQuarterToneDictionaryOfPitchClasses.collect { |aPitchClassAsSymbol|
			SPN.prParseOctave(aPitchClassAsSymbol, octave)
		}
	}
	*prDetectBInLowerOctave {
		arg pitchClass, octave;
		octave = if ("bs|bQ|bSu|bS|bSq|bs|btqs|bxqf|bx|bxqs|b♯|b♯ ¼↑|b𝄪 ¼↓|b𝄪|b𝄪 ¼↑".matchRegexp(pitchClass.asString)) {
			octave + 1
		} {
			octave
		};
		^octave
	}
	*prDetectPitchClassOctave {
		arg pitchName;
		var pitchNameAsString, size, pitchClass, octave;
		pitchNameAsString = pitchName.asString;
		size = pitchNameAsString.size;
		# pitchClass, octave = if (pitchNameAsString[size - 2] == $- || (pitchNameAsString[size - 2] == $m)) {

			[pitchNameAsString[0 .. size - 3].asSymbol, pitchNameAsString[size -1].asString.asInteger * -1]
		} {
			[pitchNameAsString[0 .. size - 2].asSymbol, pitchNameAsString[size -1].asString.asInteger]
		}
		^[pitchClass, SPN.prDetectBInLowerOctave(pitchClass, octave)]
	}
	*getValue {
		arg which = \a4, mappingSwitch = \all;
		var pitchClass, octave, pitchClassSets, pitchClassKey, midiPitchNumber;
		which = which.asString.replace("h", "").replace("l", "");
		pitchClassSets = PitchClassSet.ez.collect { |item, index|
			PitchClassSet.lily[index] ++ PitchClassSet.mus[index] ++ item };
		# pitchClass, octave = SPN.prDetectPitchClassOctave(which);
		midiPitchNumber = (octave + 1) * 12 + pitchClass.pitchClassNum;
		^switch(mappingSwitch,
			\all, { (midi: midiPitchNumber.asFloat, cps: midiPitchNumber.midicps) },
			\cps, { midiPitchNumber.midicps },
			\midi, { midiPitchNumber.asFloat }
		)
	}
	*prStyle { arg style;
		^switch(style,
			\ez, { SPN.prEzForOctave },
			\lily, { SPN.prLilyForOctave },
			\mus, { SPN.prMusForOctave }
		)
	}
	*midi {
		arg midiPitch = 69, style = \lily;
		var enharmonicQuarterToneDictionaryOfPitchClasses = SPN.prStyle(style)[
			if (style == \ez) {
			midiPitch.round(0.125) % 12
			} {
				midiPitch.round(0.5) % 12
			}
		];
		^SPN.prMapToOctave(enharmonicQuarterToneDictionaryOfPitchClasses, midiPitch)
	}
	*cps {
		arg freq = 440.0, style = \lily;
		var enharmonicQuarterToneDictionaryOfPitchClasses = SPN.prStyle(style)[
			if (style == \ez) {
				freq.cpsmidi.round(0.125) % 12
			} {
				freq.cpsmidi.round(0.5) % 12
			}
		];
		^SPN.prMapToOctave(enharmonicQuarterToneDictionaryOfPitchClasses, freq.cpsmidi)
	}
}

+ Float {
	cpsspn {
		arg style = \lily;
		^SPN.midi(this.cpsmidi, style)
	}
	midispn {
		arg style = \lily;
		^SPN.midi(this, style)
	}
}

+ Integer {
	cpsspn {
		arg style = \lily;
		^SPN.midi(this.cpsmidi, style)
	}
	midispn {
		arg style = \lily;
		^SPN.midi(this, style)
	}
}

+ Symbol {
	cps {
		^SPN.getValue(this, \cps)
	}
	midi {
		^SPN.getValue(this, \midi)
	}
	all {
		^SPN.getValue(this, \all)
	}
}