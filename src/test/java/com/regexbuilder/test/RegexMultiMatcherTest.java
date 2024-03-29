package com.regexbuilder.test;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;
import com.regexbuilder.RegexMultiMatcher;
import com.regexbuilder.RegexMultiMatcher.RegexMatcherGroup;

public class RegexMultiMatcherTest {
	
	@Test
	public void test() {
		
		RegexBuilder alphabetic = RegexFactory.regexBuilder();
		alphabetic.some(CharacterClass.Alphabetic);
		
		RegexBuilder numeric = RegexFactory.regexBuilder();
		numeric.some(CharacterClass.Numeric);
		
		RegexBuilder alphaNumeric = RegexFactory.regexBuilder();
		alphaNumeric.some(CharacterClass.Alphanumeric);
		
		RegexMultiMatcher regexMultiMatcher = new RegexMultiMatcher(Arrays.asList(alphabetic, numeric, alphaNumeric));
		
		List<RegexMatcherGroup> matchers = regexMultiMatcher.matchers("abc def012 345hi 678 jk");
		
		Assert.assertEquals(5, matchers.size());
		
	}

}
