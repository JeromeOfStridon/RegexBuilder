package app.regexBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import app.regexBuilder.ClassMatch.CharacterClass;
import lombok.Getter;
import lombok.Setter;


public class Group extends Node {

	protected ChildrenType childrenType;
	
	protected GroupType groupType;

	List<Node> nodes = new ArrayList<>();
	
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
	

	
	// Optional
	public Group optional(CharacterClass content) 			{ return _attachNode(new ClassMatch().add(content), 0, 1, false); }
	public Group optional(String content) 					{ return _attachNode(new StringMatch().add(content), 0, 1, false); }
	public Group optional(Node content) 					{ return _attachNode(content, 0, 1, false); }
	public Group optionalLazy(CharacterClass content) 		{ return _attachNode(new ClassMatch().add(content), 0, 1, true); }
	public Group optionalLazy(String content) 				{ return _attachNode(new StringMatch().add(content), 0, 1, true); }
	public Group optionalLazy(Node content) 				{ return _attachNode(content, 0, 1, true); }
	
	// Any : *
	public Group any(CharacterClass content) 				{ return _attachNode(new ClassMatch().add(content), 0, null, false); }
	public Group any(String content) 						{ return _attachNode(new StringMatch().add(content), 0, null, false); }
	public Group any(Node content) 							{ return _attachNode(content, 0, null, false);}
	public Group anyLazy(CharacterClass content) 			{ return _attachNode(new ClassMatch().add(content), 0, null, true); }
	public Group anyLazy(String content) 					{ return _attachNode(new StringMatch().add(content), 0, null, true); }	
	public Group anyLazy(Node content) 						{ return _attachNode(content, 0, null, true); }
	
	// Unique
	public Group unique(CharacterClass content) 			{ return _attachNode(new ClassMatch().add(content), 1, 1, false); }
	public Group unique(String content) 					{ return _attachNode(new StringMatch().add(content), 1, 1, false); }
	public Group unique(Node content) 						{ return _attachNode(content, 1, 1, false); }

	// Exactly
	public Group exactly(CharacterClass content, int card)	{ return _attachNode(new ClassMatch().add(content), card, card, false); }
	public Group exactly(String content, int card) 			{ return _attachNode(new StringMatch().add(content), card, card, false); }
	public Group exactly(Node content, int card) 			{ return _attachNode(content, card, card, false); }
	
	// Some
	public Group some(CharacterClass content) 				{ return _attachNode(new ClassMatch().add(content), 1, null, false); }
	public Group some(String content) 						{ return _attachNode(new StringMatch().add(content), 1, null, false); }
	public Group some(Node content) 						{ return _attachNode(content, 1, null, false); }
	public Group someLazy(CharacterClass content) 			{ return _attachNode(new ClassMatch().add(content), 1, null, true); }
	public Group someLazy(String content) 					{ return _attachNode(new StringMatch().add(content), 1, null, true); }
	public Group someLazy(Node content) 					{ return _attachNode(content, 1, null, true); }
	
	
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

	public Group setName(String groupName) {
		this.groupType = GroupType.Capturing;
		this.groupName = groupName;
		return this;
	}

	public Group setGroupType(GroupType groupType) {
		this.groupType = groupType;
		return this;
	}
	
}
