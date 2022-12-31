package app.regexBuilder.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BestPattern {
	
	
	public static Pattern getBestPattern(List<Pattern> patterns, String content, int start) {
		Integer bestI = null;
		int bestStart = content.length();
		int bestLength = 0;
				
		for(int i = 0; i < patterns.size(); i++) {
		
			Matcher matcher = patterns.get(i).matcher(content);
			
			if(!matcher.find(start)) {
				continue;
			}
			
			if(matcher.start() < bestStart || (matcher.start() == bestStart && matcher.group().length() > bestLength)) {
				bestI = i;
				bestStart = matcher.start();
				bestLength = matcher.group().length();				
			}
			
		}
		
		return bestI == null ? null : patterns.get(bestI);
	}

}
