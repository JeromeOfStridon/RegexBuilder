package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexMatcher;
import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group.TreeType;
import com.regexbuilder.RegexMatcher.RegexMatch;



public class RegexMatcherTest {
	
	

	@Test
	public void startEndTest() {
		
		Regex regex = RegexBuilder.regex()
			.unique(RegexBuilder.sequenceGroup().some(CharacterClass.Numeric).setName("integer"))
			.unique(RegexBuilder.alternativeGroup().unique(".").unique(",").setName("separator"))
			.unique(RegexBuilder.sequenceGroup().some(CharacterClass.Numeric).setName("decimal"));
				
		RegexMatcher regexMatcher = RegexBuilder.regexMatcher(regex, "test 13.37 regexer 1");
		
		assertNull(regexMatcher.group());
		assertNull(regexMatcher.group(1));
		assertNull(regexMatcher.group("integer"));
		assertNull(regexMatcher.start());
		assertNull(regexMatcher.start(1));
		assertNull(regexMatcher.start("integer"));
		assertNull(regexMatcher.end());
		assertNull(regexMatcher.end(1));
		assertNull(regexMatcher.end("integer"));
		assertNull(regexMatcher.getMatchs());
		assertNull(regexMatcher.getMatch(0));
		assertNull(regexMatcher.getMatch("integer"));
		assertNull(regexMatcher.replace("integer", "0"));

		
		
		regexMatcher.find();
		
		assertEquals("13.37", regexMatcher.group());
		
		assertEquals(5, (int) regexMatcher.start());
		assertEquals(10, (int) regexMatcher.end());
		assertEquals((Integer) 5, regexMatcher.start("integer"));
		assertEquals((Integer) 7, regexMatcher.end("integer"));
		assertEquals((Integer) 8, regexMatcher.start("decimal"));
		assertEquals((Integer) 10, regexMatcher.end("decimal"));
		assertEquals((Integer) 13, regexMatcher.groupAsInteger("integer"));
		assertEquals((Float) 37F, regexMatcher.groupAsFloat("decimal"));
		
		assertEquals(3, regexMatcher.groupContentMap().size());
		
		assertNull(regexMatcher.groupAsInteger("wrong name"));
		assertNull(regexMatcher.groupAsFloat("wrong name"));
		
		assertNull(regexMatcher.start("wrong name"));
		assertNull(regexMatcher.end("wrong name"));
		
		assertEquals("test 13,37 regexer 1", regexMatcher.replace("separator", ","));
		
		assertEquals(3, regexMatcher.groupCount());
		assertEquals("13.37", regexMatcher.getMatchs().get(0).group);
		assertEquals(5, regexMatcher.getMatchs().get(0).start);
		assertEquals(5, (int) regexMatcher.start(0));
		assertEquals(10, regexMatcher.getMatchs().get(0).end);
		assertEquals(10, (int) regexMatcher.end(0));
		
		assertEquals("13", regexMatcher.getMatchs().get(1).group);
		assertEquals("integer", regexMatcher.getMatchs().get(1).name);
		assertEquals(5, regexMatcher.getMatchs().get(1).start);
		assertEquals(5, (int) regexMatcher.start(1));
		assertEquals(7, regexMatcher.getMatchs().get(1).end);
		assertEquals(7, (int) regexMatcher.end(1));
		assertEquals("13", regexMatcher.getMatch("integer").group);
		
		
		assertEquals(".", regexMatcher.getMatchs().get(2).group);
		assertEquals("separator", regexMatcher.getMatchs().get(2).name);
		assertEquals(7, regexMatcher.getMatchs().get(2).start);
		assertEquals(7, (int) regexMatcher.start(2));
		assertEquals(8, regexMatcher.getMatchs().get(2).end);
		assertEquals(8, (int) regexMatcher.end(2));
		assertEquals(".", regexMatcher.getMatch("separator").group);

		
		assertEquals("37", regexMatcher.getMatchs().get(3).group);
		assertEquals("decimal", regexMatcher.getMatchs().get(3).name);
		assertEquals(8, regexMatcher.getMatchs().get(3).start);
		assertEquals(8, (int) regexMatcher.start(3));
		assertEquals(10, regexMatcher.getMatchs().get(3).end);
		assertEquals(10, (int) regexMatcher.end(3));
		assertEquals("37", regexMatcher.getMatch("decimal").group);
				
	}
	
	
	@Test
	public void exceptionTest() {
		
		Regex regex = RegexBuilder.regex();
		
		RegexMatcher rm = RegexBuilder.regexMatcher(regex, "");
		
		assertNull(rm.group());
		rm.find();
		assertNotNull(rm.group());
		
	}
	
	@Test
	public void debugTest() {
		
		Regex regex = RegexBuilder.regex()
			.some("a")
			.some("b")
			.some("c");
		
		RegexMatcher rm1 = RegexBuilder.regexMatcher(regex, "aaabbbbbddd");
		
		RegexMatcher debugedRb = rm1.debug();
		
		assertEquals("a+b+", debugedRb.getRegex().toString());
		
		RegexMatcher rm2 = RegexBuilder.regexMatcher(regex, "eee");
		assertNull(rm2.debug());
		
	}
	
	@Test
	public void anchorSequenceTest() {
		Regex regex = RegexBuilder.regex().anchorStart(true)
			.unique(RegexBuilder.sequenceGroup().setName("A").some(CharacterClass.Alphabetic))
			.unique(RegexBuilder.sequenceGroup().setName("1").some(CharacterClass.Numeric));
		
		RegexMatcher matcher = RegexBuilder.regexMatcher(regex, "A1");
		matcher.find();
		
		assertEquals("A", matcher.group("A"));
		assertEquals("1", matcher.group("1"));
		
		
	}
	
	@Test
	public void rawBuilderMatcherTest() {
		
		Regex regex = RegexBuilder.regex()
				.unique("AAA")
				.setName("group");
		
		assertEquals("(AAA)", regex.toString());
		
	}
	
	@Test
	public void toStringTest() {
	
		Regex regex = RegexBuilder.regex();
		regex.unique("AA");
		
		RegexMatcher rm = RegexBuilder.regexMatcher(regex, "AA");
		System.out.println(rm.toString());
		rm.find();
		assertTrue(rm.toString().startsWith("RegexMatcher("));
		
		RegexMatch regexMatch = rm.getMatch(0);
		System.out.println(regexMatch.toString());
		assertTrue(regexMatch.toString().startsWith("RegexMatcher.RegexMatch("));
	}
	
	
	@Test
	public void anonymousGroupTest() {
		
		Regex regex = RegexBuilder.regex(TreeType.Alternative)
				.unique("A")
				.unique("B")
				.unique("C")
				.setQuantity(1, 2);
		
		RegexMatcher rm = RegexBuilder.regexMatcher(regex, "BA");
		
		rm.find();
		
		assertEquals("BA", rm.group());
		assertEquals("A", rm.group(1));
		
	}
	
	
	@Test
	public void nullTreeTypeTest() {
		
		Regex regex = RegexBuilder.regex()
				.setTreeType(null)
				.unique("A")
				.unique("B")
				.unique("C")
				.setQuantity(1, 2);
		
		assertEquals("", regex.toString());
		
	}
	
	@Test
	public void stringMatchGroupTest() {
		Regex regex = RegexBuilder.regex()
				.exactly("ABC", 3);
		
		RegexMatcher rm = RegexBuilder.regexMatcher(regex, "ABCABCABC");
		rm.find();
		
		assertEquals("ABCABCABC", rm.group());
		
	}
	
	

}
