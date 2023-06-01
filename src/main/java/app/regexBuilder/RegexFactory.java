package app.regexBuilder;

import java.util.Collection;
import java.util.List;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.ChildrenType;
import app.regexBuilder.Group.GroupType;

public class RegexFactory {

	
	/**
	 * Generates group with sequencial behavior (constraints to be respected following each other)
	 */
	public static Group sequenceGroup() {
		return new Group(ChildrenType.Sequence, GroupType.None);
	}
	
	/**
	 * Generates group with alternative behavior (options all to be evaluated)
	 */
	public static Group alternativeGroup() {
		return new Group(ChildrenType.Alternative, GroupType.None);
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
	public static RegexBuilder regexBuilder() {
		return new RegexBuilder(ChildrenType.Sequence, GroupType.None);
	}
	
	/**
	 * Creates regexBuilder with specific sequence or alternative behavior (undefined group type)
	 */
	public static RegexBuilder regexBuilder(ChildrenType childrenType) {
		return new RegexBuilder(childrenType, GroupType.None);
	}
	
	/**
	 * Creates regexBuilder with generic sequential behavior and specific group type (capturing, non capturing, look ahead, look behind etc)
	 */
	public static RegexBuilder regexBuilder(GroupType groupType) {
		return new RegexBuilder(ChildrenType.Sequence, groupType);
	}
	
	public static RegexBuilder regexBuilder(ChildrenType childrenType, GroupType groupType) {
		return new RegexBuilder(childrenType, groupType);
	}
	
	public static ClassMatch classMatch(CharacterClass... charClass) {
		ClassMatch classMatch = new ClassMatch();
		for(CharacterClass characterClass : charClass) {
			classMatch.add(characterClass);
		}
		return classMatch;
	}
	
	public static ClassMatch classMatch(Character... character) {
		ClassMatch classMatch = new ClassMatch();
		classMatch.add(character);
		return classMatch;
	}
	public static ClassMatch classMatch(List<Character> characters) {
		ClassMatch classMatch = new ClassMatch();
		for(Character character : characters) {
			classMatch.add(character);
		}
		return classMatch;
	}
	
	public static ClassMatch classMatchRange(char from, char to) {
		ClassMatch classMatch = new ClassMatch();
		classMatch.add(from, to);
		return classMatch;
	}
	
	public static StringMatch stringMatch(String string) {
		return new StringMatch().add(string);
	}
	
}
