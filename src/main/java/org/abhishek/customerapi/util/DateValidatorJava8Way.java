package org.abhishek.customerapi.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DateValidatorJava8Way implements DateValidator {

	@Override
	public boolean isValid(String dateStr) {
		boolean valid = false;
		try {
			// ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
			LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));

			valid = true;
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			valid = false;
		}
		return valid;
	}

}
