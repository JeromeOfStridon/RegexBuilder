package com.regexbuilder.samples;

import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;
import com.regexbuilder.ClassMatch.CharacterClass;

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
