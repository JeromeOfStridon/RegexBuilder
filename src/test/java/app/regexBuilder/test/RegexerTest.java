package app.regexBuilder.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group;
import app.regexBuilder.RegexBuilder;
import app.regexBuilder.library.NumberLibrary;
import app.regexBuilder.library.RegexerUtils;



public class RegexerTest {
	

	
	@Test
	public void htmlEntitySimple() {
		
		Group group = RegexBuilder.sequenceGroup()
			.unique("&")
			.any("amp;")
			.unique(RegexBuilder.alternativeGroup()
					.some(CharacterClass.Alphanumeric)
					.unique(RegexBuilder.sequenceGroup()
							.unique("#")
							.between(CharacterClass.Numeric, 1, 6))
					.unique(RegexBuilder.sequenceGroup()
							.unique("#x")
							.between(CharacterClass.Alphanumeric_Hexa, 1, 6))
					)
			.unique(";");
							
							
		String actual = group.toString();
		
		assertEquals("&(amp;)*([a-zA-Z0-9]+|#[0-9]{1,6}|#x[0-9a-fA-F]{1,6});", group.toString());
		
	}
	
	@Test
	public void regexNum() {
		
		RegexBuilder regex = RegexerUtils.regexNum();
		assertEquals("(\\*|\\+|\\?|\\{[0-9],[0-9]?\\}|\\{,[0-9]\\})", regex.toString());
		
	}
	
	@Test
	public void regexClass() {
		
		RegexBuilder regex = RegexerUtils.regexClass();
		
		String result = regex.toString();
		
		assertEquals("", regex.toString());
		
	}
	
	
	
	
	public void tagWithAttributes() {
		
		RegexBuilder regexer = new RegexBuilder();
		
		regexer
			.unique("<")
			.some(CharacterClass.Alphabetic)
			.unique(" ")
			.any(new RegexBuilder()
					.some(CharacterClass.Alphabetic)
					.unique("=")
					.any(new RegexBuilder()
							.unique("\"")
							//.some(CharacterClass.Any) // TODO : implement wildcard
							.unique("\"")
						)
				);
					
	}
	
	

}
