package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.Group;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexFactory;
import com.regexbuilder.RegexMatcher;
import com.regexbuilder.samples.RegexDateTimeLibrary;
import com.regexbuilder.samples.RegexMiscLibrary;
import com.regexbuilder.samples.RegexNumberLibrary;
import com.regexbuilder.samples.RegexWebContentLibrary;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LibraryTest {
	
	@Test
	public void intInclusionBreak() {
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		
		rb
			.unique(RegexNumberLibrary.intNumber())
			.unique(RegexNumberLibrary.floatNumber());

		Assert.assertEquals("[0-9]+[0-9]+\\.[0-9]+", rb.toString());
		
	}
	
	@Test
	public void emailTest() {

		RegexBuilder emailRegexer = RegexWebContentLibrary.email();
		
		assertEquals("[a-zA-Z0-9%_\\-+]+(\\.[a-zA-Z0-9%_\\-+]+)?@([a-zA-Z0-9%_\\-+]+\\.)+[a-zA-Z]{2,10}", emailRegexer.toString());

	}
	
	@Test
	public void regularDate() {

		RegexBuilder regularDateRegexer = RegexDateTimeLibrary.regularDate();
		
		assertEquals("(0?[1-9]|[12][0-9]|30|31)/(0?[1-9]|10|11|12)/([12][0-9]{3})", regularDateRegexer.toString());
		
	}
	
	@Test
	public void clockHHMMTest() {
		
		RegexBuilder clockRegexer = RegexDateTimeLibrary.clockHHMM();
		
		assertEquals("([01][0-9]|2[0-3]):[0-5][0-9]", clockRegexer.toString());
		
	}
	
	@Test
	public void fullWrittenDate_frTest() {
		RegexBuilder rb = RegexDateTimeLibrary.fullWrittenDate_fr();
		
		RegexMatcher rm = RegexFactory.regexMatcher(rb, "1 janvier 2021");
		rm.find();
		
		assertEquals("2021", rm.group("year"));
		assertEquals("janvier", rm.group("month"));
		
	}
	
	@Test
	public void fullWrittenDate_enTest() {
		RegexMatcher rm = RegexFactory.regexMatcher(RegexDateTimeLibrary.fullWrittenDate_en(), "Mar. 31th 2022");
		rm.find();
		assertEquals("2022", rm.group("year"));
		assertEquals("Mar.", rm.group("month"));
		assertEquals("31th", rm.group("day"));
	}
	
	@Test
	public void timestampTest() {
		RegexMatcher rm = RegexFactory.regexMatcher(RegexDateTimeLibrary.timestampRegex(), "2022-12-03T12:22:03.009Z");
		
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
		
		assertEquals("&(amp;)*([a-zA-Z0-9]+|#[0-9]{1,6}|#x[0-9a-fA-F]{1,6});", RegexWebContentLibrary.htmlEntity().toString());
		String actul="&(amp;)*([a-zA-Z0-9]+|#[0-9]{1,6}|#x[0-9a-fA-F]{1,6});";
	
	}

	
	@Test
	public void colorHexCodeTest() {
		assertEquals("#[0-9a-fA-F]{6}", RegexMiscLibrary.colorHexCode().toString());
	}
	
	

}
