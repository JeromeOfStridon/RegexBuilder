package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group;
import com.regexbuilder.Group.GroupType;
import com.regexbuilder.Group.TreeType;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;



public class RegexBuilderTest {
	
	@Test
	public void emptyTest() {
		RegexBuilder test = RegexFactory.regexBuilder();
		assertEquals("", test.toString());
	}
	
	@Test
	public void anchorsTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
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
		RegexBuilder rb = RegexFactory.regexBuilder(TreeType.Alternative);
		rb.unique("A").exactly("B", 2);
		
		assertEquals("A|B{2}", rb.toString());
	}
	
	@Test
	public void testRegexBuilderGroupTypeConstructor() {
		RegexBuilder rb = RegexFactory.regexBuilder(GroupType.NonCapturing);
		rb.unique("A").exactly("B", 2);
		
		assertEquals("(?:AB{2})", rb.toString());
	}
	
	@Test
	public void testRegexBuilderTreeTypeGroupTypeConstructor() {
		RegexBuilder rb = RegexFactory.regexBuilder(TreeType.Alternative, GroupType.NonCapturing);
		rb.unique("A").exactly("B", 2);
		
		assertEquals("(?:A|B{2})", rb.toString());
	}
	
	@Test
	public void testRegexBuilderMultiGroup() {

		Group group = RegexFactory.sequenceGroup()
				.unique(CharacterClass.Alphabetic);
		
		RegexBuilder rb = RegexFactory.regexBuilder(TreeType.Sequence);
		
		rb.exactly(group, 3);
		rb.unique("-");
		rb.exactly(group, 2);
		
		assertEquals("[a-zA-Z]{3}-[a-zA-Z]{2}", rb.toString());  
	}
	

}
