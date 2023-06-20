package com.regexbuilder.samples;

import com.regexbuilder.ClassMatch;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;
import com.regexbuilder.ClassMatch.CharacterClass;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexWebContentLibrary {
	
	public static RegexBuilder email() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		
		ClassMatch acceptedChars = new ClassMatch()
				.add(CharacterClass.Alphanumeric)
				.add('%', '_', '-', '+');
		
		regexBuilder
			.some(acceptedChars)
			.optional(RegexFactory.sequenceGroup()
					.unique(".")
					.some(acceptedChars))
			.unique("@")
			.some(RegexFactory.sequenceGroup()
					.some(acceptedChars)
					.unique("."))
			.between(CharacterClass.Alphabetic, 2, 10);
		
		return regexBuilder;
	}
	
	
	public static RegexBuilder htmlEntity() {
		
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		
		regexBuilder
			.unique("&")
			.any("amp;")
			.unique(RegexFactory.alternativeGroup()
					.some(CharacterClass.Alphanumeric)
					.unique(RegexFactory.sequenceGroup()
							.unique("#")
							.between(CharacterClass.Numeric, 1, 6))
					.unique(RegexFactory.sequenceGroup()
							.unique("#x")
							.between(CharacterClass.Alphanumeric_Hexa, 1, 6))
					)
			.unique(";");
							
		return regexBuilder;
		
	}
	
	
}
