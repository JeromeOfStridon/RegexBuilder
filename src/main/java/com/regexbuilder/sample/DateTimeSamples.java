package com.regexbuilder.sample;

import java.util.Arrays;
import java.util.List;

import com.regexbuilder.Group;
import com.regexbuilder.Regex;
import com.regexbuilder.RegexBuilder;
import com.regexbuilder.ClassMatch.CharacterClass;
import com.regexbuilder.Group.TreeType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeSamples {

	
	public static Regex fullWrittenDate_en() {
		
		Group monthGroup = RegexBuilder.alternativeGroup().setName("month");
		
		List<String> monthList = Arrays.asList("January", "February", "March", "April", "May", "June", "Jul", "August", "September", "October", "November", "December");
		for(String month : monthList) {
			monthGroup.unique(
				RegexBuilder.sequenceGroup()
					.unique(month.substring(0, 3))
					.optional(RegexBuilder.alternativeGroup().unique(".").unique(month.substring(3)))
			);
		}
		
		Group dayGroup = RegexBuilder.alternativeGroup().setName("day");
		
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
		
		
		
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder.unique(monthGroup);
		regexBuilder.unique(CharacterClass.Space);
		regexBuilder.unique(dayGroup);
		regexBuilder.unique(CharacterClass.Space);
		regexBuilder.unique(numericYear().setName("year"));
		
		
		return regexBuilder;
	}
	

	
	public static Regex fullWrittenDate_fr() {
		Regex regexBuilder = RegexBuilder.regex();

		regexBuilder
			.unique(numericDay().setName("Day"))
			.unique(CharacterClass.Space)
			.unique(RegexBuilder.alternativeGroup(Arrays.asList("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "décembre")).setName("month"))
			.some(CharacterClass.Space)
			.optional(numericYear().setName("year"));

		return regexBuilder;
	}
	
	public static Regex numericDay() {
		
		Regex regexBuilder = RegexBuilder.regex(TreeType.Alternative);

		regexBuilder
		
			// case 1 to 9 or 01 to 09
			.unique(RegexBuilder.sequenceGroup()
					.optional("0")
					.unique(RegexBuilder.classMatchRange('1', '9'))
					)
			// case 10 to 29
			.unique(RegexBuilder.sequenceGroup()
					.unique(RegexBuilder.classMatch('1', '2'))
					.unique(RegexBuilder.classMatch(CharacterClass.Numeric))
					)
			// case 30
			.unique("30")
			// case 31
			.unique("31");
		
		return regexBuilder;
		
	}
	
	public static Regex numericMonth() {
		
		Regex regexBuilder = RegexBuilder.regex(TreeType.Alternative);
		
		regexBuilder
			// case 1 to 9, 01 to 09
			.unique(RegexBuilder.sequenceGroup()
				.optional("0")
				.unique(RegexBuilder.classMatchRange('1', '9'))
				)
			.unique("10")
			.unique("11")
			.unique("12");
		
		return regexBuilder;
	}

	public static Regex numericYear() {
		Regex regexBuilder = RegexBuilder.regex();

		regexBuilder
			.unique(RegexBuilder.classMatch('1', '2'))
			.exactly(CharacterClass.Numeric, 3);

		return regexBuilder;

	}

	public static Regex clockHHMM() {
		Regex regexBuilder = RegexBuilder.regex();
		regexBuilder
			// Hours
			.unique(
					RegexBuilder.alternativeGroup()
					.unique(RegexBuilder.sequenceGroup()
						.unique(RegexBuilder.classMatch('0','1'))
						.unique(CharacterClass.Numeric))
					.unique(RegexBuilder.sequenceGroup()
						.unique("2")
						.unique(RegexBuilder.classMatchRange('0', '3'))
						)
				)
			// Separator
			.unique(":")
			// Minutes
			.unique(RegexBuilder.classMatchRange('0', '5'))
			.unique(CharacterClass.Numeric);
		
		return regexBuilder;
	}

	public static Regex regularDate() {
		Regex regex = RegexBuilder.regex();

		regex
			.unique(numericDay().setName("day"))
			.unique("/")
			.unique(numericMonth().setName("month"))
			.unique("/")
			.unique(numericYear().setName("year"));

		return regex;

	}

	public static Regex timestampRegex() {

		Regex rb = RegexBuilder.regex();
			rb
				.unique(RegexBuilder.sequenceGroup().setName("year").between(CharacterClass.Numeric, 4, 4)) // Year
				.unique("-")
				.unique(RegexBuilder.sequenceGroup().setName("month").between(CharacterClass.Numeric, 2, 2)) // Month
				.unique("-")
				.unique(RegexBuilder.sequenceGroup().setName("day").between(CharacterClass.Numeric, 2, 2)) // Day
				.unique("T")
				.unique(RegexBuilder.sequenceGroup().setName("hour").between(CharacterClass.Numeric, 2, 2)) // Hour
				.unique(":")
				.unique(RegexBuilder.sequenceGroup().setName("minute").between(CharacterClass.Numeric, 2, 2)) // Minute
				.unique(":")
				.unique(RegexBuilder.sequenceGroup().setName("second").between(CharacterClass.Numeric, 2, 2)) // Second
				.unique(".")
				.unique(RegexBuilder.sequenceGroup().setName("millisecond").any(CharacterClass.Numeric)) // Millisecond
				.unique("Z");

		return rb;

	}

}
