package app.regexBuilder.parser;
import java.text.StringCharacterIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringIterator {

	StringCharacterIterator sci;
	
	public StringIterator(String string) {
		sci = new StringCharacterIterator(string);
	}

	public char current() {
		return sci.current();
	}

	public char next() {
		return sci.next();
	}

	public int getIndex() {
		return sci.getIndex();
	}

	public int getEndIndex() {
		return sci.getEndIndex();
	}
	
	public boolean nextIfMatch(String str) {
		int currentIndex = sci.getIndex();
		for(int i = 0; i < str.length(); i++) {
			// check we're not out of boundaries
			if(currentIndex + i >= sci.getEndIndex()) {
				// reset position and return false
				sci.setIndex(currentIndex);
				return false;
			}
			// 
			if(str.charAt(i) != sci.current()) {
				// reset position and return false
				sci.setIndex(currentIndex);
				return false;
			}
			sci.next();
		}
		
		// if match, don't get back
		return true;
	}
	
	public boolean match(String str) {
		
		if(sci.getIndex() + str.length() > sci.getEndIndex()) {
			return false;
		}
		
		int currentIndex = sci.getIndex();
		
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) != sci.current()) {
				sci.setIndex(currentIndex);
				return false;
			}
		}
		sci.setIndex(currentIndex);
		return true;
	}
	
	public String match(Pattern pattern) {
		String remainingContent = remainingContent();
		Matcher matcher = pattern.matcher(remainingContent());
		if(matcher.find() && matcher.start() == 0) {
			return matcher.group();
		}
		return null;
	}
	
	public String remainingContent() {
		Integer currentIndex = sci.getIndex();
		StringBuilder sb = new StringBuilder();
		while(sci.getIndex() < sci.getEndIndex()) {
			sb.append(sci.current());
			sci.next();
		}
		sci.setIndex(currentIndex);
		return sb.toString();
	}
	
	@Override
	public String toString() {
		return "StringIterator #"+sci.getIndex()+" : "+sci.current();
	}
	
}
