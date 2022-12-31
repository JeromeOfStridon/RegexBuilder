package app.regexBuilder;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import app.regexBuilder.ClassMatch.CharacterClass;

// Factory for all kinds of match & groups
public class RegexBuilder extends Group {

	
	
	public boolean anchorStart = false;
	public boolean anchorEnd = false;

	
	// CONSTRUCTORS
	public RegexBuilder(ChildrenType childrenType, GroupType groupType) {
		super(childrenType, groupType);
	}
	
	
	public RegexBuilder() {
		this(ChildrenType.Sequence, GroupType.None);
	}
	
	public RegexBuilder(ChildrenType childrenType) {
		this(childrenType, GroupType.None);
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


	// LOGIC
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(anchorStart) {
			sb.append("^");
		}
		
		if(anchorStart || anchorEnd) {
			sb.append("(");
		}
		
		String groupString = super.toString();
		if(groupString.startsWith("(") && groupString.endsWith(")")) {
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


	
	// FACTORY 
	public static Group sequenceGroup() {
		return new Group(ChildrenType.Sequence, GroupType.None);
	}
	
	public static Group alternativeGroup() {
		return new Group(ChildrenType.Alternative, GroupType.None);
	}
	
	public static Group alternativeGroup(Collection<String> alternatives) {
		Group group = alternativeGroup();
		for(String alternative : alternatives) {
			group.nodes.add(new StringMatch().add(alternative));
		}
		return group;
	}
	
	public static Group alternativeGroup(String... alternatives) {
		Group group = alternativeGroup();
		for(String alternative : alternatives) {
			group.nodes.add(new StringMatch().add(alternative));
		}
		return group;
	}
	
	
//	public static Group positiveLookAhead() {
//		return new Group(ChildrenType.Sequence, GroupType.PositiveLookAhead);
//	}
//	public static Group negativeLookAhead() {
//		return new Group(ChildrenType.Sequence, GroupType.NegativeLookAhead);
//	}
//	public static Group positiveLookBehind() {
//		return new Group(ChildrenType.Sequence, GroupType.PositiveLookBehind);
//	}
//	public static Group negativeLookBehind() {
//		return new Group(ChildrenType.Sequence, GroupType.NegativeLookBehind);
//	}
//	
//	
//	public static Group capturingSequenceGroup(String name) {
//		Group group = new Group(ChildrenType.Sequence, GroupType.Capturing);
//		group.capturingGroupName = name;
//		return group;
//	}
//	public static Group capturingAlternativeGroup(String name) {
//		Group group = new Group(ChildrenType.Alternative, GroupType.Capturing);
//		group.capturingGroupName = name;
//		return group;
//	}
//	
//	public static Group capturingAlternativeGroup(Collection<String> alternatives, String capturingGroupName) {
//		Group group = new Group(ChildrenType.Alternative, GroupType.Capturing);
//		group.capturingGroupName = capturingGroupName;
//		
//		for(String alternative : alternatives) {
//			group.nodes.add(new StringMatch().add(alternative));
//		}
//		return group;
//	}
//	
//	
//	public static Group nonCapturingGroup() {
//		return new Group(ChildrenType.Sequence, GroupType.NonCapturing);
//	}

	public static ClassMatch classMatch(CharacterClass charClass) {
		return new ClassMatch().add(charClass);
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
	
	public static ClassMatch classMatch(char from, char to) {
		ClassMatch classMatch = new ClassMatch();
		classMatch.add(from, to);
		return classMatch;
	}
	
	public static StringMatch stringMatch(String string) {
		return new StringMatch().add(string);
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
			if(groups.get(i).capturingGroupName != null) {
				// Offset of 1 because group(0) is the general one
				result.put(i + 1, groups.get(i).capturingGroupName);
			}
		}
		return result;
	}
	
	public Group asGroup() {
		Group group = new Group(this.childrenType, this.groupType);
		group.nodes = this.nodes;
		group.capturingGroupName = this.capturingGroupName;
		
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
