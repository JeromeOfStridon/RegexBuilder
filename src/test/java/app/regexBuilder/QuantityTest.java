package app.regexBuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;



public class QuantityTest {
	
	
	@Test
	public void anyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.any(CharacterClass.Alphabetic)
			.any(RegexBuilder.alternativeGroup().any("a").any("b"));
		
		Assert.assertEquals("[a-zA-Z]*(a*|b*)*", regexBuilder.toString());
	}
	
	
	@Test
	public void anyLazyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.anyLazy(CharacterClass.Alphabetic)
			.anyLazy(RegexBuilder.alternativeGroup().anyLazy("a").anyLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]*?(a*?|b*?)*?", regexBuilder.toString());
	}

	@Test
	public void someTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.some(CharacterClass.Alphabetic)
			.some(RegexBuilder.alternativeGroup().some("a").some("b"));
		
		Assert.assertEquals("[a-zA-Z]+(a+|b+)+", regexBuilder.toString());
	}

	
	@Test
	public void someLazyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.someLazy(CharacterClass.Alphabetic)
			.someLazy(RegexBuilder.alternativeGroup().someLazy("a").someLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]+?(a+?|b+?)+?", regexBuilder.toString());
	}
	
	@Test
	public void unique() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.unique(CharacterClass.Alphabetic)
			.unique(RegexBuilder.alternativeGroup().unique("a").unique("b"));
		
		Assert.assertEquals("[a-zA-Z](a|b)", regexBuilder.toString());
	}
	
	@Test
	public void optionalTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.optional(CharacterClass.Alphabetic)
			.optional(RegexBuilder.alternativeGroup().optional("a").optional("b"));
		
		Assert.assertEquals("[a-zA-Z]?(a?|b?)?", regexBuilder.toString());
	}
	
	@Test
	public void optionalLazyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.optionalLazy(CharacterClass.Alphabetic)
			.optionalLazy(RegexBuilder.alternativeGroup().optionalLazy("a").optionalLazy("b"));
		
		Assert.assertEquals("[a-zA-Z]??(a??|b??)??", regexBuilder.toString());
	}
	
	@Test
	public void minTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.min(CharacterClass.Alphabetic, 2)
			.min(RegexBuilder.alternativeGroup().min("a", 3).min("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{2,}(a{3,}|b{4,}){5,}", regexBuilder.toString());
	}
	
	@Test
	public void minLazyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.minLazy(CharacterClass.Alphabetic, 2)
			.minLazy(RegexBuilder.alternativeGroup().minLazy("a", 3).minLazy("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{2,}?(a{3,}?|b{4,}?){5,}?", regexBuilder.toString());		
	}
	
	@Test
	public void maxTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.max(CharacterClass.Alphabetic, 2)
			.max(RegexBuilder.alternativeGroup().max("a", 3).max("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{,2}(a{,3}|b{,4}){,5}", regexBuilder.toString());
	}
	
	@Test
	public void maxLazyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.maxLazy(CharacterClass.Alphabetic, 2)
			.maxLazy(RegexBuilder.alternativeGroup().maxLazy("a", 3).maxLazy("b", 4), 5);
		
		Assert.assertEquals("[a-zA-Z]{,2}?(a{,3}?|b{,4}?){,5}?", regexBuilder.toString());		
	}
	
	@Test
	public void betweenTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.between(CharacterClass.Alphabetic, 2, 3)
			.between(RegexBuilder.alternativeGroup().between("a", 3, 4).between("b", 4, 5), 5, 6);
		
		Assert.assertEquals("[a-zA-Z]{2,3}(a{3,4}|b{4,5}){5,6}", regexBuilder.toString());
	}
	
	@Test
	public void betweenLazyTest() {
		RegexBuilder regexBuilder = new RegexBuilder();
		regexBuilder
			.betweenLazy(CharacterClass.Alphabetic, 2, 3)
			.betweenLazy(RegexBuilder.alternativeGroup().betweenLazy("a", 3, 4).betweenLazy("b", 4, 5), 5, 6);
		
		Assert.assertEquals("[a-zA-Z]{2,3}?(a{3,4}?|b{4,5}?){5,6}?", regexBuilder.toString());
	}
	
	
	

}
