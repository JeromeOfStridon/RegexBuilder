package app.regexBuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;



public class RegexBuilderTest {
	
	
	@Test
	public void emptyTest() {
		RegexBuilder test = new RegexBuilder();
		assertEquals("", test.toString());
	}
	
	@Test
	public void anchorsTest() {
		RegexBuilder test1 = new RegexBuilder();
		test1
			.anchorStart(true)
			.anchorEnd(true)
			.any("a");
		
		Assert.assertEquals("^(a*)$", test1.toString());
		
	}
	
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
	
	
	
	

	
	@Test
	public void multipleLookAhead() {
		RegexBuilder rb = new RegexBuilder();
		rb
			.unique(RegexBuilder.sequenceGroup().setGroupType(GroupType.PositiveLookAhead).unique("abc"))
			.some(CharacterClass.AlphabeticUpper);

		Assert.assertEquals("(?=abc)[A-Z]+", rb.toString());
		
		
	}
	
	
	
	
	
	

}
