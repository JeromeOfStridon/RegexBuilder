package com.regexbuilder;

import java.util.Collection;
import java.util.List;

import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group.GroupType;
import com.regexbuilder.Group.TreeType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexBuilder {

	
	/**
	 * Generates group with sequencial behavior (constraints to be respected following each other)
	 */
	public static Group sequenceGroup() {
		return new Group(TreeType.Sequence, GroupType.Undefined);
	}
	
	/**
	 * Generates group with alternative behavior (options all to be evaluated)
	 */
	public static Group alternativeGroup() {
		return new Group(TreeType.Alternative, GroupType.Undefined);
	}
	
	/**
	 * Generates group with alternative behavior (options all to be evaluated)
	 * Shortcut for creating group with multiple strings to be considered as options
	 * @param alternatives list of string to be considered as alternatives
	 */
	public static Group alternativeGroup(Collection<String> alternatives) {
		Group group = alternativeGroup();
		for(String alternative : alternatives) {
			group.nodes.add(new StringMatch().add(alternative));
		}
		return group;
	}
	
	/**
	 * Generates group with alternative behavior (options all to be evaluated)
	 * Shortcut for creating group with multiple strings to be considered as options
	 * @param alternatives strings to be considered as alternatives
	 */
	public static Group alternativeGroup(String... alternatives) {
		Group group = alternativeGroup();
		for(String alternative : alternatives) {
			group.nodes.add(new StringMatch().add(alternative));
		}
		return group;
	}
	
	
	
	/**
	 * Creates regexBuilder with standard behavior (sequential, undefined group type)
	 */
	public static Regex regex() {
		return new Regex(TreeType.Sequence, GroupType.Undefined);
	}
	
	/**
	 * Creates regexBuilder with specific TreeType (sequence or alternative) 
	 */
	public static Regex regex(TreeType childrenType) {
		return new Regex(childrenType, GroupType.Undefined);
	}
	
	/**
	 * Creates regexBuilder with generic sequential behavior and specific group type (capturing, non capturing, look ahead, look behind, undefined etc)
	 */
	public static Regex regex(GroupType groupType) {
		return new Regex(TreeType.Sequence, groupType);
	}
	
	/**
	 * Creates regexBuilder with specified TreeType (sequential or alternative) and specified GroupType  (capturing, non capturing, look ahead, look behind etc) and children type (sequential or alternative)
	 */
	public static Regex regex(TreeType childrenType, GroupType groupType) {
		return new Regex(childrenType, groupType);
	}
	
	/**
	 * Creates a new ClassMatch containing specific CharacterClass instances
	 */
	public static ClassMatch classMatch(CharacterClass... charClass) {
		ClassMatch classMatch = new ClassMatch();
		for(CharacterClass characterClass : charClass) {
			classMatch.add(characterClass);
		}
		return classMatch;
	}
	
	/**
	 * Creates a new ClassMatch containing specific characters
	 */
	public static ClassMatch classMatch(Character... character) {
		ClassMatch classMatch = new ClassMatch();
		classMatch.add(character);
		return classMatch;
	}

	
	/**
	 * Creates a new ClassMatch containing specific characters (as a collection)
	 */
	public static ClassMatch classMatch(List<Character> characters) {
		ClassMatch classMatch = new ClassMatch();
		for(Character character : characters) {
			classMatch.add(character);
		}
		return classMatch;
	}
	
	/**
	 * Creates a new ClassMatch with character range
	 */
	public static ClassMatch classMatchRange(char from, char to) {
		ClassMatch classMatch = new ClassMatch();
		classMatch.addRange(from, to);
		return classMatch;
	}
	
	/**
	 * Creates a new StringMatch with specific string
	 */
	public static StringMatch stringMatch(String string) {
		return new StringMatch().add(string);
	}

	/**
	 * Creates a RegexMatcher instance combining a RegexBuilder instance and content to be matched
	 * @param regex regexBuilder to match against content
	 * @param content content to be matched against
	 */
	public static RegexMatcher regexMatcher(Regex regex, String content) {
		return new RegexMatcher(regex, content);
	}

	public static RegexMatcher regexMatcher(Regex regexBuilder, String content, int flags) {
		return new RegexMatcher(regexBuilder, content, flags);
	}
	
}
