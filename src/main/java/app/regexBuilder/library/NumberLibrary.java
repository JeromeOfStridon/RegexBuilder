package app.regexBuilder.library;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.RegexBuilder;

public class NumberLibrary {
	
	public static RegexBuilder floatNumber() {
		
		RegexBuilder rb = new RegexBuilder();
		rb
			.some(CharacterClass.Numeric)
			.unique(".")
			.some(CharacterClass.Numeric);
		return rb;
		
	}
	
	public static RegexBuilder intNumber() {
		
		RegexBuilder rb = new RegexBuilder();
		rb.some(CharacterClass.Numeric);
		return rb;
		
	}
	



}
