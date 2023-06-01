package app.regexBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.regexBuilder.ClassMatch.CharacterClass;


public class Group extends Node {

	protected ChildrenType childrenType;
	
	protected GroupType groupType;

	protected List<Node> nodes = new ArrayList<>();
	
	String groupName;
	
	protected Group(ChildrenType childrenType, GroupType groupType) {
		this.childrenType = childrenType;
		this.groupType = groupType;
	}
	
	private Group _attachNode(Node content, Integer min, Integer max, boolean lazy) {
		
		content.times(min, max);
		content.lazy = lazy;
		
		if(content instanceof RegexBuilder) {
			nodes.add(((RegexBuilder) content).asGroup());
		}
		else {
			nodes.add(content);
		}
		return this;
	}
	

	
	/**
	 * Adding CharacterClass to current group with quantity between 0 and 1
	 * @param characterClass CharacterClass to be added
	 */
	public Group optional(CharacterClass characterClass) 		{ return _attachNode(new ClassMatch().add(characterClass), 0, 1, false); }
	/**
	 * Adding String to current group with quantity between 0 and 1
	 * @param string String to be added
	 */
	public Group optional(String string) 						{ return _attachNode(new StringMatch().add(string), 0, 1, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between 0 and 1
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 */
	public Group optional(Node node) 							{ return _attachNode(node, 0, 1, false); }
	/**
	 * Adding CharacterClass to current group with quantity between 0 and 1 (lazy)
	 * @param characterClass CharacterClass to be added
	 */
	public Group optionalLazy(CharacterClass characterClass) 	{ return _attachNode(new ClassMatch().add(characterClass), 0, 1, true); }
	/**
	 * Adding String to current group with quantity between 0 and 1 (lazy)
	 * @param string String to be added
	 */
	public Group optionalLazy(String string) 					{ return _attachNode(new StringMatch().add(string), 0, 1, true); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between 0 and 1 (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 */
	public Group optionalLazy(Node node) 						{ return _attachNode(node, 0, 1, true); }
	
	
	/**
	 * Adding CharacterClass to current group with any quantity 
	 * @param characterClass CharacterClass to be added
	 */
	public Group any(CharacterClass characterClass) 			{ return _attachNode(new ClassMatch().add(characterClass), 0, null, false); }
	/**
	 * Adding String to current group with any quantity
	 * @param string String to be added
	 */
	public Group any(String string) 							{ return _attachNode(new StringMatch().add(string), 0, null, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with any quantity
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 */
	public Group any(Node node) 								{ return _attachNode(node, 0, null, false);}
	/**
	 * Adding CharacterClass to current group with any quantity (lazy)
	 * @param characterClass CharacterClass to be added
	 */
	public Group anyLazy(CharacterClass characterClass) 		{ return _attachNode(new ClassMatch().add(characterClass), 0, null, true); }
	/**
	 * Adding String to current group with any quantity (lazy)
	 * @param string String to be added
	 */
	public Group anyLazy(String string) 						{ return _attachNode(new StringMatch().add(string), 0, null, true); }	
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with any quantity (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 */
	public Group anyLazy(Node node) 							{ return _attachNode(node, 0, null, true); }
	
	
	/**
	 * Adding CharacterClass to current group with quantity of 1
	 * @param characterClass CharacterClass to be added
	 */
	public Group unique(CharacterClass characterClass) 			{ return _attachNode(new ClassMatch().add(characterClass), 1, 1, false); }
	/**
	 * Adding String to current group with quantity of 1
	 * @param string String to be added
	 */
	public Group unique(String string) 							{ return _attachNode(new StringMatch().add(string), 1, 1, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity of 1
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 */
	public Group unique(Node node) 								{ return _attachNode(node, 1, 1, false); }

	
	/**
	 * Adding CharacterClass to current group with specified quantity in card parameter
	 * @param characterClass CharacterClass to be added
	 * @param card specified quantity 
	 */
	public Group exactly(CharacterClass characterClass, int card)	{ return _attachNode(new ClassMatch().add(characterClass), card, card, false); }
	/**
	 * Adding String to current group with specified quantity in card parameter
	 * @param string String to be added
	 * @param card specified quantity
	 */
	public Group exactly(String string, int card) 			{ return _attachNode(new StringMatch().add(string), card, card, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with specified quantity in card parameter
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @param card specified quantity
	 */
	public Group exactly(Node node, int card) 			{ return _attachNode(node, card, card, false); }
	

	
	
	
	
	/**
	 * Adding CharacterClass to current group with quantity of at least 1
	 * @param characterClass CharacterClass to be added
	 */
	public Group some(CharacterClass characterClass) 				{ return _attachNode(new ClassMatch().add(characterClass), 1, null, false); }
	/**
	 * Adding String to current group with quantity of at least 1 
	 * @param string String to be added
	 */
	public Group some(String string) 						{ return _attachNode(new StringMatch().add(string), 1, null, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity of at least 1
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 */
	public Group some(Node node) 						{ return _attachNode(node, 1, null, false); }
	/**
	 * Adding CharacterClass to current group with quantity of at least 1 (lazy)
	 * @param characterClass CharacterClass to be added
	 */
	public Group someLazy(CharacterClass characterClass) 			{ return _attachNode(new ClassMatch().add(characterClass), 1, null, true); }
	/**
	 * Adding String to current group with quantity of at least 1 (lazy)
	 * @param string String to be added
	 */
	public Group someLazy(String string) 					{ return _attachNode(new StringMatch().add(string), 1, null, true); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity of at least 1 (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 */
	public Group someLazy(Node node) 					{ return _attachNode(node, 1, null, true); }
	
	
	// Min
	public Group min(CharacterClass content, int min) 		{ return _attachNode(new ClassMatch().add(content), min, null, false); }
	public Group min(String content, int min) 				{ return _attachNode(new StringMatch().add(content), min, null, false); }
	public Group min(Node content, int min) 				{ return _attachNode(content, min, null, false); }
	public Group minLazy(CharacterClass content, int min) 	{ return _attachNode(new ClassMatch().add(content), min, null, true); }
	public Group minLazy(String content, int min) 			{ return _attachNode(new StringMatch().add(content), min, null, true); }
	public Group minLazy(Node content, int min) 			{ return _attachNode(content, min, null, true); }
	
	// Max
	public Group max(CharacterClass content, int max) 		{ return _attachNode(new ClassMatch().add(content), 0, max, false); }
	public Group max(String content, int max) 				{ return _attachNode(new StringMatch().add(content), 0, max, false); }
	public Group max(Node content, int max) 				{ return _attachNode(content, 0, max, false); }
	public Group maxLazy(CharacterClass content, int max) 	{ return _attachNode(new ClassMatch().add(content), 0, max, true); }
	public Group maxLazy(String content, int max) 			{ return _attachNode(new StringMatch().add(content), 0, max, true); }
	public Group maxLazy(Node content, int max) 			{ return _attachNode(content, 0, max, true); }
	
	// Between
	public Group between(CharacterClass content, int min, int max) 		{ return _attachNode(new ClassMatch().add(content), min, max, false); }
	public Group between(String content, int min, int max) 				{ return _attachNode(new StringMatch().add(content), min, max, false); }
	public Group between(Node content, int min, int max) 				{ return _attachNode(content, min, max, false); }
	public Group betweenLazy(CharacterClass content, int min, int max) 	{ return _attachNode(new ClassMatch().add(content), min, max, true); }
	public Group betweenLazy(String content, int min, int max) 			{ return _attachNode(new StringMatch().add(content), min, max, true); }
	public Group betweenLazy(Node content, int min, int max) 			{ return _attachNode(content, min, max, true); }
		
	


	public enum ChildrenType{
		Sequence,
		Alternative
	}
	
	public enum GroupType{
		None(""),
		Capturing(""),
		NonCapturing("?:"),
		PositiveLookAhead("?="),
		NegativeLookAhead("?!"),
		PositiveLookBehind("?<="),
		NegativeLookBehind("?<!");
		
		public final String expression;
		
		GroupType(String expression) {
			this.expression = expression;
		}
		
	}
	
	public String toString() {
		if(nodes == null || nodes.isEmpty()) {
			return "";
		}
		
		StringBuilder result = new StringBuilder();
		
		if(markedAsGroup()) {
			result.append("(");
			result.append(groupType.expression);
		}
		
		
		
		if(childrenType == ChildrenType.Alternative) {
			result.append(nodes.stream().map(x -> x.toString()).collect(Collectors.joining("|")));
		}
		else if(childrenType == ChildrenType.Sequence) {
			result.append(nodes.stream().map(x -> x.toString()).collect(Collectors.joining()));
		}
		
		if(markedAsGroup()) {
			result.append(")");
		}
		
		result.append(renderSize());
		
		return result.toString();
	}

	
	private boolean markedAsGroup() {
		
		// Mark as group for all types that are not default
		if(groupType != GroupType.None) {
			return true;
		}
		
		// Mark as group to apply size on all elements
		if(nodes.size() >= 1 || !renderSize().isBlank()) {
			return true;
		}

		return false;
		
	}
	
	List<Group> getCapturingGroups(){
		
		List<Group> groups = new ArrayList<>();
		
		for(Node node : nodes) {
			if(node instanceof Group) {
				Group group = (Group) node;
				if(group.groupType == GroupType.Capturing || (group.groupType == GroupType.None && markedAsGroup())) {
					groups.add(group);
				}
				groups.addAll(group.getCapturingGroups());
			}
			else if(node instanceof StringMatch) {
				StringMatch sm = (StringMatch) node;
				if(sm.markedAsGroup()) {
					groups.add(null);
				}
			}
		}
		
		return groups;
		
	}

	/**
	 * Set capturing group name, using this method will automatically make the group a capturing group (equivalent to {@code setGroupType(GroupType.Capturing)})
	 */
	public Group setName(String groupName) {
		this.groupType = GroupType.Capturing;
		this.groupName = groupName;
		return this;
	}

	/**
	 * Set current group type
	 * @see GroupType 
	 */
	public Group setGroupType(GroupType groupType) {
		this.groupType = groupType;
		return this;
	}
	
	public Group setChildrenType(ChildrenType childrenType) {
		this.childrenType = childrenType;
		return this;
	}
	
}
