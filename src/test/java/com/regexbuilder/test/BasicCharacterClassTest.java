package com.regexbuilder.test;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;
import com.regexbuilder.ClassMatch.CharacterClass;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicCharacterClassTest {
	
	@Test
	public void testLineBreak() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		
		rb.unique(CharacterClass.Linebreak);
		
		Assert.assertEquals("\\n", rb.toString());
		
		
		
	}
	
	@Test
	public void testAny() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		
		rb.some(CharacterClass.Any);
		
		Assert.assertEquals(".+", rb.toString());
		
	}

}
