package app.regexBuilder.library;

import app.regexBuilder.ClassMatch;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.RegexBuilder;
import app.regexBuilder.RegexFactory;

public class RegexWebContentLibrary {
	
	public static RegexBuilder email() {
		RegexBuilder regex = RegexFactory.regexBuilder();
		

		ClassMatch acceptedChars = new ClassMatch()
				.add(CharacterClass.Alphanumeric)
				.add('%', '_', '-', '+');
		
		regex
			.some(acceptedChars)
			.optional(RegexFactory.sequenceGroup()
					.unique(".")
					.some(acceptedChars))
			.unique("@")
			.some(RegexFactory.sequenceGroup()
					.some(acceptedChars)
					.unique("."))
			.between(CharacterClass.Alphabetic, 2, 10);
		
		return regex;
	}
	
	
	public static RegexBuilder htmlEntity() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		
		rb
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
							
		return rb;
		
	}
	
	
}
