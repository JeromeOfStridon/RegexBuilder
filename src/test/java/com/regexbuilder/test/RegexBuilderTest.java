package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group;
import com.regexbuilder.Group.GroupType;
import com.regexbuilder.Group.TreeType;
import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;



public class RegexBuilderTest {
	
	@Test
	public void emptyTest() {
		Regex test = RegexBuilder.regex();
		assertEquals("", test.toString());
	}
	
	@Test
	public void anchorsTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.anchorStart(true)
			.anchorEnd(true)
			.any("a");
		
		assertEquals("^a*$", regexBuilder.toString());
		assertTrue(regexBuilder.isAnchorStart());
		assertTrue(regexBuilder.isAnchorEnd());
		
		regexBuilder.setAnchorStart(false);
		assertFalse(regexBuilder.isAnchorStart());
		assertTrue(regexBuilder.isAnchorEnd());
		
		
		regexBuilder.setAnchorEnd(false);
		assertFalse(regexBuilder.isAnchorStart());
		assertFalse(regexBuilder.isAnchorEnd());
		
		
	}
	
	@Test
	public void testRegexBuilderTreeTypeConstructor() {
		Regex rb = RegexBuilder.regex(TreeType.Alternative);
		rb.unique("A").exactly("B", 2);
		
		assertEquals("A|B{2}", rb.toString());
	}
	
	@Test
	public void testRegexBuilderGroupTypeConstructor() {
		Regex rb = RegexBuilder.regex(GroupType.NonCapturing);
		rb.unique("A").exactly("B", 2);
		
		assertEquals("(?:AB{2})", rb.toString());
	}
	
	@Test
	public void testRegexBuilderTreeTypeGroupTypeConstructor() {
		Regex rb = RegexBuilder.regex(TreeType.Alternative, GroupType.NonCapturing);
		rb.unique("A").exactly("B", 2);
		
		assertEquals("(?:A|B{2})", rb.toString());
	}
	
	@Test
	public void testRegexBuilderMultiGroup() {

		Group group = RegexBuilder.sequenceGroup()
				.unique(CharacterClass.Alphabetic);
		
		Regex rb = RegexBuilder.regex(TreeType.Sequence);
		
		rb.exactly(group, 3);
		rb.unique("-");
		rb.exactly(group, 2);
		
		assertEquals("[a-zA-Z]{3}-[a-zA-Z]{2}", rb.toString());  
	}
	

}
