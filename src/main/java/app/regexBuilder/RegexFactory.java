package app.regexBuilder;

import java.util.Collection;

import app.regexBuilder.Group.ChildrenType;
import app.regexBuilder.Group.GroupType;

public class RegexFactory {

	
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
	
	public static RegexBuilder regexBuilder() {
		return new RegexBuilder(ChildrenType.Sequence, GroupType.None);
	}
	
	
}
