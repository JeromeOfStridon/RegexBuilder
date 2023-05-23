package app.regexBuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.library.DateTimeLibrary;
import app.regexBuilder.library.NumberLibrary;
import app.regexBuilder.library.WebContentLibrary;
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

		RegexBuilder emailRegexer = WebContentLibrary.email();
		
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
	
	@Test
	public void fullWrittenDate_frTest() {
		RegexBuilder rb = DateTimeLibrary.fullWrittenDate_fr();
		
		RegexMatcher rm = new RegexMatcher(rb, "1er Janvier 2021");
		assertEquals("2021", rm.group("year"));
		assertEquals("Janvier", rm.group("month"));
		
	}
	
	@Test
	public void fullWrittenDate_enTest() {
		RegexMatcher rm = new RegexMatcher(DateTimeLibrary.fullWrittenDate_en(), "Mar. 31st 2022");
		assertEquals("2022", rm.group("year"));
		assertEquals("Mar.", rm.group("month"));
		assertEquals("31st", rm.group("day"));
	}
	
	@Test
	public void timestampTest() {
		RegexMatcher rm = new RegexMatcher(DateTimeLibrary.timestampRegex(), "2022-12-03T12:22:03.009Z");
		
		rm.find();
		
		assertEquals((Integer) 2022, rm.groupAsInteger("year"));
		assertEquals((Integer) 12, rm.groupAsInteger("month"));
		assertEquals((Integer) 03, rm.groupAsInteger("day"));
		assertEquals((Integer) 12, rm.groupAsInteger("hour"));
		assertEquals((Integer) 22, rm.groupAsInteger("minute"));
		assertEquals((Integer) 03, rm.groupAsInteger("second"));
		assertEquals((Integer) 9, rm.groupAsInteger("millisecond"));
	
	}
	
	
	@Test
	public void htmlEntityTest() {
		
		assertEquals("&(amp;)*([a-zA-Z0-9]+|#[0-9]{1,6}|#x[0-9a-fA-F]{1,6});", WebContentLibrary.htmlEntity().toString());
	}

	
	

}
