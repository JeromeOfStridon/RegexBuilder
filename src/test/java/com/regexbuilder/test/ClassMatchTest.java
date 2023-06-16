package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.regexbuilder.ClassMatch;
import com.regexbuilder.RegexFactory;
import com.regexbuilder.ClassMatch.CharacterClass;



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
	
	
	@Test
	public void rangeClassMatchTest() {
		ClassMatch cm = RegexFactory.classMatch(CharacterClass.Numeric).addRange('A', 'F');
		
		assertEquals("[0-9A-F]", cm.toString());
		
	}
	
	@Test
	public void arrayClassMatchTest() {
		ClassMatch cm = RegexFactory.classMatch('a', 'b', 'c');
		assertEquals("[abc]", cm.toString());
	}
	
	@Test
	public void collectionClassMatchTest() {
		ClassMatch cm = RegexFactory.classMatch(List.of('a', 'b', 'c'));
		assertEquals("[abc]", cm.toString());		
	}
	
	
	

}
