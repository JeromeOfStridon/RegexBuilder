package com.regexbuilder;

import java.util.List;

public class StringMatch extends Node {
	
	final static List<Character> escapedChars = List.of('\\','{', '}', '?', '*', '+', '[', ']', '.', '"');
	
	StringBuilder stringBuilder = new StringBuilder();
	
	
	/**
	 * appends string to content of current StringMatch
	 * @param string
	 * @return
	 */
	public StringMatch add(String string) {
		stringBuilder.append(string);
		return this;
	}
	
	/**
	 * Compiling current StringMatch into regex string
	 * @return string
	 */
	public String toString() {

		String escapedString = stringBuilder.toString();
		
		for(Character c : escapedChars) {
			escapedString = escapedString.replace(c+"", "\\"+c);
		}
		
		
		if(markedAsGroup()) {
			return "("+escapedString+")"+renderSize();
		}
		
		return escapedString+renderSize();
		
	}
	
	boolean markedAsGroup() {
		return stringBuilder.toString().length() > 1 && !renderSize().isEmpty();
	}
	
	/**
	 * Sets quantity manually by defining minSize & maxSize
	 * @param minSize
	 * @param maxSize
	 */
	public StringMatch setQuantity(Integer minSize, Integer maxSize) {
		super.setQuantity(minSize, maxSize);
		return this;
	}
	
}
