package org.abhishek.customerapi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

/**
 * Utility class for generating random past and future dates.
 * @author Abhishek OmPrakash
 *
 */
public class RandomDateUtil_NotUsed {

	public static void main(String[] args) {
		
		System.out.println(randomDateAsString());
		

	}
	
	private static final String TIMEZONE = "UTC";
	private static final int MAX_YEAR = 2015;
	private static final int MIN_YEAR = 1900;

	private static Calendar createCalendar() {
		return new GregorianCalendar(TimeZone.getTimeZone(TIMEZONE));
	}

	private static Date maxDate() {
		final Calendar cal = createCalendar();
		//cal.set(MAX_YEAR, 0, 1, 0, 0);
		cal.set(MAX_YEAR, 11, 31);
		return cal.getTime();
	}

	private static Date minDate() {
		final Calendar cal = createCalendar();
		//cal.set(MIN_YEAR, 0, 1, 0, 0);
		cal.set(MIN_YEAR, 0, 1);
		return cal.getTime();
	}

	private static Date now() {
		return new Date();
	}

	public static Date randomDate() {
		return randomDateBetween(minDate(), maxDate());
	}
	
	public static String randomDateAsString() {
		Date randomDateBetween = randomDateBetween(minDate(), maxDate());
		GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(randomDateBetween);
	    int actualMaximum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
		return DATE_FORMAT.format(randomDateBetween);
	}

	public static Date randomDateAfter(final Date min) {
		return randomDateBetween(min, maxDate());
	}

	public static Date randomDateBefore(final Date max) {
		return randomDateBetween(minDate(), max);
	}

	public static Date randomDateBetween(final Date first, final Date second) {
		if (first == null || second == null)
			throw new IllegalArgumentException("Date is null");

		if (first.equals(second))
			return first;

		//final Date min = first.before(second) ? first : second;
		//final Date max = min == first ? second : first;

		final Calendar minCal = createCalendar(), maxCal = createCalendar();
		//minCal.setTime(min);
		//maxCal.setTime(max);

		final int year = randomIntBetween(minCal.get(Calendar.YEAR), maxCal.get(Calendar.YEAR));
		final int month = randomIntBetween(minCal.get(Calendar.MONTH), maxCal.get(Calendar.MONTH));
		final int day = randomIntBetween(minCal.get(Calendar.DAY_OF_MONTH), maxCal.get(Calendar.DAY_OF_MONTH));
		//System.out.println("day = " + day + " month = " + month + " year = " + year);

		maxCal.set(Calendar.YEAR, year);
		maxCal.set(Calendar.MONTH, month);
		maxCal.set(Calendar.DAY_OF_MONTH, day);

		return maxCal.getTime();
	}

	public static Date randomFutureDate() {
		return randomDateAfter(now());
	}

	public static int randomIntBetween(final int first, final int second) {
		if (first == second)
			return first;

		final int min = Math.min(first, second);
		final int max = Math.max(first, second);
		final int diff = max - min;

		final Random rand = new Random(System.nanoTime());
		return min + rand.nextInt(diff);
	}

	public static Date randomPastDate() {
		return randomDateBefore(now());
	}
	
	public static Date sameDateRandomFutureYear(final Date date) {
		final Calendar cal = createCalendar();
		cal.setTime(date);
		cal.set(Calendar.YEAR, randomIntBetween(cal.get(Calendar.YEAR), MAX_YEAR));
		return cal.getTime();
	}
	
	public static Date sameDateRandomPastYear(final Date date) {
		final Calendar cal = createCalendar();
		cal.setTime(date);
		cal.set(Calendar.YEAR, randomIntBetween(MIN_YEAR, cal.get(Calendar.YEAR)));
		return cal.getTime();
	}

}
