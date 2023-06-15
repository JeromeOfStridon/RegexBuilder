package app.regexBuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.TreeType;
import app.regexBuilder.Group.GroupType;



public class RegexFactoryTest {
	
	

	
	@Test
	public void regexQuantityPattern() {
		
		RegexBuilder quantityPattern = RegexFactory.regexBuilder();
		quantityPattern
			.unique("{")
			.optional(RegexFactory.sequenceGroup().setName("minSize").some(CharacterClass.Numeric))
			.unique(",")
			.optional(RegexFactory.sequenceGroup().setName("maxSize").some(CharacterClass.Numeric))
			.unique("}");
		
		Assert.assertEquals("\\{([0-9]+)?,([0-9]+)?\\}", quantityPattern.toString());
		
	}
	
	
	@Test
	public void alternativeGroupTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder.unique(RegexFactory.alternativeGroup("abc", "def", "ghi"));
		
		assertEquals("(abc|def|ghi)", regexBuilder.toString());
	}
	
	
	
	@Test
	public void multipleLookAhead() {
		RegexBuilder rb = RegexFactory.regexBuilder();
		rb
			.unique(RegexFactory.sequenceGroup().setGroupType(GroupType.PositiveLookAhead).unique("abc"))
			.some(CharacterClass.AlphabeticUpper);

		Assert.assertEquals("(?=abc)[A-Z]+", rb.toString());
		
		
	}
	
	
	@Test
	public void manualAlternativeGroup() {
		Group alternative = RegexFactory.sequenceGroup();

		alternative.unique("A");
		alternative.unique("B");
		
		alternative.setTreeType(TreeType.Alternative);
		
		assertEquals("(A|B)", alternative.toString());

	}
	
	
	
	
	
	

}
