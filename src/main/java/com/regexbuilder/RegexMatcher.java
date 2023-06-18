package com.regexbuilder;

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
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
public class RegexMatcher {
	
	@Getter
	final RegexBuilder regexBuilder;
	final String content;
	final Pattern pattern;
	final Matcher matcher;
	
	private Boolean currentFind;
	private final Map<Integer, String> groupPositionsMap;
	
	RegexMatcher(RegexBuilder regexBuilder, String content, int flags) {
		this.regexBuilder = regexBuilder;
		this.content = content;
		this.pattern = Pattern.compile(regexBuilder.toString(), flags);
		this.matcher = pattern.matcher(content);
		this.groupPositionsMap = regexBuilder.getGroupPositions();
	}
	
	RegexMatcher(RegexBuilder regexBuilder, String content) {
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
	 * Testing current RegexMatcher step by step to match content, and returns RegexMatcher with longest working RegexBuilder
	 * Use this feature during implementation to help you see what part of your Regex doesn't work.
	 * @return RegexMatcher including best matching RegexBuilder
	 */
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
	
	/**
	 * Wraps matcher group method
	 * @return matched content for whole regex
	 */
	public String group() {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find anything yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		return matcher.group();
	}
	
	
	/**
	 * Wraps matcher group method
	 * @return matched content for group with given index
	 */
	public String group(int groupIndex) {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find anything yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		return matcher.group(groupIndex);
	}
	
	/**
	 * Wraps matcher group method with group called by its name as specified in RegexBuilder
	 * @return matched content for group specified by its name
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
	
	
	/**
	 * Finds group by its name as specified in RegexBuilder, extracts matching content and parse it as float if possible
	 * @param groupName name of group to be matched as specified in RegexBuilder
	 * @return parsed matched content as float
	 */
	public Float groupAsFloat(String groupName) {
		String groupContent = group(groupName);
		if(groupContent == null) {
			return null;
		}
		return Float.parseFloat(groupContent);
	}
	
	/**
	 * Finds group by its name as specified in RegexBuilder, extracts matching content and parse it as integer if possible
	 * @param groupName name of group to be matched as specified in RegexBuilder
	 * @return parsed matched content as integer
	 */
	public Integer groupAsInteger(String groupName) {
		String groupContent = group(groupName);
		if(groupContent == null) {
			return null;
		}
		return Integer.parseInt(groupContent);
	}

	/**
	 * Wraps matcher start method
	 * @return the start index of current match
	 */
	public Integer start() {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		return matcher.start();
	}

	/**
	 * Wraps matcher start method
	 * @return the start index of current match group
	 */
	public Integer start(int i) {
		
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		return matcher.start(i);
	}
	
	/**
	 * Wraps matcher start method with group called by its name as specified in RegexBuilder
	 * @return the start index of current match group specified by its name
	 */
	public Integer start(String groupName) {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		try {
			return matcher.start(regexBuilder.findGroupPosition(groupName));
		}
		catch(Exception e) {
			return null;
		}
	}
	
	
	/**
	 * Wraps matcher end method
	 * @return the end index of current match
	 */
	public Integer end() {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		return matcher.end();
	}
	
	
	/**
	 * Wraps matcher end method
	 * @return the end index of current match group
	 */
	public Integer end(int i) {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		return matcher.end(i);
	}
	
	/**
	 * Wraps matcher end method with group called by its name as specified in RegexBuilder
	 * @return the end index of current match group specified by its name
	 */
	public Integer end(String groupName) {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		try {
			return matcher.end(regexBuilder.findGroupPosition(groupName));
		}
		catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * Wraps matcher groupCount method
	 * @return the number of capturing groups in matcher's pattern
	 */
	public int groupCount() {
		return matcher.groupCount();
	}
	
	
	/**
	 * finds group with specified name and replace its content with specified content
	 * @param groupName group to be found
	 * @param replacementString content to replace group content
	 * @return complete RegexMatcher content with replaced content
	 */
	public String replace(String groupName, String replacementString) {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		Integer groupPosition = regexBuilder.findGroupPosition(groupName);
		return content.substring(0, matcher.start(groupPosition))+replacementString+content.substring(matcher.end(groupPosition));
	}

	/**
	 * Creates a key value map with group names specified in RegexBuilder as keys, and group contents as values
	 * @return map with associated group names and content
	 */
	public Map<String, String> groupContentMap() {
		Map<String, String> groupsAsMap = new LinkedHashMap<>();
		for(Entry<Integer, String> entry : regexBuilder.getGroupPositions().entrySet()) {
			if(matcher.group(entry.getKey()) != null) {
				groupsAsMap.put(entry.getValue(), matcher.group(entry.getKey()));
			}
		}
		
		return groupsAsMap;
	}
	
	@ToString
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class RegexMatch{
		
		@ToStringExclude public final RegexMatcher regexMatcher;
		public final int start;
		public final int end;
		public final String group;
		public final String name;
		
	}

	/**
	 * Provides all RegexMatch for all current groups
	 * @return list of RegexMatch
	 */
	public List<RegexMatch> getMatchs() {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		List<RegexMatch> matchs = new ArrayList<>();
		for(int i = 0; i <= matcher.groupCount(); i++) {
			matchs.add(getMatch(i));
		}
		return matchs;
	}
	
	/**
	 * Provides RegexMatch for group with specified index
	 * @param index
	 */
	public RegexMatch getMatch(int index) {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		return new RegexMatch(this, matcher.start(index), matcher.end(index), matcher.group(index), groupPositionsMap.get(index));
	}
	

	/**
	 * Provides RegexMatch for group with specified name
	 * @param index
	 */
	public RegexMatch getMatch(String groupName) {
		if(currentFind == null) {
			log.error("Cannot get groups when matcher didn't find yet, check the find() method first !\n    content = "+content+"\n    pattern = "+pattern);
			return null;
		}
		Integer index = regexBuilder.findGroupPosition(groupName);
		return new RegexMatch(this, matcher.start(index), matcher.end(index), matcher.group(index), groupPositionsMap.get(index));
	}
	
}
