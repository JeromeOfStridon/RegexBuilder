package app.regexBuilder.test;

import org.junit.Assert;
import org.junit.Test;

import app.regexBuilder.Node;
import app.regexBuilder.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParserTest {
	
	@Test 
	public void test1() {
		String regex = "(([A-Z]?([|&][A-Z]{1,3})+)|(([A-Z]\\.){2,5})+)";
		Node parser = Parser.parse(regex);
		log.debug(parser.toString());
	}
	
	@Test
	public void test2() {
		testRecompilation("abc{3,5}");
	}
	
	@Test
	public void test3() {
		String regex = "#[a-zA-Z0-9]{6}";
		Node parser = Parser.parse(regex);
		testRecompilation(regex);
	}
	

	
	@Test
	public void test4() {
		String regex = "[0-9]{1,3}(,[0-9]{3})+(\\.[0-9]+)?";
		Node parser = Parser.parse(regex);
		
		
		testRecompilation(regex);
	}
	
	@Test
	public void test5() {
		String regex = "[0-9]{1,3}(,[0-9]{3})+([.][0-9]+)?";
		String simplified = "[0-9]{1,3}(,[0-9]{3})+(\\.[0-9]+)?";
		Node parser = Parser.parse(regex);
		log.debug(parser.toString());
		
		testRecompilation(simplified);
	}
	
	public void testRecompilation(String regex) {
		Node parser = Parser.parse(regex);
		log.debug(parser.toString());
		Assert.assertEquals(regex, parser.toString());
	}

}
