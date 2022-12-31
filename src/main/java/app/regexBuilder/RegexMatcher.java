package app.regexBuilder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegexMatcher {
	
	final RegexBuilder regexBuilder;
	final String content;
	final Pattern pattern;
	final Matcher matcher;
	
	public RegexMatcher(RegexBuilder regexBuilder, String content, int flags) {
		this.regexBuilder = regexBuilder;
		this.content = content;
		this.pattern = Pattern.compile(regexBuilder.toString(), flags);
		this.matcher = pattern.matcher(content);	
	}
	
	public RegexMatcher(RegexBuilder regexBuilder, String content) {
		this(regexBuilder, content, 0);
	}
	
	public boolean find() {
		return matcher.find();
	}
	
	public String group(String groupName) {
		
		Map<Integer, String> positions = regexBuilder.getGroupPositions();
		Integer ii = regexBuilder.findGroupPosition(groupName);
		try {
			
			return matcher.group(regexBuilder.findGroupPosition(groupName));
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public Float groupAsFloat(String groupName) {
		String content = group(groupName);
		if(content == null) {
			return null;
		}
		return Float.parseFloat(content);
	}
	
	public RegexMatcher debug() {
		Map<RegexBuilder, String> resultMap = new LinkedHashMap<>();
		
		for(int i = 0; i < regexBuilder.nodes.size(); i++) {
			RegexBuilder regexClone = regexBuilder.clone();
			for(int j = 0; j < i; j++) {
				regexClone.nodes.remove(regexClone.nodes.size() - 1);
			}
			RegexMatcher cloneMatcher = new RegexMatcher(regexClone, content);
			
			if(cloneMatcher.find()) {
				log.debug("Matching regex test : "+regexClone.compile());
				return cloneMatcher;
			}
			log.debug("Unmatching regex test : "+regexClone.compile());
		}
		
		return null;

	}

}
