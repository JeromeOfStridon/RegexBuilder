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
		
		Regex regex = RegexBuilder.regex()
				.unique(CharacterClass.Linebreak);
		
		Assert.assertEquals("\\n", regex.toString());
		
		
		
	}
	
	@Test
	public void testAny() {
		
		Regex regex = RegexBuilder.regex()
				.some(CharacterClass.Any);
		
		Assert.assertEquals(".+", regex.toString());
		
	}

}
