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
				
		ClassMatch acceptedChars = new ClassMatch()
				.add(CharacterClass.Alphanumeric)
				.add('%', '_', '-', '+');
		
		Regex regex = RegexBuilder.regex()
			.some(acceptedChars)
			.optional(RegexBuilder.sequenceGroup()
					.unique(".")
					.some(acceptedChars))
			.unique("@")
			.some(RegexBuilder.sequenceGroup()
					.some(acceptedChars)
					.unique("."))
			.between(CharacterClass.Alphabetic, 2, 10);
		
		return regex;
	}
	
	
	public static Regex htmlEntity() {
		
		return RegexBuilder.regex()
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
							
		
		
	}
	
	public static Regex ipV4() {
		
		
		Group byteGroup = RegexBuilder.alternativeGroup()
			.unique(RegexBuilder.sequenceGroup().unique("25").unique(RegexBuilder.classMatchRange('0', '5'))) // 250 to 255
			.unique(RegexBuilder.sequenceGroup().unique("2").unique(RegexBuilder.classMatchRange('0', '4')).unique(CharacterClass.Numeric)) // 200 to 249
			.unique(RegexBuilder.sequenceGroup().optional(RegexBuilder.classMatch('0','1')).optional(CharacterClass.Numeric).optional(CharacterClass.Numeric)); // 0 to 199
		
		return RegexBuilder.regex().unique(byteGroup).unique(".").unique(byteGroup).unique(".").unique(byteGroup).unique(".").unique(byteGroup);
		
		
	}
	
	public static Regex ipV6() {
		
		
		Group byteGroup = RegexBuilder.alternativeGroup()
			.unique(RegexBuilder.sequenceGroup().unique("25").unique(RegexBuilder.classMatchRange('0', '5'))) // 250 to 255
			.unique(RegexBuilder.sequenceGroup().unique("2").unique(RegexBuilder.classMatchRange('0', '4')).unique(CharacterClass.Numeric)) // 200 to 249
			.unique(RegexBuilder.sequenceGroup().optional(RegexBuilder.classMatch('0','1')).optional(CharacterClass.Numeric).optional(CharacterClass.Numeric)); // 0 to 199
		
		return RegexBuilder.regex().unique(byteGroup).unique(".").unique(byteGroup).unique(".").unique(byteGroup).unique(".").unique(byteGroup);
		
		
	}
	
	
}
