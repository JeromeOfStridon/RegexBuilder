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



public class RegexBuilderTest {
	
	

	
	@Test
	public void regexQuantityPattern() {
		
		Regex quantityPattern = RegexBuilder.regex()
			.unique("{")
			.optional(RegexBuilder.sequenceGroup().setName("minSize").some(CharacterClass.Numeric))
			.unique(",")
			.optional(RegexBuilder.sequenceGroup().setName("maxSize").some(CharacterClass.Numeric))
			.unique("}");
		
		Assert.assertEquals("\\{([0-9]+)?,([0-9]+)?\\}", quantityPattern.toString());
		
	}
	
	
	@Test
	public void alternativeGroupTest() {
		Regex regex = RegexBuilder.regex()
				.unique(RegexBuilder.alternativeGroup("abc", "def", "ghi"));
		
		assertEquals("(abc|def|ghi)", regex.toString());
	}
	
	
	
	@Test
	public void multipleLookAhead() {
		Regex regex = RegexBuilder.regex()
			.unique(RegexBuilder.sequenceGroup().setGroupType(GroupType.PositiveLookAhead).unique("abc"))
			.some(CharacterClass.AlphabeticUpper);

		Assert.assertEquals("(?=abc)[A-Z]+", regex.toString());
		
		
	}
	
	
	@Test
	public void manualAlternativeGroup() {
		Group alternative = RegexBuilder.sequenceGroup()
				.unique("A")
				.unique("B")
				.setTreeType(TreeType.Alternative);
		
		assertEquals("(A|B)", alternative.toString());

	}
	
	
	
	
	
	

}
