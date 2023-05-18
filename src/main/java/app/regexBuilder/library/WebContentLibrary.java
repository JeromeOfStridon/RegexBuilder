package app.regexBuilder.library;

import java.util.Arrays;
import java.util.List;

import app.regexBuilder.ClassMatch;
import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;

public class WebContentLibrary {
	
	public static RegexBuilder email() {
		RegexBuilder regex = new RegexBuilder();
		

		ClassMatch acceptedChars = new ClassMatch()
				.add(CharacterClass.Alphanumeric)
				.add('%', '_', '-', '+');
		
		regex
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
	
	
}
