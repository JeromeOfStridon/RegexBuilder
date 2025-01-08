package com.regexbuilder.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexMultiMatcher;
import com.regexbuilder.RegexMultiMatcher.RegexMatcherGroup;

public class RegexMultiMatcherTest {
	
	@Test
	public void test() {
		
		Regex alphabetic = RegexBuilder.regex();
		alphabetic.some(CharacterClass.Alphabetic);
		
		Regex numeric = RegexBuilder.regex();
		numeric.some(CharacterClass.Numeric);
		
		Regex alphaNumeric = RegexBuilder.regex();
		alphaNumeric.some(CharacterClass.Alphanumeric);
		
		RegexMultiMatcher regexMultiMatcher = new RegexMultiMatcher(Arrays.asList(alphabetic, numeric, alphaNumeric));
		
		List<RegexMatcherGroup> matchers = regexMultiMatcher.matchers("abc def012 345hi 678 jk");
		
		Assert.assertEquals(5, matchers.size());
		
	}

}
