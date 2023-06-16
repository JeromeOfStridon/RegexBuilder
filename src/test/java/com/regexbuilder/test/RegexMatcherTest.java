package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;
import com.regexbuilder.RegexMatcher;
import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group.TreeType;
import com.regexbuilder.RegexMatcher.RegexMatch;



public class RegexMatcherTest {
	
	

	@Test
	public void startEndTest() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();

		rb
			.unique(RegexFactory.sequenceGroup().some(CharacterClass.Numeric).setName("integer"))
			.unique(RegexFactory.alternativeGroup().unique(".").unique(",").setName("separator"))
			.unique(RegexFactory.sequenceGroup().some(CharacterClass.Numeric).setName("decimal"));
				
		RegexMatcher rm = RegexFactory.regexMatcher(rb, "test 13.37 regexer 1");
		
		assertNull(rm.group());
		assertNull(rm.group(1));
		assertNull(rm.group("integer"));
		assertNull(rm.start());
		assertNull(rm.start(1));
		assertNull(rm.start("integer"));
		assertNull(rm.end());
		assertNull(rm.end(1));
		assertNull(rm.end("integer"));

		
		
		rm.find();
		
		assertEquals("13.37", rm.group());
		
		assertEquals(5, (int) rm.start());
		assertEquals(10, (int) rm.end());
		assertEquals((Integer) 5, rm.start("integer"));
		assertEquals((Integer) 7, rm.end("integer"));
		assertEquals((Integer) 8, rm.start("decimal"));
		assertEquals((Integer) 10, rm.end("decimal"));
		assertEquals((Integer) 13, rm.groupAsInteger("integer"));
		assertEquals((Float) 37F, rm.groupAsFloat("decimal"));
		
		assertEquals(3, rm.groupsAsMap().size());
		
		assertNull(rm.groupAsInteger("wrong name"));
		assertNull(rm.groupAsFloat("wrong name"));
		
		assertNull(rm.start("wrong name"));
		assertNull(rm.end("wrong name"));
		
		assertEquals("test 13,37 regexer 1", rm.replace("separator", ","));
		
		assertEquals(3, rm.groupCount());
		assertEquals("13.37", rm.getMatchs().get(0).group);
		assertEquals(5, rm.getMatchs().get(0).start);
		assertEquals(5, (int) rm.start(0));
		assertEquals(10, rm.getMatchs().get(0).end);
		assertEquals(10, (int) rm.end(0));
		
		assertEquals("13", rm.getMatchs().get(1).group);
		assertEquals("integer", rm.getMatchs().get(1).name);
		assertEquals(5, rm.getMatchs().get(1).start);
		assertEquals(5, (int) rm.start(1));
		assertEquals(7, rm.getMatchs().get(1).end);
		assertEquals(7, (int) rm.end(1));
		
		
		assertEquals(".", rm.getMatchs().get(2).group);
		assertEquals("separator", rm.getMatchs().get(2).name);
		assertEquals(7, rm.getMatchs().get(2).start);
		assertEquals(7, (int) rm.start(2));
		assertEquals(8, rm.getMatchs().get(2).end);
		assertEquals(8, (int) rm.end(2));
		
		assertEquals("37", rm.getMatchs().get(3).group);
		assertEquals("decimal", rm.getMatchs().get(3).name);
		assertEquals(8, rm.getMatchs().get(3).start);
		assertEquals(8, (int) rm.start(3));
		assertEquals(10, rm.getMatchs().get(3).end);
		assertEquals(10, (int) rm.end(3));
				
	}
	
	
	@Test
	public void exceptionTest() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		
		RegexMatcher rm = RegexFactory.regexMatcher(rb, "");
		
		assertNull(rm.group());
		rm.find();
		assertNotNull(rm.group());
		
	}
	
	@Test
	public void debugTest() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		rb
			.some("a")
			.some("b")
			.some("c");
		
		RegexMatcher rm1 = RegexFactory.regexMatcher(rb, "aaabbbbbddd");
		
		RegexMatcher debugedRb = rm1.debug();
		
		assertEquals("a+b+", debugedRb.getRegexBuilder().toString());
		
		RegexMatcher rm2 = RegexFactory.regexMatcher(rb, "eee");
		assertNull(rm2.debug());
		
	}
	
	@Test
	public void anchorSequenceTest() {
		RegexBuilder rb = RegexFactory.regexBuilder().anchorStart(true);
		rb
			.unique(RegexFactory.sequenceGroup().setName("A").some(CharacterClass.Alphabetic))
			.unique(RegexFactory.sequenceGroup().setName("1").some(CharacterClass.Numeric));
		
		RegexMatcher matcher = RegexFactory.regexMatcher(rb, "A1");
		matcher.find();
		
		assertEquals("A", matcher.group("A"));
		assertEquals("1", matcher.group("1"));
		
		
	}
	
	@Test
	public void rawBuilderMatcherTest() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		
		rb.unique("AAA");
		rb.setName("group");
		
		assertEquals("(AAA)", rb.toString());
		
	}
	
	@Test
	public void toStringTest() {
	
		RegexBuilder rb = RegexFactory.regexBuilder();
		rb.unique("AA");
		
		RegexMatcher rm = RegexFactory.regexMatcher(rb, "AA");
		System.out.println(rm.toString());
		rm.find();
		assertTrue(rm.toString().startsWith("RegexMatcher("));
		
		RegexMatch regexMatch = rm.getMatch(0);
		System.out.println(regexMatch.toString());
		assertTrue(regexMatch.toString().startsWith("RegexMatcher.RegexMatch("));
	}
	
	
	@Test
	public void anonymousGroupTest() {
		
		RegexBuilder rb = RegexFactory.regexBuilder(TreeType.Alternative);
		rb.unique("A").unique("B").unique("C").setQuantity(1, 2);
		
		RegexMatcher rm = RegexFactory.regexMatcher(rb, "BA");
		
		rm.find();
		
		assertEquals("BA", rm.group());
		assertEquals("A", rm.group(1));
		
	}
	
	
	@Test
	public void nullTreeTypeTest() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		rb.setTreeType(null);
		rb.unique("A").unique("B").unique("C").setQuantity(1, 2);
		
		assertEquals("", rb.toString());
		
	}
	
	@Test
	public void stringMatchGroupTest() {
		RegexBuilder rb = RegexFactory.regexBuilder();
		rb.exactly("ABC", 3);
		
		RegexMatcher rm = RegexFactory.regexMatcher(rb, "ABCABCABC");
		rm.find();
		
		assertEquals("ABCABCABC", rm.group());
		
	}
	
	

}
