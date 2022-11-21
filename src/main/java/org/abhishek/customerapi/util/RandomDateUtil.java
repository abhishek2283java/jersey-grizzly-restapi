package org.abhishek.customerapi.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class RandomDateUtil {

	public static void main(String[] args) {
		RandomDateUtil dateUtil = new RandomDateUtil();
		// System.out.println(dateUtil.randomIntBetween(1, 10));

		List<String> list = dateUtil.getRandomDateStringsBetween(2012, 2012, 20);
		list.forEach(dateStr -> System.out.println(dateStr + "->" + dateUtil.isDateStringAValidDate(dateStr)));
		// int maxDayOfMonth = dateUtil.getMaxDayOfMonth(2012, 2);
		// System.out.println("maxDayOfMonth:" + maxDayOfMonth);
	}

	public List<String> getRandomDateStringsBetween(int minYear, int maxYear, int numOfDateStrings) {
		List<String> dateStrList = new ArrayList<>();
		for (int i = 1; i <= numOfDateStrings; i++) {
			String dateString = getSingleRandomDateStringBetween(minYear, maxYear);
			dateStrList.add(dateString);
		}
		return dateStrList;
	}
	
	public List<String> getRandomDateStringsBetween(int minYear, int maxYear) {
		return getRandomDateStringsBetween(minYear, maxYear, 1);
	}

	public String getSingleRandomDateStringBetween(int minYear, int maxYear) {
		// get min date
		Date minDate = minDate(minYear);
		Date maxDate = maxDate(maxYear);
		// System.out.println(toString(minDate));
		Date randomDateBetween = randomDateBetween(minDate, maxDate);
		String dateStr = toString(randomDateBetween);
		//System.out.println(dateStr);
		return dateStr;
	}

	private Date randomDateBetween(Date minDate, Date maxDate) {
		if (minDate == null || maxDate == null)
			throw new IllegalArgumentException("Date is null");

		if (minDate.equals(maxDate))
			return minDate;

		Calendar minCal = toCalendar(minDate);
		Calendar maxCal = toCalendar(maxDate);

		final int year = randomIntBetween(minCal.get(Calendar.YEAR), maxCal.get(Calendar.YEAR));
		final int month = randomIntBetween(minCal.get(Calendar.MONTH), maxCal.get(Calendar.MONTH));
		// get Max days for the month
		int maxDayOfMonth = getMaxDayOfMonth(year, month);
		final int day = randomIntBetween(minCal.get(Calendar.DAY_OF_MONTH), maxDayOfMonth);

		maxCal.set(Calendar.YEAR, year);
		maxCal.set(Calendar.MONTH, month);
		maxCal.set(Calendar.DAY_OF_MONTH, day);

		return maxCal.getTime();
	}

	private int getMaxDayOfMonth(int year, int month) {
		Calendar calendar = new GregorianCalendar(year, month, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	private int randomIntBetween(final int first, final int second) {
		if (first == second)
			return first;

		final int min = Math.min(first, second);
		final int max = Math.max(first, second);
		final int diff = max - min + 1;

		final Random rand = new Random(System.nanoTime());
		return min + rand.nextInt(diff);
	}

	private Calendar createCalendar() {
		return new GregorianCalendar(TimeZone.getTimeZone("UTC"));
	}

	private Calendar toCalendar(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	private Date maxDate(int year) {
		final Calendar cal = createCalendar();
		// cal.set(MAX_YEAR, 0, 1, 0, 0);
		cal.set(year, 11, 31);
		return cal.getTime();
	}

	private Date minDate(int year) {
		final Calendar cal = createCalendar();
		// cal.set(MAX_YEAR, 0, 1, 0, 0);
		cal.set(year, 0, 1);
		return cal.getTime();
	}

	private String toString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	private boolean isDateStringAValidDate(String dateString) {
		DateValidator validator = new DateValidatorJava8Way();
		boolean isValid = validator.isValid(dateString);
		return isValid;
	}
}
