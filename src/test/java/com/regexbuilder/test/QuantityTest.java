package com.regexbuilder.test;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;
import com.regexbuilder.ClassMatch.CharacterClass;



public class QuantityTest {
	
	
	@Test
	public void anyTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.any(CharacterClass.Alphabetic)
			.any(RegexFactory.alternativeGroup().any("a").any("b"));
		
		Assert.assertEquals("[a-zA-Z]*(a*|b*)*", regexBuilder.toString());
	}
	
	
	@Test
	public void anyLazyTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.anyLazy(CharacterClass.Alphabetic)
			.anyLazy(RegexFactory.alternativeGroup().anyLazy("a").anyLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]*?(a*?|b*?)*?", regexBuilder.toString());
	}

	@Test
	public void someTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.some(CharacterClass.Alphabetic)
			.some(RegexFactory.alternativeGroup().some("a").some("b"));
		
		Assert.assertEquals("[a-zA-Z]+(a+|b+)+", regexBuilder.toString());
	}

	
	@Test
	public void someLazyTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.someLazy(CharacterClass.Alphabetic)
			.someLazy(RegexFactory.alternativeGroup().someLazy("a").someLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]+?(a+?|b+?)+?", regexBuilder.toString());
	}
	
	@Test
	public void unique() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.unique(CharacterClass.Alphabetic)
			.unique(RegexFactory.alternativeGroup().unique("a").unique("b"));
		
		Assert.assertEquals("[a-zA-Z](a|b)", regexBuilder.toString());
	}
	
	@Test
	public void exactly() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.exactly(CharacterClass.Alphabetic, 3)
			.exactly(RegexFactory.alternativeGroup().exactly("a", 5).exactly("b", 6), 4);
		
		Assert.assertEquals("[a-zA-Z]{3}(a{5}|b{6}){4}", regexBuilder.toString());
	}
	
	@Test
	public void optionalTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.optional(CharacterClass.Alphabetic)
			.optional(RegexFactory.alternativeGroup().optional("a").optional("b"));
		
		Assert.assertEquals("[a-zA-Z]?(a?|b?)?", regexBuilder.toString());
	}
	
	@Test
	public void optionalLazyTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.optionalLazy(CharacterClass.Alphabetic)
			.optionalLazy(RegexFactory.alternativeGroup().optionalLazy("a").optionalLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]??(a??|b??)??", regexBuilder.toString());
	}
	
	@Test
	public void minTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.min(CharacterClass.Alphabetic, 2)
			.min(RegexFactory.alternativeGroup().min("a", 3).min("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{2,}(a{3,}|b{4,}){5,}", regexBuilder.toString());
	}
	
	@Test
	public void minLazyTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.minLazy(CharacterClass.Alphabetic, 2)
			.minLazy(RegexFactory.alternativeGroup().minLazy("a", 3).minLazy("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{2,}?(a{3,}?|b{4,}?){5,}?", regexBuilder.toString());		
	}
	
	@Test
	public void maxTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.max(CharacterClass.Alphabetic, 2)
			.max(RegexFactory.alternativeGroup().max("a", 3).max("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{,2}(a{,3}|b{,4}){,5}", regexBuilder.toString());
	}
	
	@Test
	public void maxLazyTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.maxLazy(CharacterClass.Alphabetic, 2)
			.maxLazy(RegexFactory.alternativeGroup().maxLazy("a", 3).maxLazy("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{,2}?(a{,3}?|b{,4}?){,5}?", regexBuilder.toString());		
	}
	
	@Test
	public void betweenTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.between(CharacterClass.Alphabetic, 2, 3)
			.between(RegexFactory.alternativeGroup().between("a", 3, 4).between("b", 4, 5), 5, 6);
		
		Assert.assertEquals("[a-zA-Z]{2,3}(a{3,4}|b{4,5}){5,6}", regexBuilder.toString());
	}
	
	@Test
	public void betweenLazyTest() {
		RegexBuilder regexBuilder = RegexFactory.regexBuilder();
		regexBuilder
			.betweenLazy(CharacterClass.Alphabetic, 2, 3)
			.betweenLazy(RegexFactory.alternativeGroup().betweenLazy("a", 3, 4).betweenLazy("b", 4, 5), 5, 6);
		
		Assert.assertEquals("[a-zA-Z]{2,3}?(a{3,4}?|b{4,5}?){5,6}?", regexBuilder.toString());
	}
	
	
	

}
