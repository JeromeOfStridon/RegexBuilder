package app.regexBuilder.test;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group;
import app.regexBuilder.library.Library;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InclusionTest {
	
	@Test
	public void intInclusionBreak() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb
			.unique("a")
			.unique(Library.intNumber())
			.unique("b");
		
		System.out.println(rb.toString());
		
		
		
		System.out.println(Library.intNumber().toString());
		
		System.out.println(((Group) Library.intNumber()).toString());
		
	}
	
	

}
