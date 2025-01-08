package com.regexbuilder.sample;

import com.regexbuilder.ClassMatch;
import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group;
import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebContentSamples {
	
	public static Regex email() {
		Regex regexBuilder = RegexBuilder.regex();
		
		ClassMatch acceptedChars = new ClassMatch()
				.add(CharacterClass.Alphanumeric)
				.add('%', '_', '-', '+');
		
		regexBuilder
			.some(acceptedChars)
			.optional(RegexBuilder.sequenceGroup()
					.unique(".")
					.some(acceptedChars))
			.unique("@")
			.some(RegexBuilder.sequenceGroup()
					.some(acceptedChars)
					.unique("."))
			.between(CharacterClass.Alphabetic, 2, 10);
		
		return regexBuilder;
	}
	
	
	public static Regex htmlEntity() {
		
		Regex regexBuilder = RegexBuilder.regex();
		
		regexBuilder
			.unique("&")
			.any("amp;")
			.unique(RegexBuilder.alternativeGroup()
					.some(CharacterClass.Alphanumeric)
					.unique(RegexBuilder.sequenceGroup()
							.unique("#")
							.between(CharacterClass.Numeric, 1, 6))
					.unique(RegexBuilder.sequenceGroup()
							.unique("#x")
							.between(CharacterClass.Alphanumeric_Hexa, 1, 6))
					)
			.unique(";");
							
		return regexBuilder;
		
	}
	
	public static Regex ipV4() {
		
		Regex regexBuilder = RegexBuilder.regex();
		
		Group byteGroup = RegexBuilder.alternativeGroup()
			.unique(RegexBuilder.sequenceGroup().unique("25").unique(RegexBuilder.classMatchRange('0', '5'))) // 250 to 255
			.unique(RegexBuilder.sequenceGroup().unique("2").unique(RegexBuilder.classMatchRange('0', '4')).unique(CharacterClass.Numeric)) // 200 to 249
			.unique(RegexBuilder.sequenceGroup().optional(RegexBuilder.classMatch('0','1')).optional(CharacterClass.Numeric).optional(CharacterClass.Numeric)); // 0 to 199
		
		regexBuilder.unique(byteGroup).unique(".").unique(byteGroup).unique(".").unique(byteGroup).unique(".").unique(byteGroup);
		
		return regexBuilder;
		
	}
	
	
}
