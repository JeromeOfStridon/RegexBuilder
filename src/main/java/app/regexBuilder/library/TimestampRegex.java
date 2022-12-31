package app.regexBuilder.library;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;

public class TimestampRegex {
	
	public static RegexBuilder timestampRegex() {
		
		RegexBuilder rb = new RegexBuilder();
		rb
			.between(CharacterClass.Numeric, 4, 4) 	// Year
			.unique("-")
			.between(CharacterClass.Numeric, 2, 2) 	// Month
			.unique("-")
			.between(CharacterClass.Numeric, 2, 2) 	// Day
			.unique("T")
			.between(CharacterClass.Numeric, 2, 2) 	// Hour
			.unique(":")
			.between(CharacterClass.Numeric, 2, 2) 	// Minute
			.unique(":")
			.between(CharacterClass.Numeric, 2, 2) 	// Second
			.unique(".")
			.any(CharacterClass.Numeric) 			// Millisecond
			.unique("Z");
			
		//System.out.println(rb.toString());
		
		return rb;
		
	}

}
