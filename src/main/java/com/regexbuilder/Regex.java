package com.regexbuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group.GroupType;
import com.regexbuilder.Group.TreeType;

import lombok.Getter;
import lombok.Setter;

// Factory for all kinds of match & groups
public class Regex extends Group {

	@Getter @Setter
	boolean anchorStart = false;

	@Getter @Setter
	boolean anchorEnd = false;

	
	// CONSTRUCTORS
	protected Regex(TreeType childrenType, GroupType groupType) {
		super(childrenType, groupType);
	}
	
	/**
	 * Setter for anchorStart returning current RegexBuilder instance
	 * @param anchorStart should RegexBuilder have a start anchor or not
	 * @return self
	 */
	public Regex anchorStart(boolean anchorStart) {
		this.anchorStart = anchorStart;
		return this;
	}

	/**
	 * Setter for anchorEnd returning current RegexBuilder instance
	 * @param anchorStart should RegexBuilder have a end anchor or not
	 * @return current RegexBuilder instance
	 */
	public Regex anchorEnd(boolean anchorEnd) {
		this.anchorEnd = anchorEnd;
		return this;
	}


	/**
	 * Compiling current RegexBuilder into regex string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(anchorStart) {
			sb.append("^");
		}
		
		String groupString = super.toString();
		if(groupString.startsWith("(") && groupString.endsWith(")") && super.markedAsGroup() && groupType == GroupType.Undefined) {
			groupString = groupString.substring(1, groupString.length()-1);
		}
		
		sb.append(groupString);
		
		if(anchorEnd) {
			sb.append("$");
		}
		return sb.toString();
	}



	public Integer findGroupPosition(String groupName) {
		Map<Integer, String> map = getGroupPositions();
		for(Entry<Integer, String> entry : map.entrySet()) {
			if(StringUtils.equals(groupName, entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	/**
	 * Creates a map with regex capturing groups and associated name if any
	 * @return map of group ids and names
	 */
	public Map<Integer, String> getGroupPositions(){
		Map<Integer, String> result = new LinkedHashMap<>();
		List<Group> groups = getCapturingGroups();
		for(int i = 0; i < groups.size(); i++) {
			if(groups.get(i) == null) {
				continue;
			}
			if(groups.get(i).groupName != null) {
				// Offset of 1 because group(0) is the general one
				result.put(i + 1, groups.get(i).groupName);
			}
		}
		return result;
	}
	
	@Override
	List<Group> getCapturingGroups(){
		List<Group> groups = new ArrayList<>();
		// As marked as group, Will be considered as a capturing group, so needs to be included
		if(groupType == GroupType.Undefined && markedAsGroup()) {
			groups.add(this);
		}
		groups.addAll(super.getCapturingGroups());
		return groups;
	}
	/**
	 * Cast current RegexBuilder instance into Group
	 * @return Group built out of current RegexBuilder instance
	 */
	public Group asGroup() {
		Group group = new Group(this.treeType, this.groupType);
		group.nodes = this.nodes;
		group.groupName = this.groupName;
		
		return group;
	}
	
	/**
	 * Creates a regular Regex Pattern instance out of current RegexBuilder instance
	 * @return Pattern instance
	 */
	public Pattern compile() {
		return Pattern.compile(this.toString());
	}
	
	/**
	 * Creates a clone of current RegexBuilder instance
	 * @return cloned RegexBuilder instance
	 */
	public Regex clone() {
		Regex cloned = SerializationUtils.clone(this);
		return cloned;
	}
	
	
	/**
	 * Set capturing group name, using this method will automatically make the group a capturing group (equivalent to {@code setGroupType(GroupType.Capturing)})
	 * @return self
	 */
	public Regex setName(String groupName) { super.setName(groupName); return this; }
	
	/**
	 * Set current group GroupType
	 * @param groupType GroupType to set for this group
	 * @return self
	 */
	public Regex setGroupType(GroupType groupType) { super.setGroupType(groupType); return this; }
	
	/**
	 * Set current group TreeType
	 * @param treeType ChildrenTYpe to set for this group
	 * @return self
	 */
	public Regex setTreeType(TreeType treeType) { super.setTreeType(treeType); return this; }

	/**
	 * Sets quantity manually by defining minSize & maxSize
	 * @param minSize
	 * @param maxSize
	 */
	public Regex setQuantity(Integer minSize, Integer maxSize) { super.setQuantity(minSize, maxSize); return this; }
	
	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between 0 and 1
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Regex optional(ClassMatch classMatch) 				{ super.optional(classMatch); return this; }
	/**
	 * Adding CharacterClass to current group with quantity between 0 and 1
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Regex optional(CharacterClass characterClass) 		{ super.optional(characterClass); return this; }
	/**
	 * Adding String to current group with quantity between 0 and 1
	 * @param string String to be added
	 * @return self
	 */
	public Regex optional(String string) 						{ super.optional(string); return this; }
	/**
	 * Adding StringMatch to current group with quantity between 0 and 1
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Regex optional(StringMatch stringMatch) 				{ super.optional(stringMatch); return this; }
	/**
	 * Adding Group to current group with quantity between 0 and 1
	 * @param group Group to be added
	 * @return self
	 */
	public Regex optional(Group group) 							{ super.optional(group); return this; }

