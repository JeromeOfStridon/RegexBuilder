package app.regexBuilder.library;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import app.regexBuilder.ClassMatch;
import app.regexBuilder.Group;
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
	
	
	public static RegexBuilder htmlEntity() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb
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
							
		return rb;
		
	}
	
	
}
