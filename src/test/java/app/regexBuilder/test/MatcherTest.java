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
	
	@Test
	public void validDate() {
		
		
		Assert.assertEquals("^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$", "");
	}
	
	@Test
	public void testRegexBuilderQuantity() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb.unique("0");
		rb.unique("2");
		rb.unique("4");
		
		Assert.assertEquals("024", rb.toString());
		
	}
	
	
	
	
	

}