	/**
	 * Adding Node (ClassMatch, Group or StringMatch) to current group with quantity between 0 and 1 (lazy)
	 * @param node Node (ClassMatch, Group or StringMatch) to be added
	 * @return self
	 */
	public Regex optionalLazy(ClassMatch classMatch) 			{ super.optionalLazy(classMatch); return this; }
	/**
	 * Adding CharacterClass to current group with quantity between 0 and 1 (lazy)
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Regex optionalLazy(CharacterClass characterClass) 	{ super.optionalLazy(characterClass); return this; }
	/**
	 * Adding String to current group with quantity between 0 and 1 (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Regex optionalLazy(String string) 					{ super.optionalLazy(string); return this; }
	/**
	 * Adding String to current group with quantity between 0 and 1 (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Regex optionalLazy(StringMatch stringMatch) 			{ super.optionalLazy(stringMatch); return this; }
	/**
	 * Adding Group to current group with quantity between 0 and 1 (lazy)
	 * @param group Group to be added
	 * @return self
	 */
	public Regex optionalLazy(Group group) 						{ super.optionalLazy(group); return this; }
	
	
	/**
	 * Adding ClassMatch to current group with any quantity
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Regex any(ClassMatch classMatch) 					{ super.any(classMatch); return this; }
	/**
	 * Adding CharacterClass to current group with any quantity 
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Regex any(CharacterClass characterClass) 			{ super.any(characterClass); return this; }
	/**
	 * Adding String to current group with any quantity
	 * @param string String to be added
	 * @return self
	 */
	public Regex any(String string) 							{ super.any(string); return this; }
	/**
	 * Adding StringMatch to current group with any quantity
	 * @param stringMatch String to be added
	 * @return self
	 */
	public Regex any(StringMatch stringMatch) 					{ super.any(stringMatch); return this; }
	/**
	 * Adding Group to current group with any quantity
	 * @param group Group to be added
	 * @return self
	 */
	public Regex any(Group group) 								{ super.any(group); return this; }
	

	
	/**
	 * Adding ClassMatch to current group with any quantity (lazy)
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Regex anyLazy(ClassMatch classMatch) 				{ super.anyLazy(classMatch); return this; }
	/**
	 * Adding CharacterClass to current group with any quantity (lazy)
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Regex anyLazy(CharacterClass characterClass) 		{ super.anyLazy(characterClass); return this; }
	/**
	 * Adding String to current group with any quantity (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Regex anyLazy(String string) 						{ super.anyLazy(string); return this; }	
	/**
	 * Adding StringMatch to current group with any quantity (lazy)
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Regex anyLazy(StringMatch stringMatch) 				{ super.anyLazy(stringMatch); return this; }	
	/**
	 * Adding Group to current group with any quantity (lazy)
	 * @param group Group to be added
	 * @return self
	 */
	public Regex anyLazy(Group group) 							{ super.anyLazy(group); return this; }
	
	
	/**
	 * Adding ClassMatch to current group with quantity of 1
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Regex unique(ClassMatch classMatch) 					{ super.unique(classMatch); return this; }
	/**
	 * Adding CharacterClass to current group with quantity of 1
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Regex unique(CharacterClass characterClass) 			{ super.unique(characterClass); return this; }
	/**
	 * Adding String to current group with quantity of 1
	 * @param string String to be added
	 * @return self
	 */
	public Regex unique(String string) 							{ super.unique(string); return this; }
	/**
	 * Adding StringMatch to current group with quantity of 1
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Regex unique(StringMatch stringMatch) 				{ super.unique(stringMatch); return this; }
	/**
	 * Adding Group to current group with quantity of 1
	 * @param group Group to be added
	 * @return self
	 */
	public Regex unique(Group group) 							{ super.unique(group); return this; }
	


	
	/**
	 * Adding ClassMatch to current group with specified quantity in card parameter
	 * @param classMatch ClassMatch to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Regex exactly(ClassMatch classMatch, int card)			{ super.exactly(classMatch, card); return this; }
	/**
	 * Adding CharacterClass to current group with specified quantity in card parameter
	 * @param characterClass CharacterClass to be added
	 * @param card specified quantity 
	 * @return self
	 */
	public Regex exactly(CharacterClass characterClass, int card)	{ super.exactly(characterClass, card); return this; }
	/**
	 * Adding String to current group with specified quantity in card parameter
	 * @param string String to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Regex exactly(String string, int card) 					{ super.exactly(string, card); return this; }
	/**
	 * Adding StringMatch to current group with specified quantity in card parameter
	 * @param stringMatch StringMatch to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Regex exactly(StringMatch stringMatch, int card) 		{ super.exactly(stringMatch, card); return this; }
	/**
	 * Adding Group to current group with specified quantity in card parameter
	 * @param group Group to be added
	 * @param card specified quantity
	 * @return self
	 */
	public Regex exactly(Group group, int card) 					{ super.exactly(group, card); return this; }
	

	
	/**
	 * Adding ClassMatch to current group with quantity of at least 1
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Regex some(ClassMatch classMatch)						{ super.some(classMatch); return this; }
	/**
	 * Adding CharacterClass to current group with quantity of at least 1
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Regex some(CharacterClass characterClass) 				{ super.some(characterClass); return this; }
	/**
	 * Adding String to current group with quantity of at least 1 
	 * @param string String to be added
	 * @return self
	 */
	public Regex some(String string) 								{ super.some(string); return this; }
	/**
	 * Adding StringMatch to current group with quantity of at least 1 
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Regex some(StringMatch stringMatch) 						{ super.some(stringMatch); return this; }
	/**
	 * Adding Group to current group with quantity of at least 1
	 * @param group Group to be added
	 * @return self
	 */
	public Regex some(Group group) 									{ super.some(group); return this; }

