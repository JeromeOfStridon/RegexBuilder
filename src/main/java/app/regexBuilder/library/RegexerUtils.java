package app.regexBuilder.library;

import app.regexBuilder.RegexBuilder;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.ChildrenType;

public class RegexerUtils {
	
	public static RegexBuilder regexNum() {
		
		RegexBuilder regex = new RegexBuilder(ChildrenType.Alternative);
		
		regex
			.unique("*")
			.unique("+")
			.unique("?")
			.unique(RegexBuilder.sequenceGroup()
				.unique("{")
				.unique(CharacterClass.Numeric)
				.unique(",")
				.optional(CharacterClass.Numeric)
				.unique("}")
				)
			.unique(RegexBuilder.sequenceGroup()
				.unique("{,")
				.unique(CharacterClass.Numeric)
				.unique("}")
				);
	
		System.out.println(regex.toString());
		
		return regex;
		
	}
	

	
	public static RegexBuilder regexClass() {
		RegexBuilder regex = new RegexBuilder(ChildrenType.Sequence);
		
		regex.unique("[")
			.some(RegexBuilder.alternativeGroup()
				.unique("\\]")
				.unique(RegexBuilder.classMatch(']').beNegative())
				)
			.unique("]");
		
		
		
		return regex;
	}
	
	public static RegexBuilder regexAlternativeGroup() {
		RegexBuilder regex = new RegexBuilder(ChildrenType.Sequence);
		
		regex
			.unique("[")
			.some(RegexBuilder.alternativeGroup()
				.unique("\\]")
				.unique(RegexBuilder.classMatch(']').beNegative())
				)
			.unique("]");
		
		
		
		return regex;
	}

}
