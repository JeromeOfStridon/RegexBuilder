package app.regexBuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;
import app.regexBuilder.Group.TreeType;



public class RegexBuilderTest {
	
	@Test
	public void emptyTest() {
		RegexBuilder test = RegexFactory.regexBuilder();
		assertEquals("", test.toString());
	}
	
	@Test
	public void anchorsTest() {
		RegexBuilder test1 = RegexFactory.regexBuilder();
		test1
			.anchorStart(true)
			.anchorEnd(true)
			.any("a");
		
		Assert.assertEquals("^a*$", test1.toString());
		
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
	

}