	/**
	 * Adding ClassMatch to current group with quantity of at least 1 (lazy)
	 * @param classMatch ClassMatch to be added
	 * @return self
	 */
	public Regex someLazy(ClassMatch classMatch)					{ super.someLazy(classMatch); return this; }
	/**
	 * Adding CharacterClass to current group with quantity of at least 1 (lazy)
	 * @param characterClass CharacterClass to be added
	 * @return self
	 */
	public Regex someLazy(CharacterClass characterClass) 			{ super.someLazy(characterClass); return this; }
	/**
	 * Adding String to current group with quantity of at least 1 (lazy)
	 * @param string String to be added
	 * @return self
	 */
	public Regex someLazy(String string) 							{ super.someLazy(string); return this; }
	/**
	 * Adding StringMatch to current group with quantity of at least 1 (lazy)
	 * @param stringMatch StringMatch to be added
	 * @return self
	 */
	public Regex someLazy(StringMatch stringMatch) 					{ super.someLazy(stringMatch); return this; }
	/**
	 * Adding Group to current group with quantity of at least 1 (lazy)
	 * @param group Group to be added
	 * @return self
	 */
	public Regex someLazy(Group group) 								{ super.someLazy(group); return this; }

	
	/**
	 * Adding ClassMatch to current group with minimum quantity as specified in min parameter
	 * @param classMatch ClassMatch to be added 
	 * @param min minimum quantity
	 * @return self
	 */
	public Regex min(ClassMatch classMatch, int min)					{ super.min(classMatch, min); return this; }
	/**
	 * Adding CharacterClass to current group with minimum quantity as specified in min parameter
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Regex min(CharacterClass characterClass, int min) 		{ super.min(characterClass, min); return this; }
	/**
	 * Adding String to current group with minimum quantity as specified in min parameter
	 * @param string String to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Regex min(String string, int min) 						{ super.min(string, min); return this; }
	/**
	 * Adding StringMatch to current group with minimum quantity as specified in min parameter
	 * @param stringMatch StringMatch to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Regex min(StringMatch stringMatch, int min) 					{ super.min(stringMatch, min); return this; }
	/**
	 * Adding Group to current group with minimum quantity as specified in min parameter
	 * @param group Group to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Regex min(Group group, int min) 							{ super.min(group, min); return this; }
	
	/**
	 * Adding ClassMatch to current group with minimum quantity as specified in min parameter (lazy)
	 * @param classMatch ClassMatch to be added
	 * @param min minimum quantity 
	 * @return self
	 */
	public Regex minLazy(ClassMatch classMatch, int min)				{ super.minLazy(classMatch, min); return this; }
	/**
	 * Adding CharacterClass to current group with minimum quantity as specified in min parameter (lazy)
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Regex minLazy(CharacterClass characterClass, int min) 	{ super.minLazy(characterClass, min); return this; }
	/**
	 * Adding String to current group with minimum quantity as specified in min parameter (lazy)
	 * @param string String to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Regex minLazy(String string, int min) 					{ super.minLazy(string, min); return this; }
	/**
	 * Adding StringMatch to current group with minimum quantity as specified in min parameter (lazy)
	 * @param stringMatch StringMatch to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Regex minLazy(StringMatch stringMatch, int min) 			{ super.minLazy(stringMatch, min); return this; }
	/**
	 * Adding Group to current group with minimum quantity as specified in min parameter (lazy)
	 * @param group Group to be added
	 * @param min minimum quantity
	 * @return self
	 */
	public Regex minLazy(Group group, int min) 						{ super.minLazy(group, min); return this; }



	
	
