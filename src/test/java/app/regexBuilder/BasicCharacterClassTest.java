package app.regexBuilder;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BasicCharacterClassTest {
	
	@Test
	public void testLineBreak() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb.unique(CharacterClass.Linebreak);
		
		Assert.assertEquals("\\n", rb.toString());
		
		
		
	}
	
	@Test
	public void testAny() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb.some(CharacterClass.Any);
		
		Assert.assertEquals(".+", rb.toString());
		
	}

}
