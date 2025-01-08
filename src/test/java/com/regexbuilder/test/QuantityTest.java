package com.regexbuilder.test;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.ClassMatch.CharacterClass;



public class QuantityTest {
	
	
	@Test
	public void anyTest() {
		Regex regex = RegexBuilder.regex()
			.any(CharacterClass.Alphabetic)
			.any(RegexBuilder.alternativeGroup().any("a").any("b"));
		
		Assert.assertEquals("[a-zA-Z]*(a*|b*)*", regex.toString());
	}
	
	
	@Test
	public void anyLazyTest() {
		Regex regex = RegexBuilder.regex()
			.anyLazy(CharacterClass.Alphabetic)
			.anyLazy(RegexBuilder.alternativeGroup().anyLazy("a").anyLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]*?(a*?|b*?)*?", regex.toString());
	}

	@Test
	public void someTest() {
		Regex regex = RegexBuilder.regex()
			.some(CharacterClass.Alphabetic)
			.some(RegexBuilder.alternativeGroup().some("a").some("b"));
		
		Assert.assertEquals("[a-zA-Z]+(a+|b+)+", regex.toString());
	}

	
	@Test
	public void someLazyTest() {
		Regex regex = RegexBuilder.regex()
			.someLazy(CharacterClass.Alphabetic)
			.someLazy(RegexBuilder.alternativeGroup().someLazy("a").someLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]+?(a+?|b+?)+?", regex.toString());
	}
	
	@Test
	public void unique() {
		Regex regex = RegexBuilder.regex()
			.unique(CharacterClass.Alphabetic)
			.unique(RegexBuilder.alternativeGroup().unique("a").unique("b"));
		
		Assert.assertEquals("[a-zA-Z](a|b)", regex.toString());
	}
	
	@Test
	public void exactly() {
		Regex regex = RegexBuilder.regex()
			.exactly(CharacterClass.Alphabetic, 3)
			.exactly(RegexBuilder.alternativeGroup().exactly("a", 5).exactly("b", 6), 4);
		
		Assert.assertEquals("[a-zA-Z]{3}(a{5}|b{6}){4}", regex.toString());
	}
	
	@Test
	public void optionalTest() {
		Regex regex = RegexBuilder.regex()
			.optional(CharacterClass.Alphabetic)
			.optional(RegexBuilder.alternativeGroup().optional("a").optional("b"));
		
		Assert.assertEquals("[a-zA-Z]?(a?|b?)?", regex.toString());
	}
	
	@Test
	public void optionalLazyTest() {
		Regex regex = RegexBuilder.regex()
			.optionalLazy(CharacterClass.Alphabetic)
			.optionalLazy(RegexBuilder.alternativeGroup().optionalLazy("a").optionalLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]??(a??|b??)??", regex.toString());
	}
	
	@Test
	public void minTest() {
		Regex regex = RegexBuilder.regex()
			.min(CharacterClass.Alphabetic, 2)
			.min(RegexBuilder.alternativeGroup().min("a", 3).min("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{2,}(a{3,}|b{4,}){5,}", regex.toString());
	}
	
	@Test
	public void minLazyTest() {
		Regex regex = RegexBuilder.regex()
			.minLazy(CharacterClass.Alphabetic, 2)
			.minLazy(RegexBuilder.alternativeGroup().minLazy("a", 3).minLazy("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{2,}?(a{3,}?|b{4,}?){5,}?", regex.toString());		
	}
	
	@Test
	public void maxTest() {
		Regex regex = RegexBuilder.regex()
			.max(CharacterClass.Alphabetic, 2)
			.max(RegexBuilder.alternativeGroup().max("a", 3).max("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{,2}(a{,3}|b{,4}){,5}", regex.toString());
	}
	
	@Test
	public void maxLazyTest() {
		Regex regex = RegexBuilder.regex()
			.maxLazy(CharacterClass.Alphabetic, 2)
			.maxLazy(RegexBuilder.alternativeGroup().maxLazy("a", 3).maxLazy("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{,2}?(a{,3}?|b{,4}?){,5}?", regex.toString());		
	}
	
	@Test
	public void betweenTest() {
		Regex regex = RegexBuilder.regex()
			.between(CharacterClass.Alphabetic, 2, 3)
			.between(RegexBuilder.alternativeGroup().between("a", 3, 4).between("b", 4, 5), 5, 6);
		
		Assert.assertEquals("[a-zA-Z]{2,3}(a{3,4}|b{4,5}){5,6}", regex.toString());
	}
	
	@Test
	public void betweenLazyTest() {
		Regex regex = RegexBuilder.regex()
			.betweenLazy(CharacterClass.Alphabetic, 2, 3)
			.betweenLazy(RegexBuilder.alternativeGroup().betweenLazy("a", 3, 4).betweenLazy("b", 4, 5), 5, 6);
		
		Assert.assertEquals("[a-zA-Z]{2,3}?(a{3,4}?|b{4,5}?){5,6}?", regex.toString());
	}
	
	
	

}
