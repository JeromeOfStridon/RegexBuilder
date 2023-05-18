package app.regexBuilder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;



public class BasicTest {
	
	
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
	public void anyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.any(CharacterClass.Alphabetic)
			.any(RegexBuilder.alternativeGroup().any("a").any("b"));
		
		Assert.assertEquals("[a-zA-Z]*(a*|b*)*", regexBuilder.toString());
	}
	
	
	@Test
	public void anyLazyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.anyLazy(CharacterClass.Alphabetic)
			.anyLazy(RegexBuilder.alternativeGroup().anyLazy("a").anyLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]*?(a*?|b*?)*?", regexBuilder.toString());
	}

	@Test
	public void someTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.some(CharacterClass.Alphabetic)
			.some(RegexBuilder.alternativeGroup().some("a").some("b"));
		
		Assert.assertEquals("[a-zA-Z]+(a+|b+)+", regexBuilder.toString());
	}

	
	@Test
	public void someLazyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.someLazy(CharacterClass.Alphabetic)
			.someLazy(RegexBuilder.alternativeGroup().someLazy("a").someLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]+?(a+?|b+?)+?", regexBuilder.toString());
	}
	
	@Test
	public void unique() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.unique(CharacterClass.Alphabetic)
			.unique(RegexBuilder.alternativeGroup().unique("a").unique("b"));
		
		Assert.assertEquals("[a-zA-Z](a|b)", regexBuilder.toString());
	}
	
	@Test
	public void optionalTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.optional(CharacterClass.Alphabetic)
			.optional(RegexBuilder.alternativeGroup().optional("a").optional("b"));
		
		Assert.assertEquals("[a-zA-Z]?(a?|b?)?", regexBuilder.toString());
	}
	
	@Test
	public void optionalLazyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.optionalLazy(CharacterClass.Alphabetic)
			.optionalLazy(RegexBuilder.alternativeGroup().optionalLazy("a").optionalLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]??(a??|b??)??", regexBuilder.toString());
	}
	
	@Test
	public void minTest() {
		
	}
	
	@Test
	public void minLazyTest() {
		
	}
	
	@Test
	public void maxTest() {
		
	}
	
	@Test
	public void maxLazyTest() {
		
	}
	
	@Test
	public void betweenTest() {
		
	}
	
	@Test
	public void betweenLazyTest() {
		
	}
	
	
	
	

	
	@Test
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
