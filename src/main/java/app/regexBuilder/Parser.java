package app.regexBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;
import app.regexBuilder.parser.StringIterator;
import app.regexBuilder.util.ListUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Parser {
	
	
	public static Node parse(String str) {
		StringIterator it = new StringIterator(str);
		
		List<Object> objects = new ArrayList<>();
		
		while(it.getIndex() < it.getEndIndex()) {
			parseNext(it, objects);
		}
		
		RegexBuilder node = buildRegexBuilder(objects);
		
		return node;
		
	}
	
	private static void parseNext(StringIterator it, List<Object> tokens) {
		if(it.match("|")) {
			tokens.add(it.current());
			it.next();
			return;
		}
		
		if(
				ListUtils.addIfNotNull(tokens, parseComposedClassMatch(it)) ||
				ListUtils.addIfNotNull(tokens, parseGroup(it)) ||
				ListUtils.addIfNotNull(tokens, parseSingleClassMatch(it)) 
				) {
		}
		else {
			
			StringBuilder sb = new StringBuilder();
			it.nextIfMatch("\\");
			sb.append(it.current());
			tokens.add(new StringMatch().add(sb.toString()));
			it.next();
		}
		parseQuantity(it, (Node) tokens.get(tokens.size() - 1));

		
	}
	


	private static Node parseGroup(StringIterator it) {
		
		GroupType groupType = GroupType.Capturing;
		
		if(it.current() != '(') {
			return null;
		}
		
		it.next();
		
		if(it.nextIfMatch("?:")) {
			groupType = GroupType.NonCapturing;
		}
		// positive lookahead
		else if(it.nextIfMatch("?=")) {
			groupType = GroupType.PositiveLookAhead;
		}
		// negative lookahead
		else if(it.nextIfMatch("?!")) {
			groupType = GroupType.NegativeLookAhead;
		}
		// positive lookbehind
		else if(it.nextIfMatch("?<=")) {
			 groupType = GroupType.PositiveLookBehind;
		}
		// negative lookbehind
		else if(it.nextIfMatch("?<!")) {
			groupType = GroupType.NegativeLookBehind;
		}
		
		
		List<Object> groupNodes = new ArrayList<>();
		
		while(it.current() != ')') {
			parseNext(it, groupNodes);
		}
		it.next();
		
		Group node = buildNode(groupNodes);
		
		node.setGroupType(groupType);
		
		return node;
		
	}
	


	private static Node parseComposedClassMatch(StringIterator it) {
		
		if(!it.nextIfMatch("[")){
			return null;
		}
		
		ClassMatch classMatch = new ClassMatch();
		if(it.nextIfMatch("^")) {
			classMatch.setNegative(true);
		}
		while(!it.nextIfMatch("]")) {
			Pattern pattern = Pattern.compile("[a-zA-Z0-9]-[a-zA-Z0-9]");
			String result = it.match(pattern);
			if(result != null) {
				classMatch.add(result.charAt(0), result.charAt(2));
				it.nextIfMatch(result);
			}
			else if(it.current() == '\\'){
				classMatch.add(CharacterClass.fromString(it.current()+""+it.next()));
				it.next();
			}
			else {
				classMatch.add(it.current());
				it.next();
			}
		}
		
		return classMatch;
		
	}
	
	private static Node parseSingleClassMatch(StringIterator it) {
		
		CharacterClass charClass = CharacterClass.getSingleClasses().stream().filter(x -> x.string != null && it.match(x.string)).findFirst().orElse(null);
		if(charClass != null) {
			it.nextIfMatch(charClass.string);
			return RegexBuilder.classMatch(charClass);
		}
		return null;
		
	}
	
	
	
	// Copy group instance into regexbuilder
	public static RegexBuilder buildRegexBuilder(List<Object> objects) {
		Group group = buildNode(objects);
		RegexBuilder regexBuilder = new RegexBuilder(group.childrenType);
		regexBuilder.nodes = group.nodes;
		return regexBuilder;
	}
	
	public static Group buildNode(List<Object> objects) {
		
		Group group;
		// alternative group
		if(objects.stream().anyMatch(x -> x instanceof String && ((String) x).equals("|"))) {
			group = RegexBuilder.alternativeGroup();
		}
		else {
			group = RegexBuilder.sequenceGroup();
		}
		
		List<Node> nodes = new ArrayList<>();
		
		
		for(int i = 0; i < objects.size(); i++) {
			Object object = objects.get(i);
			
			if(object instanceof StringMatch) {
				if(!nodes.isEmpty() && nodes.get(nodes.size() - 1) instanceof StringMatch && 
						((StringMatch) object).minSize == 1 && 
						((StringMatch) object).maxSize == 1 && 
						((StringMatch) nodes.get(nodes.size() - 1)).minSize == 1 &&
						((StringMatch) nodes.get(nodes.size() - 1)).maxSize == 1 ) {
					StringMatch previousNode = (StringMatch) nodes.get(nodes.size() - 1);
					previousNode.add(((StringMatch) object).get());
				}
				else {
					nodes.add((StringMatch) object);
				}
				
			}
			else if(object instanceof String) {
				String string = (String) object;
				if(string.equals("|")) {
					Group newGroup = RegexBuilder.sequenceGroup();
					newGroup.nodes.addAll(nodes);
					group.nodes.add(newGroup);
					nodes = new ArrayList<>();
					continue;
				}
				log.error("Unknown string object : "+string);
			}
			else if(object instanceof Node){
				nodes.add((Node) object);
			}
			else {
				log.error("Uknown object type");
			}
			
		}
		
		group.nodes.addAll(nodes);
		
		return group;
	}
	
	public static void parseQuantity(StringIterator it, Node node) {
		
		RegexBuilder quantityPattern = new RegexBuilder();
		quantityPattern
			.unique("{")
			.unique(RegexBuilder.alternativeGroup()
				.unique(RegexBuilder.sequenceGroup().captureAs("uniqueSize").unique(CharacterClass.Numeric))
				.unique(RegexBuilder.sequenceGroup()
					.optional(RegexBuilder.sequenceGroup().captureAs("minSize").some(CharacterClass.Numeric))
					.unique(",")
					.optional(RegexBuilder.sequenceGroup().captureAs("maxSize").some(CharacterClass.Numeric))
				)			
			)
			.unique("}");
		
		
		
		if(it.nextIfMatch("+")) {
			node.minSize = 1;
			node.maxSize = null;
			return;
		}
		if(it.nextIfMatch("?")) {
			node.minSize = 0;
			node.maxSize = 1;
			return;
		}
		if(it.nextIfMatch("*")) {
			node.minSize = 0;
			node.maxSize = null;
			return;
		}
		if(it.nextIfMatch("{,}")) {
			return;
		}

		Pattern quantityPat = Pattern.compile(quantityPattern.toString());
		String quantity = it.match(quantityPat);
		if(quantity != null) {
			
			Matcher matcher = quantityPat.matcher(quantity);
			if(!matcher.find()) {
				return;
			}
			
			String uniqueSize = matcher.group(quantityPattern.findGroupPosition("uniqueSize"));
			if(uniqueSize != null) {
				node.minSize = node.maxSize = Integer.parseInt(uniqueSize);
			}
			else {
				
				String minSizeString = matcher.group(quantityPattern.findGroupPosition("minSize"));
				if(minSizeString != null) {
					node.minSize = Integer.parseInt(minSizeString);
				}
				
				String maxSizeString = matcher.group(quantityPattern.findGroupPosition("maxSize"));
				if(maxSizeString != null) {
					node.maxSize = Integer.parseInt(maxSizeString);
				}
				
			}
			
			it.nextIfMatch(quantity);
				
			
		}
		
	}


}
