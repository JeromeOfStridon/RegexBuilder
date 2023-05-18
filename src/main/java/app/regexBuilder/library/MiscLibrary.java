package app.regexBuilder.library;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;

public class MiscLibrary {
	
	public static RegexBuilder colorHexCode() {
		RegexBuilder regex = new RegexBuilder();
			
			regex
			.unique("#")
			.between(CharacterClass.Alphanumeric_Hexa, 1, 6);
		
		return regex;
	}
	

}
