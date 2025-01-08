package com.regexbuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;


public class ClassMatch extends Node {

	LinkedHashSet<Object> items = new LinkedHashSet<>();

	boolean negative = false;
	
	/**
	 * Sets the ClassMatch to be negative (excluding specified classes or characters), opposite to {@link #bePositive()}
	 * @return self
	 */
	public ClassMatch beNegative() {
		negative = true;
		return this;
	}
	
	/**
	 * Sets the ClassMatch to be positive (including specified classes or characters), opposite to {@link #beNegative()}
	 * @return self
	 */
	public ClassMatch bePositive() {
		negative = false;
		return this;
	}
	
	
	/**
	 * Add CharacterClass to current ClassMatch
	 * @return self
	 */
	public ClassMatch add(CharacterClass cc) {
		items.add(cc);
		return this;
	}
	

	/**
	 * Add Characters to current ClassMatch
	 * @return self
	 */
	public ClassMatch add(Character... charArray) {
//		for(Character character : charArray) {
//			
//		}
		items.addAll(Arrays.asList(charArray));
		return this;
	}
	
	/**
	 * Add Character collection to current ClassMatch
	 * @return self
	 */
	public ClassMatch addAll(Collection<Character> characterList) {
		for(Character c : characterList) {
			items.add(c);
		}
		return this;
	}
	
	
	/**
	 * Add ClassRange to current ClassMatch
	 * @param from character specifying range start
	 * @param to character specifying range end
	 * @return self
	 */
	public ClassMatch addRange(char from, char to) {
		items.add(new ClassRange(from, to));
		return this;
	}
	

	public enum CharacterClass{
		
		Any("."),
		
		AlphabeticLower("a-z"),
		AlphabeticUpper("A-Z"),
		Alphabetic(AlphabeticLower, AlphabeticUpper),
		
		Numeric("0-9"),
		Alphanumeric(Alphabetic, Numeric),
		Alphanumeric_Hexa("0-9a-fA-F"),
		
		// Shorthands
		Digit("\\d"),
		NonDigit("\\D"),
		Whitespace("\\s"),
		NonWhitespace("\\S"),
		Word("\\w"), // equivalent to [a-zA-Z0-9_]
		NonWord("\\W"),
		Tab("\\t"),
		Linebreak("\\n"),
		CarriageReturn("\\r");
		
		
		final List<CharacterClass> children;
		final String string;
		
		private CharacterClass(CharacterClass... cc) {
			this.children = Arrays.asList(cc);
			this.string = null;
		}
		
		private CharacterClass(String content) {
			this.children = null;
			this.string = content;
		}

		boolean isSingleCharacter() {
			return string.length() < 3;
		}
		
	}

	/**
	 * Compiling current ClassMatch into regex string
	 * @return string
	 */
	public String toString() {
		
		List<String> finalClasses = new ArrayList<>();
		for(Object item : items) {
			// Any overrides any other option
			if(CharacterClass.Any.equals(item)) {
				finalClasses = Arrays.asList(".");
				negative = false;
				items = new LinkedHashSet<>(Arrays.asList(item));
				break;
			}
			else if(item instanceof CharacterClass) {
				List<CharacterClass> ccs = toBaseClasses(Arrays.asList((CharacterClass) item));
				finalClasses.addAll(ccs.stream().map(x -> x.string).collect(Collectors.toList()));
			}
			else if(item instanceof ClassRange) {
				ClassRange classRange = (ClassRange) item;
				finalClasses.add(classRange.from+"-"+classRange.to);
			}
			else if(item instanceof Character) {
				Character c = (Character) item;
				// escape "-" as this could be understood as a range character symbol
				if(Arrays.asList('-', '\\', '"').contains(c)){
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
	
	/**
	 * Sets quantity manually by defining minSize & maxSize
	 * @param minSize
	 * @param maxSize
	 */
	public ClassMatch setQuantity(Integer minSize, Integer maxSize) {
		super.setQuantity(minSize, maxSize);
		return this;
	}
	
	public ClassMatch setLazy(boolean lazy) {
		super.setLazy(lazy);
		return this;
	}
	
	@AllArgsConstructor
	private static class ClassRange implements Serializable{
		Character from;
		Character to;
	}

	
	
}
