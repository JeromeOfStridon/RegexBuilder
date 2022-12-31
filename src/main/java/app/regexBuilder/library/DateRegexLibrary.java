package app.regexBuilder.library;

import app.regexBuilder.RegexBuilder;

import java.util.List;

import app.regexBuilder.ClassMatch.CharacterClass;

public class DateRegexLibrary {
	
	public static RegexBuilder fullWrittenDate() {
		RegexBuilder regex = new RegexBuilder();
			
			regex
			.unique(RegexBuilder.sequenceGroup().captureAs("Day")
				.unique( RegexBuilder.classMatch('1', '9'))
				.optional(RegexBuilder.classMatch(CharacterClass.Numeric))
				)
			.unique(CharacterClass.Space)
			
			.unique(RegexBuilder.alternativeGroup(List.of("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "décembre")))
			.optional(CharacterClass.Space)
			.optional(humanYear());
		
		return regex;
	}
	
	public static RegexBuilder humanYear() {
		RegexBuilder regex = new RegexBuilder();
		
		regex
			.unique(RegexBuilder.alternativeGroup(List.of("19", "20")))
			.between(CharacterClass.Numeric, 2, 2);
			
		return regex;
		
	}
		
	

}
