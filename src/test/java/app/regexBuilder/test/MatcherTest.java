package app.regexBuilder.test;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.RegexMatcher;
import app.regexBuilder.ClassMatch.CharacterClass;



public class MatcherTest {
	
	@Test
	public void Pattern() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb
			.unique(CharacterClass.Alphabetic)
			.any(RegexBuilder.classMatch(CharacterClass.Alphanumeric).add('_').add('-'));
		
		RegexMatcher rm = new RegexMatcher(rb, "");
		

		
		
		
	}
	
	//@Test
	public void intersection() {
		RegexBuilder rb = new RegexBuilder();
		rb.unique(RegexBuilder.classMatch(CharacterClass.Word));
		
		Assert.assertEquals("[\\w&&[^\\d]]", rb.toString());
	}
	

	

	
	
	
	
	

}
