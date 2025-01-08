package com.regexbuilder.test;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.ClassMatch.CharacterClass;



public class QuantityTest {
	
	
	@Test
	public void anyTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.any(CharacterClass.Alphabetic)
			.any(RegexBuilder.alternativeGroup().any("a").any("b"));
		
		Assert.assertEquals("[a-zA-Z]*(a*|b*)*", regexBuilder.toString());
	}
	
	
	@Test
	public void anyLazyTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.anyLazy(CharacterClass.Alphabetic)
			.anyLazy(RegexBuilder.alternativeGroup().anyLazy("a").anyLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]*?(a*?|b*?)*?", regexBuilder.toString());
	}

	@Test
	public void someTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.some(CharacterClass.Alphabetic)
			.some(RegexBuilder.alternativeGroup().some("a").some("b"));
		
		Assert.assertEquals("[a-zA-Z]+(a+|b+)+", regexBuilder.toString());
	}

	
	@Test
	public void someLazyTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.someLazy(CharacterClass.Alphabetic)
			.someLazy(RegexBuilder.alternativeGroup().someLazy("a").someLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]+?(a+?|b+?)+?", regexBuilder.toString());
	}
	
	@Test
	public void unique() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.unique(CharacterClass.Alphabetic)
			.unique(RegexBuilder.alternativeGroup().unique("a").unique("b"));
		
		Assert.assertEquals("[a-zA-Z](a|b)", regexBuilder.toString());
	}
	
	@Test
	public void exactly() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.exactly(CharacterClass.Alphabetic, 3)
			.exactly(RegexBuilder.alternativeGroup().exactly("a", 5).exactly("b", 6), 4);
		
		Assert.assertEquals("[a-zA-Z]{3}(a{5}|b{6}){4}", regexBuilder.toString());
	}
	
	@Test
	public void optionalTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.optional(CharacterClass.Alphabetic)
			.optional(RegexBuilder.alternativeGroup().optional("a").optional("b"));
		
		Assert.assertEquals("[a-zA-Z]?(a?|b?)?", regexBuilder.toString());
	}
	
	@Test
	public void optionalLazyTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.optionalLazy(CharacterClass.Alphabetic)
			.optionalLazy(RegexBuilder.alternativeGroup().optionalLazy("a").optionalLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]??(a??|b??)??", regexBuilder.toString());
	}
	
	@Test
	public void minTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.min(CharacterClass.Alphabetic, 2)
			.min(RegexBuilder.alternativeGroup().min("a", 3).min("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{2,}(a{3,}|b{4,}){5,}", regexBuilder.toString());
	}
	
	@Test
	public void minLazyTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.minLazy(CharacterClass.Alphabetic, 2)
			.minLazy(RegexBuilder.alternativeGroup().minLazy("a", 3).minLazy("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{2,}?(a{3,}?|b{4,}?){5,}?", regexBuilder.toString());		
	}
	
	@Test
	public void maxTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.max(CharacterClass.Alphabetic, 2)
			.max(RegexBuilder.alternativeGroup().max("a", 3).max("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{,2}(a{,3}|b{,4}){,5}", regexBuilder.toString());
	}
	
	@Test
	public void maxLazyTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.maxLazy(CharacterClass.Alphabetic, 2)
			.maxLazy(RegexBuilder.alternativeGroup().maxLazy("a", 3).maxLazy("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{,2}?(a{,3}?|b{,4}?){,5}?", regexBuilder.toString());		
	}
	
	@Test
	public void betweenTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.between(CharacterClass.Alphabetic, 2, 3)
			.between(RegexBuilder.alternativeGroup().between("a", 3, 4).between("b", 4, 5), 5, 6);
		
		Assert.assertEquals("[a-zA-Z]{2,3}(a{3,4}|b{4,5}){5,6}", regexBuilder.toString());
	}
	
	@Test
	public void betweenLazyTest() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			.betweenLazy(CharacterClass.Alphabetic, 2, 3)
			.betweenLazy(RegexBuilder.alternativeGroup().betweenLazy("a", 3, 4).betweenLazy("b", 4, 5), 5, 6);
		
		Assert.assertEquals("[a-zA-Z]{2,3}?(a{3,4}?|b{4,5}?){5,6}?", regexBuilder.toString());
	}
	
	
	

}
