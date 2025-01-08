package com.regexbuilder.sample;

import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.ClassMatch.CharacterClass;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MiscSamples {
	
	public static Regex colorHexCode() {
		Regex regex = RegexBuilder.regex();
			
			regex
			.unique("#")
			.between(CharacterClass.Alphanumeric_Hexa, 6, 6);
		
		return regex;
	}
	

}
