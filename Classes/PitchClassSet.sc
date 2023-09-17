PitchClassSet {
	*prMake {
		arg quarterToneScale;
		var result;
		result = quarterToneScale.collectAs({ |pitchClass, index|
			var key;
			key = index / 2;
			key-> pitchClass }, IdentityDictionary);

		// adding integer
		(0..11).do { |i|
			result.add(i -> result[i.asFloat]);
		};

		// adding \t, \t5, 't.5', \e, \e5 and 'e.5'
		result.add(\t -> result[10]);
		result.add('t.5' -> result[10.5]);
		result.add(\t5 -> result[10.5]);
		result.add(\e -> result[11]);
		result.add('e.5' -> result[11.5]);
		result.add(\e5 -> result[11.5]);
		^result
	}

	*prMakeEZ {
		arg sixteenthToneScale;
		var result;
		result = sixteenthToneScale.collectAs({ |pitchClass, index|
			var key;
			key = index / 8;
			key-> pitchClass }, IdentityDictionary);

		// adding integer
		(0..11).do { |i|
			result.add(i -> result[i.asFloat]);
		};

		result.add(\t -> result[10]);
		result.add('t.5' -> result[10.5]);
		result.add(\t5 -> result[10.5]);
		result.add(\e -> result[11]);
		result.add('e.5' -> result[11.5]);
		result.add(\e5 -> result[11.5]);
		^result
	}
	*prEzCommonPart {
		^[
			['dn',		'd',											'cS',		'eF'	],
			[\d1_16],
			[\d1_8],
			[\d3_16],
			['dq',					'eU',					'fFu',		'cSq',		'eFq'	],
			[\d5_16],
			[\d3_8],
			[\d7_16],
			['ds',		'ef',								'fF'							],
			[\d9_16],
			[\d5_8],
			[\em5_16],
			['eu',					'dQ',		'fU',		'fFq'							],
			[\em3_16],
			[\em1_8],
			[\em1_16],
			['en',		'e',		'ff',								'dS'				],
			[\e1_16],
			[\e1_8],
			[\e3_16],
			['eq',		'fu',								'dSq',		'gFu'				],
			[\fm3_16],
			[\fm1_8],
			[\fm1_16],
			['fn',		'f',		'es',								'gF'				],
			[\f1_16],
			[\f1_8],
			[\f3_16],
			['fq',		'eQ',					'gU',		'gFq',		'eSu'				],
			[\f5_16],
			[\f3_8],
			[\f7_16],
			['fs',		'gf',								'eS'							],
			[\f9_16],
			[\f5_8],
			['gu',		'fQ',								'eSq',		'fSu',		'aFu'	],
			[\gm5_16],
			[\gm3_16],
			[\gm1_8],
			[\gm1_16],
			['gn',		'g',											'fS',		'aF'	],
			[\g1_16],
			[\g1_8],
			[\g3_16],
			['gq',		'aU',								'fSq',		'aFq'				],
			[\g5_16],
			[\g3_8],
			[\g7_16],
			['gs',		'af'																],
			[\g9_16],
			[\g5_8],
			[\am5_16],
			['au',		'gQ',								'gSu',		'bFu'				],
			[\am3_16],
			[\am1_8],
			[\am1_16],
			['an',		'a',											'gS',		'bF'	],
			[\a1_16],
			[\a1_8],
			[\a3_16]
		]
	}
	*prEzClassLowerPart {
		^[
			['cn',		'c',		'bs',								'dF'				],
			[\c1_16],
			[\c1_8],
			[\c3_16],
			['cq',					'bQ',		'dU',		'dFq',		'bSu'				],
			[\c5_16],
			[\c3_8],
			[\c7_16],
			['cs',		'df',								'bS'							],
			[\c9_16],
			[\c5_8],
			[\dm5_16],
			['du',					'cQ',					'bSq',		'cSu',		'eFu'	],
			[\dm3_16],
			[\dm1_8],
			[\dm1_16],
		]
	}
	*prEzClassHigherPart {
		^[
			['aq',		'bU',								'gSq',		'bFq',		'cFu'	],
			[\a5_16],
			[\a3_8],
			[\a7_16],
			['as',		'bf',								'cF'							],
			[\a9_16],
			[\a5_8],
			[\bm5_16],
			['bu',		'aQ',					'cU',		'cFq'							],
			[\bm3_16],
			[\bm1_8],
			[\bm1_16],
			['bn',		'b',											'aS',		'cf'	],
			[\b1_16],
			[\b1_8],
			[\b3_16],
			['bq',		'cu',								'aSq',		'dFu'				],
			[\cm3_16],
			[\cm1_8],
			[\cm1_16]
		]
	}
	*prEzOctaveLowerPart	{
		^[
			['cn',		'c',		'bsl',								'dF'				],
			[\c1_16],
			[\c1_8],
			[\c3_16],
			['cq',					'bQl',		'dU',		'dFq',		'bSul'				],
			[\c5_16],
			[\c3_8],
			[\c7_16],
			['cs',		'df',								'bSl'							],
			[\c9_16],
			[\c5_8],
			[\dm5_16],
			['du',					'cQ',					'bSql',		'cSu',		'eFu'	],
			[\dm3_16],
			[\dm1_8],
			[\dm1_16]
		]
	}
	*prEzOctaveHigherPart{
		^[
			['aq',		'bU',								'gSq',		'bFq',		'cFuh'	],
			[\a5_16],
			[\a3_8],
			[\a7_16],
			['as',		'bf',								'cFh'							],
			[\a9_16],
			[\a5_8],
			[\bm5_16],
			['bu',		'aQ',					'cUh',		'cFqh'							],
			[\bm3_16],
			[\bm1_8],
			[\bm1_16],
			['bn',		'b',											'aS',		'cfh'	],
			[\b1_16],
			[\b1_8],
			[\b3_16],
			['bq',		'cuh',								'aSq',		'dFuh'				],
			[\cm3_16h],
			[\cm1_8h],
			[\cm1_16h]
		]
	}
	*ez {
		arg pitchClassNum = nil;
		var sixteenthToneScale;
		sixteenthToneScale = PitchClassSet.prEzClassLowerPart
		++ PitchClassSet.prEzCommonPart
		++ PitchClassSet.prEzClassHigherPart;
		^if (pitchClassNum==nil) {
			PitchClassSet.prMakeEZ(sixteenthToneScale)
		} {
			PitchClassSet.prMakeEZ(sixteenthToneScale)[pitchClassNum]
		}
	}
	*prLilyCommonPart {
		^[
			['d',											'cx',		'eff'				],
			['dqs',					'etqf',					'fffqf',	'cxqs',		'effqs'	],
			['ds',		'ef',								'fff'							],
			['eqf',					'dtqs',		'ftqf',		'fffqs'							],
			['e',		'ff',								'dx'							],
			['eqs',		'fqf',								'dxqs',		'gffqf'				],
			['f',		'es',								'gff'							],
			['fqs',		'etqs',					'gtqf',		'gffqs',	'exqf'				],
			['fs',		'gf',								'ex'							],
			['gqf',		'ftqs',								'exqs',		'fxqf',		'affqf'	],
			['g',											'fx',		'aff'				],
			['gqs',		'atqf',								'fxqs',		'affqs'				],
			['gs',		'af'																],
			['aqf',		'gtqs',								'gxqf',		'bffqf'				],
			['a',											'gx',		'bff'				]
		]
	}
	*prLilyClassLowerPart {
		^[
			['c',		'bs',								'dff'							],
			['cqs',					'btqs',		'dtqf',		'dffqs',	'bxqf'				],
			['cs',		'df',								'bx'							],
			['dqf',					'ctqs',					'bxqs',		'cxqf',		'effqf'	]
		]
	}
	*prLilyClassHigherPart {
		^[
			['aqs',		'btqf',								'gxqs',		'bffqs',	'cffqf'	],
			['as',		'bf',								'cff'							],
			['bqf',		'atqs',					'ctqf',		'cffqs'							],
			['b',											'ax',		'cf'				],
			['bqs',		'cqf',								'axqs',		'dffqf'				]
		]
	}
	*prLilyOctaveLowerPart {
		^[
			['c',		'bsl',								'dff'							],
			['cqs',					'btqsl',	'dtqf',		'dffqs',		'bxqfl'			],
			['cs',		'df',								'bxl'							],
			['dqf',					'ctqs',					'bxqsl',	'cxqf',		'effqf'	]
		]
	}
	*prLilyOctaveHigherPart {
		^[
			['aqs',		'btqf',								'gxqs',		'bffqs',	'cffqfh'],
			['as',		'bf',								'cffh'							],
			['bqf',		'atqs',					'ctqfh',	'cffqsh'						],
			['b',											'ax',		'cfh'				],
			['bqs',		'cqfh',								'axqs',		'dffqfh'			]
		]
	}
	*lily {
		arg pitchClassNum = nil;
		var quarterToneScale;
		quarterToneScale = PitchClassSet.prLilyClassLowerPart
		++ PitchClassSet.prLilyCommonPart
		++ PitchClassSet.prLilyClassHigherPart;
		^if (pitchClassNum==nil) {
			PitchClassSet.prMake(quarterToneScale)
		} {
			PitchClassSet.prMake(quarterToneScale)[pitchClassNum]
		}
	}
	*prMusicSymbolCommonPart {
		^[
			['d♮',								'c𝄪',		'e𝄫'							],
			['d ¼↑ ',	'd♯ ¼↓ ',	'e♭ ¼↓ ',				'f𝄫 ¼↓ ',	'c𝄪 ¼↑ ',		'e𝄫 ¼↑ '	],
			['d♯',		'e♭',								'f𝄫'							],
			['e♮ ¼↓ ',	'e♭ ¼↑ ',	'd♯ ¼↑ ',	'f♭ ¼↓ '	,	'f𝄫 ¼↑ '							],
			['e♮',		'f♭',								'd𝄪'								],
			['e ¼↑ ',	'f ¼↓ ', 	'f♭ ¼↑ ',	'e♯ ¼↓ ',	'd𝄪 ¼↑ ',		'g𝄫 ¼↓ '				],
			['f♮',		'e♯',								'g𝄫'							],
			['f ¼↑ ',	'e♯ ¼↑ ',	'f♯ ¼↓ ',	'g♭ ¼↓ ',	'g𝄫 ¼↑ ',	'e𝄪 ¼↓ '				],
			['f♯',		'g♭',								'e𝄪'								],
			['g ¼↓ ',	'f♯ ¼↑ ',	'g♭ ¼↑ ',	'e𝄪 ¼↑ ',		'f𝄪 ¼↓ ',		'a𝄫 ¼↓ '				],
			['g♮',											'f𝄪',		'a𝄫'				],
			['g ¼↑ ',	'a♭ ¼↓ ',	'g♯ ¼↓ ',							'f𝄪 ¼↑ ',		'a𝄫 ¼↑ '	],
			['g♯',		'a♭'																],
			['a ¼↓ ',	'g♯ ¼↑ ',	'a♭ ¼↑ ',				'g𝄪 ¼↓ ',		'b𝄫 ¼↓ '				],
			['a♮',											'g𝄪',		'b𝄫'				]
		]
	}
	*prMusicSymbolClassLowerPart {
		^[
			['c♮',		'b♯',								'd𝄫'							],
			['c ¼↑ ',	'c♯ ¼↓ ',	'b♯ ¼↑ ',	'd♭ ¼↓ ',	'd𝄫 ¼↑ ',	'b𝄪 ¼↓ '				],
			['c♯',		'd♭',								'b𝄪'								],
			['d ¼↓ ',	'd♭ ¼↑ ',	'c♯ ¼↑ ',				'b𝄪 ¼↑ ',		'c𝄪 ¼↓ ',		'e𝄫 ¼↓ '	]
		]
	}
	*prMusicSymbolClassHigherPart {
		^[
			['a ¼↑ ',	'b♭ ¼↓ ',	'a♯ ¼↓ ',	'g𝄪 ¼↑ ',		'b𝄫 ¼↑ ',	'c𝄫 ¼↓ '				],
			['a♯',		'b♭',								'c𝄫'							],
			['b ¼↓ ',	'a♯ ¼↑ ',	'b♭ ¼↑ ',	'c♭ ¼↓ ',	'c𝄫 ¼↑ '							],
			['b♮',											'a𝄪',		'c♭'				],
			['b ¼↑ ',	'c ¼↓ ',		'c♭ ¼↑ ',	'b♯ ¼↓ ',	'a𝄪 ¼↑ ',		'd𝄫 ¼↓ '				]
		]
	}
	*prMusicSymbolOctaveLowerPart {
		^[
			['c♮',		'b♯l',								'd𝄫'							],
			['c ¼↑ ',	'c♯ ¼↓ ',	'b♯ ¼↑l',	'd♭ ¼↓ ',	'd𝄫 ¼↑ ',	'b𝄪 ¼↓l'				],
			['c♯',		'd♭',								'b𝄪l'							],
			['d ¼↓ ',	'd♭ ¼↑ ',	'c♯ ¼↑ ',				'b𝄪 ¼↑l',	'c𝄪 ¼↓ ',		'e𝄫 ¼↓ '	]
		]
	}
	*prMusicSymbolOctaveHigherPart {
		^[
			['a ¼↑ ',	'b♭ ¼↓ ',	'a♯ ¼↓ ',	'g𝄪 ¼↑ ',		'b𝄫 ¼↑ ',	'c𝄫 ¼↓h'			],
			['a♯',		'b♭',								'c𝄫h'							],
			['b ¼↓ ',	'a♯ ¼↑ ',	'b♭ ¼↑ ',	'c♭ ¼↓h',	'c𝄫 ¼↑h'						],
			['b♮',											'a𝄪',		'c♭h'				],
			['b ¼↑ ',	'c ¼↓h',	'c♭ ¼↑h',	'b♯ ¼↓ ',	'a𝄪 ¼↑ ',		'd𝄫 ¼↓h'			]
		]
	}
	*mus {
		arg pitchClassNum = nil;
		var quarterToneScale;
		quarterToneScale = PitchClassSet.prMusicSymbolClassLowerPart
		++ PitchClassSet.prMusicSymbolCommonPart
		++ PitchClassSet.prMusicSymbolClassHigherPart;
		^if (pitchClassNum==nil) {
			PitchClassSet.prMake(quarterToneScale)
		} {
			PitchClassSet.prMake(quarterToneScale)[pitchClassNum]
		}
	}
	*getName {
		arg which = 0;
		var pitchClassSets;
		pitchClassSets = PitchClassSet.ez.collect { |item, index|
			PitchClassSet.lily[index] ++ PitchClassSet.mus[index] ++ item };
		^pitchClassSets[which]
	}
	*getValue {
		arg which = \c;
		var pitchClass, pitchClassSets, valueFound, pitchClassKey;
		pitchClassSets = PitchClassSet.ez.collect { |item, index|
			PitchClassSet.lily[index] ++ PitchClassSet.mus[index] ++ item };
		pitchClassSets.do { |pitchClassSet|
			if (pitchClassSet.includes(which)) {
				pitchClassKey = {
					valueFound = pitchClassSets.findKeyForValue(pitchClassSet).asString;
					if ("(e|t)$".matchRegexp(valueFound)) {
						valueFound.replace("t", "10").replace("e", "11")
					} {
						valueFound.replace("t", "10.").replace("e", "11.")
					}
					.interpret
				}
			}
		};
		^pitchClassKey.()
	}
}

+ Float {
	pitchClassName {
		^PitchClassSet.getName(this)
	}
	pcname {
		^PitchClassSet.getName(this)
	}
}

+ Integer {
	pitchClassName {
		^PitchClassSet.getName(this.asFloat)
	}
	pcname {
		^PitchClassSet.getName(this.asFloat)
	}
}

+ Symbol {
	pitchClassNum {
		^PitchClassSet.getValue(this)
	}
	pitchClassName {
		^PitchClassSet.getName(this)
	}
	pcnum {
		^PitchClassSet.getValue(this)
	}
	pcname {
		^PitchClassSet.getName(this)
	}

}