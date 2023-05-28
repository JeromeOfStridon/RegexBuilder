package app.regexBuilder.library;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.RegexBuilder;
import app.regexBuilder.RegexFactory;

public class MiscLibrary {
	
	public static RegexBuilder colorHexCode() {
		RegexBuilder regex = RegexFactory.regexBuilder();
			
			regex
			.unique("#")
			.between(CharacterClass.Alphanumeric_Hexa, 6, 6);
		
		return regex;
	}
	

}
