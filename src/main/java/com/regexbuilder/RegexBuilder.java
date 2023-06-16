package com.regexbuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

// Factory for all kinds of match & groups
public class RegexBuilder extends Group {

	@Getter @Setter
	boolean anchorStart = false;

	@Getter @Setter
	boolean anchorEnd = false;

	
	// CONSTRUCTORS
	protected RegexBuilder(TreeType childrenType, GroupType groupType) {
		super(childrenType, groupType);
	}
	
	/**
	 * Setter for anchorStart returning current RegexBuilder instance
	 * @param anchorStart should RegexBuilder have a start anchor or not
	 * @return self
	 */
	public RegexBuilder anchorStart(boolean anchorStart) {
		this.anchorStart = anchorStart;
		return this;
	}

	/**
	 * Setter for anchorEnd returning current RegexBuilder instance
	 * @param anchorStart should RegexBuilder have a end anchor or not
	 * @return current RegexBuilder instance
	 */
	public RegexBuilder anchorEnd(boolean anchorEnd) {
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
	public RegexBuilder clone() {
		RegexBuilder cloned = SerializationUtils.clone(this);
		return cloned;
	}

	
}
