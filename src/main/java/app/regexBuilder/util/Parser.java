package app.regexBuilder.util;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import app.regexBuilder.ClassMatch;
import app.regexBuilder.ClassMatch.CharacterClass;

public class Parser {
	
	public static ClassMatch toClassMatch(String str) {
		Set<Character> chars = new TreeSet<>();
		
		for(int i = 0; i < str.length(); i++) {
			chars.add(str.charAt(i));
		}
		
		System.out.println(chars.toString());
		
		ClassMatch classMatch = new ClassMatch();
		for(Character c : chars) {
			CharacterClass cc = List.of(ClassMatch.CharacterClass.values()).stream().filter(x -> x.string != null && x != CharacterClass.Any && c.toString().matches("["+x.string+"]")).findFirst().orElse(null);
			if(cc != null) {
				classMatch.add(cc);
			}
			else {
				classMatch.add(c);
			}
		}
		
		return classMatch;
		
	}

	
	@Deprecated
	public static void main(String[] args) {
		String str = "and (segmentact0_.username in ('ethan.hu')) and segmentact0_.created>'2022-12-12 11:58:34.55' and segmentact0_.created<'2022-12-20 11:58:34.55' and (segmentact0_.type in (2))) where segmentact0_.project_id=31 and (segmentact0_.macro_status in (2 , 4 , 8 , 5 , 3 , 6 , 7 , 9 , 11 , 1 , 0 , 10 , 10 , 10 , 10 , 10)) and (taskentity2_.status in (2 , 4 , 8 , 5 , 3 , 6 , 7 , 9 , 11 , 1 , 0 , 10 , 10 , 10 , 10 , 10))";
		
		ClassMatch cm = new ClassMatch();
		for(char c : str.toCharArray()) {
			cm.add(c);
		}
		
		cm.factorize(CharacterClass.AlphabeticLower, CharacterClass.Numeric, CharacterClass.Space);
		
		System.out.println(cm.toString());
	}

}
