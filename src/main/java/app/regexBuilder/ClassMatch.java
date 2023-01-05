package app.regexBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Setter;


public class ClassMatch extends Node {

	HashSet<Object> items = new HashSet<>();

	@Setter
	boolean negative = false;
	
	
	public ClassMatch beNegative() {
		negative = true;
		return this;
	}
	public ClassMatch bePositive() {
		negative = false;
		return this;
	}
	
	public ClassMatch add(CharacterClass cc) {
		items.add(cc);
		return this;
	}
	

	
	public ClassMatch add(Character... charArray) {
		for(Character c : charArray) {
			items.add(c);
		}
		return this;
	}
	
	public ClassMatch addAll(List<Character> characterList) {
		for(Character c : characterList) {
			items.add(c);
		}
		return this;
	}
	
	
	
	public ClassMatch add(char a, char b) {
		items.add(new ClassRange(a, b));
		return this;
	}
	
	public ClassMatch factorize(CharacterClass... classes) {
		
		for(Object item : new TreeSet<>(items)) {
			if(item instanceof Character) {
				CharacterClass cc = List.of(classes).stream().filter(x -> ((Character) item).toString().matches("["+x.string+"]")).findFirst().orElse(null);
				if(cc != null) {
					items.remove(item);
					items.add(cc);
				}
			}
		}
		
		return this;
	}
	
	

	public static enum CharacterClass{
		
		Any("."),
		
		AlphabeticLower("a-z"),
		AlphabeticUpper("A-Z"),
		Alphabetic(AlphabeticLower, AlphabeticUpper),
		
		Numeric("0-9"),
		Alphanumeric(Alphabetic, Numeric),
		Alphanumeric_Hexa("0-9a-fA-F"),
		
		// Shorthands
		Space("\\s"),
		NonSpace("\\S"),
		Word("\\w"), // equivalent to [a-zA-Z0-9_]
		NonWord("\\W"),
		Tab("\\t"),
		Linebreak("\\n");
		
		
		
		public final List<CharacterClass> children;
		public final String string;
		
		private CharacterClass(CharacterClass... cc) {
			this.children = List.of(cc);
			this.string = null;
		}
		
		private CharacterClass(String content) {
			this.children = null;
			this.string = content;
		}
		
		
		
		public String getString() {
			return string;
		}
		
		public static CharacterClass fromString(String pattern) {
			for(CharacterClass charClass : values()) {
				if(charClass.string != null && charClass.equals(pattern)) {
					return charClass;
				}
			}
			return null;
		}
		
		public static CharacterClass getFirstOf(String externalPattern) {
			for(CharacterClass charClass : values()) {
				if(charClass.string != null && externalPattern.startsWith(charClass.getString())) {
					externalPattern = externalPattern.substring(charClass.string.length());
					return charClass;
				}
			}
			return null;
		}
		
		public static List<CharacterClass> getSingleClasses(){
			return List.of(CharacterClass.values()).stream().filter(x -> x.string != null && !x.string.contains("-")).collect(Collectors.toList());
		}

		boolean isSingleCharacter() {
			return string.length() < 3;
		}
		
	}
	
	public String toString() {

		
		
		List<String> finalClasses = new ArrayList<>();
		for(Object item : items) {
			// Any overrides any other option
			if(CharacterClass.Any.equals(item)) {
				finalClasses = List.of(".");
				negative = false;
				items = new HashSet<>(Set.of(item));
				break;
			}
			else if(item instanceof CharacterClass) {
				List<CharacterClass> ccs = toBaseClasses(List.of((CharacterClass) item));
				finalClasses.addAll(ccs.stream().map(x -> x.string).collect(Collectors.toList()));
			}
			else if(item instanceof ClassRange) {
				ClassRange classRange = (ClassRange) item;
				finalClasses.add(classRange.from+"-"+classRange.to);
			}
			else if(item instanceof Character) {
				Character c = (Character) item;
				// escape "-" as this could be understood as a range character symbol
				if(List.of('-', '\\', '"').contains(c)){
					finalClasses.add("\\"+c);
				}
				else {
					finalClasses.add(c.toString());
				}
			}
			
			
		}
		
		
			
		// If only one character, turn it into a string match
		if(finalClasses.size() == 1 && !negative && items.iterator().next() instanceof Character) {
			StringMatch stringMatch = RegexBuilder.stringMatch(finalClasses.get(0));
			return stringMatch.toString()+renderSize();
		}
		if(finalClasses.size() == 1 && !negative && items.iterator().next() instanceof CharacterClass && ((CharacterClass) items.iterator().next()).isSingleCharacter()) {
			return ((CharacterClass) items.iterator().next()).string + renderSize();
		}
		
		return "["+(negative?"^":"")+finalClasses.stream().distinct().collect(Collectors.joining())+"]"+renderSize();
		
	}
	
	private List<CharacterClass> toBaseClasses(List<CharacterClass> classes){
		
		List<CharacterClass> result = new ArrayList<>();
		
		for(CharacterClass initClass : classes) {
		
			if(initClass.string != null) {
				result.add(initClass);
			}
			else {
				result.addAll(toBaseClasses(initClass.children));
			}
		}
	
		return result;
	}
	
	@AllArgsConstructor
	private static class ClassRange implements Serializable{
		Character from;
		Character to;
	}




	
	
}
