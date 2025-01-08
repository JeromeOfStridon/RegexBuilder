package com.regexbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.regexbuilder.ClassMatch.CharacterClass;



public class Group extends Node {

	TreeType treeType;
	
	GroupType groupType;

	List<Node> nodes = new ArrayList<>();
	
	String groupName;
	
	/**
	 * Creates group with specified TreeType and GroupType
	 */
	protected Group(TreeType treeType, GroupType groupType) {
		this.treeType = treeType;
		this.groupType = groupType;
	}
	
	/**
	 * Creates group with standard behavior (sequential, undefined group type)
	 */
	protected Group() {
		this(TreeType.Sequence, GroupType.Undefined);
	}
	
	private Group add(Node content) {
		
		if(content instanceof Regex) {
			nodes.add(((Regex) content).asGroup());
		}
		else {
			nodes.add(content);
		}
		return this;
		
	}

	
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between 0 and 1
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Group optional(ClassMatch classMatch) 				{ return add(new Group().add(classMatch).setQuantity(0, 1).setLazy(false)); }
	/**
	 * Adding CharacterClass to current group with quantity between 0 and 1
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group optional(CharacterClass characterClass) 		{ return add(new ClassMatch().add(characterClass).setQuantity(0,1).setLazy(false)); }
	/**
	 * Adding String to current group with quantity between 0 and 1
	 * @param string String to be added
	 * @return self
	 */
	public Group optional(String string) 						{ return add(new StringMatch().add(string).setQuantity(0, 1).setLazy(false)); }
	/**
	 * Adding StringMatch to current group with quantity between 0 and 1
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Group optional(StringMatch stringMatch) 				{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(0, 1).setLazy(false)); }
	/**
	 * Adding Group to current group with quantity between 0 and 1
	 * @param group Group to be added
	 * @return self
	 */
	public Group optional(Group group) 							{ return add(new Group().add(group).setQuantity(0, 1).setLazy(false)); }

	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between 0 and 1 (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Group optionalLazy(ClassMatch classMatch) 			{ return add(new Group().add(classMatch).setQuantity(0, 1).setLazy(true)); }
	/**
	 * Adding CharacterClass to current group with quantity between 0 and 1 (lazy)
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group optionalLazy(CharacterClass characterClass) 	{ return add(new ClassMatch().add(characterClass).setQuantity(0, 1).setLazy(true)); }
	/**
	 * Adding String to current group with quantity between 0 and 1 (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Group optionalLazy(String string) 					{ return add(new StringMatch().add(string).setQuantity(0, 1).setLazy(true)); }
	/**
	 * Adding String to current group with quantity between 0 and 1 (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Group optionalLazy(StringMatch stringMatch) 			{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(0, 1).setLazy(true)); }
	/**
	 * Adding Group to current group with quantity between 0 and 1 (lazy)
	 * @param group Group to be added
	 * @return self
	 */
	public Group optionalLazy(Group group) 						{ return add(new Group().add(group).setQuantity(0, 1).setLazy(true)); }
	
	
	/**
	 * Adding ClassMatch to current group with any quantity
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Group any(ClassMatch classMatch) 						{ return add(new Group().add(classMatch).setQuantity(0, null).setLazy(false)); }
	/**
	 * Adding CharacterClass to current group with any quantity 
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group any(CharacterClass characterClass) 			{ return add(new ClassMatch().add(characterClass).setQuantity(0, null).setLazy(false)); }
	/**
	 * Adding String to current group with any quantity
	 * @param string String to be added
	 * @return self
	 */
	public Group any(String string) 							{ return add(new StringMatch().add(string).setQuantity(0, null).setLazy(false)); }
	/**
	 * Adding StringMatch to current group with any quantity
	 * @param stringMatch String to be added
	 * @return self
	 */
	public Group any(StringMatch stringMatch) 						{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(0, null).setLazy(false)); }
	/**
	 * Adding Group to current group with any quantity
	 * @param group Group to be added
	 * @return self
	 */
	public Group any(Group group) 								{ return add(new Group().add(group).setQuantity(0, null).setLazy(false)); }
	

	
	/**
	 * Adding ClassMatch to current group with any quantity (lazy)
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Group anyLazy(ClassMatch classMatch) 				{ return add(new Group().add(classMatch).setQuantity(0, null).setLazy(true)); }
	/**
	 * Adding CharacterClass to current group with any quantity (lazy)
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group anyLazy(CharacterClass characterClass) 		{ return add(new ClassMatch().add(characterClass).setQuantity(0, null).setLazy(true)); }
	/**
	 * Adding String to current group with any quantity (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Group anyLazy(String string) 						{ return add(new StringMatch().add(string).setQuantity(0, null).setLazy(true)); }	
	/**
	 * Adding StringMatch to current group with any quantity (lazy)
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Group anyLazy(StringMatch stringMatch) 				{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(0, null).setLazy(true)); }	
	/**
	 * Adding Group to current group with any quantity (lazy)
	 * @param group Group to be added
	 * @return self
	 */
	public Group anyLazy(Group group) 							{ return add(new Group().add(group).setQuantity(0, null).setLazy(true)); }
	
	
	/**
	 * Adding ClassMatch to current group with quantity of 1
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Group unique(ClassMatch classMatch) 					{ return add(new Group().add(classMatch).setQuantity(1, 1).setLazy(false)); }
	/**
	 * Adding CharacterClass to current group with quantity of 1
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group unique(CharacterClass characterClass) 			{ return add(new ClassMatch().add(characterClass).setQuantity(1, 1).setLazy(false)); }
	/**
	 * Adding String to current group with quantity of 1
	 * @param string String to be added
	 * @return self
	 */
	public Group unique(String string) 							{ return add(new StringMatch().add(string).setQuantity(1, 1).setLazy(false)); }
	/**
	 * Adding StringMatch to current group with quantity of 1
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Group unique(StringMatch stringMatch) 				{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(1, 1).setLazy(false)); }
	/**
	 * Adding Group to current group with quantity of 1
	 * @param group Group to be added
	 * @return self
	 */
	public Group unique(Group group) 							{ return add(new Group().add(group).setQuantity(1, 1).setLazy(false)); }
	


	
	/**
	 * Adding ClassMatch to current group with specified quantity in card parameter
	 * @param classMatch ClassMatch to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Group exactly(ClassMatch classMatch, int card)			{ return add(new Group().add(classMatch).setQuantity(card, card).setLazy(false)); }
	/**
	 * Adding CharacterClass to current group with specified quantity in card parameter
	 * @param characterClass CharacterClass to be added
	 * @param card specified quantity 
	 * @return self
	 */
	public Group exactly(CharacterClass characterClass, int card)	{ return add(new ClassMatch().add(characterClass).setQuantity(card, card).setLazy(false)); }
	/**
	 * Adding String to current group with specified quantity in card parameter
	 * @param string String to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Group exactly(String string, int card) 					{ return add(new StringMatch().add(string).setQuantity(card, card).setLazy(false)); }
	/**
	 * Adding StringMatch to current group with specified quantity in card parameter
	 * @param stringMatch StringMatch to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Group exactly(StringMatch stringMatch, int card) 		{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(card, card).setLazy(false)); }
	/**
	 * Adding Group to current group with specified quantity in card parameter
	 * @param group Group to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Group exactly(Group group, int card) 					{ return add(new Group().add(group).setQuantity(card, card).setLazy(false)); }
	

	
	/**
	 * Adding ClassMatch to current group with quantity of at least 1
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Group some(ClassMatch classMatch)						{ return add(new Group().add(classMatch).setQuantity(1, null).setLazy(false)); }
	/**
	 * Adding CharacterClass to current group with quantity of at least 1
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group some(CharacterClass characterClass) 				{ return add(new ClassMatch().add(characterClass).setQuantity(1, null).setLazy(false)); }
	/**
	 * Adding String to current group with quantity of at least 1 
	 * @param string String to be added
	 * @return self
	 */
	public Group some(String string) 								{ return add(new StringMatch().add(string).setQuantity(1, null).setLazy(false)); }
	/**
	 * Adding StringMatch to current group with quantity of at least 1 
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Group some(StringMatch stringMatch) 						{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(1, null).setLazy(false)); }
	/**
	 * Adding Group to current group with quantity of at least 1
	 * @param group Group to be added
	 * @return self
	 */
	public Group some(Group group) 									{ return add(new Group().add(group).setQuantity(1, null).setLazy(false)); }

