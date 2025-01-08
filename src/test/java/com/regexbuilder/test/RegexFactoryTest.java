package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.Group;
import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group.GroupType;
import com.regexbuilder.Group.TreeType;



public class RegexFactoryTest {
	
	

	
	@Test
	public void regexQuantityPattern() {
		
		Regex quantityPattern = RegexBuilder.regex();
		quantityPattern
			.unique("{")
			.optional(RegexBuilder.sequenceGroup().setName("minSize").some(CharacterClass.Numeric))
			.unique(",")
			.optional(RegexBuilder.sequenceGroup().setName("maxSize").some(CharacterClass.Numeric))
			.unique("}");
		
		Assert.assertEquals("\\{([0-9]+)?,([0-9]+)?\\}", quantityPattern.toString());
		
	}
	
	
	@Test
	public void alternativeGroupTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder.unique(RegexBuilder.alternativeGroup("abc", "def", "ghi"));
		
		assertEquals("(abc|def|ghi)", regexBuilder.toString());
	}
	
	
	
	@Test
	public void multipleLookAhead() {
		Regex rb = RegexBuilder.regex();
		rb
			.unique(RegexBuilder.sequenceGroup().setGroupType(GroupType.PositiveLookAhead).unique("abc"))
			.some(CharacterClass.AlphabeticUpper);

		Assert.assertEquals("(?=abc)[A-Z]+", rb.toString());
		
		
	}
	
	
	@Test
	public void manualAlternativeGroup() {
		Group alternative = RegexBuilder.sequenceGroup();

		alternative.unique("A");
		alternative.unique("B");
		
		alternative.setTreeType(TreeType.Alternative);
		
		assertEquals("(A|B)", alternative.toString());

	}
	
	
	
	
	
	

}
