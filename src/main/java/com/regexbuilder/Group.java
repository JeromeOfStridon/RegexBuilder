package com.regexbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.regexbuilder.ClassMatch.CharacterClass;


public class Group extends Node {

	TreeType treeType;
	
	GroupType groupType;

	List<Node> nodes = new ArrayList<>();
	
	String groupName;
	
	protected Group(TreeType childrenType, GroupType groupType) {
		this.treeType = childrenType;
		this.groupType = groupType;
	}
	
	private Group attachNode(Node content, Integer min, Integer max, boolean lazy) {
		
		content.setQuantity(min, max);
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
	 * @return self
	 */
	public Group optional(CharacterClass characterClass) 		{ return attachNode(new ClassMatch().add(characterClass), 0, 1, false); }
	/**
	 * Adding String to current group with quantity between 0 and 1
	 * @param string String to be added
	 * @return self
	 */
	public Group optional(String string) 						{ return attachNode(new StringMatch().add(string), 0, 1, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between 0 and 1
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Group optional(Node node) 							{ return attachNode(node, 0, 1, false); }
	/**
	 * Adding CharacterClass to current group with quantity between 0 and 1 (lazy)
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group optionalLazy(CharacterClass characterClass) 	{ return attachNode(new ClassMatch().add(characterClass), 0, 1, true); }
	/**
	 * Adding String to current group with quantity between 0 and 1 (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Group optionalLazy(String string) 					{ return attachNode(new StringMatch().add(string), 0, 1, true); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between 0 and 1 (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Group optionalLazy(Node node) 						{ return attachNode(node, 0, 1, true); }
	
	
	/**
	 * Adding CharacterClass to current group with any quantity 
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group any(CharacterClass characterClass) 			{ return attachNode(new ClassMatch().add(characterClass), 0, null, false); }
	/**
	 * Adding String to current group with any quantity
	 * @param string String to be added
	 * @return self
	 */
	public Group any(String string) 							{ return attachNode(new StringMatch().add(string), 0, null, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with any quantity
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Group any(Node node) 								{ return attachNode(node, 0, null, false);}
	/**
	 * Adding CharacterClass to current group with any quantity (lazy)
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group anyLazy(CharacterClass characterClass) 		{ return attachNode(new ClassMatch().add(characterClass), 0, null, true); }
	/**
	 * Adding String to current group with any quantity (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Group anyLazy(String string) 						{ return attachNode(new StringMatch().add(string), 0, null, true); }	
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with any quantity (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Group anyLazy(Node node) 							{ return attachNode(node, 0, null, true); }
	
	
	/**
	 * Adding CharacterClass to current group with quantity of 1
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group unique(CharacterClass characterClass) 			{ return attachNode(new ClassMatch().add(characterClass), 1, 1, false); }
	/**
	 * Adding String to current group with quantity of 1
	 * @param string String to be added
	 * @return self
	 */
	public Group unique(String string) 							{ return attachNode(new StringMatch().add(string), 1, 1, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity of 1
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Group unique(Node node) 								{ return attachNode(node, 1, 1, false); }

	
	/**
	 * Adding CharacterClass to current group with specified quantity in card parameter
	 * @param characterClass CharacterClass to be added
	 * @param card specified quantity 
	 * @return self
	 */
	public Group exactly(CharacterClass characterClass, int card)	{ return attachNode(new ClassMatch().add(characterClass), card, card, false); }
	/**
	 * Adding String to current group with specified quantity in card parameter
	 * @param string String to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Group exactly(String string, int card) 			{ return attachNode(new StringMatch().add(string), card, card, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with specified quantity in card parameter
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Group exactly(Node node, int card) 			{ return attachNode(node, card, card, false); }
	

	
	/**
	 * Adding CharacterClass to current group with quantity of at least 1
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group some(CharacterClass characterClass) 				{ return attachNode(new ClassMatch().add(characterClass), 1, null, false); }
	/**
	 * Adding String to current group with quantity of at least 1 
	 * @param string String to be added
	 * @return self
	 */
	public Group some(String string) 						{ return attachNode(new StringMatch().add(string), 1, null, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity of at least 1
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Group some(Node node) 						{ return attachNode(node, 1, null, false); }
	/**
	 * Adding CharacterClass to current group with quantity of at least 1 (lazy)
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group someLazy(CharacterClass characterClass) 			{ return attachNode(new ClassMatch().add(characterClass), 1, null, true); }
	/**
	 * Adding String to current group with quantity of at least 1 (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Group someLazy(String string) 					{ return attachNode(new StringMatch().add(string), 1, null, true); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity of at least 1 (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Group someLazy(Node node) 					{ return attachNode(node, 1, null, true); }
	
	
	/**
	 * Adding CharacterClass to current group with minimum quantity as specified in min parameter
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group min(CharacterClass characterClass, int min) 		{ return attachNode(new ClassMatch().add(characterClass), min, null, false); }
	/**
	 * Adding String to current group with minimum quantity as specified in min parameter
	 * @param string String to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group min(String string, int min) 				{ return attachNode(new StringMatch().add(string), min, null, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with minimum quantity as specified in min parameter
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group min(Node node, int min) 				{ return attachNode(node, min, null, false); }
	/**
	 * Adding CharacterClass to current group with minimum quantity as specified in min parameter (lazy)
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group minLazy(CharacterClass characterClass, int min) 	{ return attachNode(new ClassMatch().add(characterClass), min, null, true); }
	/**
	 * Adding String to current group with minimum quantity as specified in min parameter (lazy)
	 * @param string String to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group minLazy(String string, int min) 			{ return attachNode(new StringMatch().add(string), min, null, true); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with minimum quantity as specified in min parameter (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group minLazy(Node node, int min) 			{ return attachNode(node, min, null, true); }

	
	
	
	/**
	 * Adding CharacterClass to current group with maximum quantity as specified in max parameter
	 * @param characterClass CharacterClass to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group max(CharacterClass characterClass, int max) 		{ return attachNode(new ClassMatch().add(characterClass), 0, max, false); }
	/**
	 * Adding String to current group with maximum quantity as specified in max parameter
	 * @param string String to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group max(String string, int max) 				{ return attachNode(new StringMatch().add(string), 0, max, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with maximum quantity as specified in max parameter
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group max(Node node, int max) 				{ return attachNode(node, 0, max, false); }
	/**
	 * Adding CharacterClass to current group with maximum quantity as specified in max parameter (lazy)
	 * @param characterClass CharacterClass to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group maxLazy(CharacterClass characterClass, int max) 	{ return attachNode(new ClassMatch().add(characterClass), 0, max, true); }
	/**
	 * Adding String to current group with maximum quantity as specified in max parameter (lazy)
	 * @param string String to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group maxLazy(String string, int max) 			{ return attachNode(new StringMatch().add(string), 0, max, true); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with maximum quantity as specified in max parameter (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group maxLazy(Node node, int max) 			{ return attachNode(node, 0, max, true); }
	
	// Between
	/**
	 * Adding CharacterClass to current group with quantity between specified values in min & max parameters
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group between(CharacterClass characterClass, int min, int max) 		{ return attachNode(new ClassMatch().add(characterClass), min, max, false); }
	/**
	 * Adding String to current group with quantity between specified values in min & max parameters
	 * @param string String to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group between(String string, int min, int max) 				{ return attachNode(new StringMatch().add(string), min, max, false); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between specified values in min & max parameters
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group between(Node node, int min, int max) 				{ return attachNode(node, min, max, false); }
	/**
	 * Adding CharacterClass to current group with quantity between specified values in min & max parameters (lazy)
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group betweenLazy(CharacterClass characterClass, int min, int max) 	{ return attachNode(new ClassMatch().add(characterClass), min, max, true); }
	/**
	 * Adding String to current group with quantity between specified values in min & max parameters (lazy)
	 * @param string String to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group betweenLazy(String string, int min, int max) 			{ return attachNode(new StringMatch().add(string), min, max, true); }
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between specified values in min & max parameters (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group betweenLazy(Node node, int min, int max) 			{ return attachNode(node, min, max, true); }
		
	


	public enum TreeType{
		Sequence,
		Alternative
	}
	
	public enum GroupType{
		Undefined(""),
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
	
	/**
	 * Compiling current Group into regex string
	 * @return regex string
	 */
	public String toString() {
		if(nodes == null || nodes.isEmpty()) {
			return "";
		}
		
		StringBuilder result = new StringBuilder();
		
		if(markedAsGroup()) {
			result.append("(");
			result.append(groupType.expression);
		}
		
		
		
		if(treeType == TreeType.Sequence) {
			result.append(nodes.stream().map(Object::toString).collect(Collectors.joining()));
		}
		else if(treeType == TreeType.Alternative) {
			result.append(nodes.stream().map(Object::toString).collect(Collectors.joining("|")));
		}
		else {
			return "";
		}
		
		if(markedAsGroup()) {
			result.append(")");
		}
		
		result.append(renderSize());
		
		return result.toString();
	}

	
	protected boolean markedAsGroup() {
		
		if(nodes.isEmpty()) {
			return false;
		}
		
		//  all groups that needs to be identified
		if(groupType != GroupType.Undefined) {
			return true;
		}
		
		// If none type group, and size is 
		if(nodes.size() <= 1) {
			return false;
		}
		if(treeType == TreeType.Alternative) {
			return true;
		}
		
		if(renderSize().isBlank()) {
			return false;
		}
		
		return true;
		
	}
	
	List<Group> getCapturingGroups(){
		
		List<Group> groups = new ArrayList<>();
		
		for(Node node : nodes) {
			if(node instanceof Group) {
				Group group = (Group) node;
				if(group.groupType == GroupType.Capturing || (group.groupType == GroupType.Undefined && group.markedAsGroup())) {
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
	 * @return self
	 */
	public Group setName(String groupName) {
		this.groupType = GroupType.Capturing;
		this.groupName = groupName;
		return this;
	}

	/**
	 * Set current group GroupType
	 * @param groupType GroupType to set for this group
	 * @return self
	 */
	public Group setGroupType(GroupType groupType) {
		this.groupType = groupType;
		return this;
	}
	
	/**
	 * Set current group TreeType
	 * @param treeType ChildrenTYpe to set for this group
	 * @return self
	 */
	public Group setTreeType(TreeType treeType) {
		this.treeType = treeType;
		return this;
	}
	
	/**
	 * Sets quantity manually by defining minSize & maxSize
	 * @param minSize
	 * @param maxSize
	 */
	public Group setQuantity(Integer minSize, Integer maxSize) {
		super.setQuantity(minSize, maxSize);
		return this;
	}
	
}
