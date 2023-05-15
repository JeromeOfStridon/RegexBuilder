package app.regexBuilder.test;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group;
import app.regexBuilder.library.Library;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LibraryTest {
	
	@Test
	public void intInclusionBreak() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb
			.unique(Library.intNumber())
			.unique(Library.floatNumber());

		Assert.assertEquals("([0-9]+)([0-9]+\\.[0-9]+)", rb.toString());
		
	}
	
	

}
