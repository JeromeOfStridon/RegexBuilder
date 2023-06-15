package app.regexBuilder.library;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.RegexBuilder;
import app.regexBuilder.RegexFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexMiscLibrary {
	
	public static RegexBuilder colorHexCode() {
		RegexBuilder regex = RegexFactory.regexBuilder();
			
			regex
			.unique("#")
			.between(CharacterClass.Alphanumeric_Hexa, 6, 6);
		
		return regex;
	}
	

}
