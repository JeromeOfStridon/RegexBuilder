package app.regexBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.regexBuilder.ClassMatch.CharacterClass;
import lombok.Getter;
import lombok.Setter;


public class Group extends Node {

	@Getter @Setter
	protected ChildrenType childrenType;
	
	@Getter @Setter
	protected GroupType groupType;

	List<Node> nodes = new ArrayList<>();
	
	String capturingGroupName;
	
	protected Group(ChildrenType childrenType, GroupType groupType) {
		this.childrenType = childrenType;
		this.groupType = groupType;
	}
	
	public Group between(CharacterClass content, Integer min, Integer max) {
		ClassMatch match = new ClassMatch().add(content);
		match.times(min, max);
		nodes.add(match);
		return this;
	}
	
	public Group between(String content, Integer min, Integer max) {
		StringMatch match = new StringMatch().add(content);
		match.times(min, max);
		nodes.add(match);
		return this;
	}
	
	public Group between(Node content, Integer min, Integer max) {
		
		content.times(min, max);
		if(content instanceof RegexBuilder) {
			nodes.add(((RegexBuilder) content).asGroup());
		}
		else {
			nodes.add(content);
		}
		return this;
	}
	

	
	// Any : *
	public Group any(CharacterClass content) {
		return between(content, 0, null);
	}
	
	public Group any(String content) {
		return between(content, 0, null);
	}
	
	public Group any(Node content) {
		return between(content, 0, null);
	}
	
	
	// Some
	public Group some(CharacterClass content) {
		return between(content, 1, null);
	}
	
	public Group some(String content) {
		return between(content, 1, null);
	}
	
	public Group some(Node content) {
		return between(content, 1, null);
	}

	// Unique
	public Group unique(CharacterClass content) {
		return between(content, 1, 1);
	}
	
	public Group unique(String content) {
		return between(content, 1, 1);
	}
	
	public Group unique(Node content) {
		return between(content, 1, 1);
	}
	
	// Optional
	public Group optional(CharacterClass content) {
		return between(content, 0, 1);
	}
	
	public Group optional(String content) {
		return between(content, 0, 1);
	}
	
	public Group optional(Node content) {
		return between(content, 0, 1);
	}
	
	
	// Min
	public Group min(CharacterClass content, int min) {
		return between(content, min, null);
	}
	
	public Group min(String content, int min) {
		return between(content, min, null);
	}
	
	public Group min(Node content, int min) {
		return between(content, min, null);
	}


	// Max
	public Group max(CharacterClass content, int max) {
		return between(content, 0, max);
	}
	
	public Group max(String content, int max) {
		return between(content, 0, max);
	}
	
	public Group max(Node content, int max) {
		return between(content, 0, max);
	}


	public static enum ChildrenType{
		Sequence,
		Alternative
	}
	
	public static enum GroupType{
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

	public Group captureAs(String capturingGroupName) {
		this.groupType = GroupType.Capturing;
		this.capturingGroupName = capturingGroupName;
		return this;
	}

	public Group set(GroupType groupType) {
		this.groupType = groupType;
		return this;
	}

	
	

	
}