	/**
	 * Adding ClassMatch to current group with maximum quantity as specified in max parameter
	 * @param classMatch ClassMatch to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex max(ClassMatch classMatch, int max)					{ super.max(classMatch, max); return this; }
	/**
	 * Adding CharacterClass to current group with maximum quantity as specified in max parameter
	 * @param characterClass CharacterClass to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex max(CharacterClass characterClass, int max) 		{ super.max(characterClass, max); return this; }
	/**
	 * Adding String to current group with maximum quantity as specified in max parameter
	 * @param string String to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex max(String string, int max) 						{ super.max(string, max); return this; }
	/**
	 * Adding StringMatch to current group with maximum quantity as specified in max parameter
	 * @param stringMatch StringMatch to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex max(StringMatch stringMatch, int max) 				{ super.max(stringMatch, max); return this; }
	/**
	 * Adding Group to current group with maximum quantity as specified in max parameter
	 * @param group Group to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex max(Group group, int max) 							{ super.max(group, max); return this; }

	/**
	 * Adding ClassMatch to current group with maximum quantity as specified in max parameter (lazy)
	 * @param classMatch ClassMatch to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex maxLazy(ClassMatch classMatch, int max)			{ super.maxLazy(classMatch, max); return this; }
	/**
	 * Adding CharacterClass to current group with maximum quantity as specified in max parameter (lazy)
	 * @param characterClass CharacterClass to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex maxLazy(CharacterClass characterClass, int max) 	{ super.maxLazy(characterClass, max); return this; }
	/**
	 * Adding String to current group with maximum quantity as specified in max parameter (lazy)
	 * @param string String to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex maxLazy(String string, int max) 					{ super.maxLazy(string, max); return this; }
	/**
	 * Adding StringMatch to current group with maximum quantity as specified in max parameter (lazy)
	 * @param stringMatch StringMatch to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex maxLazy(StringMatch stringMatch, int max) 			{ super.maxLazy(stringMatch, max); return this; }
	/**
	 * Adding Group to current group with maximum quantity as specified in max parameter (lazy)
	 * @param group Group to be added
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex maxLazy(Group group, int max) 						{ super.maxLazy(group, max); return this; }

	// Between
	/**
	 * Adding Group to current group with quantity between specified values in min & max parameters
	 * @param classMatch Group to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex between(ClassMatch classMatch, int min, int max) 				{ super.between(classMatch, min, max); return this; }
	/**
	 * Adding CharacterClass to current group with quantity between specified values in min & max parameters
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex between(CharacterClass characterClass, int min, int max) 		{ super.between(characterClass, min, max); return this; }
	/**
	 * Adding String to current group with quantity between specified values in min & max parameters
	 * @param string String to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex between(String string, int min, int max) 						{ super.between(string, min, max); return this;  }
	/**
	 * Adding StringMatch to current group with quantity between specified values in min & max parameters
	 * @param stringMatch StringMatch to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex between(StringMatch stringMatch, int min, int max) 			{ super.between(stringMatch, min, max); return this; }
	/**
	 * Adding Group to current group with quantity between specified values in min & max parameters
	 * @param group Group to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex between(Group group, int min, int max) 						{ super.between(group, min, max); return this; }

	/**
	 * Adding ClassMatch to current group with quantity between specified values in min & max parameters (lazy)
	 * @param classMatch ClassMatch to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex betweenLazy(ClassMatch classMatch, int min, int max) 			{ super.betweenLazy(classMatch, min, max); return this; }
	/**
	 * Adding CharacterClass to current group with quantity between specified values in min & max parameters (lazy)
	 * @param characterClass CharacterClass to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	 public Regex betweenLazy(CharacterClass characterClass, int min, int max) 	{ super.betweenLazy(characterClass, min, max); return this; }
	/**
	 * Adding String to current group with quantity between specified values in min & max parameters (lazy)
	 * @param string String to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex betweenLazy(String string, int min, int max) 					{ super.betweenLazy(string, min, max); return this; }
	/**
	 * Adding StringMatch to current group with quantity between specified values in min & max parameters (lazy)
	 * @param stringMatch StringMatch to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex betweenLazy(StringMatch stringMatch, int min, int max) 		{ super.betweenLazy(stringMatch, min, max); return this; }
	/**
	 * Adding Group to current group with quantity between specified values in min & max parameters (lazy)
	 * @param group Group to be added
	 * @param min minimum quantity
	 * @param max maximum quantity
	 * @return self
	 */
	public Regex betweenLazy(Group group, int min, int max) 					{ super.betweenLazy(group, min, max); return this; }


	
}
