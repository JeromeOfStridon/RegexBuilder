package app.regexBuilder;

import java.util.List;

public class StringMatch extends Node {
	
	public final static List<Character> escapedChars = List.of('\\','{', '}', '?', '*', '+', '[', ']', '.', '"');
	
	StringBuilder sb = new StringBuilder();
	
	public StringMatch add(String str) {
		sb.append(str);
		return this;
	}
	
	public String toString() {

		String escapedString = sb.toString();
		
		for(Character c : escapedChars) {
			escapedString = escapedString.replace(c+"", "\\"+c);
		}
		
		
		if(markedAsGroup()) {
			return "("+escapedString+")"+renderSize();
		}
		
		return escapedString+renderSize();
		
	}
	
	public boolean markedAsGroup() {
		return sb.toString().length() > 1 && !renderSize().isEmpty();
	}
	
	
}