	/**
	 * Adding ClassMatch to current group with quantity of at least 1 (lazy)
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Group someLazy(ClassMatch classMatch)					{ return add(new Group().add(classMatch).setQuantity(1, null).setLazy(true)); }
	/**
	 * Adding CharacterClass to current group with quantity of at least 1 (lazy)
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Group someLazy(CharacterClass characterClass) 			{ return add(new ClassMatch().add(characterClass).setQuantity(1, null).setLazy(true)); }
	/**
	 * Adding String to current group with quantity of at least 1 (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Group someLazy(String string) 							{ return add(new StringMatch().add(string).setQuantity(1, null).setLazy(true)); }
	/**
	 * Adding StringMatch to current group with quantity of at least 1 (lazy)
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Group someLazy(StringMatch stringMatch) 					{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(1, null).setLazy(true)); }
	/**
	 * Adding Group to current group with quantity of at least 1 (lazy)
	 * @param group Group to be added
	 * @return self
	 */
	public Group someLazy(Group group) 								{ return add(new Group().add(group).setQuantity(1, null).setLazy(true)); }

	
	/**
	 * Adding ClassMatch to current group with minimum quantity as specified in min parameter
	 * @param classMatch ClassMatch to be added 
	 * @param min minimum quantity
	 * @return self
	 */
	public Group min(ClassMatch classMatch, int min)					{ return add(new Group().add(classMatch).setQuantity(min, null).setLazy(false)); }
	/**
	 * Adding CharacterClass to current group with minimum quantity as specified in min parameter
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group min(CharacterClass characterClass, int min) 		{ return add(new ClassMatch().add(characterClass).setQuantity(min, null).setLazy(false)); }
	/**
	 * Adding String to current group with minimum quantity as specified in min parameter
	 * @param string String to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group min(String string, int min) 						{ return add(new StringMatch().add(string).setQuantity(min, null).setLazy(false)); }
	/**
	 * Adding StringMatch to current group with minimum quantity as specified in min parameter
	 * @param stringMatch StringMatch to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group min(StringMatch stringMatch, int min) 					{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(min, null).setLazy(false)); }
	/**
	 * Adding Group to current group with minimum quantity as specified in min parameter
	 * @param group Group to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group min(Group group, int min) 							{ return add(new Group().add(group).setQuantity(min, null).setLazy(false)); }
	
	/**
	 * Adding ClassMatch to current group with minimum quantity as specified in min parameter (lazy)
	 * @param classMatch ClassMatch to be added
	 * @param min minimum quantity 
	 * @return self
	 */
	public Group minLazy(ClassMatch classMatch, int min)				{ return add(new Group().add(classMatch).setQuantity(min, null).setLazy(true)); }
	/**
	 * Adding CharacterClass to current group with minimum quantity as specified in min parameter (lazy)
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group minLazy(CharacterClass characterClass, int min) 	{ return add(new ClassMatch().add(characterClass).setQuantity(min, null).setLazy(true)); }
	/**
	 * Adding String to current group with minimum quantity as specified in min parameter (lazy)
	 * @param string String to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group minLazy(String string, int min) 					{ return add(new StringMatch().add(string).setQuantity(min, null).setLazy(true)); }
	/**
	 * Adding StringMatch to current group with minimum quantity as specified in min parameter (lazy)
	 * @param stringMatch StringMatch to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group minLazy(StringMatch stringMatch, int min) 			{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(min, null).setLazy(true)); }
	/**
	 * Adding Group to current group with minimum quantity as specified in min parameter (lazy)
	 * @param group Group to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Group minLazy(Group group, int min) 						{ return add(new Group().add(group).setQuantity(min, null).setLazy(true)); }



	
	
	/**
	 * Adding ClassMatch to current group with maximum quantity as specified in max parameter
	 * @param classMatch ClassMatch to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group max(ClassMatch classMatch, int max)					{ return add(new Group().add(classMatch).setQuantity(0, max).setLazy(false)); }
	/**
	 * Adding CharacterClass to current group with maximum quantity as specified in max parameter
	 * @param characterClass CharacterClass to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group max(CharacterClass characterClass, int max) 		{ return add(new ClassMatch().add(characterClass).setQuantity(0, max).setLazy(false)); }
	/**
	 * Adding String to current group with maximum quantity as specified in max parameter
	 * @param string String to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group max(String string, int max) 						{ return add(new StringMatch().add(string).setQuantity(0, max).setLazy(false)); }
	/**
	 * Adding StringMatch to current group with maximum quantity as specified in max parameter
	 * @param stringMatch StringMatch to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group max(StringMatch stringMatch, int max) 				{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(0, max).setLazy(false)); }
	/**
	 * Adding Group to current group with maximum quantity as specified in max parameter
	 * @param group Group to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group max(Group group, int max) 							{ return add(new Group().add(group).setQuantity(0, max).setLazy(false)); }

	/**
	 * Adding ClassMatch to current group with maximum quantity as specified in max parameter (lazy)
	 * @param classMatch ClassMatch to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group maxLazy(ClassMatch classMatch, int max)			{ return add(new Group().add(classMatch).setQuantity(0, max).setLazy(true)); }
	/**
	 * Adding CharacterClass to current group with maximum quantity as specified in max parameter (lazy)
	 * @param characterClass CharacterClass to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group maxLazy(CharacterClass characterClass, int max) 	{ return add(new ClassMatch().add(characterClass).setQuantity(0, max).setLazy(true)); }
	/**
	 * Adding String to current group with maximum quantity as specified in max parameter (lazy)
	 * @param string String to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group maxLazy(String string, int max) 					{ return add(new StringMatch().add(string).setQuantity(0, max).setLazy(true)); }
	/**
	 * Adding StringMatch to current group with maximum quantity as specified in max parameter (lazy)
	 * @param stringMatch StringMatch to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group maxLazy(StringMatch stringMatch, int max) 			{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(0, max).setLazy(true)); }
	/**
	 * Adding Group to current group with maximum quantity as specified in max parameter (lazy)
	 * @param group Group to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Group maxLazy(Group group, int max) 						{ return add(new Group().add(group).setQuantity(0, max).setLazy(true)); }

	// Between
	/**
	 * Adding Group to current group with quantity between specified values in min & max parameters
	 * @param classMatch Group to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group between(ClassMatch classMatch, int min, int max) 				{ return add(new Group().add(classMatch).setQuantity(min, max).setLazy(false)); }
	/**
	 * Adding CharacterClass to current group with quantity between specified values in min & max parameters
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group between(CharacterClass characterClass, int min, int max) 		{ return add(new ClassMatch().add(characterClass).setQuantity(min, max).setLazy(false)); }
	/**
	 * Adding String to current group with quantity between specified values in min & max parameters
	 * @param string String to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group between(String string, int min, int max) 						{ return add(new StringMatch().add(string).setQuantity(min, max).setLazy(false)); }
	/**
	 * Adding StringMatch to current group with quantity between specified values in min & max parameters
	 * @param stringMatch StringMatch to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group between(StringMatch stringMatch, int min, int max) 			{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(min, max).setLazy(false)); }
	/**
	 * Adding Group to current group with quantity between specified values in min & max parameters
	 * @param group Group to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group between(Group group, int min, int max) 						{ return add(new Group().add(group).setQuantity(min, max).setLazy(false)); }

	/**
	 * Adding ClassMatch to current group with quantity between specified values in min & max parameters (lazy)
	 * @param classMatch ClassMatch to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group betweenLazy(ClassMatch classMatch, int min, int max) 			{ return add(new Group().add(classMatch).setQuantity(min, max).setLazy(true)); }
	/**
	 * Adding CharacterClass to current group with quantity between specified values in min & max parameters (lazy)
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	 public Group betweenLazy(CharacterClass characterClass, int min, int max) 	{ return add(new ClassMatch().add(characterClass).setQuantity(min, max).setLazy(true)); }
	/**
	 * Adding String to current group with quantity between specified values in min & max parameters (lazy)
	 * @param string String to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group betweenLazy(String string, int min, int max) 					{ return add(new StringMatch().add(string).setQuantity(min, max).setLazy(true)); }
	/**
	 * Adding StringMatch to current group with quantity between specified values in min & max parameters (lazy)
	 * @param stringMatch StringMatch to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group betweenLazy(StringMatch stringMatch, int min, int max) 		{ return add(new StringMatch().add(stringMatch.stringBuilder.toString()).setQuantity(min, max).setLazy(true)); }
	/**
	 * Adding Group to current group with quantity between specified values in min & max parameters (lazy)
	 * @param group Group to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Group betweenLazy(Group group, int min, int max) 					{ return add(new Group().add(group).setQuantity(min, max).setLazy(true)); }
		



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
		
		simplify();
		
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
	
	public void simplify() {

		// if only one node, try to merge
		if(nodes.size() == 1 && nodes.get(0) instanceof Group) {

			Group subGroup = (Group) nodes.get(0);
		
			// abort if conflict on groupType
			if(this.groupType != GroupType.Undefined && subGroup.groupType != GroupType.Undefined) {
				return;
			}
			
			// abort if conflict on groupName
			if(this.groupName != null && subGroup.groupName != null) {
				return;
			}
			
			// abort if conflict on size, at least one should be 
			if(!(Objects.equals(this.minSize, 1) && Objects.equals(this.maxSize, 1)) && !(Objects.equals(subGroup.minSize, 1) && Objects.equals(subGroup.maxSize, 1))) {
				return;
			}
			
			this.nodes = subGroup.nodes;
			this.treeType = subGroup.treeType;
			
			// formula to try get the non default value from both group & subgroup : this.value = this.value = default ? subGroup.value : this.value
			this.groupType = this.groupType == GroupType.Undefined ? subGroup.groupType : this.groupType;
			this.groupName = this.groupName == null ? subGroup.groupName : this.groupName;
			this.minSize = (Objects.equals(this.minSize, 1) && Objects.equals(this.maxSize, 1)) ? subGroup.minSize : this.minSize;
			this.maxSize = (Objects.equals(this.minSize, 1) && Objects.equals(this.maxSize, 1)) ? subGroup.maxSize : this.maxSize;
			this.lazy = this.lazy == false ? subGroup.lazy : this.lazy;
			
		
		}
			
		
	}
	
	
	
	protected boolean markedAsGroup() {
		
		if(nodes.isEmpty()) {
			return false;
		}
		
		//  all groups that needs to be identified
		if(groupType != GroupType.Undefined) {
			return true;
		}
		
		// If contains only one element, don't bother
		if(nodes.size() == 1) {
			return false;
		}
		if(treeType == TreeType.Alternative) {
			return true;
		}
		
		if(StringUtils.isBlank(renderSize())) {
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
