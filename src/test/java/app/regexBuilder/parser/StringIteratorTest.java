package app.regexBuilder.parser;

import org.junit.Assert;
import org.junit.Test;

public class StringIteratorTest {
	
	@Test
	public void test1() {
		
		StringIterator si = new StringIterator("abcdef");
		
		Assert.assertTrue(si.nextIfMatch("abc"));
		Assert.assertTrue(si.current() == 'd');
		Assert.assertFalse(si.nextIfMatch("defg"));
		Assert.assertTrue(si.nextIfMatch("def"));
		
		
	}

}
