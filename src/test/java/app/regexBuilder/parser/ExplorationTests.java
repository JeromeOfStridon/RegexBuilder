package app.regexBuilder.parser;

import org.junit.Test;

import app.regexBuilder.Node;
import app.regexBuilder.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExplorationTests {
	
	@Test
	public void variableRegex() {
		String pattern = "(?<!\\w)((\\{[^\\s]+\\})|(\\{\\{[^\\s]+\\}\\})|(\\%[^\\s]+\\%)|(\\$[A-Za-z][^\\s]*))|(\\[[^]]+\\]|([\\!\\?]{1}[^\\s\\?\\!]+))";
		
		Node parser = Parser.parse(pattern);
		
		
		log.debug(parser.toString());
		
		
		// ?<! negative lookbehind
	}
	
	
	@Test
	public void test2() {
		String pattern = "((a)){1}";

		Node parser = Parser.parse(pattern);
		
		log.debug(parser.toString());

	}

}
