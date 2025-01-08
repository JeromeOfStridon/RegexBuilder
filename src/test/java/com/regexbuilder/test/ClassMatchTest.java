package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.regexbuilder.ClassMatch;
import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.RegexBuilder;



public class ClassMatchTest {
	
	
	@Test
	public void genericTest() {
		ClassMatch cm = RegexBuilder
				.classMatch(CharacterClass.AlphabeticUpper)
				.add('$')
				.add('€');
		
		assertEquals("[A-Z$€]", cm.toString());
		
		cm.beNegative();
		
		assertEquals("[^A-Z$€]", cm.toString());
		
		cm.bePositive();
		
		assertEquals("[A-Z$€]", cm.toString());
		
		cm.addAll(Arrays.asList('0','1','2','3'));
		
		assertEquals("[A-Z$€0123]", cm.toString());
		
	}
	
	@Test
	public void singleClassMatchTest() {
		
		ClassMatch cm = RegexBuilder.classMatch('a');
		
		assertEquals("a", cm.toString());
		
	}
	
	
	@Test
	public void rangeClassMatchTest() {
		ClassMatch cm = RegexBuilder.classMatch(CharacterClass.Numeric).addRange('A', 'F');
		
		assertEquals("[0-9A-F]", cm.toString());
		
	}
	
	@Test
	public void arrayClassMatchTest() {
		ClassMatch cm = RegexBuilder.classMatch('a', 'b', 'c');
		assertEquals("[abc]", cm.toString());
	}
	
	@Test
	public void collectionClassMatchTest() {
		ClassMatch cm = RegexBuilder.classMatch(Arrays.asList('a', 'b', 'c'));
		assertEquals("[abc]", cm.toString());		
	}
	
	
	

}
