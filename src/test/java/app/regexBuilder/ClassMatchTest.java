package app.regexBuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;



public class ClassMatchTest {
	
	
	@Test
	public void test1() {
		ClassMatch cm = RegexBuilder
				.classMatch(CharacterClass.AlphabeticUpper)
				.add('$')
				.add('€');
		
		assertEquals("[A-Z$€]", cm.toString());
		
		cm.beNegative();
		
		assertEquals("[^A-Z$€]", cm.toString());
		
		cm.bePositive();
		
		assertEquals("[A-Z$€]", cm.toString());
		
	}
	
	
	

}
