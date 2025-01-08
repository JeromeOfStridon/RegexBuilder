package com.regexbuilder.sample;

import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.ClassMatch.CharacterClass;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NumberSamples {
	
	public static Regex floatNumber() {
		
		Regex rb = RegexBuilder.regex();
		rb
			.some(CharacterClass.Numeric)
			.unique(".")
			.some(CharacterClass.Numeric);
		return rb;
		
	}
	
	public static Regex intNumber() {
		
		Regex rb = RegexBuilder.regex();
		rb.some(CharacterClass.Numeric);
		return rb;
		
	}
	



}
