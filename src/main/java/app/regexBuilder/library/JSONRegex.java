package app.regexBuilder.library;

import java.util.List;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;
import app.regexBuilder.RegexBuilder;

public class JSONRegex {
	
	public static void main(String[] args) {
		
		RegexBuilder objectNode = new RegexBuilder();
		objectNode
			.unique("{")
			.any(CharacterClass.Any)
			.unique("}");
		
		RegexBuilder array = new RegexBuilder();
		array
			.unique("[")
			.any(CharacterClass.Any)
			.unique("]");
		
		RegexBuilder attribute = new RegexBuilder();
		attribute
			.unique("'")
			.anyLazy(CharacterClass.Any)
			.unique("'")
			.unique(":");
		
		RegexBuilder string = contentWithDelimiters('\"');
		
		
		List<String> lists = List.of("[1,2,3,4]");
		
		for(String str : lists) {
			
		}
		
	}
	
	public static class JsonNode{
		String content;
	}
	
	
	public static RegexBuilder contentWithDelimiters(Character delimiter) {
		RegexBuilder rb = new RegexBuilder();
		
		rb
			.unique(delimiter.toString())
			.anyLazy(CharacterClass.Any)
			.unique(RegexBuilder.sequenceGroup().setGroupType(GroupType.NegativeLookBehind).unique(RegexBuilder.classMatch('\\')))
			.unique(delimiter.toString());
		
		return rb;
	}
	

}
