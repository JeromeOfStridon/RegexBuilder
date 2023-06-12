package app.regexBuilder;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.library.RegexDateTimeLibrary;
import app.regexBuilder.library.RegexMiscLibrary;
import app.regexBuilder.library.RegexNumberLibrary;
import app.regexBuilder.library.RegexWebContentLibrary;
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
		
		assertEquals("[%a-zA-Z0-9+\\-_]+(\\.[%a-zA-Z0-9+\\-_]+)?@([%a-zA-Z0-9+\\-_]+\\.)+[a-zA-Z]{2,10}", emailRegexer.toString());

	}
	
	@Test
	public void regularDate() {

		String g1 = "(0[0-9]|[12][0-9]|3[01])/(0[0-9]|1[012])/[12][0-9]{3})";
		String g2 = "(0?[1-9]|[1-2][0-9]|30|31)/(0?[1-9]|10|11|12)/(1|2)[0-9]{3}";



		RegexBuilder regularDateRegexer = RegexDateTimeLibrary.regularDate();
		
		assertEquals("(0?[0-9]|[12][0-9]|3[01])/(0[0-9]|1[012])/[12][0-9]{3})", regularDateRegexer);
		
	}
	
	@Test
	public void clockHHMMTest() {
		
		RegexBuilder clockRegexer = RegexDateTimeLibrary.clockHHMM();
		
		assertEquals("([01][0-9]|2[0-3]):[0-5][0-9]", clockRegexer.toString());
		
	}
	
	@Test
	public void fullWrittenDate_frTest() {
		RegexBuilder rb = RegexDateTimeLibrary.fullWrittenDate_fr();
		
		RegexMatcher rm = new RegexMatcher(rb, "1 janvier 2021");
		rm.find();
		for(Group group : rb.getCapturingGroups()) {
			log.debug("capturing : "+group.toString());
		}
		log.debug(rb.getGroupPositions().toString());
		//log.debug(rm.groupsAsMap().toString());
		log.debug(rb.toString());
		log.debug(rm.debug().getRegexBuilder().toString());
		
		
		assertEquals("2021", rm.group("year"));
		assertEquals("janvier", rm.group("month"));
		
	}
	
	@Test
	public void fullWrittenDate_enTest() {
		RegexMatcher rm = new RegexMatcher(RegexDateTimeLibrary.fullWrittenDate_en(), "Mar. 31th 2022");
		rm.find();
		assertEquals("2022", rm.group("year"));
		assertEquals("Mar.", rm.group("month"));
		assertEquals("31th", rm.group("day"));
	}
	
	@Test
	public void timestampTest() {
		RegexMatcher rm = new RegexMatcher(RegexDateTimeLibrary.timestampRegex(), "2022-12-03T12:22:03.009Z");
		
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
