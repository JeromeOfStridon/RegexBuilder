package app.regexBuilder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.library.DateTimeLibrary;
import app.regexBuilder.library.NumberLibrary;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LibraryTest {
	
	@Test
	public void intInclusionBreak() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb
			.unique(NumberLibrary.intNumber())
			.unique(NumberLibrary.floatNumber());

		Assert.assertEquals("([0-9]+)([0-9]+\\.[0-9]+)", rb.toString());
		
	}
	
	@Test
	public void emailTest() {

		RegexBuilder emailRegexer = NumberLibrary.email();
		
		assertEquals("[%a-zA-Z0-9+\\-_]+(\\.[%a-zA-Z0-9+\\-_]+)?@([%a-zA-Z0-9+\\-_]+\\.)+[a-zA-Z]{2,10}", emailRegexer.toString());

	}
	
	@Test
	public void regularDate() {

		RegexBuilder regularDateRegexer = DateTimeLibrary.regularDate();
		
		assertEquals("((0[0-9])|([12][0-9])|(3[01]))/((0[0-9])|(1[012]))/([12][0-9]{3})", regularDateRegexer);
		
	}
	
	@Test
	public void clockHHMMTest() {
		
		RegexBuilder clockRegexer = DateTimeLibrary.clockHHMM();
		
		assertEquals("(?([0-1])[0-1][0-9]|2[0-3])\\:[0-5][0-9]", clockRegexer.toString());
		
	}
	
	

}
