package app.regexBuilder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;



public class BasicTest {
	
	
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
	public void anyLazyTests() {
		
		RegexBuilder anyLazyRegexBuilder = new RegexBuilder();
		anyLazyRegexBuilder
			.anyLazy(CharacterClass.Alphabetic)
			.anyLazy(RegexBuilder.alternativeGroup().anyLazy("a").anyLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]*?(a*?|b*?)*?", anyLazyRegexBuilder.toString());
	}
	
	
	@Test
	public void someLazyTests() {
		
		RegexBuilder someLazyRegexBuilder = new RegexBuilder();
		someLazyRegexBuilder
			.someLazy(CharacterClass.Alphabetic)
			.someLazy(RegexBuilder.alternativeGroup().someLazy("a").someLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]+?(a+?|b+?)+?", someLazyRegexBuilder.toString());
	}
	
	@Test
	public void optionalLazyTests() {
		
		RegexBuilder optionalLazyRegexBuilder = new RegexBuilder();
		optionalLazyRegexBuilder
			.optionalLazy(CharacterClass.Alphabetic)
			.optionalLazy(RegexBuilder.alternativeGroup().optionalLazy("a").optionalLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]??(a??|b??)??", optionalLazyRegexBuilder.toString());
	}
	
	
	
	

	
	//@Test
	public void intersection() {
		RegexBuilder rb = new RegexBuilder();
		rb.unique(RegexBuilder.classMatch(CharacterClass.Word));
		
		Assert.assertEquals("[\\w&&[^\\d]]", rb.toString());
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
