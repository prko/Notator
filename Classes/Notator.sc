Notator {
	*stringToArray {
		arg input;
		^if (input.class.asSymbol == \String) {
			input.replace($\n, " ").replace(" ", " ").split($ ).collect { |item| item.replace("__", "_O").replace("_", " ") }
		} {
			input
		}
	}
	*parseStringAndSymbol {
		arg stringOrSymbolOrArray;
		var stringToParse, thisEntryHasChord, pitchEndingIndex;

		("\t\tparseStringAndSymbol:").postln;

		^if (stringOrSymbolOrArray.class.asSymbol == \String || (stringOrSymbolOrArray.class.asSymbol == \Symbol)) {

			("stringOrSymbolOrArray:" + stringOrSymbolOrArray ++ ";" + stringOrSymbolOrArray.class).postln;
			stringToParse = stringOrSymbolOrArray.asString;
			thisEntryHasChord = stringToParse.asString[0] == $<;
			stringToParse = if (thisEntryHasChord) {
				var pitchEndingIndex = stringToParse.findAll($>)[0];
				stringToParse[0..pitchEndingIndex].replace(">", "").replace(" ", "=").replace("<", "")
				++ stringToParse[pitchEndingIndex + 1 ..]
			} {
				stringToParse
			};
			stringToParse.split($ ).collect { |testArrayItem, testArrayIndex|
				var testArrayItemParser;

				("\n\ntestArrayItem:" + testArrayItem ++ ";" + "testArrayIndex:" + testArrayIndex ++ ";" + "testArrayItem.class:" + testArrayItem.class++"\n\n").postln;

				testArrayItemParser = {
					var rhythmParser;
					("testArrayItem:" + testArrayItem ++ ";" + "testArrayIndex:" + testArrayIndex).postln;
					("testArrayItem[0]:" + testArrayItem[0] + testArrayItem[0].class).postln;

					("stringToParse[0] == $t && (testArrayIndex == 0 || (testArrayIndex == 1)):" + (stringToParse[0] == $t && (testArrayIndex == 0 || (testArrayIndex == 1)))).postln;
					("stringToParse[0] == $t && (testArrayIndex == 2):" + (stringToParse[0] == $t && (testArrayIndex == 2))).postln;
					("testArrayItem[0] == $|:" + (testArrayItem[0] == $|)).postln;
					("testArrayItem[0] == $\\:" + (testArrayItem[0] == $\\)).postln;
					("testArrayIndex == 0 && thisEntryHasChord:" + (testArrayIndex == 0 && thisEntryHasChord)).postln;
					("("++"^([a-g]|[0-9])((m|)(tq|q|)(s|S|ss|x|f|ff|F|Q|U|u|n)|)(((m|)1|3|5|7|9|11|13|15_8|16)|)(m|)[0-9]$".quote ++ ")
.matchRegexp(testArrayItem)
&& (testArrayIndex == 0)
&& thisEntryHasChord.not
&& (testArrayItem[0] != $|)
&& (testArrayItem[0] != $\\)" + (("^([a-g]|[0-9])((m|)(tq|q|)(s|S|ss|x|f|ff|F|Q|U|u|n)|)(((m|)1|3|5|7|9|11|13|15_8|16)|)(m|)[0-9]$")
							.matchRegexp(testArrayItem)
							&& (testArrayIndex == 0)
							&& thisEntryHasChord.not
							&& (testArrayItem[0] != $|)
							&& (testArrayItem[0] != $\\))).postln;
					("(testArrayIndex == 1) &&
" ++ "^([2-9]|_|m|l|b|w|h|q|e|x|t|i|y)|^[0-9]{1,3}\\.{0,3}$".quote ++".matchRegexp(testArrayItem):" + ((testArrayIndex == 1) &&
							"^([2-9]|_|m|l|b|w|h|q|e|x|t|i|y)|^[0-9]{1,3}\\.{0,3}$".matchRegexp(testArrayItem))).postln;
					("(testArrayItem.size == 1  &&  (testArrayItem[0] != $f)) ||  (testArrayItem[0] != $F  &&  testArrayItem[0] != $/):" + ((testArrayItem.size == 1  &&  (testArrayItem[0] != $f))  ||  (testArrayItem[0] != $F  &&  testArrayItem[0] != $/))).postln;


					("^(s|f|p|w)".quote ++ ".matchRegexp(testArrayItem) || (testArrayItem[0] == $F && (testArrayItem.size == 1):" + ("^(s|f|p|w)".matchRegexp(testArrayItem) || (testArrayItem[0] == $F && (testArrayItem.size == 1)))).postln;
					(" testArrayItem[0] == $/ :" +  (testArrayItem[0] == $/) ).postln;

					rhythmParser = { |input|
						("input:" + input).postln;
						input
						.asString
						.replace("128...", "Ty")  .replace("128..", "Dy")  .replace("128.", "dy")  .replace("128", "y") // semihemidemisemiquaver, quasihemidemisemiquaver
						.replace("64...", "Ti")  .replace("64..", "Di")  .replace("64.", "di")  .replace("64", "i")     // hemidemisemiquaver
						.replace("32...", "Tt")  .replace("32..", "Dt")  .replace("32.", "dt")  .replace("32", "t")     // demisemiquaver
						.replace("16...", "Tx")  .replace("16..", "Dx")  .replace("16.", "dx")  .replace("16", "x")     // semiquaver
						.replace("8...", "Te")   .replace("8..", "De")   .replace("8.", "de")   .replace("8", "e")      // quaver
						.replace("4...", "Tq")   .replace("4..", "Dq")   .replace("4.", "dq")   .replace("4", "q")      // crotchet, quarter
						.replace("2...", "Th")   .replace("2..", "Dh")   .replace("2.", "dh")   .replace("2", "h")      // minim, half
						.replace("1...", "Tw")   .replace("1..", "Dw")   .replace("1.", "dw")   .replace("1", "w")      // semibreve, whole

						//.replace("m...", "Tm")  .replace("m..", "Dm")  .replace("m.", "dm")  // maxima
						.replace("l...", "T0")  .replace("l..", "D0")  .replace("l.", "d0")  // longa
						.replace("b...", "T9")  .replace("b..", "D9")  .replace("b.", "d9")  // breve
						.replace("w...", "T8")  .replace("w..", "D8")  .replace("w.", "d8")  // semibreve
						.replace("h...", "T7")  .replace("h..", "D7")  .replace("h.", "d7")  // minim
						.replace("q...", "T6")  .replace("q..", "D6")  .replace("q.", "d6")  // crotchet
						.replace("e...", "T5")  .replace("e..", "D5")  .replace("e.", "d5")  // quaver
						.replace("x...", "T4")  .replace("x..", "D4")  .replace("x.", "d4")  // semiquaver
						.replace("t...", "T3")  .replace("t..", "D3")  .replace("t.", "d3")  // demisemiquaver
						.replace("i...", "T2")  .replace("i..", "D2")  .replace("i.", "d2")  // hemidemisemiquaver
						.replace("y...", "T1")  .replace("y..", "D1")  .replace("y.", "d1")  // semihemidemisemiquaver, quasihemidemisemiquaver
						.asSymbol
					};

					(
						case
						{ stringToParse[0] == $t && (testArrayIndex == 0 || (testArrayIndex == 1))} {
							("testArrayItem:" + testArrayItem).postln;
							("testArrayItem.class:" + testArrayItem.class).postln;
							testArrayItem.asSymbol
						}
						{ stringToParse[0] == $t && (testArrayIndex == 2)} {
							("testArrayItem:" + testArrayItem).postln;
							("testArrayItem.class:" + testArrayItem.class).postln;
							testArrayItem.asString.interpret
						}
						{ testArrayItem[0] == $| } {
							"The pitch or the pitches are same as in the previous entry.".postln;
							("testArrayItem:" + testArrayItem).postln;
							("testArrayItem.class:" + testArrayItem.class).postln;
							testArrayItem
						}
						{ testArrayItem[0] == $\\  } {
							"This is a repetition of the previous entry.".postln;
							("testArrayItem:" + testArrayItem).postln;
							("testArrayItem.class:" + testArrayItem.class).postln;
							testArrayItem
						}
						{ testArrayIndex == 0 && thisEntryHasChord } {
							var chord;
							"\n\nchord detected\n".postln;
							("testArrayItem:" + testArrayItem).postln;
							("testArrayItem.class:" + testArrayItem.class).postln;
							chord = testArrayItem.split($=);

							chord.collect { |anItem|
								if ("^[0-9]".matchRegexp(anItem)) {
									("anItem Number:" + anItem).postln;
									anItem.asFloat
								} {
									("anItem non-Number:" + anItem).postln;
									anItem.asSymbol
								}
							}
						}
						{
							("^([a-g]|[0-9])((m|)(tq|q|)(s|S|ss|x|f|ff|F|Q|U|u|n)|)(((m|)1|3|5|7|9|11|13|15_8|16)|)(m|)[0-9]$")
							.matchRegexp(testArrayItem)
							&& (testArrayIndex == 0)
							&& thisEntryHasChord.not
							&& (testArrayItem[0] != $|)
							&& (testArrayItem[0] != $\\) } {
							"single pitch\n".postln;
							("testArrayItem:" + testArrayItem).postln;
							("testArrayItem.class:" + testArrayItem.class).postln;
							if ("^[0-9]".matchRegexp(testArrayItem)) {
								testArrayItem.asFloat
							} { testArrayItem.asSymbol }
						}
						{ (testArrayIndex == 1) &&
							"^([2-9]|_|m|l|b|w|h|q|e|x|t|i|y)|^[0-9]{1,3}\\.{0,3}$".matchRegexp(testArrayItem) } {
							var thisItem = rhythmParser.(testArrayItem);
							("thisItem:" + thisItem).postln;
							"this is not a tuplet\n".postln;
							thisItem.asSymbol
						}
						{ (testArrayItem.size == 1  &&  (testArrayItem[0] != $f))  ||  (testArrayItem[0] != $F  &&  testArrayItem[0] != $/) } {
							var thisItem = testArrayItem
							.replace(".", "s")  .replace("!", "S")  .replace(">", "a")  .replace("^", "A")  .replace("-", "o")  .replace("_", "O")
							.replace("(", "u")  .replace(")", "U")  .replace("~", "j")  .replace("`", "J");
							"articulation, slur or ties".postln;
							thisItem.asSymbol
						}
						{ "^(s|f|p|w)".matchRegexp(testArrayItem) || (testArrayItem[0] == $F && (testArrayItem.size == 1)) } {
							("testArrayItem:" + testArrayItem).postln;
							("testArrayItem.class:" + testArrayItem.class).postln;
							"this item is dynamic marking".postln;
							testArrayItem.asSymbol
						}
						{ testArrayItem[0] == $/ } {
							var tupletParser;
							tupletParser = { |textToBeParsed|
								var thisItem;
								("testArrayItem:" + testArrayItem + testArrayItem.class).postln;
								("textToBeParsed:" + textToBeParsed[1..] + textToBeParsed[1..].class).postln;
								thisItem = textToBeParsed[1..].replace(":", " ").replace("/", " ").split($ );
								("thisItem:" + thisItem + thisItem.class + thisItem.size).postln;
								switch (thisItem.size,
									1, {
										var parsed = [rhythmParser.(thisItem[0])];
										"middle of tuplet and defines rhythmic value".postln;
										if (testArrayItem[1] == $/) { [parsed]
										} {
											parsed
										}
									},
									2, {
										"end of tuplet and defines rhythmic value".postln;
										if (testArrayItem[1] == $/) { [[rhythmParser.(thisItem[0]), thisItem[1]]]
										} {
											[rhythmParser.(thisItem[0]), thisItem[1]]
										}
									},
									3, {
										case
										{ textToBeParsed[textToBeParsed.size - 1] == $/  } {
											"end of a tuplet and a nested tuplet.".postln;
											/*
											thisItem[0] = if ("^[0-9]".matchRegexp(thisItem[0])){
											thisItem[0].asString.interpret
											} {
											thisItem[0]
											};
											*/
											("thisItem[0]:" + thisItem[0] + thisItem[0].class).postln;
											[[rhythmParser.(thisItem[0]), thisItem[1] ], thisItem[2]]
										}
										{ textToBeParsed[textToBeParsed.size - 1] != $/ } {
											var parsed = thisItem.collect { |anItem, index|
												("textToBeParsed:" + textToBeParsed).postln;
												("anItem:" + anItem).postln;
												if (index == 1 || (index == 2)) {
													anItem.asString.interpret
												} {
													rhythmParser.(anItem)
												}
											};
											"start of tuplet and defines rhythmic value. the start value is also the tuplet note type.".postln;
											if (testArrayItem[1] == $/) { [parsed]
											} {
												parsed
											}
										}
									},
									4, {
										"start of tuplet and defines rhythmic value".postln;
										thisItem.collect { |anItem, index|
											if (index == 1 || (index == 2)) {
												anItem.asString.interpret
											} {
												rhythmParser.(anItem)
											}
										}
									}
								)
							};
							"tuplet detected\n".postln;
							"tuplet parsed\n".postln;
							if (testArrayItem[1] == $/) {
								tupletParser.(testArrayItem[1..])
							} {
								tupletParser.(testArrayItem)
							}
						}
					)
				}.()
			}
		} {
			"\t\t\tthis entry is array. No parsing is needed".postln;
			stringOrSymbolOrArray
		}
	}
	*notate {
		arg score, musicXMLfilePath = ("~/Downloads/untitled.musicxml".standardizePath), app = "MuseScore 4";
		var musicXMLfile, renotatedVariable, scdfile, scdfilePath, partLables = [], routineText, q,
		div, noteTypeDurationPairs, noteTypeKeyFinder, noteTypeTranscriber,
		midiPitchNumber2Pitch, dynamicMIDIvelocityPairs, dynamicTranscriber, articulations, ties, slur,
		header, partsInTheFirstBar, itemsPerPartPerBar,
		partAttributesPerBarLast = (), voiceEntryLastPerPart = (), dynamicsLastPerPart = (),
		octaveLastPerPart = (), durationsLastPerPart = (), articulationLastPerPart = (),
		musicXMLtxt;

		musicXMLfilePath = musicXMLfilePath.replace(" ", "_");
		musicXMLfile = File(musicXMLfilePath, "w");

		scdfilePath = musicXMLfilePath.splitext[0] ++ ".scd";
		scdfile = File(scdfilePath, "w+");

		q = { |text| text.asString.quote };

		midiPitchNumber2Pitch = ();

		(0, 0.0625 .. 127).do { |sixteenthStep|
			var sixteenthToneStep, octave;
			sixteenthToneStep = [
				\c,  \c1_16, \c1_8, \c3_16,  \cq, \c5_16,  \c3_8, \c7_16,
				\cs, \c9_16, \c5_8, \dm5_16, \du, \dm3_16, \dm1_8, \dm1_16,
				\d,  \d1_16, \d1_8, \d3_16,  \dq, \d5_16,  \d3_8, \d7_16,
				\ds, \d9_16, \d5_8, \em5_16, \eu, \em3_16, \em1_8, \em1_16,
				\e,  \e1_16, \e1_8, \e3_16,  \eq, \fm3_16, \fm1_8, \fm1_16,
				\f,  \f1_16, \f1_8, \f3_16,  \fq, \f5_16,  \f3_8, \f7_16,
				\fs, \f9_16, \f5_8, \gm5_16, \gu, \gm3_16, \gm1_8, \gm1_16,
				\g,  \g1_16, \g1_8, \g3_16,  \gq, \g5_16,  \g3_8, \g7_16,
				\gs, \g9_16, \g5_8, \am5_16, \au, \am3_16, \am1_8, \am1_16,
				\a,  \a1_16, \a1_8, \a3_16,  \aq, \a5_16,  \a3_8, \a7_16,
				\as, \a9_16, \a5_8, \bm5_16, \bu, \bm3_16, \bm1_8, \bm1_16,
				\b,  \b1_16, \b1_8, \b3_16,  \bq, \cm3_16h,  \cm1_8h, \cm1_16h
			][sixteenthStep * 8 % 96];
			octave = (sixteenthStep / 12).floor - 1;
			midiPitchNumber2Pitch.put(sixteenthStep.asSymbol, sixteenthToneStep ++ octave.asInteger)
		};

		dynamicMIDIvelocityPairs = (ffff: 115, fff: 103, ff:91, F:79, f:79, mf: 67, mp: 55, p: 43, pp: 31, ppp: 19, pppp: 7, sf: 96);
		dynamicTranscriber = { |dynamic|
			dynamic.asString
			.replace("F4", "ffff") .replace("F3", "fff") .replace("F2", "ff") .replace("F1", "f") .replace("F", "f") .replace("F0", "mf")
			.replace("s4", "ffff") .replace("s3", "fff") .replace("s2", "ff") .replace("s1", "f")                    .replace("s0", "mf")
			.replace("p4", "pppp") .replace("p3", "ppp") .replace("p2", "pp") .replace("p1", "p")                    .replace("p0", "mp")
			.replace("q4", "pppp") .replace("q3", "ppp") .replace("q2", "pp") .replace("q1", "p")                    .replace("q0", "mp")
			.asSymbol
		};

		// To get div from 2 to 10 div per quarter note.
		div = { |a b| lcm(a, b) };
		(3..10).do { |i| if (i == 3 ) {
			div = div.(2, i)
		} {
			div = lcm(div, i) }
		};
		div = div * 100; // * 100: to display 128th

		noteTypeDurationPairs = (
			T_: [\maxima, div * 60],          D_: [\maxima, div * 56],         d_: [\maxima, div * 48],         _: [\maxima, div * 32],
			T0: [\long, div * 30],            D0: [\long, div * 28],           d0: [\long, div * 24],          \0: [\long, div * 16],
			T9: [\breve, div * 15],           D9: [\breve, div * 14],          d9: [\breve, div * 12],         \9: [\breve, div * 8],
			T8: [\whole, div * 7.5],          D8: [\whole, div * 7],           d8: [\whole, div * 6],          \8: [\whole, div * 4],
			T7: [\half, div * 3.75],          D7: [\half, div * 3.5],          d7: [\half, div * 3],           \7: [\half, div * 2],
			T6: [\quarter, div * 1.875],      D6: [\quarter, div * 1.75],      d6: [\quarter, div * 1.5],      \6: [\quarter, div],
			T5: [\eighth, div * 0.9375],      D5: [\eighth, div * 0.875],      d5: [\eighth, div * 0.75],      \5: [\eighth, div / 2],
			T4: ['16th', div * 0.46875],      D4: ['16th', div * 0.4375],      d4: ['16th', div * 0.375],      \4: ['16th', div / 4],
			T3: ['32nd', div * 0.234375],     D3: ['32nd', div * 0.21875],     d3: ['32nd', div * 0.1875],     \3: ['32nd', div / 8],
			T2: ['64th', div * 0.1171875],    D2: ['64th', div * 0.109375],    d2: ['64th', div * 0.09375],    \2: ['64th', div / 16],
			T1: ['128th', div * 0.05859375],    D1: ['128th', div * 0.0546875],  d1: ['128th', div * 0.046875],  \1: ['128th', div / 32],
			//'!': ['256th', div / 64],
			//'`': ['512th', div / 128],
			//'~': ['1024th', div / 256]
		);

		noteTypeTranscriber = { |type|
			if (type != \nil) {
				type.asString
				/*.replace("Tm", "T_")  .replace("Dm", "D_")  .replace("dm", "d_")  */.replace("m", "_") // maxima
				.replace("Tl", "T0")  .replace("Dl", "D0")  .replace("dl", "d0")  .replace("l", "0") // longa
				.replace("Tb", "T9")  .replace("Db", "D9")  .replace("db", "d9")  .replace("b", "9") // breve
				.replace("Tw", "T8")  .replace("Dw", "D8")  .replace("dw", "d8")  .replace("w", "8") // semibreve
				.replace("Th", "T7")  .replace("Dh", "D7")  .replace("dh", "d7")  .replace("h", "7") // minim
				.replace("Tq", "T6")  .replace("Dq", "D6")  .replace("dq", "d6")  .replace("q", "6") // crotchet
				.replace("Te", "T5")  .replace("De", "D5")  .replace("de", "d5")  .replace("e", "5") // quaver
				.replace("Tx", "T4")  .replace("Dx", "D4")  .replace("dx", "d4")  .replace("x", "4") // semiquaver
				.replace("Tt", "T3")  .replace("Dt", "D3")  .replace("dt", "d3")  .replace("t", "3") // demisemiquaver
				.replace("Ti", "T2")  .replace("Di", "D2")  .replace("di", "d2")  .replace("i", "2") // hemidemisemiquaver
				.replace("Ty", "T1")  .replace("Dy", "D1")  .replace("dy", "d1")  .replace("y", "1") // semihemidemisemiquaver, quasihemidemisemiquaver
				.asSymbol
			}
		};

		noteTypeKeyFinder = { |value|
			var duration2Key = ();
			noteTypeDurationPairs.pairsDo { |key, value| duration2Key.put(value[1], key) };
			duration2Key[value]
		};

		articulations = (
			a: \accent,          s: \staccato,       o: \tenuto,
			'>': \accent,        '.': \staccato,      '-': \tenuto,
			A: 'strong-accent',  S: \staccatissimo,  O: 'detached-legato',
			'^': 'strong-accent', '!': \staccatissimo, '_': 'detached-legato'
		);

		ties = (
			j: (
				\tie:  "        <tie type=" ++ q.(\start) ++ "/>\n",
				\tied: "          <tied type=" ++ q.(\start) ++ "/>\n"
			),
			'~': (
				\tie:  "        <tie type=" ++ q.(\start) ++ "/>\n",
				\tied: "          <tied type=" ++ q.(\start) ++ "/>\n"
			),
			J: (
				\tie:  "        <tie type=" ++ q.(\stop) ++ "/>\n",
				\tied: "          <tied type=" ++ q.(\stop) ++ "/>\n"
			),
			'`': (
				\tie:  "        <tie type=" ++ q.(\stop) ++ "/>\n",
				\tied: "          <tied type=" ++ q.(\stop) ++ "/>\n"
			),
			lv: (
				\tie:  "        <tie type=" ++ q.('let-ring') ++ "/>\n",
				\tied: "          <tied type=" ++ q.('let-ring') ++ "/>\n"
			),
			/*
			r: (
				\tie:  "        <tie type=" ++ q.('let-ring') ++ "/>\n",
				\tied: "          <tied type=" ++ q.('let-ring') ++ "/>\n"
			),
			*/
			r: (
			\tie:  "        <tie type=" ++ q.(\stop) ++ "/>\n",
			\tied: "          <tied type=" ++ q.(\stop) ++ "/>\n" ++
			"          <tied type=" ++ q.('let-ring') ++ "/>\n"
			),
			R: (
				\tie:  "        <tie type=" ++ q.(\stop) ++ "/>\n",
				\tied: "          <tied type=" ++ q.(\stop) ++ "/>\n" ++
				"          <tied type=" ++ q.('let-ring') ++ "/>\n"
			)
		);

		slur = (
			u: "          <slur number=" ++ q.(1) ++ " type=" ++ q.(\start) ++ "/>\n",
			'(': "          <slur number=" ++ q.(1) ++ " type=" ++ q.(\start) ++ "/>\n",
			')': "          <slur number=" ++ q.(1) ++ " type=" ++ q.(\stop) ++ "/>\n",
			U: "          <slur number=" ++ q.(1) ++ " type=" ++ q.(\stop) ++ "/>\n"
		);


		header = "<?xml version=" ++ q.("1.0") + "encoding=" ++ q.("UTF-8") + "standalone=" ++ q.("no") ++ "?>\n" ++
		"<!DOCTYPE score-partwise PUBLIC" + q.("-//Recordare//DTD MusicXML 4.0 Partwise//EN") +
		q.("http://www.musicxml.org/dtds/partwise.dtd") ++ ">\n" ++
		"<score-partwise version=" ++ q.("4.0") ++ ">\n" ++
		"  <work>\n" ++
		"    <work-title>" ++ score[0][\title] ++ "</work-title>\n" ++
		"  </work>\n" ++
		"  <movement-title>" ++ score[0][\title] ++ "</movement-title>\n" ++
		"  <identification>\n" ++
		"    <creator type=" ++ q.("composer") ++ ">" ++ score[0][\composer] ++ "</creator>\n" ++
		"    <rights>" ++ score[0][\rights] ++ "</rights>\n" ++
		"    <encoding>\n" ++
		"      <software>SuperCollider</software>\n" ++
		"      <encoding-date>" ++ Date.localtime.format("%Y-%m-%d") ++ "</encoding-date>\n" ++
		"    </encoding>\n" ++
		"  </identification>\n" ++

		"  <defaults>\n" ++
		"      <scaling>\n" ++
		"         <millimeters>6.35</millimeters>\n" ++
		"         <tenths>40</tenths>\n" ++
		"      </scaling>\n" ++
		"      <page-layout>\n" ++
		"         <page-height>1870.8661417323</page-height>\n" ++
		"         <page-width>1322.8346456693</page-width>\n" ++
		"         <page-margins type=" ++ q.("both") ++ ">\n" ++
		"            <left-margin>20</left-margin>\n" ++
		"            <right-margin>20</right-margin>\n" ++
		"            <top-margin>20</top-margin>\n" ++
		"            <bottom-margin>20</bottom-margin>\n" ++
		"         </page-margins>\n" ++
		"      </page-layout>\n" ++
		"      <system-layout>\n" ++
		"         <system-margins>\n" ++
		"            <left-margin>0</left-margin>\n" ++
		"            <right-margin>0</right-margin>\n" ++
		"         </system-margins>\n" ++
		"         <system-distance>127</system-distance>\n" ++
		"         <top-system-distance>10</top-system-distance>\n" ++
		"      </system-layout>\n" ++
		"  </defaults>\n" ++

		"  <part-list>\n";

		{
			var part1 = [], part2 = [], part3 = [];
			score[1].keys.remove(\bar)
			.do { |partName|
				switch(partName.asString.size,
					2, { part1 = part1.add(partName) },
					3, { part2 = part2.add(partName) },
					4, { part3 = part3.add(partName) }
				);
				partsInTheFirstBar = part1.sort ++ part2.sort ++ part3.sort
			}
		}.();

		scdfile << ("(\n");

		partsInTheFirstBar.do { |partKey|
			if (score[1][partKey][\lbl] == nil || (score[1][partKey][\lbl] == '')) {score[1][partKey][\lbl] = \Piano};
			header = header ++ "    <score-part id=" ++ q.(partKey.asString.toUpper) ++ ">\n" ++
			"      <part-name>" ++ score[1][partKey][\lbl] ++ "</part-name>\n" ++
			"    </score-part>\n";
			voiceEntryLastPerPart.put(partKey, ());
			dynamicsLastPerPart.put(partKey, ());
			durationsLastPerPart.put(partKey, ());
			octaveLastPerPart.put(partKey, ());
			articulationLastPerPart.put(partKey, ());
			partLables = partLables.add(score[1][partKey][\lbl])
		};

		partAttributesPerBarLast.put(\atr, (key: \temp, time: \temp, staves: \temp, clef: \temp, trans: \temp));

		header = header ++ "  </part-list>\n" ++
		"<!--=======================================================-->\n";

		musicXMLfile << (header.replace($', "&apos;"));
		header = "";

		itemsPerPartPerBar = "";

		renotatedVariable = $~ ++ scdfilePath.replace("~", "").replace(".", "_").replace("-", "_").basename.splitext[0][0].toLower ++ scdfilePath.basename.splitext[0][1..].replace("-", "_").replace(" ", "_");

		scdfile <<  (renotatedVariable + "= ( // score start\n");
		partsInTheFirstBar.do { |partKey, partIndex|
			var voiceIDLast, durationSum,
			thisVoiceDynamicLast, thisVoiceDurationLast, thisVoiceOctaveLast, thisVoiceArticulationLast,
			thisVoiceEntryLast, thisPartDynamic;

			("\n==================================================\n@ partKey:" + partKey + partKey.class).postln;

			itemsPerPartPerBar = itemsPerPartPerBar ++ "  <part id=" ++ q.(partKey.asString.toUpper) ++ ">\n";

			scdfile << ("\t'" ++ score[1][partKey][\lbl] ++ "': ( // part start\n");

			(1 .. score.size - 1).do { |barIndex|
				var itemsPerBar, partItemsPerBar, partAttributesPerBar, voiceIDs, splitAt, pitches_accidentals;

				scdfile << ("\t\t" ++ barIndex ++ ": ( // bar" + barIndex + "start\n");

				("==================================================\n\t@ bar:" + barIndex).post;
				("\t@ part:" + partKey).postln;

				itemsPerBar = score[barIndex];
				partItemsPerBar = itemsPerBar[partKey];
				partItemsPerBar.keys.asArray.do { |item|
					if (item.asString[0] == $v) {
						voiceIDs = voiceIDs.add(item)
					}
				};
				partAttributesPerBar = partItemsPerBar[\atr];


				("\n\t\tpartAttributesPerBar:" + partAttributesPerBar).postln;

				if (partAttributesPerBar == nil) {
					if (barIndex < 2) {
						partAttributesPerBar = (key: [0, \none], time: \x, staves: 4, clef: [[\g, 2, 2], [\g, 2], [\f, 4], [\f, 4, -2]], trans: [0, 0, 0]);
					} {
						partAttributesPerBar = partAttributesPerBarLast[\atr]
					}
				};
				("\n\t\t- partAttributesPerBarLast:\n\t\t  " + partAttributesPerBarLast).postln;

				("(partAttributesPerBar[\\" ++ "key] != partAttributesPerBarLast[\\atr][\\" ++ "key])" + (partAttributesPerBar[\key] != partAttributesPerBarLast[\atr][\key])).postln;
				("partAttributesPerBar[\\" ++ "key]:" + partAttributesPerBar[\key] ++ "\n").postln;
				("(partAttributesPerBar[\\" ++ "time] != partAttributesPerBarLast[\\atr][\\" ++ "time])" + (partAttributesPerBar[\time] != partAttributesPerBarLast[\atr][\time])).postln;
				("partAttributesPerBar[\\" ++ "time]:" + partAttributesPerBar[\time] ++ "\n").postln;
				("(partAttributesPerBar[\\" ++ "staves] != partAttributesPerBarLast[\\atr][\\" ++ "staves])" + (partAttributesPerBar[\staves] != partAttributesPerBarLast[\atr][\staves])).postln;
				("partAttributesPerBar[\\" ++ "staves]:" + partAttributesPerBar[\staves] ++ "\n").postln;
				("(partAttributesPerBar[\\" ++ "clef] != partAttributesPerBarLast[\\atr][\\" ++ "clef])" + (partAttributesPerBar[\clef] != partAttributesPerBarLast[\atr][\clef])).postln;
				("partAttributesPerBar[\\" ++ "clef]:" + partAttributesPerBar[\clef] ++ "\n").postln;
				("(partAttributesPerBar[\\" ++ "trans] != partAttributesPerBarLast[\\atr][\\" ++ "trans])" + (partAttributesPerBar[\trans] != partAttributesPerBarLast[\atr][\trans])).postln;
				("partAttributesPerBar[\\" ++ "trans]:" + partAttributesPerBar[\trans] ++ "\n").postln;

				if (partAttributesPerBar[\key] == nil) {
					partAttributesPerBar[\key] = partAttributesPerBarLast[\atr][\key]
				};
				("\t\t- partAttributesPerBarLast[\\"++"atr][\\"++"key]:" + partAttributesPerBarLast[\atr][\key]).postln;

				if (partAttributesPerBar[\time] == nil) {
					partAttributesPerBar[\time] = partAttributesPerBarLast[\atr][\time]
				};
				("\t\t- partAttributesPerBarLast[\\"++"atr][\\"++"time]:" + partAttributesPerBarLast[\atr][\time]).postln;

				if (partAttributesPerBar[\staves] == nil) {
					partAttributesPerBar[\staves] = partAttributesPerBarLast[\atr][\staves]
				};
				("\t\t- partAttributesPerBarLast[\\"++"atr][\\"++"staves]:" + partAttributesPerBarLast[\atr][\staves]).postln;

				if (partAttributesPerBar[\clef] == nil) {
					partAttributesPerBar[\clef] = partAttributesPerBarLast[\atr][\clef]
				};
				("\t\t- partAttributesPerBarLast[\\"++"atr][\\"++"clef]:" + partAttributesPerBarLast[\atr][\clef]).postln;

				if (partAttributesPerBar[\trans] == nil) {
					partAttributesPerBar[\trans] = partAttributesPerBarLast[\atr][\trans]
				};
				("\t\t- partAttributesPerBarLast[\\"++"atr][\\"++"trans]:" + partAttributesPerBarLast[\atr][\trans]).postln;

				itemsPerPartPerBar = itemsPerPartPerBar ++ "    <measure number=" ++ q.(barIndex) ++ ">\n" ++
				"      <attributes>\n" ++
				"        <divisions>" ++ div ++"</divisions>\n" ++

				if (partAttributesPerBar[\key] != partAttributesPerBarLast[\atr][\key]) {
					"        <key>\n" ++
					"          <fifths>" ++ partAttributesPerBar[\key][0] ++ "</fifths>\n" ++
					"          <mode>" ++ partAttributesPerBar[\key][1] ++ "</mode>\n" ++
					"        </key>\n"
				} {
					""
				} ++

				if (partAttributesPerBar[\time] != partAttributesPerBarLast[\atr][\time]) {
					"        <time>\n" ++
					if ( partAttributesPerBar[\time] == \x) {
						"          <senza-misura/>\n"
					} {
						"          <beats>" ++ partAttributesPerBar[\time][0] ++ "</beats>\n" ++
						"          <beat-type>" ++ partAttributesPerBar[\time][1] ++ "</beat-type>\n"
					} ++
					"        </time>\n"
				} {
					""
				} ++

				if (partAttributesPerBar[\staves] != partAttributesPerBarLast[\atr][\staves]) {
					"        <staves>" ++ partAttributesPerBar[\staves] ++ "</staves>\n"
				} {
					""
				};

				pitches_accidentals = switch (partAttributesPerBar[\key][0],
					7,     { (c: 1.0, d: 1.0, e: 1.0, f: 1.0, g: 1.0, a: 1.0, b: 1.0) },
					6,     { (c: 1.0, d: 1.0, e: 1.0, f: 1.0, g: 1.0, a: 1.0, b: 0.0) },
					5,     { (c: 1.0, d: 1.0, e: 0.0, f: 1.0, g: 1.0, a: 1.0, b: 0.0) },
					4,     { (c: 1.0, d: 1.0, e: 0.0, f: 1.0, g: 1.0, a: 0.0, b: 0.0) },
					3,     { (c: 1.0, d: 0.0, e: 0.0, f: 1.0, g: 1.0, a: 0.0, b: 0.0) },
					2,     { (c: 1.0, d: 0.0, e: 0.0, f: 1.0, g: 0.0, a: 0.0, b: 0.0) },
					1,     { (c: 0.0, d: 0.0, e: 0.0, f: 1.0, g: 0.0, a: 0.0, b: 0.0) },
					0,     { (c: 0.0, d: 0.0, e: 0.0, f: 0.0, g: 0.0, a: 0.0, b: 0.0) },
					\none, { (c: 0.0, d: 0.0, e: 0.0, f: 0.0, g: 0.0, a: 0.0, b: 0.0) },
					-1,    { (c: 0.0, d: 0.0, e: 0.0, f: 0.0, g: 0.0, a: 0.0, b: -1.0) },
					-2,    { (c: 0.0, d: 0.0, e: -1.0, f: 0.0, g: 0.0, a: 0.0, b: -1.0) },
					-3,    { (c: 0.0, d: 0.0, e: -1.0, f: 0.0, g: 0.0, a: -1.0, b: -1.0) },
					-4,    { (c: 0.0, d: -1.0, e: -1.0, f: 0.0, g: 0.0, a: -1.0, b: -1.0) },
					-5,    { (c: 0.0, d: -1.0, e: -1.0, f: 0.0, g: -1.0, a: -1.0, b: -1.0) },
					-6,    { (c: -1.0, d: -1.0, e: -1.0, f: 0.0, g: -1.0, a: -1.0, b: -1.0) },
					-7,    { (c: -1.0, d: -1.0, e: -1.0, f: -1.0, g: -1.0, a: -1.0, b: -1.0) }
				);

				switch(partAttributesPerBar[\staves],
					2, { splitAt = [4] },
					4, { splitAt = (9: 1, 8: 1, 7: 1, 6: 1, 5: 2, 4: 2, 3: 3, 2: 3, 1: 4, 0: 4, -1: 4) }
				);

				if (partAttributesPerBar[\clef] != partAttributesPerBarLast[\atr][\clef]) {
					partAttributesPerBar[\clef].do { |subkeys, index|
						itemsPerPartPerBar = itemsPerPartPerBar ++
						"        <clef number=" ++ q.((index + 1).asString) ++ ">\n" ++
						"          <sign>" ++ subkeys[0].asString.toUpper ++ "</sign>\n" ++
						"          <line>" ++ subkeys[1] ++ "</line>\n" ++
						(
							if (subkeys.size == 3) {
								"          <clef-octave-change>" ++ subkeys[2] ++ "</clef-octave-change>\n"
							} {
								""
							}
						) ++
						"        </clef>\n";
					}
				};

				if (partAttributesPerBar[\trans] != partAttributesPerBarLast[\atr][\trans]) {
					if (partAttributesPerBar[\trans] != nil) {
						itemsPerPartPerBar = itemsPerPartPerBar ++
						"        <transpose>\n" ++
						"          <diatonic>" ++ partAttributesPerBar[\trans][0]  ++ "</diatonic>\n" ++
						"          <chromatic>" ++ partAttributesPerBar[\trans][1] ++"</chromatic>\n" ++
						"          <octave-change>" ++ partAttributesPerBar[\trans][2] ++ "</octave-change>\n" ++
						"        </transpose>\n"
					}
				};

				itemsPerPartPerBar = itemsPerPartPerBar ++ "      </attributes>\n";

				itemsPerPartPerBar = if (barIndex != 1) {
					itemsPerPartPerBar.replace("      <attributes>\n" ++
						"        <divisions>" ++ div ++ "</divisions>\n" ++ "      </attributes>\n", "")
				} {
					itemsPerPartPerBar
				};

				partAttributesPerBarLast.put(\atr, partAttributesPerBar);

				musicXMLfile << (itemsPerPartPerBar);
				itemsPerPartPerBar = "";

				"\t\t------------------------------------------".postln;
				"\t\t@ bar partAttributesPerBar registration finished.".postln;
				"\t\t------------------------------------------".postln;

				voiceIDs.sort.do { |thisVoiceID, thisVoiceIDIndex|

					var thisVoiceEntries = partItemsPerBar[thisVoiceID],
					actualNotesHistoryThisVoiceThisBar = [],
					normalNotesHistoryThisVoiceThisBar = [], normalTypeHistoryThisVoiceThisBar = [];

					("\n\t\t- voiceEntryLastPerPart:" + voiceEntryLastPerPart).postln;

					("\t\t\t- thisVoiceDynamicLast:" + thisVoiceDynamicLast).postln;
					if (dynamicsLastPerPart[partKey][thisVoiceID] == nil) {
						dynamicsLastPerPart[partKey].put(thisVoiceID, \mp)
					};
					("\t\t\t- dynamicsLastPerPart:\n\t\t " + dynamicsLastPerPart).postln;

					("\t\t\t- thisVoiceDurationLast:" + thisVoiceDurationLast).postln;
					if (durationsLastPerPart[partKey][thisVoiceID] == nil) {
						thisVoiceDurationLast = durationsLastPerPart[partKey].put(thisVoiceID, \6)
					};
					("\t\t\t- thisVoiceDurationLast (updated):" + thisVoiceDurationLast).postln;
					("\t\t\t- durationsLastPerPart:\n\t\t " + durationsLastPerPart).postln;

					("\t\t\t- thisVoiceOctaveLast:" + thisVoiceOctaveLast).postln;
					if (octaveLastPerPart[partKey][thisVoiceID] == nil) {
						thisVoiceOctaveLast = octaveLastPerPart[partKey].put(thisVoiceID, 4)
					};
					("\t\t\t- thisVoiceOctaveLast (updated):" + thisVoiceOctaveLast).postln;
					("\t\t\t- octaveLastPerPart:\n\t\t " + octaveLastPerPart).postln;

					("\t\t\t- thisVoiceArticulationLast:" + thisVoiceArticulationLast).postln;
					if (articulationLastPerPart[partKey][thisVoiceID] == nil) {
						thisVoiceArticulationLast = articulationLastPerPart[partKey].put(thisVoiceID, \noArticulation)
					};
					("\t\t\t- thisVoiceArticulationLast (updated):" + thisVoiceArticulationLast).postln;
					("\t\t\t- octaveLastPerPart:\n\t\t " + octaveLastPerPart).postln;

					thisVoiceEntries = Notator.stringToArray(thisVoiceEntries);

					thisVoiceEntries.removeAllSuchThat { |item| item.asString.size == 0 };

					scdfile << ("\t\t\t" ++ thisVoiceID ++ ": [ // voice start\n");

					thisVoiceEntries.do { |thisEntry, index|
						var parseStringAndSymbol, thisEntryIsTempo, thisEntryIsRepetition, thisEntryIsRepetitive,
						thisEntryIsDynamic, singleMIDINoteChecker, thisEntryIsSingleNote, thisEntryIsChord,
						thisEntryIsRest, thisEntryTemporaryUpdated, thisAccidental;

						("\n\t----------------------------------------------\n\t\t@ bar:" + barIndex + "\t@ part:" + partKey).postln;
						("\t\t@ thisVoice:" + thisVoiceID).postln;
						("\t\t@ thisEntry index" + index ++ ":" + thisEntry + thisEntry.class).postln;
						("\t----------------------------------------------").postln;

						thisVoiceEntryLast = voiceEntryLastPerPart[partKey][thisVoiceID];
						("\t\t- thisVoiceEntryLast (from the previous one):" + thisVoiceEntryLast).postln;

						thisVoiceOctaveLast = octaveLastPerPart[partKey][thisVoiceID];
						("\t\t- thisVoiceOctaveLast (from the previous one):" + thisVoiceOctaveLast).postln;

						thisVoiceArticulationLast = articulationLastPerPart[partKey][thisVoiceID];
						("\t\t- thisVoiceArticulationLast (from the previous one):" + thisVoiceArticulationLast).postln;

						thisEntry = Notator.parseStringAndSymbol(thisEntry);

						("\n\t\t@ parsed thisEntry index" + index ++ ":" + thisEntry + thisEntry.class).postln;
						thisEntry.do{ |item| [item.asSymbol, item.class].postln };

						thisEntryIsTempo = thisEntry[0] == \t;
						thisEntryIsDynamic = (dynamicMIDIvelocityPairs[dynamicTranscriber.(thisEntry[0])] != nil && (thisEntry.size == 1));
						thisEntryIsRepetition = thisEntry[0].asSymbol == '\\';
						thisEntryIsRepetitive = thisEntry[0].asSymbol == '|';

						(
							case
							{ thisEntryIsTempo } { // This entry is a tempo entry.
								var noteType = noteTypeTranscriber.(thisEntry[1]), beatUnitPair = noteTypeDurationPairs[noteType],
								bpm = thisEntry[2], bpmNormalised = bpm * beatUnitPair[1] / div, hasADot = noteType.asString.contains("d");
								("\t\t\t\tThis entry is a tempo entry. bpm:" + bpm ++"; beatUnitPair:" + beatUnitPair ++ "(div)").postln;
								itemsPerPartPerBar = itemsPerPartPerBar ++
								"      <sound tempo=" ++ q.(bpmNormalised) ++ "/>\n" ++ // necessary?
								"      <direction placement=" ++ q.("above") + "directive=" ++ q.("yes")++">\n" ++
								"        <direction-type>\n" ++
								"          <metronome>\n" ++
								"            <beat-unit>" ++ beatUnitPair[0] ++ "</beat-unit>\n" ++
								(if (hasADot) {"            <beat-unit-dot/>\n"} {""}) ++
								"            <per-minute>" ++ bpm ++ "</per-minute>\n" ++
								"          </metronome>\n" ++
								"      </direction-type>\n" ++
								"      </direction>\n";
								scdfile << (
									"\t\t\t\t(tempo: (beatUnit: \\" ++ beatUnitPair[0] ++ ", beatUnitNumDot:" + (if(hasADot) { 1 } { 0 }) ++ ", bpm:" + bpm ++ ")),\n" ++
									"\t\t\t\t(tempoNormalised: (beatUnit: \\quarter" ++ ", bpm:" + bpmNormalised ++ ")),\n"
								)
							}
							{ thisEntryIsDynamic } { // This entry is a dynamic entry.
								("\t\t\t\tThis entry is a dynamic entry.").postln;
								("\t\t- thisVoiceDynamicLast:" + thisVoiceDynamicLast).postln;
								thisPartDynamic = thisEntry[0];
								("\t\t- thisEntry:" + thisPartDynamic).postln;
								thisPartDynamic = dynamicTranscriber.(thisPartDynamic);
								("\t\t  modified thisEntry:" + thisPartDynamic).postln;
								itemsPerPartPerBar = itemsPerPartPerBar ++
								"      <direction>\n" ++
								"        <direction-type>\n" ++
								"          <dynamics" /*++ default-y=" ++ q.("275")*/ ++ ">\n" ++
								"            <" ++ thisPartDynamic ++ "/>\n" ++
								"          </dynamics>\n" ++
								"        </direction-type>\n" ++
								"        <sound dynamics=" ++ q.(dynamicMIDIvelocityPairs[thisPartDynamic]) ++ "/>\n" ++
								"      </direction>\n";
								scdfile << ("\t\t\t\t(dynamicForPart: \\" ++ thisPartDynamic ++ "),\n");
								thisVoiceDynamicLast = dynamicsLastPerPart[partKey].put(thisVoiceID, thisPartDynamic);
								("\t\t- updated thisVoiceDynamicLast:" + thisVoiceDynamicLast).postln;
								("\t\t- dynamicsLastPerPart:\n\t\t " + dynamicsLastPerPart).postln;
							}
							{ thisEntryIsRepetition } { // This entry is a repetition of the previous one.
								("thisVoiceEntryLast:" + thisVoiceEntryLast).postln;
								("thisEntry[0]:" + thisEntry[0]).postln;
								thisEntry = thisVoiceEntryLast;
								("\t\t\t\tThis entry is a repetition of the previous one.\n").postln;
								("\t\t\t\tmodified Entry" ++ thisEntry ++ "\n").postln
							}
							{ thisEntryIsRepetitive } {
								("thisVoiceEntryLast:" + thisVoiceEntryLast).postln;
								("\t\t\t\tthisEntry[0]:" + thisEntry[0]).postln;
								thisEntry[0] = thisVoiceEntryLast[0];
								("\t\t\t\tThis pitch is the same as the pitch of the previous entry.\n").postln;
								("\t\t\t\tmodified Entry[0]:" + thisEntry[0] ++ "\n").postln;
								("\t\t\t\tmodified Entry:" + thisEntry ++ "\n").postln
							}
						);

						singleMIDINoteChecker = { |element = (thisEntry[0].class.asString)|
							"(Integer|Float)".matchRegexp(element)
						};

						thisEntryIsSingleNote = ((97..103).includes(thisEntry[0].asString[0].ascii));

						thisEntryIsChord = (thisEntry[0].size != 0);

						thisEntryIsRest = (thisEntry[0].asString[0].ascii == 114);


						if (thisEntryIsSingleNote||singleMIDINoteChecker.()||thisEntryIsChord||thisEntryIsRest) {
							var duration_Dynamic_Articulation_Tie_Slur_Candidate,
							dynamic_Articulation_Tie_Slur_Candidate, articulation_Tie_Slur_Candidate,
							tie_Slur_Candidate, slur_Candidate,
							thisPitches, thisPitchClass, thisPitchAlteration, thisOctave, thisPitchPerform, thisMIDIPerfrom, thisDuration,
							thisNoteType, thisNoteTypeAndDuration,
							tupletTimeRegister, tupletNotationRegister, tupletDurationCalculator,
							thisDynamic, thisArticulation, thisTie, thisSlur;

							duration_Dynamic_Articulation_Tie_Slur_Candidate = thisEntry[1].asSymbol;
							dynamic_Articulation_Tie_Slur_Candidate = if (thisEntry[2] != nil) { thisEntry[2] }.asSymbol;
							articulation_Tie_Slur_Candidate         = if (thisEntry[3] != nil) { thisEntry[3] }.asSymbol;
							tie_Slur_Candidate                      = if (thisEntry[4] != nil) { thisEntry[4] }.asSymbol;
							slur_Candidate                          = if (thisEntry[5] != nil) { thisEntry[5] }.asSymbol;

							thisVoiceDurationLast = durationsLastPerPart[partKey][thisVoiceID];
							thisVoiceDynamicLast = dynamicsLastPerPart[partKey][thisVoiceID];
							("\t\t- thisEntry[0]:" + thisEntry[0]).postln;

							thisPitches = (
								case
								{ thisEntryIsRest } {
									("\t\t\t\tThis entry is a rest.").postln;

									("thisVoiceEntryLast == nil:" + (thisVoiceEntryLast == nil)).postln;
									if (thisVoiceEntryLast == nil) {
										thisVoiceEntryLast = [\a4]
									} {
										("thisVoiceEntryLast[0] == \t:" + (thisVoiceEntryLast[0] == \t)).postln;
										("(dynamicMIDIvelocityPairs[thisVoiceEntryLast[0]] != nil)" + (dynamicMIDIvelocityPairs[thisVoiceEntryLast[0]] != nil)).postln;
										if (
											thisVoiceEntryLast[0] == \t
											||
											(dynamicMIDIvelocityPairs[thisVoiceEntryLast[0]] != nil)
										) {
											("thisVoiceEntryLast[0] == \t:" + (thisVoiceEntryLast[0] == \t)).postln;
											("(dynamicMIDIvelocityPairs[thisVoiceEntryLast[0]] != nil):" +
												(dynamicMIDIvelocityPairs[thisVoiceEntryLast[0]] != nil)).postln;
											thisVoiceEntryLast = [\a4]
										}
									};
									("thisVoiceEntryLast" + thisVoiceEntryLast).postln;

									thisEntryTemporaryUpdated = (

										("thisVoiceEntryLast[0].size == 0:" + (thisVoiceEntryLast[0].size == 0)).postln;
										if (thisVoiceEntryLast[0].size == 0)  {
											(thisEntry[0] ++ thisVoiceEntryLast[0].asString[1..]).postln
										} { var result;
											("thisVoiceEntryLast[0] != 0" + (thisVoiceEntryLast[0] != 0)).postln;
											(
												"thisVoiceEntryLast[0]:" + thisVoiceEntryLast[0] +
												thisVoiceEntryLast[0].class
											).postln;
											if (singleMIDINoteChecker.(thisVoiceEntryLast[0].class.asString)) {
												"thisVoiceEntryLast[0].class is integer or float".postln;
												result = midiPitchNumber2Pitch[thisVoiceEntryLast[0].asFloat.asSymbol]
											} { result = thisVoiceEntryLast[0] };

											("result.size == 0:" + (result.size == 0)).postln;

											if (result.size == 0) {
												("result:" + result).postln;
												(thisEntry[0] ++ result.asString[1..]).postln
											} {
												("result[result.size - 1]:" + result[result.size - 1]).postln;
												if (singleMIDINoteChecker.(result[result.size - 1].class.asString)) {
													singleMIDINoteChecker.(result[result.size - 1].class.asString).postln;
													("midiPitchNumber2Pitch[result[result.size - 1].asFloat]:" + midiPitchNumber2Pitch[result[result.size - 1].asFloat.asSymbol]).postln;
													(thisEntry[0] ++ midiPitchNumber2Pitch[result[result.size - 1].asFloat.asSymbol].asString[1..]).postln
												} {
													singleMIDINoteChecker.(result[result.size - 1].class.asString).postln;
													(thisEntry[0] ++ result[result.size-1].asString[1..]).postln
												}
											}
										}
									);
									[thisEntryTemporaryUpdated]
								}
								{ thisEntryIsSingleNote /*&& (thisEntry[0] != \F)*/} {
									("\t\t\t\tThis entry is a single note.").postln;
									[thisEntry[0]]
								}
								{ singleMIDINoteChecker.() } {
									("\t\t\t\tThis entry is a single MIDI note.").postln;
									[midiPitchNumber2Pitch[thisEntry[0].asFloat.asSymbol]];
								}
								{ thisEntryIsChord } {
									("\t\t\t\tThis entry is a chord.").postln;
									(
										"\t\t\t\tThe MIDI pitch number is transcribed into the" ++
										" scientific pitch notation."
									).postln;
									thisEntry[0].postln;
									thisEntryTemporaryUpdated = thisEntry[0].collect { |aNote|
										if (singleMIDINoteChecker.(aNote.class.asString)) {
											aNote = aNote.asFloat.asSymbol;
											midiPitchNumber2Pitch[aNote]
										} {
											aNote
										}
									};
								}
							);

							("\t\t\t\t- thisPitches:" + (thisPitches.asString + thisPitches.class)).postln;

							tupletTimeRegister = { |nestedTupletOrNot, depth = 1|

								"\n\t\t\t\t\ttupletTimeRegister".postln;
								"\t\t\t\t\t(".postln;
								("\t\t\t\t\t- nestedTupletOrNot:" + nestedTupletOrNot).postln;
								("\t\t\t\t\t- nestedTupletOrNot[0] size:" + nestedTupletOrNot[0].size).postln;
								(
									"\t\t\t\t\t- actualNotesHistoryThisVoiceThisBar:" +
									actualNotesHistoryThisVoiceThisBar
								).postln;
								(
									"\t\t\t\t\t- normalNotesHistoryThisVoiceThisBar:" +
									normalNotesHistoryThisVoiceThisBar
								).postln;
								(
									"\t\t\t\t\t- normalTypeHistoryThisVoiceThisBar:" +
									normalTypeHistoryThisVoiceThisBar
								).postln;

								if (nestedTupletOrNot[0].size != 0) {
									"\n\t\t\t\t\t- This is a nested tuplet and will be re-parsed.".postln;
									depth = depth + 1;
									//normalTypeHistoryThisVoiceThisBar.insert(depth - 1, 0);
									tupletTimeRegister.(nestedTupletOrNot[0], depth)
								} {
									"\t\t\t\t\t- This is not a nested tuplet. @tupletTimeRegister".postln;
									("depth:" + depth).postln;
									itemsPerPartPerBar = itemsPerPartPerBar ++
									"        <time-modification>\n"
									"          <actual-notes>" ++
									actualNotesHistoryThisVoiceThisBar[0 .. depth - 1].product ++
									"</actual-notes>\n" ++
									"          <normal-notes>" ++
									normalNotesHistoryThisVoiceThisBar[0 .. depth - 1].product ++
									"</normal-notes>\n" ++
									"          <normal-type>" ++
									//noteTypeDurationPairs[noteTypeTranscriber.(nestedTupletOrNot[0])][0] ++
									noteTypeDurationPairs[noteTypeTranscriber
										.(normalTypeHistoryThisVoiceThisBar[depth - 1])][0] ++
									"</normal-type>\n" ++
									"        </time-modification>\n";
								};
								"\t\t\t\t\t)\n".postln;
							};

							tupletNotationRegister = { |nestedTupletOrNot, depth = 1|
								"\n\t\t\t\t\ttupletNotationRegister".postln;
								"\t\t\t\t\t(".postln;
								("\t\t\t\t\t- nestedTupletOrNot:" + nestedTupletOrNot).postln;
								("\t\t\t\t\t- nestedTupletOrNot[0] size:" + nestedTupletOrNot[0].size).postln;

								if (nestedTupletOrNot[0].size != 0) {
									"\n\t\t\t\t\t- This is a nested tuplet and will be re-parsed.".postln;
									depth = depth +1;
									tupletNotationRegister.(nestedTupletOrNot[0], depth)
								} {
									"\t\t\t\t\t- This is not a nested tuplet. @tupletNotationRegister".postln;
									switch(nestedTupletOrNot.size,
										4, {
											itemsPerPartPerBar = itemsPerPartPerBar ++
											"        <notations>\n" ++
											"          <tuplet type=" ++ q.("start") + "bracket=" ++ q.("yes") +
											"number=" ++
											q.(depth) ++ ">\n" ++
											"            <tuplet-actual>\n" ++
											"              <tuplet-number>" ++
											actualNotesHistoryThisVoiceThisBar[depth - 1] ++
											"</tuplet-number>\n" ++
											"              <tuplet-type>" ++
											noteTypeDurationPairs[noteTypeTranscriber.(nestedTupletOrNot[3])][0]  ++
											"</tuplet-type>\n" ++
											"            </tuplet-actual>\n" ++
											"            <tuplet-normal>\n" ++
											"              <tuplet-number>" ++
											normalNotesHistoryThisVoiceThisBar[depth - 1] ++
											"</tuplet-number>\n" ++
											"              <tuplet-type>" ++
											noteTypeDurationPairs[noteTypeTranscriber.(nestedTupletOrNot[3])][0] ++
											"</tuplet-type>\n" ++
											"            </tuplet-normal>\n" ++
											"          </tuplet>\n" ++
											"        </notations>\n"
										},
										3, {
											itemsPerPartPerBar = itemsPerPartPerBar ++
											"        <notations>\n" ++
											"          <tuplet type=" ++ q.("start") + "bracket=" ++ q.("yes") +
											"number=" ++
											q.(depth) ++ ">\n" ++
											"            <tuplet-actual>\n" ++
											"              <tuplet-number>" ++
											actualNotesHistoryThisVoiceThisBar[depth - 1] ++
											"</tuplet-number>\n" ++
											"              <tuplet-type>" ++
											noteTypeDurationPairs[noteTypeTranscriber.(nestedTupletOrNot[0])][0]  ++
											"</tuplet-type>\n" ++
											"            </tuplet-actual>\n" ++
											"            <tuplet-normal>\n" ++
											"              <tuplet-number>" ++
											normalNotesHistoryThisVoiceThisBar[depth - 1] ++
											"</tuplet-number>\n" ++
											"              <tuplet-type>" ++
											noteTypeDurationPairs[noteTypeTranscriber.(nestedTupletOrNot[0])][0] ++
											"</tuplet-type>\n" ++
											"            </tuplet-normal>\n" ++
											"          </tuplet>\n" ++
											"        </notations>\n"
										},
										2, {
											itemsPerPartPerBar = itemsPerPartPerBar ++
											"        <notations>\n" ++
											"          <tuplet type=" ++ q.("stop") + "bracket=" ++ q.("yes") +
											"number=" ++
											q.(depth) ++ "/>\n"
											++ "        </notations>\n"
										}
									)
								};
								"\t\t\t\t\t)".postln;
							};

							tupletDurationCalculator = { |noteType, nestedTupletOrNot, depth = 1|
								var duration;
								"\n\t\t\t\t\ttupletDurationCalculator".postln;
								"\t\t\t\t\t(".postln;
								("\t\t\t\t\t- nestedTupletOrNot:" + nestedTupletOrNot).postln;
								("\t\t\t\t\t- nestedTupletOrNot[0] size:" + nestedTupletOrNot[0].size).postln;
								(
									"\t\t\t\t\t- actualNotesHistoryThisVoiceThisBar:" +
									actualNotesHistoryThisVoiceThisBar
								).postln;
								(
									"\t\t\t\t\t- normalNotesHistoryThisVoiceThisBar:" +
									normalNotesHistoryThisVoiceThisBar
								).postln;
								(
									"\t\t\t\t\t- normalTypeHistoryThisVoiceThisBar:" +
									normalTypeHistoryThisVoiceThisBar
								).postln;

								if (nestedTupletOrNot[0].size != 0) {
									"\n\t\t\t\t\t- This is a nested tuplet and will be re-parsed.".postln;
									depth = depth + 1;
									duration = tupletDurationCalculator.(noteType, nestedTupletOrNot[0], depth);
									"\t\t\t\t\t)\n".postln;
									duration
								} {
									("\t\t\t\t\t- depth:" + depth).postln;

									(
										noteType + "/" + actualNotesHistoryThisVoiceThisBar[0 .. depth - 1].product
										+ "*" + normalNotesHistoryThisVoiceThisBar[0 .. depth - 1].product
									).postln;
									duration = noteType / actualNotesHistoryThisVoiceThisBar[0 .. depth - 1].product
									* normalNotesHistoryThisVoiceThisBar[0 .. depth - 1].product;
									"\t\t\t\t\t)\n".postln;
									duration
								};
							};

							musicXMLfile << (itemsPerPartPerBar);
							itemsPerPartPerBar = "";

							thisPitches.do { |thisPitch, noteIndex|

								("\n\t\t\t\t@ thisPitch index" + (noteIndex.asString ++ ":" + thisPitch + thisPitch.class ++ "\n")).postln;

								thisPitch = thisPitch.asString;

								thisPitch = if (thisPitch.size < 3) {
									if (thisPitch.size == 2) {
										if ("^[a-g][0-9]$".matchRegexp(thisPitch)) {
											thisPitch[0] ++ "n" ++ thisPitch[1]
										} {
											thisPitch[0] ++ thisPitch[1] ++ thisVoiceOctaveLast
										}
									} {
										thisPitch[0] ++ "n" ++ thisVoiceOctaveLast
									}
								} {
									thisPitch
								};

								thisOctave = if (thisPitch[thisPitch.size - 2] == $m) {
									thisPitch[thisPitch.size - 1].asSymbol.asInteger * -1
								} {
									if (thisPitch[thisPitch.size - 2] == $h) {
										thisPitch[thisPitch.size - 1].asSymbol.asInteger + 1
									} {
										if ("(m1_16|m1_8|m3_16|u|m5_16|m3_8|m7_16|m9_16|m5_8|m11_16|U|tqf|m13_16|m7_8|m15_16|ff|F|qf|qs|q|S|x|15_16|7_8|13_16|tqs|Q|11_16|5_8|9_16|7_16|3_8|5_16|3_16|1_8|1_16|n|s|f)$".matchRegexp(thisPitch)) {
											thisVoiceOctaveLast
										} {
											thisPitch[thisPitch.size - 1].asSymbol.asInteger
										}
									}
								};

								thisMIDIPerfrom = if (thisPitches.size == 0) {
									if (thisEntryIsRest.not) {
										if ("(_8|_16|s|q|u|U|S|X|h|Q|F|f|n|[a-g])$".matchRegexp(thisPitch)) {
											(thisPitch ++ thisOctave.asString).asSymbol.midi(\ez)
										} {
											thisPitch.asSymbol.midi(\ez)
										}
									}
								} {
									thisPitches.collect { |thisPitchesItem|
										if (thisEntryIsRest.not) {
											if ("(_8|_16|s|q|u|U|S|X|h|Q|F|f|n|[a-g])$".matchRegexp(thisPitchesItem.asString)  &&  thisEntryIsRest.not) {
												(thisPitchesItem ++ thisOctave.asString).asSymbol.midi(\ez)
											} {
												thisPitchesItem.postln;
												thisPitchesItem.asSymbol.midi(\ez).postln;
											}
										}
									}
								};

								thisPitchPerform = if (thisPitches.size == 0) {
									if ("(_8|_16|s|q|u|U|S|X|h|Q|F|f|n|[a-g])$".matchRegexp(thisPitch)) {
										"'" ++ thisPitch ++ thisOctave ++ "'"
									}{	"'" ++ thisPitch ++ "'"
									}
								} {
									thisPitches.collect { |thisPitchesItem|
										if ("(_8|_16|s|q|u|U|S|X|h|Q|F|f|n|[a-g])$".matchRegexp(thisPitchesItem.asString)) {
											"'" ++ thisPitchesItem ++ thisOctave ++ "'"
										} {
											"'" ++ thisPitchesItem ++ "'"
										}
									}
								};

								thisPitchAlteration = if (
									thisPitch[thisPitch.size - 2] == $m || (thisPitch[thisPitch.size - 2] == $h)
								) {
									thisPitch[1 .. thisPitch.size - 3]
								} {
									thisPitch[1 .. thisPitch.size - 2]
								};
								thisAccidental = case
								{ thisPitchAlteration.contains("m1_16")  } { "arrow-down" }
								{ thisPitchAlteration.contains("m1_8")   } { "natural-down" }
								{ thisPitchAlteration.contains("m3_16")  } { "" }
								{ thisPitchAlteration.contains("u")      } { "quarter-flat" }
								{ thisPitchAlteration.contains("m5_16")  } { "" }
								{ thisPitchAlteration.contains("m3_8")   } { "flat-up" }
								{ thisPitchAlteration.contains("m7_16")  } { "" }
								{ thisPitchAlteration.contains("m9_16")  } { "" }
								{ thisPitchAlteration.contains("m5_8")   } { "flat-down" }
								{ thisPitchAlteration.contains("m11_16") } { "" }
								{ thisPitchAlteration.contains("U")      } { "three-quarters-flat" }
								{ thisPitchAlteration.contains("tqf")    } { "three-quarters-flat" }
								{ thisPitchAlteration.contains("m13_16") } { "" }
								{ thisPitchAlteration.contains("m7_8")   } { "flat-flat-up" }
								{ thisPitchAlteration.contains("m15_16") } { "" }
								{ thisPitchAlteration.contains("ff")     } { "flat-flat" }
								{ thisPitchAlteration.contains("F")      } { "flat-flat" }
								{ thisPitchAlteration.contains("qf")     } { "quarter-flat" }
								{ thisPitchAlteration.contains("qs")     } { "quarter-sharp" }
								{ thisPitchAlteration.contains("q")      } { "quarter-sharp" }
								{ thisPitchAlteration.contains("S")      } { "double-sharp" }
								{ thisPitchAlteration.contains("x")      } { "double-sharp" }
								{ thisPitchAlteration.contains("15_16")  } { "" }
								{ thisPitchAlteration.contains("7_8")    } { "double-sharp-down" }
								{ thisPitchAlteration.contains("13_16")  } { "" }
								{ thisPitchAlteration.contains("tqs")    } { "three-quarters-sharp" }
								{ thisPitchAlteration.contains("Q")      } { "three-quarters-sharp" }
								{ thisPitchAlteration.contains("11_16")  } { "" }
								{ thisPitchAlteration.contains("5_8")    } { "sharp-up" }
								{ thisPitchAlteration.contains("9_16")   } { "" }
								{ thisPitchAlteration.contains("7_16")   } { "" }
								{ thisPitchAlteration.contains("3_8")    } { "sharp-down" }
								{ thisPitchAlteration.contains("5_16")   } { "" }
								{ thisPitchAlteration.contains("3_16")   } { "" }
								{ thisPitchAlteration.contains("1_8")    } { "natural-up" }
								{ thisPitchAlteration.contains("1_16")   } { "arrow-up" }
								{ thisPitchAlteration.contains("n")      } { "natural" }
								{ thisPitchAlteration.contains("s")      } { "sharp" }
								{ thisPitchAlteration.contains("f")      } { "flat" };

								thisPitchAlteration = thisPitchAlteration
								.replace("m1_16", -0.125)
								.replace("m1_8", -0.25)
								.replace("m3_16", -0.375)
								.replace("u", -0.5)
								.replace("m5_16", -0.625)
								.replace("m3_8", -0.75)
								.replace("m7_16", -0.875)
								.replace("m9_16", -1.125)
								.replace("m5_8", -1.25)
								.replace("m11_16", -1.375)
								.replace("U", -1.5)
								.replace("tqf", -1.625)
								.replace("m13_16", -1.75)
								.replace("m7_8", -1.5)
								.replace("m15_16", -1.875)
								.replace("ff", -2.0)
								.replace("F", -2.0)
								.replace("qf", -0.5)
								.replace("S", 2.0)
								.replace("x", 2.0)
								.replace("ss", 2.0)
								.replace("15_16", 1.875)
								.replace("7_8", 1.75)
								.replace("13_16", 1.625)
								.replace("Q", 1.5)
								.replace("tqs", 1.5)
								.replace("11_16", 1.375)
								.replace("5_8", 1.25)
								.replace("9_16", 1.125)
								.replace("7_16", 0.875)
								.replace("3_8", 0.75)
								.replace("5_16", 0.625)
								.replace("qs", 0.5)
								.replace("3_16", 0.375)
								.replace("1_8", 0.25)
								.replace("1_16", 0.125)
								.replace("n", 0.0)
								.replace("s", 1.0)
								.replace("q", 0.5)
								.replace("f", -1.0);

								thisPitchClass = thisPitch[0].toUpper;

								("\t\t\t\t\t- thisPitch:" + thisPitch + thisPitch.class).postln;
								("\t\t\t\t\t\t- thisPitchClass:" + thisPitchClass).postln;
								("\t\t\t\t\t\t- thisPitchAlteration:" + thisPitchAlteration + thisPitchAlteration.class).postln;
								("\t\t\t\t\t\t- thisPitchOctave:" + thisOctave + "; thisVoiceOctaveLast:" + thisVoiceOctaveLast).postln;
								octaveLastPerPart[partKey].put(thisVoiceID, thisOctave);

								("\n\t\t\t\t\t- note type detection:").postln;
								("\t\t\t\t\t\t- in the note type information:" + thisEntry[1] + thisEntry[1].class).postln;

								thisNoteType = (
									if (duration_Dynamic_Articulation_Tie_Slur_Candidate.size == 0) {
										if (noteTypeDurationPairs.keys.
											includes(
												noteTypeTranscriber.
												(
													dynamicTranscriber
													.(duration_Dynamic_Articulation_Tie_Slur_Candidate)
												)
											)
											.not
										) {
											("\t\t\t\t\t\t- This entry does not contain note type information."+
												"\n\t\t\t\t\t\t  The last duration of this voice is used.").postln;
											thisVoiceDurationLast
										} {
											"\t\t\t\t\t\t- This entry contains note type information".postln;
											noteTypeTranscriber.(duration_Dynamic_Articulation_Tie_Slur_Candidate)
										}
									} {
										if (
											duration_Dynamic_Articulation_Tie_Slur_Candidate.size != 0
											/*tuplet if an array*/
										) {
											var tupletParser = { |tupletElement, depth = 1|
												if (tupletElement[0].size != 0) {
													("\t\t\t\t\t\t- This entry has a nested tuplet. It will be reparsed.").postln;
													("tupletElement[0]:" + tupletElement[0]).postln;
													depth = depth + 1;
													tupletParser.(tupletElement[0], depth)
												} {
													if (noteIndex == 0) {
														switch (tupletElement.size,
															4, {
																"\t\t\t\t\t\t- This is a start of" ++
																" a tuplet group.".postln;
																actualNotesHistoryThisVoiceThisBar =
																actualNotesHistoryThisVoiceThisBar
																.insert(depth - 1, tupletElement[1]);
																normalNotesHistoryThisVoiceThisBar =
																normalNotesHistoryThisVoiceThisBar
																.insert(depth - 1, tupletElement[2]);
																normalTypeHistoryThisVoiceThisBar =
																normalTypeHistoryThisVoiceThisBar
																.insert(depth - 1, tupletElement[3])
															},
															3, {
																"\t\t\t\t\t\t- This is a start of" ++
																" a tuplet group.".postln;
																actualNotesHistoryThisVoiceThisBar =
																actualNotesHistoryThisVoiceThisBar
																.insert(depth - 1, tupletElement[1]);
																normalNotesHistoryThisVoiceThisBar =
																normalNotesHistoryThisVoiceThisBar
																.insert(depth - 1, tupletElement[2]);
																normalTypeHistoryThisVoiceThisBar =
																normalTypeHistoryThisVoiceThisBar
																.insert(depth - 1, tupletElement[0])
															},
															2, {
																"\t\t\t\t\t\t- This is an end of" ++
																" a tuplet group.".postln;
																/*
																actualNotesHistoryThisVoiceThisBar
																.removeAt(depth - 1);
																normalNotesHistoryThisVoiceThisBar
																.removeAt(depth - 1);
																normalTypeHistoryThisVoiceThisBar
																.removeAt(depth - 1)
																*/
															},
															1, {
																"\t\t\t\t\t\t- This is a middle of" ++
																" a tuplet group.".postln;
															}
														)
													};
													noteTypeTranscriber.(tupletElement[0])
												}
											};
											tupletParser = tupletParser
											.(duration_Dynamic_Articulation_Tie_Slur_Candidate);
											noteTypeKeyFinder.(noteTypeDurationPairs[tupletParser][1])
										}
									}
								);

								("\t\t- durationsLastPerPart:\n\t\t " + durationsLastPerPart).postln;
								("\t\t- durationsLastInThisPart:" + partKey + durationsLastPerPart[partKey]).postln;
								("\t\t- thisVoiceDurationLast:" + thisVoiceDurationLast).postln;

								("\t\t\t\t\t- duration_Dynamic_Articulation_Tie_Slur_Candidate:" +
									duration_Dynamic_Articulation_Tie_Slur_Candidate).postln;
								("\t\t\t\t\t- duration_Dynamic_Articulation_Tie_Slur_Candidate.size:" +
									duration_Dynamic_Articulation_Tie_Slur_Candidate.size).postln;

								durationsLastPerPart[partKey].put(thisVoiceID, thisNoteType);
								("\t\t\t\t\t- thisNoteType:" + thisNoteType + thisNoteType.class).postln;
								thisNoteTypeAndDuration = noteTypeDurationPairs[thisNoteType];

								thisDuration = if(duration_Dynamic_Articulation_Tie_Slur_Candidate.size == 0) {
									"\t\t\t\t\t- This is not a tuplet.".postln;
									thisNoteTypeAndDuration[1]
								} {
									"\t\t\t\t\t- This is a tuplet.".postln;
									("\t\t\t\t\t  ThisNoteTypeAndDuration" + thisNoteTypeAndDuration +
										"will be recalculated.").postln;
									tupletDurationCalculator
									.(thisNoteTypeAndDuration[1], duration_Dynamic_Articulation_Tie_Slur_Candidate)
								}
								.asInteger;

								"\t\t\t\t\t- thisNoteTypeAndDuration (tuplet duration applied if any):".postln;
								("\t\t\t\t\t " + thisNoteTypeAndDuration).postln;

								("\t\t- backup information for the next voice:").postln;
								("\t\t\t- thisVoiceID:" + thisVoiceID ++ "; voiceIDLast: " + voiceIDLast).postln;

								if (noteIndex == 0) {

									"\t\t\t- noteIndex == 0".postln;

									if (thisVoiceID != voiceIDLast) {

										"\t\t\t- thisVoiceID != voiceIDLast".postln;

										if (durationSum != 0) {

											"\t\t\t- durationSum != 0".postln;

											("\t\t\t- durationSum:" + durationSum +
												"; durationSum.class:" + durationSum.class).postln;

											if (durationSum.class.asSymbol != \Nil) {

												"\t\t\t- durationSum != nil".postln;

												itemsPerPartPerBar = itemsPerPartPerBar ++
												"      <backup>\n" ++
												"        <duration>" ++ durationSum ++ "</duration>\n" ++
												"      </backup>\n";
												("\t\t\t- The backup in the voiceID" + voiceIDLast +
													durationSum + "is applied for the voiceID" +
													thisVoiceID ++ ".").postln;
												durationSum = thisDuration;
												("\t\t\t- The previous backup in the" + thisVoiceID
													+ "is nullified").postln;
											} {
												"\t\t\t- durationSum == nil".postln;
												("\t\t\t- The previous backup was" + durationSum +
													"in the voiceID" +  thisVoiceID).postln;

												durationSum = thisDuration;
												("\t\t\t- The new backup in the voiceID" + thisVoiceID + "is" +
													durationSum ++ ".").postln
											}
										}
									} {
										"\t\t\t- thisVoiceID == voiceIDLast".postln;
										("\t\t\t- The previous backup was" + durationSum + "in the voiceID" +
											thisVoiceID).postln;

										if (durationSum == nil) {
											("\t\t\tdurationSum is thisDuration" + durationSum).postln;
											durationSum = thisDuration
										} {
											("\t\t\t- durationSum is the sum of the durationSum and thisDuration:" +
												durationSum + "+" +    thisDuration).postln;
											durationSum = durationSum +    thisDuration;
										};

										("\t\t\t- The new backup in the voiceID" + thisVoiceID + "is" +
											durationSum).postln;
									};
									voiceIDLast = thisVoiceID
								};

								("\t\t- dynamicsLastPerPart:\n\t\t " + dynamicsLastPerPart).postln;
								("\t\t- thisVoiceDynamicLast:" + thisVoiceDynamicLast).postln;

								thisDynamic = (
									case
									{ dynamicMIDIvelocityPairs.keys.includes(
										dynamicTranscriber.(dynamic_Articulation_Tie_Slur_Candidate)
									)
									} {
										"\t\t- This entry contains dynamic information at entry[2].".postln;
										dynamicTranscriber.(dynamic_Articulation_Tie_Slur_Candidate) }
									{ dynamicMIDIvelocityPairs.keys.includes(
										dynamicTranscriber.(duration_Dynamic_Articulation_Tie_Slur_Candidate)
									)
									} { "\t\t- This entry contains dynamic information at entry[1].".postln;
										dynamicTranscriber.(duration_Dynamic_Articulation_Tie_Slur_Candidate) }
								);

								if (thisDynamic == nil) {
									"\t\t- This entry contains no dynamic information at all.".postln;
								};
								("\t\t\t\t\t- thisDynamic:" + thisDynamic).postln;
								("\t\t\t\t\t- thisVoiceDynamicLast:" + thisVoiceDynamicLast).postln;

								thisDynamic = if (
									thisDynamic == thisVoiceDynamicLast
									&&
									(thisDynamic != \sf)
								) {
									"\t\t\t\t\t- This dynamic is the same as the previous one.".postln;
									thisVoiceDynamicLast
								} {
									thisDynamic
								};

								("\t\t\t\t\t- the updated thisDynamic:" + thisDynamic + thisDynamic.class).postln;
								("\t\t\t\t\t- the updated thisVoiceDynamicLast:" + thisVoiceDynamicLast).postln;

								("thisDynamic != nil:" + (thisDynamic != nil)).postln;
								("(noteIndex == 0):" + (noteIndex == 0)).postln;
								("thisDynamic == thisVoiceDynamicLast:" + (thisDynamic == thisVoiceDynamicLast)).postln;
								("thisDynamic != \\" ++ "sf:" + (thisDynamic != \sf)).postln;

								("partItemsPerBar.size:" + partItemsPerBar.size).postln;
								("(partItemsPerBar.size > 3):" + (partItemsPerBar.size > 3)).postln;
								("(partItemsPerBar.size > 1):" + (partItemsPerBar.size > 1)).postln;
								("(thisVoiceID == \\"++ "v1):" + (thisVoiceID == \v1)).postln;
								("thisVoiceID:" + thisVoiceID ++ ";" + "thisVoiceID.class" + thisVoiceID.class).postln;
								("partItemsPerBar[\staves]:" + partItemsPerBar[\staves]).postln;
								((partItemsPerBar.size > 3)  &&  (thisVoiceID == \v1) && (partAttributesPerBar[\staves] == 1)).postln;

								itemsPerPartPerBar = itemsPerPartPerBar ++
								if (thisDynamic != nil  &&  (noteIndex == 0)) {
									if (thisDynamic == thisVoiceDynamicLast  &&  (thisDynamic != \sf)) {
										"      <direction>\n" ++
										"        <direction-type>\n" ++
										"          <dynamics>\n"
										"          </dynamics>\n" ++
										"        </direction-type>\n" ++
										"        <sound dynamics=" ++
										q.(dynamicMIDIvelocityPairs[thisDynamic]) ++ "/>\n"
									} {
										"      <direction" + "placement=" ++
										if (
											partItemsPerBar.keys.includes(\v2)   &&  (thisVoiceID == \v1) && (partAttributesPerBar[\staves] == 1)) {
											q.("above") ++ ">\n" ++
											"        <direction-type>\n" ++
											"          <dynamics" /*++ default-y=" ++ q.("15")*/ ++ ">\n"
										}  {
											q.("below") ++ ">\n" ++
											"        <direction-type>\n" ++
											"          <dynamics" /*++ default-y=" ++ q.("275")*/ ++ ">\n"
										} ++
										"            <" ++ thisDynamic.asString ++
										"/>\n" ++
										"          </dynamics>\n" ++
										"        </direction-type>\n" ++
										"        <voice>" ++ thisVoiceID.asString[1] ++ "</voice>\n" ++
										"        <sound dynamics=" ++
										q.(dynamicMIDIvelocityPairs[thisDynamic]) ++ "/>\n"
									} ++
									"      </direction>\n"

								} { "" };

								musicXMLfile << (itemsPerPartPerBar);
								itemsPerPartPerBar = "";

								itemsPerPartPerBar = itemsPerPartPerBar ++
								"      <note>\n";

								if ( noteIndex != 0 ) {
									"\t\t\t- this is a note in a chord".postln;
									itemsPerPartPerBar = itemsPerPartPerBar ++
									"        <chord/>\n";
								};

								if (thisDynamic != nil) {
									thisVoiceDynamicLast = dynamicsLastPerPart[partKey].put(thisVoiceID, thisDynamic)
								}{
									thisDynamic = thisVoiceDynamicLast
								};

								if (thisEntryIsRest) {
									("\t\t\t- this is a Rest. thisVoiceID:" + thisVoiceID).postln;
									itemsPerPartPerBar = itemsPerPartPerBar ++
									"        <rest/>\n"
								} {
									itemsPerPartPerBar = itemsPerPartPerBar ++
									"        <pitch>\n" ++
									"          <step>" ++ thisPitchClass ++ "</step>\n" ++
									"          <alter>" ++ thisPitchAlteration ++ "</alter>\n" ++
									"          <octave>" ++ thisOctave ++ "</octave>\n" ++
									"        </pitch>\n";
								};

								itemsPerPartPerBar = itemsPerPartPerBar ++
								"        <duration>" ++ thisDuration ++ "</duration>\n";

								thisTie = {
									case
									{
										tie_Slur_Candidate != nil
										&&
										ties.keys.includes(tie_Slur_Candidate)
									} {
										"index 4".postln;
										tie_Slur_Candidate
									}
									{
										articulation_Tie_Slur_Candidate != nil
										&&
										ties.keys.includes(articulation_Tie_Slur_Candidate)
									} {
										"index 3".postln;
										articulation_Tie_Slur_Candidate
									}
									{
										dynamic_Articulation_Tie_Slur_Candidate != nil
										&&
										ties.keys.includes(dynamic_Articulation_Tie_Slur_Candidate)
									} {
										"index 2".postln;
										dynamic_Articulation_Tie_Slur_Candidate
									}
									{
										duration_Dynamic_Articulation_Tie_Slur_Candidate != nil
										&&
										ties.keys.includes(duration_Dynamic_Articulation_Tie_Slur_Candidate)
									} {
										"index 1".postln;
										duration_Dynamic_Articulation_Tie_Slur_Candidate
									}
								}.();

								("\n\t\t\t\t\t- thisTie:" + thisTie + thisTie.class).postln;


								if(thisTie != nil) {
									itemsPerPartPerBar = itemsPerPartPerBar ++
									ties[thisTie][\tie]
								} {
									""
								};

								itemsPerPartPerBar = itemsPerPartPerBar ++
								"        <voice>" ++ thisVoiceID.asString[1] ++ "</voice>\n" ++
								"        <type>" ++  thisNoteTypeAndDuration[0] ++ "</type>\n" ++
								(
									if ("d".matchRegexp(thisNoteType.asString)) {
										"        <dot/>\n"
									} {
										if ("D".matchRegexp(thisNoteType.asString)) {
											"        <dot/>\n" ++
											"        <dot/>\n"
										} {
											if ("T".matchRegexp(thisNoteType.asString)) {
												"        <dot/>\n" ++
												"        <dot/>\n" ++
												"        <dot/>\n"
											} {""}
										}
									}
								);

								("thisAccidental:" + thisAccidental ++
									"\nthisAccidental.class:" + thisAccidental.class ++
									"\nthisAlter: " + thisPitchAlteration ++
									"\npitches_accidentals[thisPitch[0].asSymbol].class:" + pitches_accidentals[thisPitch[0].asSymbol].class ++
									"\nthisPitchAlteration.class:" + thisPitchAlteration.class ++
									"\npitches_accidentals[thisPitch[0].asSymbol]:" + pitches_accidentals[thisPitch[0].asSymbol] ++
									"\nthisPitch[0]:" + thisPitch[0] ++
									"\npitches_accidentals: " + pitches_accidentals ++
									"\nthisPitchAlteration:" + thisPitchAlteration
								).postln;
								("pitches_accidentals[thisPitch[0].asSymbol] != thisPitchAlteration.asString:" + (pitches_accidentals[thisPitch[0].asSymbol] != thisPitchAlteration)).postln;
								if (
									thisAccidental.size != 0
									&& (pitches_accidentals[thisPitch[0].asSymbol].asString != thisPitchAlteration)
								) {
									itemsPerPartPerBar = itemsPerPartPerBar ++
									"        <accidental>" ++ thisAccidental ++ "</accidental>\n" };

								if (duration_Dynamic_Articulation_Tie_Slur_Candidate.size > 0  && (noteIndex == 0)) {
									tupletTimeRegister.(duration_Dynamic_Articulation_Tie_Slur_Candidate)
								};


								if (splitAt != nil) {
									("\t\t\t\t\t- splitAt[thisOctave]:" + splitAt[thisOctave]).postln };

								("partAttributesPerBarLast[\staves]:" + partAttributesPerBarLast[\staves]).postln;
								("partAttributesPerBar[\staves]:" + partAttributesPerBar[\staves]).postln;

								itemsPerPartPerBar = itemsPerPartPerBar ++
								(
									if (partItemsPerBar.size > 3 && (partAttributesPerBar[\staves] == 1)) {
										if (thisVoiceID == \v1)    {
											"        <stem" + /*default-y="3" +*/ ">up</stem>\n"
										} {
											"        <stem" + /*default-y="3" +*/ ">down</stem>\n"
										}
									}
									{
										""
									}
								) ++
								"        <staff>" ++  (
									switch(splitAt.size,
										0, {
											1
										},
										1, {
											(\True: 1, \False: 2)[(thisOctave >= splitAt[0]).class.asSymbol]
										},
										11, {
											splitAt[thisOctave]
										}
									)
								) ++ "</staff>\n";

								if(thisTie != nil) {
									itemsPerPartPerBar = itemsPerPartPerBar ++
									"        <notations>\n" ++
									ties[thisTie][\tied] ++
									"        </notations>\n"
								} {
									""
								};

								if (duration_Dynamic_Articulation_Tie_Slur_Candidate.size > 0  &&  (noteIndex ==0)) {
									tupletNotationRegister.(duration_Dynamic_Articulation_Tie_Slur_Candidate)
								};



								thisArticulation = {
									case
									{
										articulation_Tie_Slur_Candidate != nil
										&&
										articulations.keys.includes(articulation_Tie_Slur_Candidate)
									} {
										"index 3".postln;
										articulation_Tie_Slur_Candidate
									}
									{
										dynamic_Articulation_Tie_Slur_Candidate != nil
										&&
										articulations.keys.includes(dynamic_Articulation_Tie_Slur_Candidate)
									} {
										"index 2".postln;
										dynamic_Articulation_Tie_Slur_Candidate
									}
									{
										articulations.keys.includes(duration_Dynamic_Articulation_Tie_Slur_Candidate)
									} {
										"index 1".postln;
										duration_Dynamic_Articulation_Tie_Slur_Candidate
									}
								}.();

								("\n\t\t\t\t\t- thisArticulation:" + articulations[thisArticulation]).postln;

								if (thisArticulation != nil  &&  (noteIndex == 0))  {
									itemsPerPartPerBar = itemsPerPartPerBar ++
									"        <notations>\n" ++
									"          <articulations>\n" ++
									"            <" ++ articulations[thisArticulation] ++ "/>\n" ++
									"          </articulations>\n" ++
									"        </notations>\n"
								};

								thisSlur = {
									case
									{
										slur_Candidate != nil
										&&
										slur.keys.includes(slur_Candidate)
									} {
										"index 5".postln;
										slur_Candidate
									}
									{
										tie_Slur_Candidate != nil
										&&
										slur.keys.includes(tie_Slur_Candidate)
									} {
										"index 4".postln;
										tie_Slur_Candidate
									}
									{
										articulation_Tie_Slur_Candidate != nil
										&&
										slur.keys.includes(articulation_Tie_Slur_Candidate)
									} {
										"index 3".postln;
										articulation_Tie_Slur_Candidate
									}
									{
										dynamic_Articulation_Tie_Slur_Candidate != nil
										&&
										slur.keys.includes(dynamic_Articulation_Tie_Slur_Candidate)
									} {
										"index 2".postln;
										dynamic_Articulation_Tie_Slur_Candidate
									}
									{
										slur.keys.includes(duration_Dynamic_Articulation_Tie_Slur_Candidate)
									} {
										"index 1".postln;
										duration_Dynamic_Articulation_Tie_Slur_Candidate
									}
								}.();

								("\n\t\t\t\t\t- thisSlur:" + thisSlur + thisSlur.class).postln;

								if (thisSlur != nil  &&  (noteIndex == 0))  {
									itemsPerPartPerBar = itemsPerPartPerBar ++
									"        <notations>\n" ++
									slur[thisSlur] ++
									"        </notations>\n"
								};



								itemsPerPartPerBar = itemsPerPartPerBar ++
								"      </note>\n";
								("\n\t\t\t\t- thisPitch index" + noteIndex + "registration finished.").postln;
								("\n\t\t\t\t-------------------------------------------------------").postln;
								pitches_accidentals.put(thisPitch[0].asSymbol, thisPitchAlteration);

							};

							scdfile << (
								"\t\t\t\t(\n" ++
								"\t\t\t\t\tdur:" + (thisDuration/252000) ++ "," +
								"tie:" + if (thisTie != nil) {
									$\\ ++ (thisTie.asString.replace("r", "lv").replace("R", "stop_then_lv")
										.replace("j", "start").replace("J", "stop")
										.replace("~", "start").replace("`", "stop"))
								} { "\\nil" } ++ "," +
								"slur:" + if (thisSlur != nil) {
									$\\ ++ (thisSlur.asString.replace("u", "start").replace("U", "stop")
										.replace("(", "start").replace(")", "stop"))
								} { "\\nil" } ++ "," +
								"articulation: '" ++ articulations[thisArticulation] ++ "',\n" ++
								"\t\t\t\t\tmidinote:" + if (thisEntryIsRest) { "Rest(" ++ (thisDuration/252000) ++ ")"
								} {
									thisMIDIPerfrom
								}
								++ "," +
								"pitch:" + if (thisEntryIsRest) { "Rest(" ++ (thisDuration/252000) ++ ")"
								} {
									thisPitchPerform
								}
								++ ",\n" ++
								{
									var dynamicMark;
									dynamicMark = if (thisDynamic == nil) {
										if (thisVoiceDynamicLast == nil) {
											thisPartDynamic
										} {
											thisVoiceDynamicLast
										}
									} {
										thisDynamic
									};
									"\t\t\t\t\tdynamic:" + $\\ ++ dynamicMark ++ "," +
									"velocity:" + dynamicMIDIvelocityPairs[dynamicMark] ++ "\n"
								}.() ++
								"\t\t\t\t),\n").replace("[ ", "[").replace(" ]", "]").replace("\nil", "nil");
						};
						("\t\tthisEntryTemporaryUpdated:" + thisEntryTemporaryUpdated).postln;

						("\t\t- thisVoiceEntryLast:" + thisVoiceEntryLast).postln;
						voiceEntryLastPerPart[partKey].put(thisVoiceID, thisEntry);
						("\t\t- voiceEntryLastPerPart:\n\t\t " + voiceEntryLastPerPart).postln;
						("\t\t- voiceEntryLastPerPart[partKey][thisVoiceID]:" + voiceEntryLastPerPart[partKey][thisVoiceID]).postln;
						("\n\t\t- part" + partKey + "thisVoiceID:" + thisVoiceID + "thisEntry index" + index + "registration finished.\n").postln;
					};

					scdfile << (if (thisVoiceIDIndex < (voiceIDs.size - 1)) {
						"\t\t\t], // voice end\n"
					} {
						"\t\t\t] // voice end\n"
					});

					"\t\t-------------------------------------------------------".postln;
					("\t\t- part" + partKey + "thisVoiceID:" + thisVoiceID + "registration finished.").postln;
					"\t\t-------------------------------------------------------".postln;

					musicXMLfile << (itemsPerPartPerBar);
					itemsPerPartPerBar = "";
				};

				scdfile << (if (barIndex < (score.size - 1)) {
					"\t\t), // bar end\n"
				} {
					"\t\t) // bar end\n"
				});

				("\t\t\t- bar" + barIndex.asString + partKey + "finished.\n").postln;
				("\t- bar" + barIndex.asString + "finished.\n").postln;
				itemsPerPartPerBar = itemsPerPartPerBar ++ "    </measure>\n" ++
				"  <!--=====================================================-->\n";
				musicXMLfile << (itemsPerPartPerBar);
				itemsPerPartPerBar = "";
			};

			scdfile << ("\t)" ++ if (partIndex < (partsInTheFirstBar.size - 1)) {
				", // part end\n"
			} {
				" // part end\n"
			});

			itemsPerPartPerBar = itemsPerPartPerBar ++ "  </part>\n" ++
			"<!--=======================================================-->\n";
			musicXMLfile << (itemsPerPartPerBar);
			itemsPerPartPerBar = "";
		};

		scdfile << ("); // score end\n\n");
		musicXMLfile << (itemsPerPartPerBar ++ "</score-partwise>");
		itemsPerPartPerBar = "";

		routineText = "";
		{
			var numVoices;
			numVoices = "player[bar].keys.count { |item| item.asString.contains(" ++ q.("v") ++ ") }";
			routineText = routineText ++
			"~play = {\n" ++
			"\ts.waitForBoot {\n" ++
			"\t\tvar parts, detectNumVoices, eventPlayer;\n" ++
			"\t\tparts =" +  partLables.collect { |aPartLabel| "'" ++ aPartLabel ++ "'"} ++ ";\n" ++
			"\t\tdetectNumVoices = { |nthBar, thisPart|\n" ++
			"\t\t\t" ++ renotatedVariable ++ "[thisPart][nthBar].keys.count { |item|\n" ++
			"\t\t\t\titem.asString.contains(" ++ q.("v") ++ ") }\n" ++
			"\t\t};\n" ++
			"\t\teventPlayer = { |thisEntry, velocityFactorForRestNTiedNote, slur, instrument|\n" ++
			"\t\t\tvar articulationToVelocity, articulationToLegato, sustain;\n" ++
			"\t\t\t# articulationToVelocity, articulationToLegato = switch (thisEntry[\\" ++ "articulation],\n" ++
			"\t\t\t\t'accent',          { [4, 1]    },\n" ++
			"\t\t\t\t'strong-accent',   { [8, 1]    },\n" ++
			"\t\t\t\t'staccato',        { [1, 0.4]  },\n" ++
			"\t\t\t\t'staccatissimo',   { [2, 0.2] },\n" ++
			"\t\t\t\t'tenuto',          { [0, 1]    },\n" ++
			"\t\t\t\t'detached-legato', { [0, 0.8]  },\n" ++
			"\t\t\t\t'nil',             { [0, 1]    }\n" ++
			"\t\t\t);\n" ++
			"\t\t\t\n" ++
			"\t\t\t(" ++ q.("\\" ++ "t- articulationToLegato:") + "+ articulationToLegato).postln;\n" ++
			"\t\t\tarticulationToLegato = if (slur == \\on) {\n" ++
			"\t\t\t\t1\n" ++
			"\t\t\t} {\n" ++
			"\t\t\t\tif (thisEntry[\\" ++ "tie] == \\" ++ "lv) {\n" ++
			"\t\t\t\t\t1.2\n" ++
			"\t\t\t\t} { 0.9\n" ++
			"\t\t\t\t}\n" ++
			"\t\t\t} * articulationToLegato;\n" ++
			"\t\t\t(" ++ q.("\\" ++ "t- slur-applied articulationToLegato:") + "+ articulationToLegato).postln;\n" ++
			"\t\t\t(\n" ++
			"\t\t\t\tinstrument: instrument,\n" ++
			"\t\t\t\tdur:        if (thisEntry[\\" ++ "tiedDur] == 0) {\n" ++
			"\t\t\t\t\tthisEntry[\\" ++ "dur]\n" ++
			"\t\t\t\t} {\n" ++
			"\t\t\t\t\tthisEntry[\\" ++ "tiedDur]\n" ++
			"\t\t\t\t},\n" ++
			"\t\t\t\tamp:        thisEntry[\\" ++ "velocity] / 127 * velocityFactorForRestNTiedNote,\n" ++
			"\t\t\t\tmidinote:   thisEntry[\\" ++ "midinote],\n" ++
			"\t\t\t\tlegato:     articulationToLegato\n" ++
			"\t\t\t).play.postln;\n" ++
			"\t\t\t" ++ q.("") ++ ".postln;\n" ++
			"\t\t};\n" ++
			"\t\t\n" ++
			"\t\tparts.do { |aPart|\n" ++
			"\t\t\tvar numBars, nthBar, instrument, scorePlay;\n" ++
			"\t\tnumBars =" + renotatedVariable ++ "[parts[0]].size" ++ ";\n" ++
			"\t\t\tnthBar = 1;\n" ++
			"\t\t\tinstrument = if (SynthDescLib.global.synthDescs.keys.includes(aPart)) {\n" ++
			"\t\t\t\taPart\n" ++
			"\t\t\t} {\n" ++
			"\t\t\t\t\\" ++ "default\n" ++
			"\t\t\t};\n" ++
			"\t\t\tscorePlay = { |nthBarNum|\n" ++
			"\t\t\t\tvar thisPartThisBarVoiceSize = detectNumVoices.(nthBarNum, aPart);\n" ++
			"\t\t\t\t(" ++ q.("\\" ++ "nbar:") + "+ nthBarNum).postln;\n" ++
			"\t\t\t\tthisPartThisBarVoiceSize.do { |thisVoiceIndex|\n" ++
			"\t\t\t\t\tthisVoiceIndex = thisVoiceIndex + 1;\n" ++
			"\t\t\t\t\tfork{\n" ++
			"\t\t\t\t\t\tvar thisPartThisBarThisVoice, thisPartThisBarThisVoiceLastItemIndex, slur;\n" ++
			"\t\t\t\t\t\tthisPartThisBarThisVoice =" + renotatedVariable ++ "[\n" ++
			"\t\t\t\t\t\t\taPart][nthBarNum][\n" ++
			"\t\t\t\t\t\t\t(" ++ q.("v") + "++ thisVoiceIndex).asSymbol\n" ++
			"\t\t\t\t\t\t];\n" ++
			"\t\t\t\t\t\tthisPartThisBarThisVoiceLastItemIndex = thisPartThisBarThisVoice.size - 1;\n" ++
			"\t\t\t\t\t\tthisPartThisBarThisVoice.size.do { |thisVoiceItemIndex|\n" ++
			"\t\t\t\t\t\t\tvar entryCurrent, entryNext, entryPrevious;\n" ++
			"\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t(\n"++
			"\t\t\t\t\t\t\t" ++ q.("\\n\\nthisVoiceItemIndex:") ++ "\n" ++
			"\t\t\t\t\t\t\t\t+ thisVoiceItemIndex +" + q.("@ bar") + "+ nthBarNum ++" + q.("\\" ++ "n") ++ "\n" ++
			"\t\t\t\t\t\t\t).postln;\n" ++
			"\t\t\t\t\t\t\t# entryPrevious, entryCurrent, entryNext = (\n" ++
			"\t\t\t\t\t\t\t\tcase\n" ++
			"\t\t\t\t\t\t\t\t{ thisVoiceItemIndex < thisPartThisBarThisVoiceLastItemIndex\n" ++
			"\t\t\t\t\t\t\t\t\t&&  (thisVoiceItemIndex != 0) } {\n" ++
			"\t\t\t\t\t\t\t\t\t[\n" ++
			"\t\t\t\t\t\t\t\t\t\tthisPartThisBarThisVoice[thisVoiceItemIndex - 1],\n" ++
			"\t\t\t\t\t\t\t\t\t\tthisPartThisBarThisVoice[thisVoiceItemIndex],\n" ++
			"\t\t\t\t\t\t\t\t\t\tthisPartThisBarThisVoice[thisVoiceItemIndex + 1]\n" ++
			"\t\t\t\t\t\t\t\t\t]\n" ++
			"\t\t\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t\t\t\t{ thisVoiceItemIndex == thisPartThisBarThisVoiceLastItemIndex  &&  (thisVoiceItemIndex != 0)} {\n" ++
			"\t\t\t\t\t\t\t\t\t[\n" ++
			"\t\t\t\t\t\t\t\t\t\tthisPartThisBarThisVoice[thisVoiceItemIndex - 1],\n" ++
			"\t\t\t\t\t\t\t\t\t\tthisPartThisBarThisVoice[thisVoiceItemIndex],\n" ++
			"\t\t\t\t\t\t\t\t\t\tif (nthBarNum < numBars) {\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t" ++ renotatedVariable ++ "[aPart][nthBarNum + 1][\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t\t(" ++ q.("v") + "++ thisVoiceIndex).asSymbol\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t][0]\n" ++
			"\t\t\t\t\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t\t\t\t\t]\n" ++
			"\t\t\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t\t\t\t{ thisVoiceItemIndex == 0 } {\n" ++
			"\t\t\t\t\t\t\t\t\t[\n" ++
			"\t\t\t\t\t\t\t\t\t\tif (nthBarNum == 1) {\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t(tie: \\" ++ "nil)\n" ++
			"\t\t\t\t\t\t\t\t\t\t} {\n" ++

			"\t\t\t\t\t\t\t\t\t\t\tvar previousItem =" + renotatedVariable++ "[aPart][nthBarNum - 1][\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t\t(" ++  q.("v") + "++ thisVoiceIndex).asSymbol\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t];\n" ++
			"\t\t\t\t\t\t\t\t\t\t\tpreviousItem[previousItem.size - 1]\n" ++
			"\t\t\t\t\t\t\t\t\t\t},\n" ++
			"\t\t\t\t\t\t\t\t\t\tthisPartThisBarThisVoice[thisVoiceItemIndex],\n" ++
			"\t\t\t\t\t\t\t\t\t\tthisPartThisBarThisVoice[thisVoiceItemIndex + 1]\n" ++
			"\t\t\t\t\t\t\t\t\t]\n" ++
			"\t\t\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t\t\t);\n" ++
			"\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t(\n" ++
			"\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "n\\" ++ "t* thisVoiceItemIndex:") + "+ thisVoiceItemIndex ++" + q.("\\" ++ "n\\" ++ "n\\" ++ "t*") ++ "\n" ++
			"\t\t\t\t\t\t\t\t+ entryCurrent ++ " ++ q.("\\" ++ "n") ++ "\n" ++
			"\t\t\t\t\t\t\t).postln;\n" ++
			"\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\tcase\n" ++
			"\t\t\t\t\t\t\t{ entryCurrent.keys.includes(\\" ++ "tempo) } {\n" ++
			"\t\t\t\t\t\t\t\tTempoClock.default.tempo = entryCurrent[\\" ++ "tempo][\\" ++ "bpm] / 60;\n" ++
			"\t\t\t\t\t\t\t\t(\n" ++
			"\t\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "t* TempoClock.default.tempo:") ++ "\n" ++
			"\t\t\t\t\t\t\t\t\t+ TempoClock.default.tempo ++ " ++ q.("\\" ++ "n" ) ++ "\n" ++
			"\t\t\t\t\t\t\t\t).postln\n" ++
			"\t\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t\t\t{ thisPartThisBarThisVoice[thisVoiceItemIndex].keys.includes(\\"++ "midinote)\n" ++
			"\t\t\t\t\t\t\t} {\n" ++
			"\t\t\t\t\t\t\t\tvar tiedRhythmicValue = 0, velocityFactor = 1, waitTime;\n" ++
			"\t\t\t\t\t\t\t\tswitch (entryCurrent[\\" ++ "slur],\n" ++
			"\t\t\t\t\t\t\t\t\t\\" ++ "start, { slur = \\" ++ "on },\n" ++
			"\t\t\t\t\t\t\t\t\t\\" ++ "stop, { slur = \\" ++ "off }\n" ++
			"\t\t\t\t\t\t\t\t);\n" ++
			"\t\t\t\t\t\t\t\tif (entryCurrent[\\" ++ "tie] == \\" ++ "start\n" ++
			"\t\t\t\t\t\t\t\t\t&& " + q.("^start") ++ ".matchRegexp(entryPrevious[\\" ++ "tie].asString).not) {\n" ++
			"\t\t\t\t\t\t\t\t\tvar actualiseRhythmicValue;\n" ++
			"\t\t\t\t\t\t\t\t\tactualiseRhythmicValue = { |testBar, testItemIdx, testItem|\n" ++
			"\t\t\t\t\t\t\t\t\t\tvar nextTestItemDetector, nextTestItem, nexttestItemIdx;\n" ++
			"\t\t\t\t\t\t\t\t\t\t\tnextTestItemDetector = { |testBarNum|\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t" ++ renotatedVariable ++ "[aPart][testBarNum][\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t\t("+ q.("v") + "++ thisVoiceIndex).asSymbol\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t\t]\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t};\n" ++
			"\t\t\t\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "n\\" ++ "t* Checking the next tie status") ++ ".postln;\n" ++
			"\t\t\t\t\t\t\t\t\t\t(\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "n\\" ++ "t\\" ++ "t* nthBar:") + "+ testBar ++\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t" ++ q.("; testItemIdx:") + "+ testItemIdx ++\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t" ++ q.("; testItem[\\" ++ "dur]:") + "+ testItem[\\" ++ "dur]\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t).postln;\n" ++
			"\t\t\t\t\t\t\t\t\t\t(\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "n\\" ++ "t\\" ++ "t* previous tiedRhythmicValue:") ++ "\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t+ tiedRhythmicValue\n" ++
			"\t\t\t\t\t\t\t\t\t\t).postln;\n" ++
			"\t\t\t\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t\t\t\ttiedRhythmicValue = tiedRhythmicValue + testItem[\\" ++ "dur];\n" ++
			"\t\t\t\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t\t\t\t(" ++ q.("\\" ++ "t\\" ++ "t* updated tiedRhythmicValue:") + "+ tiedRhythmicValue).postln;\n" ++
			"\t\t\t\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t\t\t\tif (testItemIdx < (nextTestItemDetector.(testBar).size - 1)) {\n" ++
			"\t\t\t\t\t\t\t\t\t\t\tnexttestItemIdx = testItemIdx + 1;\n" ++
			"\t\t\t\t\t\t\t\t\t\t\tnextTestItem = nextTestItemDetector.(testBar)[nexttestItemIdx];\n" ++
			"\t\t\t\t\t\t\t\t\t\t} {\n" ++
			"\t\t\t\t\t\t\t\t\t\t\ttestBar = testBar + 1;\n" ++
			"\t\t\t\t\t\t\t\t\t\t\tnexttestItemIdx = 0;\n" ++
			"\t\t\t\t\t\t\t\t\t\t\tnextTestItem = nextTestItemDetector.(testBar)[nexttestItemIdx];\n" ++
			"\t\t\t\t\t\t\t\t\t\t};\n" ++
			"\t\t\t\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t\t\t\t(" ++ q.("\\" ++ "t\\" ++ "t* the nextTestItem['tie'] of the testItem") + "+ testItemIdx ++ " ++ q.(":") + "+ nextTestItem[\\" ++ "tie]).postln;\n" ++

			"\t\t\t\t\t\t\t\t\t\t(" ++ q.("\\" ++ "t\\" ++ "t*") + "+" + q.("^stop") ++ ".quote ++ " ++ q.(".matchRegexp(nextTestItem['tie'].asString): ") + "+" + q.("^stop") ++ ".matchRegexp(nextTestItem[\\" ++ "tie].asString)).postln;\n" ++
			"\t\t\t\t\t\t\t\t\t\tif (" ++ q.("^stop") ++ ".matchRegexp(testItem[\\" ++ "tie].asString)) {\n"++
			"\t\t\t\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "t\\" ++ "t* no need to check the further ties\\" ++ "n") ++ ".postln\n" ++
			"\t\t\t\t\t\t\t\t\t\t} {\n" ++
			"\t\t\t\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "n\\" ++ "t* Checking the next tie status").postln ++ ";\n" ++
			"\t\t\t\t\t\t\t\t\t\t\tactualiseRhythmicValue.(testBar, nexttestItemIdx, nextTestItem);\n" ++
			"\t\t\t\t\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t\t\t\t\t};\n" ++
			"\t\t\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t\t\tactualiseRhythmicValue.(nthBarNum, thisVoiceItemIndex, entryCurrent);\n" ++
			"\t\t\t\t\t\t\t\t};\n" ++
			"\t\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t\t(" ++ q.("\\" ++ "t* entryPrevious 'waitTimeCorrectionForTiedNote':") ++ "+ entryPrevious[\\" ++ "waitTimeCorrectionForTiedNote] ++" + q.("\\" ++ "n") ++ ").postln;\n" ++
			"\t\t\t\t\t\t\t\tif (" ++ q.("^start") ++ ".matchRegexp(entryPrevious[\\" ++ "tie].asString)) {\n" ++
			"\t\t\t\t\t\t\t\t\tentryCurrent.put(\\" ++ "waitTimeCorrectionForTiedNote, 0);\n" ++
			"\t\t\t\t\t\t\t\t\tvelocityFactor = 0\n" ++
			"\t\t\t\t\t\t\t\t};\n" ++
			"\t\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t\tif (" ++ q.("^Rest") ++ ".matchRegexp(entryCurrent[\\" ++ "midinote].asString)) {\n" ++
			"\t\t\t\t\t\t\t\t\tvelocityFactor = 0\n" ++
			"\t\t\t\t\t\t\t\t};\n" ++
			"\t\t\t\t\t\t\t\t\n" ++
			"\t\t\t\t\t\t\t\tentryCurrent.put(\\" ++ "tiedDur, tiedRhythmicValue);\n" ++
			"\t\t\t\t\t\t\t\t(\n" ++
			"\t\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "t* entryCurrent 'tiedDur':") + "+ entryCurrent['tiedDur'] ++" + q.(";") ++ "\n" ++
			"\t\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "t'dur':") ++ " + entryCurrent['dur'] ++" + q.(";") ++ "\n" ++
			"\t\t\t\t\t\t\t\t\t" ++ q.("\\" ++ "t'waitTimeCorrectionForTiedNote':") ++ " + entryCurrent['waitTimeCorrectionForTiedNote'] ++" + q.(";\\" ++ "n") ++ "\n" ++
			"\t\t\t\t\t\t\t\t).postln;\n" ++
			"\t\t\t\t\t\t\t\teventPlayer.(entryCurrent, velocityFactor, slur, instrument);\n" ++
			"\t\t\t\t\t\t\t\twaitTime = if (entryCurrent[\\" ++ "waitTimeCorrectionForTiedNote] != nil) {\n" ++
			"\t\t\t\t\t\t\t\t\tentryCurrent[\\" ++ "waitTimeCorrectionForTiedNote]\n" ++
			"\t\t\t\t\t\t\t\t} {\n" ++
			"\t\t\t\t\t\t\t\t\tif (entryCurrent[\\" ++ "tiedDur] == 0) {\n" ++
			"\t\t\t\t\t\t\t\t\t\tentryCurrent[\\" ++ "dur]\n" ++
			"\t\t\t\t\t\t\t\t\t} {\n" ++
			"\t\t\t\t\t\t\t\t\t\tentryCurrent[\\" ++ "tiedDur]\n" ++
			"\t\t\t\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t\t\t\t};\n" ++
			"\t\t\t\t\t\t\t\t(" ++ q.("\t* v") + "++ thisVoiceIndex + " ++ q.("waitTime:") ++ " + waitTime).postln;\n" ++
			"\t\t\t\t\t\t\t\twaitTime.wait\n" ++
			"\t\t\t\t\t\t\t};\n" ++
			"\t\t\t\t\t\t\t(" ++ q.("\t*") + "+ (thisVoiceItemIndex) +" + q.("/") + "+ (thisPartThisBarThisVoiceLastItemIndex) +" + q.("finished") ++ ").postln;\n" ++
			"\t\t\t\t\t\t\t(" ++ q.("\t* nth bar of total bars:") + "+ nthBar + " ++ q.("/") + "+" + "numBars).postln;\n" ++
			"\t\t\t\t\t\t\tif (thisVoiceItemIndex == thisPartThisBarThisVoiceLastItemIndex) {\n" ++
			"\t\t\t\t\t\t\t\tif (nthBar < numBars) {\n" ++
			"\t\t\t\t\t\t\t\t\tnthBar = nthBar + 1;\n" ++
			"\t\t\t\t\t\t\t\t\tscorePlay.(nthBar)\n" ++
			"\t\t\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t\t}\n" ++
			"\t\t\t\t\t}\n" ++
			"\t\t\t\t}\n" ++
			"\t\t\t};\n" ++
			"\t\t\tscorePlay.(nthBar)\n" ++
			"\t\t}\n" ++
			"\t}\n" ++
			"};\n" ++
			"~play.()\n" ++
			")\n"
		}.();

		scdfile << (routineText);
		scdfile.close;

		scdfile = File(scdfilePath, "r").readAllString;
		scdfile = scdfile.replace(",\n\t\t\t]", "\n\t\t\t]");
		File.use(scdfilePath, "w", { |f| f.write(scdfile) });

		musicXMLfile.close;

		Platform.case(
			\linux,   { app + q.(musicXMLfilePath) },
			\osx,     { "open -a" + q.(app) + q.(musicXMLfilePath) },
			\windows, {
				app = switch(app,
					\reaper, {
						"C:/Program Files/REAPER (x64)/reaper.exe" // <- change application musicXMLfilePath if it is incorrect.
					},
					\musescore, {
						"C:/Program Files/MuseScore 4/bin/MuseScore4.exe" // <- check the application musicXMLfilePath.
					}
				);
				"start" + q.("") + q.(app) + q.(musicXMLfilePath) }
		).unixCmd;

		musicXMLfilePath.dirname.openOS;

		scdfilePath.load;
		scdfilePath.openDocument;

		^[musicXMLfilePath, scdfilePath]
	}
}

+ Array {
	notate { arg musicXMLfilePath = ("~/Download/untitled.musicxml".standardizePath), app = "MuseScore 4";
		^Notator.notate(this, musicXMLfilePath, app)
	}
}