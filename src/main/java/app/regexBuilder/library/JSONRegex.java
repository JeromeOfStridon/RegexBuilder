package app.regexBuilder.library;

import java.util.List;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;
import app.regexBuilder.RegexBuilder;

public class JSONRegex {
	
	public static void main(String[] args) {
		
		
		
		
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
	
	public RegexBuilder jsonObject() {
		RegexBuilder objectNode = new RegexBuilder();
		objectNode
			.unique("{")
			.anyLazy(CharacterClass.Any)
			.unique("}");
		
		return objectNode;
	}
	
	public RegexBuilder jsonArray() {
		RegexBuilder array = new RegexBuilder();
		array
			.unique("[")
			.any(CharacterClass.Any)
			.unique("]");

		return array;
	}
	
//	public static class JsonNode{
//		String content;
//	}
	
	
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
