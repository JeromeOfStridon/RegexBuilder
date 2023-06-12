package app.regexBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

// Factory for all kinds of match & groups
public class RegexBuilder extends Group {

	
	
	public boolean anchorStart = false;
	public boolean anchorEnd = false;

	
	// CONSTRUCTORS
	protected RegexBuilder(TreeType childrenType, GroupType groupType) {
		super(childrenType, groupType);
	}
	
	// ANCHORS
	public RegexBuilder anchorStart(boolean b) {
		anchorStart = b;
		return this;
	}

	public RegexBuilder anchorEnd(boolean b) {
		anchorEnd = b;
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
		
		if(anchorStart || anchorEnd) {
			sb.append("(");
		}
		
		String groupString = super.toString();
		if(groupString.startsWith("(") && groupString.endsWith(")") && super.markedAsGroup()) {
			groupString = groupString.substring(1, groupString.length()-1);
		}
		
		sb.append(groupString);
		
		if(anchorStart || anchorEnd) {
			sb.append(")");
		}
		
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
		if(groupType == GroupType.None && markedAsGroup()) {
			groups.add(this);
		}
		groups.addAll(super.getCapturingGroups());
		return groups;
	}
	
	public Group asGroup() {
		Group group = new Group(this.treeType, this.groupType);
		group.nodes = this.nodes;
		group.groupName = this.groupName;
		
		return group;
	}
	
	
	public Pattern compile() {
		return Pattern.compile(this.toString());
	}
	
	
	public RegexBuilder clone() {
		RegexBuilder cloned = SerializationUtils.clone(this);
		return cloned;
	}

	
}
