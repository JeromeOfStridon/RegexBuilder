package app.regexBuilder.library;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.RegexBuilder;
import app.regexBuilder.RegexFactory;

public class NumberLibrary {
	

	
	
	
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
