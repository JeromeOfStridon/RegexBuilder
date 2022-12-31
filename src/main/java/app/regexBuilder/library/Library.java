package app.regexBuilder.library;

import java.util.List;

import app.regexBuilder.ClassMatch;
import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group.ChildrenType;
import app.regexBuilder.RegexBuilder;

public class Library {
	

	public static RegexBuilder colorHexCode() {
		RegexBuilder regex = new RegexBuilder();
			
			regex
			.unique("#")
			.between(CharacterClass.Alphanumeric_Hexa, 1, 6);
		
		return regex;
	}
	
	
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
	

	
	public static RegexBuilder email() {
		RegexBuilder regex = new RegexBuilder();
		

		ClassMatch acceptedChars = new ClassMatch()
				.add(CharacterClass.Alphanumeric)
				.add('%', '_', '-', '+');
		
		regex
			.some(acceptedChars)
			.optional(RegexBuilder.sequenceGroup()
					.unique(".")
					.some(acceptedChars))
			.unique("@")
			.some(RegexBuilder.sequenceGroup()
					.some(acceptedChars)
					.unique("."))
			.between(CharacterClass.Alphabetic, 2, 10);
		
		return regex;
	}
	
	public static RegexBuilder regularDate() {
		RegexBuilder regex = new RegexBuilder();
		
		regex
		.unique(RegexBuilder.alternativeGroup()
			// from 01 to 09
			.unique(RegexBuilder.sequenceGroup()
						.unique("0")
						.unique(RegexBuilder.classMatch(CharacterClass.Numeric)))
			// from 10 to 29
			.unique(RegexBuilder.sequenceGroup()
						.unique(RegexBuilder.classMatch(List.of('1', '2')))
						.unique(RegexBuilder.classMatch(CharacterClass.Numeric)))
			// 30 & 31
			.unique(RegexBuilder.sequenceGroup()
						.unique("3")
						.unique(RegexBuilder.classMatch(List.of('0', '1')))))
		.unique("/")
		.unique(RegexBuilder.alternativeGroup()
			// from 01 to 09
			.unique(RegexBuilder.sequenceGroup()
						.unique("0")
						.unique(RegexBuilder.classMatch(CharacterClass.Numeric)))
			// 10, 11, 12
			.unique(RegexBuilder.sequenceGroup()
						.unique("1")
						.unique(RegexBuilder.classMatch(List.of('0', '1', '2')))))
		.unique("/")
		.unique(RegexBuilder.sequenceGroup()
			.unique(RegexBuilder.classMatch(List.of('1', '2')))
			.between(RegexBuilder.classMatch(CharacterClass.Numeric), 3, 3));
		
		return regex;
		
	}
	
	public static RegexBuilder floatNumber() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb
			.some(CharacterClass.Numeric)
			.unique(".")
			.some(CharacterClass.Numeric);
		
		
		return rb;
		
	}
	
public static RegexBuilder intNumber() {
		
		RegexBuilder rb = new RegexBuilder();
		
		rb.some(CharacterClass.Numeric);
		
		
		return rb;
		
	}
	

}
