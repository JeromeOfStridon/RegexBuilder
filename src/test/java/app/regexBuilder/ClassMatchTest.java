package app.regexBuilder;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import app.regexBuilder.ClassMatch.CharacterClass;



public class ClassMatchTest {
	
	
	@Test
	public void genericTest() {
		ClassMatch cm = RegexFactory
				.classMatch(CharacterClass.AlphabeticUpper)
				.add('$')
				.add('€');
		
		assertEquals("[A-Z$€]", cm.toString());
		
		cm.beNegative();
		
		assertEquals("[^A-Z$€]", cm.toString());
		
		cm.bePositive();
		
		assertEquals("[A-Z$€]", cm.toString());
		
		cm.addAll(List.of('0','1','2','3'));
		
		assertEquals("[A-Z$€0123]", cm.toString());
		
	}
	
	@Test
	public void singleClassMatchTest() {
		
		ClassMatch cm = RegexFactory.classMatch('a');
		
		assertEquals("a", cm.toString());
		
	}
	
	
	

}
