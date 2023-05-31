package app.regexBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import app.regexBuilder.ClassMatch.CharacterClass;



public class RegexMatcherTest {
	
	

	@Test
	public void startEndTest() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();

		rb
			.unique(RegexFactory.sequenceGroup().some(CharacterClass.Numeric).setName("integer"))
			.unique(RegexFactory.alternativeGroup().unique(".").unique(",").setName("separator"))
			.unique(RegexFactory.sequenceGroup().some(CharacterClass.Numeric).setName("decimal"));
				
		RegexMatcher rm = new RegexMatcher(rb, "test 13.37 regexer 1");
		
		rm.find();
		
		assertEquals(5, rm.start());
		assertEquals(10, rm.end());
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
				
	}
	
	
	@Test
	public void exceptionTest() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		
		RegexMatcher rm = new RegexMatcher(rb, "");
		
		boolean exceptionCatched = false;
		try {
			rm.group();
		}
		catch(Exception e) {
			exceptionCatched = true;
		}
		
		assertTrue(exceptionCatched);
		
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
		
		RegexMatcher rm1 = new RegexMatcher(rb, "aaabbbbbddd");
		
		RegexMatcher debugedRb = rm1.debug();
		
		assertEquals("a+b+", debugedRb.getRegexBuilder().toString());
		
		RegexMatcher rm2 = new RegexMatcher(rb, "eee");
		assertNull(rm2.debug());
		
	}
	
	@Test
	public void anchorSequenceTest() {
		RegexBuilder rb = RegexFactory.regexBuilder().anchorStart(true);
		rb
			.unique(RegexFactory.sequenceGroup().setName("A").some(CharacterClass.Alphabetic))
			.unique(RegexFactory.sequenceGroup().setName("1").some(CharacterClass.Numeric));
		
		RegexMatcher matcher = new RegexMatcher(rb, "A1");
		matcher.find();
		
		assertEquals("A", matcher.group("A"));
		assertEquals("1", matcher.group("1"));
		
		
	}
	
	

}
