package app.regexBuilder.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.GroupType;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class MiscTest {
	
	@Test
	public void multipleLookahead() {
		
		Pattern pattern = Pattern.compile("(?=A)([A-Z]*");
		
		Matcher matcher = pattern.matcher("ABC");
		
		while(matcher.find()) {
			log.debug("Match found on "+matcher.group());
			for(int i = 1; i <= matcher.groupCount(); i++) {
				log.debug("group #"+i+" : "+matcher.group(i));
			}
		}
		
		log.debug("done");
		
	}
	
	@Test
	public void multipleLookahead2() {
		
		String pattern = "A(?=B)(?=BC)B(?=C)C";
		
		String content = "ABC";
		
	}
	
	@Test
	public void testSegmentation() {
		
		RegexBuilder regexBuilder = new RegexBuilder();
		
		
		regexBuilder
			.unique(RegexBuilder.sequenceGroup().setGroupType(GroupType.NegativeLookAhead)
					.unique(RegexBuilder.classMatch(CharacterClass.Alphanumeric))
					
			)
			.any(CharacterClass.Space)
			.unique(RegexBuilder.sequenceGroup().setGroupType(GroupType.NegativeLookAhead)
					.unique(RegexBuilder.classMatch(CharacterClass.Alphanumeric)))
			
			;
		
		
	}
	

}
