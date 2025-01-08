package com.regexbuilder.test;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.ClassMatch.CharacterClass;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicCharacterClassTest {
	
	@Test
	public void testLineBreak() {
		
		Regex rb = RegexBuilder.regex();
		
		rb.unique(CharacterClass.Linebreak);
		
		Assert.assertEquals("\\n", rb.toString());
		
		
		
	}
	
	@Test
	public void testAny() {
		
		Regex rb = RegexBuilder.regex();
		
		rb.some(CharacterClass.Any);
		
		Assert.assertEquals(".+", rb.toString());
		
	}

}
