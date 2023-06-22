package com.regexbuilder.sample;

import com.regexbuilder.ClassMatch;
import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebContentSamples {
	
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
	
	public static RegexBuilder ipV4() {
		
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		
		Group byteGroup = RegexFactory.alternativeGroup()
			.unique(RegexFactory.sequenceGroup().unique("25").unique(RegexFactory.classMatchRange('0', '5'))) // 250 to 255
			.unique(RegexFactory.sequenceGroup().unique("2").unique(RegexFactory.classMatchRange('0', '4')).unique(CharacterClass.Numeric)) // 200 to 249
			.unique(RegexFactory.sequenceGroup().optional(RegexFactory.classMatch('0','1')).optional(CharacterClass.Numeric).optional(CharacterClass.Numeric)); // 0 to 199
		
		regexBuilder.unique(byteGroup).unique(".").unique(byteGroup).unique(".").unique(byteGroup).unique(".").unique(byteGroup);
		
		return regexBuilder;
		
	}
	
	
}
