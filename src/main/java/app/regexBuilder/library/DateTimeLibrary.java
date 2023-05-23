package app.regexBuilder.library;

import java.util.List;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.Group;
import app.regexBuilder.Group.ChildrenType;
import app.regexBuilder.RegexBuilder;

public class DateTimeLibrary {

	
	public static RegexBuilder fullWrittenDate_en() {
		
		Group monthGroup = RegexBuilder.alternativeGroup().captureAs("month");
		
		List<String> monthList = List.of("January", "February", "March", "April", "May", "June", "Jul", "August", "September", "October", "November", "December");
		for(String month : monthList) {
			monthGroup.unique(
				RegexBuilder.sequenceGroup()
					.unique(month.substring(0, 3))
					.optional(RegexBuilder.alternativeGroup().unique(".").unique(month.substring(3)))
			);
		}
		
		Group dayGroup = RegexBuilder.alternativeGroup().captureAs("day");
		
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
		
		
		
		RegexBuilder rb = new RegexBuilder();
		rb.unique(monthGroup);
		rb.unique(CharacterClass.Space);
		rb.unique(dayGroup);
		rb.unique(CharacterClass.Space);
		rb.unique(year().captureAs("year"));
		
		
		return rb;
	}
	

	
	public static RegexBuilder fullWrittenDate_fr() {
		RegexBuilder regex = new RegexBuilder();

		regex
			.unique(RegexBuilder.sequenceGroup()
				.captureAs("Day")
				.unique(RegexBuilder.classMatchRange('1', '9'))
				.optional(RegexBuilder.classMatch(CharacterClass.Numeric)))
			.unique(CharacterClass.Space)
			.unique(RegexBuilder.alternativeGroup(List.of("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "décembre")).captureAs("month"))
			.some(CharacterClass.Space)
			.optional(year().captureAs("year"));

		return regex;
	}

	public static RegexBuilder year() {
		RegexBuilder regex = new RegexBuilder();

		regex.unique(RegexBuilder.alternativeGroup(List.of("1", "2"))).between(CharacterClass.Numeric, 3, 3);

		return regex;

	}

	public static RegexBuilder clockHHMM() {
		RegexBuilder rb = new RegexBuilder();
		rb
			.unique(
				RegexBuilder.alternativeGroup()
					.unique(RegexBuilder.sequenceGroup().unique(RegexBuilder.classMatch('0','1')).unique(CharacterClass.Numeric))
					.unique(RegexBuilder.sequenceGroup().unique("2").unique(RegexBuilder.classMatchRange('0', '3')))
				)
			.unique(":")
			.unique(RegexBuilder.classMatchRange('0', '5'))
			.unique(CharacterClass.Numeric);
			
		return rb;
	}

	public static RegexBuilder regularDate() {
		RegexBuilder regex = new RegexBuilder();

		regex.unique(RegexBuilder.alternativeGroup()
				// from 01 to 09
				.unique(RegexBuilder.sequenceGroup().unique("0")
						.unique(RegexBuilder.classMatch(CharacterClass.Numeric)))
				// from 10 to 29
				.unique(RegexBuilder.sequenceGroup().unique(RegexBuilder.classMatch(List.of('1', '2')))
						.unique(RegexBuilder.classMatch(CharacterClass.Numeric)))
				// 30 & 31
				.unique(RegexBuilder.sequenceGroup().unique("3").unique(RegexBuilder.classMatch(List.of('0', '1')))))
				.unique("/").unique(RegexBuilder.alternativeGroup()
						// from 01 to 09
						.unique(RegexBuilder.sequenceGroup().unique("0")
								.unique(RegexBuilder.classMatch(CharacterClass.Numeric)))
						// 10, 11, 12
						.unique(RegexBuilder.sequenceGroup().unique("1")
								.unique(RegexBuilder.classMatch(List.of('0', '1', '2')))))
				.unique("/").unique(RegexBuilder.sequenceGroup().unique(RegexBuilder.classMatch(List.of('1', '2')))
						.between(RegexBuilder.classMatch(CharacterClass.Numeric), 3, 3));

		return regex;

	}

	public static RegexBuilder timestampRegex() {

		RegexBuilder rb = new RegexBuilder();
			rb
				.unique(RegexBuilder.sequenceGroup().captureAs("year").between(CharacterClass.Numeric, 4, 4)) // Year
				.unique("-")
				.unique(RegexBuilder.sequenceGroup().captureAs("month").between(CharacterClass.Numeric, 2, 2)) // Month
				.unique("-")
				.unique(RegexBuilder.sequenceGroup().captureAs("day").between(CharacterClass.Numeric, 2, 2)) // Day
				.unique("T")
				.unique(RegexBuilder.sequenceGroup().captureAs("hour").between(CharacterClass.Numeric, 2, 2)) // Hour
				.unique(":")
				.unique(RegexBuilder.sequenceGroup().captureAs("minute").between(CharacterClass.Numeric, 2, 2)) // Minute
				.unique(":")
				.unique(RegexBuilder.sequenceGroup().captureAs("second").between(CharacterClass.Numeric, 2, 2)) // Second
				.unique(".")
				.unique(RegexBuilder.sequenceGroup().captureAs("millisecond").any(CharacterClass.Numeric)) // Millisecond
				.unique("Z");

		return rb;

	}

}
