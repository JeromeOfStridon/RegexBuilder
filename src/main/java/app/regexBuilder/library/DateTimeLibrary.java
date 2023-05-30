package app.regexBuilder.library;

import java.util.List;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group;
import app.regexBuilder.Group.ChildrenType;
import app.regexBuilder.RegexBuilder;
import app.regexBuilder.RegexFactory;

public class DateTimeLibrary {

	
	public static RegexBuilder fullWrittenDate_en() {
		
		Group monthGroup = RegexFactory.alternativeGroup().setName("month");
		
		List<String> monthList = List.of("January", "February", "March", "April", "May", "June", "Jul", "August", "September", "October", "November", "December");
		for(String month : monthList) {
			monthGroup.unique(
				RegexFactory.sequenceGroup()
					.unique(month.substring(0, 3))
					.optional(RegexFactory.alternativeGroup().unique(".").unique(month.substring(3)))
			);
		}
		
		Group dayGroup = RegexFactory.alternativeGroup().setName("day");
		
		dayGroup
			.unique("1st")
			.unique("2nd")
			.unique("3rd")
			.unique("21st")
			.unique("22nd")
			.unique("23rd");
		
		for(int i = 0; i <= 31; i++) {
			if(i >= 21 && i <= 23) {
				continue;
			}
			dayGroup.unique(i+"th");	
		}
		
		
		
		RegexBuilder rb = RegexFactory.regexBuilder();
		rb.unique(monthGroup);
		rb.unique(CharacterClass.Space);
		rb.unique(dayGroup);
		rb.unique(CharacterClass.Space);
		rb.unique(year().setName("year"));
		
		
		return rb;
	}
	

	
	public static RegexBuilder fullWrittenDate_fr() {
		RegexBuilder regex = RegexFactory.regexBuilder();

		regex
			.unique(RegexFactory.sequenceGroup()
				.setName("Day")
				.unique(RegexFactory.classMatchRange('1', '9'))
				.optional(RegexFactory.classMatch(CharacterClass.Numeric)))
			.unique(CharacterClass.Space)
			.unique(RegexFactory.alternativeGroup(List.of("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "décembre")).setName("month"))
			.some(CharacterClass.Space)
			.optional(year().setName("year"));

		return regex;
	}
	
	public static RegexBuilder dayNumber() {
		
		RegexBuilder regex = RegexFactory.regexBuilder(ChildrenType.Alternative);

		regex
		
			// case 1 to 9 or 01 to 09
			.unique(RegexFactory.sequenceGroup()
					.optional("0")
					.unique(RegexFactory.classMatchRange('1', '9'))
					)
			// case 10 to 29
			.unique(RegexFactory.sequenceGroup()
					.unique(RegexFactory.classMatchRange('1', '2'))
					.unique(RegexFactory.classMatch(CharacterClass.Numeric))
					)
			// case 30
			.unique("30")
			// case 31
			.unique("31");
		
		return regex;
		
	}
	
	public static RegexBuilder monthNumber() {
		
		RegexBuilder regex = RegexFactory.regexBuilder(ChildrenType.Alternative);
		
		regex
			// case 1 to 9, 01 to 09
			.unique(RegexFactory.sequenceGroup()
				.optional("0")
				.unique(RegexFactory.classMatchRange('1', '9'))
				)
			.unique("10")
			.unique("11")
			.unique("12");
		
		return regex;
	}

	public static RegexBuilder year() {
		RegexBuilder regex = RegexFactory.regexBuilder();

		regex.unique(RegexFactory.alternativeGroup(List.of("1", "2"))).exactly(CharacterClass.Numeric, 3);

		return regex;

	}

	public static RegexBuilder clockHHMM() {
		RegexBuilder rb = RegexFactory.regexBuilder();
		rb
			.unique(
					RegexFactory.alternativeGroup()
					.unique(RegexFactory.sequenceGroup().unique(RegexFactory.classMatch('0','1')).unique(CharacterClass.Numeric))
					.unique(RegexFactory.sequenceGroup().unique("2").unique(RegexFactory.classMatchRange('0', '3')))
				)
			.unique(":")
			.unique(RegexFactory.classMatchRange('0', '5'))
			.unique(CharacterClass.Numeric);
			
		return rb;
	}

	public static RegexBuilder regularDate() {
		RegexBuilder regex = RegexFactory.regexBuilder();

		regex.unique(RegexFactory.alternativeGroup()
				// from 01 to 09
				.unique(RegexFactory.sequenceGroup().unique("0")
						.unique(RegexFactory.classMatch(CharacterClass.Numeric)))
				// from 10 to 29
				.unique(RegexFactory.sequenceGroup().unique(RegexFactory.classMatch(List.of('1', '2')))
						.unique(RegexFactory.classMatch(CharacterClass.Numeric)))
				// 30 & 31
				.unique(RegexFactory.sequenceGroup().unique("3").unique(RegexFactory.classMatch(List.of('0', '1')))))
				.unique("/").unique(RegexFactory.alternativeGroup()
						// from 01 to 09
						.unique(RegexFactory.sequenceGroup().unique("0")
								.unique(RegexFactory.classMatch(CharacterClass.Numeric)))
						// 10, 11, 12
						.unique(RegexFactory.sequenceGroup().unique("1")
								.unique(RegexFactory.classMatch(List.of('0', '1', '2')))))
				.unique("/").unique(RegexFactory.sequenceGroup().unique(RegexFactory.classMatch(List.of('1', '2')))
						.between(RegexFactory.classMatch(CharacterClass.Numeric), 3, 3));

		return regex;

	}

	public static RegexBuilder timestampRegex() {

		RegexBuilder rb = RegexFactory.regexBuilder();
			rb
				.unique(RegexFactory.sequenceGroup().setName("year").between(CharacterClass.Numeric, 4, 4)) // Year
				.unique("-")
				.unique(RegexFactory.sequenceGroup().setName("month").between(CharacterClass.Numeric, 2, 2)) // Month
				.unique("-")
				.unique(RegexFactory.sequenceGroup().setName("day").between(CharacterClass.Numeric, 2, 2)) // Day
				.unique("T")
				.unique(RegexFactory.sequenceGroup().setName("hour").between(CharacterClass.Numeric, 2, 2)) // Hour
				.unique(":")
				.unique(RegexFactory.sequenceGroup().setName("minute").between(CharacterClass.Numeric, 2, 2)) // Minute
				.unique(":")
				.unique(RegexFactory.sequenceGroup().setName("second").between(CharacterClass.Numeric, 2, 2)) // Second
				.unique(".")
				.unique(RegexFactory.sequenceGroup().setName("millisecond").any(CharacterClass.Numeric)) // Millisecond
				.unique("Z");

		return rb;

	}

}
