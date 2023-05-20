package app.regexBuilder.library;

import java.util.List;

import app.regexBuilder.ClassMatch.CharacterClass;
import app.regexBuilder.RegexBuilder;

public class DateTimeLibrary {

	public static RegexBuilder fullWrittenDate() {
		RegexBuilder regex = new RegexBuilder();

		regex.unique(RegexBuilder.sequenceGroup().captureAs("Day").unique(RegexBuilder.classMatchRange('1', '9'))
				.optional(RegexBuilder.classMatch(CharacterClass.Numeric))).unique(CharacterClass.Space)

				.unique(RegexBuilder.alternativeGroup(List.of("janvier", "f�vrier", "mars", "avril", "mai", "juin",
						"juillet", "aout", "septembre", "octobre", "novembre", "d�cembre")))
				.optional(CharacterClass.Space).optional(humanYear());

		return regex;
	}

	public static RegexBuilder humanYear() {
		RegexBuilder regex = new RegexBuilder();

		regex.unique(RegexBuilder.alternativeGroup(List.of("19", "20"))).between(CharacterClass.Numeric, 2, 2);

		return regex;

	}

	public static RegexBuilder clockHHMM() {

		return null;
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
				.between(CharacterClass.Numeric, 4, 4) // Year
				.unique("-")
				.between(CharacterClass.Numeric, 2, 2) // Month
				.unique("-")
				.between(CharacterClass.Numeric, 2, 2) // Day
				.unique("T")
				.between(CharacterClass.Numeric, 2, 2) // Hour
				.unique(":")
				.between(CharacterClass.Numeric, 2, 2) // Minute
				.unique(":")
				.between(CharacterClass.Numeric, 2, 2) // Second
				.unique(".")
				.any(CharacterClass.Numeric) // Millisecond
				.unique("Z");

		// System.out.println(rb.toString());

		return rb;

	}

}
