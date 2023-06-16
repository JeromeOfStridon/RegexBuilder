package com.regexbuilder.library;

import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;
import com.regexbuilder.ClassMatch.CharacterClass;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexNumberLibrary {
	
	public static RegexBuilder floatNumber() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		rb
			.some(CharacterClass.Numeric)
			.unique(".")
			.some(CharacterClass.Numeric);
		return rb;
		
	}
	
	public static RegexBuilder intNumber() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		rb.some(CharacterClass.Numeric);
		return rb;
		
	}
	



}
