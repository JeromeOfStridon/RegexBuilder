package app.regexBuilder.test;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;



public class RegexBuilderTest {
	
	@Test
	public void regexQuantityPattern() {
		
		RegexBuilder quantityPattern = new RegexBuilder();
		quantityPattern
			.unique("{")
			.optional(RegexBuilder.sequenceGroup().captureAs("minSize").some(CharacterClass.Numeric))
			.unique(",")
			.optional(RegexBuilder.sequenceGroup().captureAs("maxSize").some(CharacterClass.Numeric))
			.unique("}");
		
		Assert.assertEquals("\\{([0-9]+)?,([0-9]+)?\\}", quantityPattern.toString());
		
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
	
	
	@Test
	public void firstTest() {
		
	}
	
	
	
	

}
