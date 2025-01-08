package com.regexbuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class RegexMultiMatcher {
	
	final Map<Regex, Pattern> patterns = new LinkedHashMap<>();
	
	
	public RegexMultiMatcher(List<Regex> regexList) {
		for(Regex regex : regexList) {
			patterns.put(regex, regex.compile());
		}
	}
	
	public List<RegexMatcherGroup> matchers(String content){
		
		List<RegexMatcherGroup> groups = new ArrayList<>();
		
		for(Entry<Regex, Pattern> entry : patterns.entrySet()) {
			
			Matcher matcher = entry.getValue().matcher(content);
			while(matcher.find()) {
				groups.add(RegexMatcherGroup.builder()
						.start(matcher.start())
						.end(matcher.end())
						.group(matcher.group())
						.regex(entry.getKey())
						.build());
			}
			
		}
		
		// order by length desc
		groups = groups.stream().sorted(Comparator.comparingInt(RegexMatcherGroup::length).reversed()).collect(Collectors.toList());
			
		// remove overlap
		for(int i = 0; i < groups.size(); i++) {
			for(int j = i+1; j < groups.size(); j++) {
				if(groups.get(i).start > groups.get(j).end) {
					continue;
				}
				if(groups.get(i).end < groups.get(j).start) {
					continue;
				}
				
				groups.remove(j);
				j--;
			}
		}
		
		groups = groups.stream().sorted(Comparator.comparingInt(RegexMatcherGroup::getStart)).collect(Collectors.toList());
		
		return groups;
		
	}
	
	@Data
	@Builder
	@AllArgsConstructor
	public static class RegexMatcherGroup{
		public int start;
		public int end;
		public String group;
		public Regex regex;
		
		public int length() {
			return end-start;
		}
		
	}
	

}
