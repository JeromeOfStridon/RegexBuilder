package app.regexBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ToStringExclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegexMatcher {
	
	@Getter
	final RegexBuilder regexBuilder;
	final String content;
	final Pattern pattern;
	final Matcher matcher;
	
	private Boolean currentFind;
	
	public RegexMatcher(RegexBuilder regexBuilder, String content, int flags) {
		this.regexBuilder = regexBuilder;
		this.content = content;
		this.pattern = Pattern.compile(regexBuilder.toString(), flags);
		this.matcher = pattern.matcher(content);	
	}
	
	public RegexMatcher(RegexBuilder regexBuilder, String content) {
		this(regexBuilder, content, 0);
	}
	
	/**
	 * Finds the next match between regex & content
	 * @return boolean true if next match has been found, false if not
	 */
	public boolean find() {
		currentFind = matcher.find();
		return currentFind;
	}
	
	/**
	 * Returns matched content for whole regex
	 */
	public String group() {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find anything yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		return matcher.group();
	}
	
	/**
	 * Returns matched content for specific group
	 */
	public String group(String groupName) {
		
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		try {
			return matcher.group(regexBuilder.findGroupPosition(groupName));
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public Float groupAsFloat(String groupName) {
		String groupContent = group(groupName);
		if(groupContent == null) {
			return null;
		}
		return Float.parseFloat(groupContent);
	}
	
	public Integer groupAsInteger(String groupName) {
		String groupContent = group(groupName);
		if(groupContent == null) {
			return null;
		}
		return Integer.parseInt(groupContent);
	}
	
	public RegexMatcher debug() {
		
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
		}
		
		return null;

	}
	
	public int start() {
		return matcher.start();
	}
	public int start(int i) {
		return matcher.start(i);
	}
	
	public int end() {
		return matcher.end();
	}
	public int end(int i) {
		return matcher.end(i);
	}
	
	public int groupCount() {
		return matcher.groupCount();
	}
	
	public Integer start(String groupName) {
		try {
			return matcher.start(regexBuilder.findGroupPosition(groupName));
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public Integer end(String groupName) {
		try {
			return matcher.end(regexBuilder.findGroupPosition(groupName));
		}
		catch(Exception e) {
			return null;
		}
	}
	

	public String replace(String groupName, String replacementString) {
		Integer groupPosition = regexBuilder.findGroupPosition(groupName);
		return content.substring(0, matcher.start(groupPosition))+replacementString+content.substring(matcher.end(groupPosition));
	}

	public Map<String, String> groupsAsMap() {
		Map<String, String> groupsAsMap = new LinkedHashMap<>();
		for(Entry<Integer, String> entry : regexBuilder.getGroupPositions().entrySet()) {
			if(matcher.group(entry.getKey()) != null) {
				groupsAsMap.put(entry.getValue(), matcher.group(entry.getKey()));
			}
		}
		
		return groupsAsMap;
	}
	

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class RegexMatch{
		
		@ToStringExclude final public RegexMatcher regexMatcher;
		public final int start;
		public final int end;
		public final String group;
		public final String name;
		
	}


	public List<RegexMatch> getMatchs() {
		Map<Integer, String> map = regexBuilder.getGroupPositions();
		List<RegexMatch> matchs = new ArrayList<>();
		for(int i = 0; i <= matcher.groupCount(); i++) {
			matchs.add(new RegexMatch(this, matcher.start(i), matcher.end(i), matcher.group(i), map.get(i)));
		}
		return matchs;
	}
	
}
