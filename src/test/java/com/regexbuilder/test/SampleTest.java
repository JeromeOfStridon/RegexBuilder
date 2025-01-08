package com.regexbuilder.test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.RegexMatcher;
import com.regexbuilder.sample.DateTimeSamples;
import com.regexbuilder.sample.MiscSamples;
import com.regexbuilder.sample.NumberSamples;
import com.regexbuilder.sample.WebContentSamples;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleTest {
	
	@Test
	public void intInclusionBreak() {
		
		Regex rb = RegexBuilder.regex();
		
		rb
			.unique(NumberSamples.intNumber())
			.unique(NumberSamples.floatNumber());

		Assert.assertEquals("[0-9]+[0-9]+\\.[0-9]+", rb.toString());
		
	}
	
	@Test
	public void emailTest() {

		Regex emailRegexer = WebContentSamples.email();
		
		assertEquals("[a-zA-Z0-9%_\\-+]+(\\.[a-zA-Z0-9%_\\-+]+)?@([a-zA-Z0-9%_\\-+]+\\.)+[a-zA-Z]{2,10}", emailRegexer.toString());

	}
	
	@Test
	public void regularDate() {

		Regex regularDateRegexer = DateTimeSamples.regularDate();
		
		assertEquals("(0?[1-9]|[12][0-9]|30|31)/(0?[1-9]|10|11|12)/([12][0-9]{3})", regularDateRegexer.toString());
		
	}
	
	@Test
	public void clockHHMMTest() {
		
		Regex clockRegexer = DateTimeSamples.clockHHMM();
		
		assertEquals("([01][0-9]|2[0-3]):[0-5][0-9]", clockRegexer.toString());
		
	}
	
	@Test
	public void fullWrittenDate_frTest() {
		Regex rb = DateTimeSamples.fullWrittenDate_fr();
		
		RegexMatcher rm = RegexBuilder.regexMatcher(rb, "1 janvier 2021");
		rm.find();
		
		assertEquals("2021", rm.group("year"));
		assertEquals("janvier", rm.group("month"));
		
	}
	
	@Test
	public void fullWrittenDate_enTest() {
		RegexMatcher rm = RegexBuilder.regexMatcher(DateTimeSamples.fullWrittenDate_en(), "Mar. 31th 2022");
		rm.find();
		assertEquals("2022", rm.group("year"));
		assertEquals("Mar.", rm.group("month"));
		assertEquals("31th", rm.group("day"));
	}
	
	@Test
	public void timestampTest() {
		RegexMatcher rm = RegexBuilder.regexMatcher(DateTimeSamples.timestampRegex(), "2022-12-03T12:22:03.009Z");
		
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
		assertEquals("&(amp;)*([a-zA-Z0-9]+|#[0-9]{1,6}|#x[0-9a-fA-F]{1,6});", WebContentSamples.htmlEntity().toString());	
	}

	
	@Test
	public void colorHexCodeTest() {
		assertEquals("#[0-9a-fA-F]{6}", MiscSamples.colorHexCode().toString());
	}
	
	@Test
	public void ipv4Test() {
		assertEquals("(25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9]?[0-9]?)", WebContentSamples.ipV4().toString());
	}



}
