package app.regexBuilder.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import app.regexBuilder.Group;
import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.library.Library;



public class RegexerTest {
	
	@Test
	public void email() {
		
		RegexBuilder emailRegexer = new RegexBuilder();
			
		emailRegexer
			.some(CharacterClass.Alphanumeric)
			.unique("@")
			.some(CharacterClass.Alphanumeric)
			.unique(".")
			.min(CharacterClass.Alphabetic, 3);
		
		String actual = emailRegexer.toString();

		assertEquals("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{3,}", emailRegexer.toString());
		
	}
	
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
		
		RegexBuilder regex = Library.regexNum();
		assertEquals("(\\*|\\+|\\?|\\{[0-9],[0-9]?\\}|\\{,[0-9]\\})", regex.toString());
		
	}
	
	@Test
	public void regexClass() {
		
		RegexBuilder regex = Library.regexClass();
		
		String result = regex.toString();
		
		assertEquals("", regex.toString());
		
	}
	
	public void website() {
		RegexBuilder regexer = new RegexBuilder();
		
		List<String> domains = Arrays.asList("com", "net", "org", "paris");
		
		regexer
			.optional("https://")
			.some(new RegexBuilder()
					.some(CharacterClass.Alphanumeric)
					.unique("."))
			.unique(RegexBuilder.alternativeGroup(domains));
			
					
					
			
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
