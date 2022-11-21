package org.abhishek.customerapi.util;

public class PhoneNumberGenerator {
	public static void main(String[] args) {
		PhoneNumberGenerator phoneNumGenerator = new PhoneNumberGenerator();
		System.out.println(phoneNumGenerator.generatePhoneNumberStartingWith(7));
	}
	
	//startingWith is single digit
	public int generatePhoneNumberStartingWith(int startingWith) {
		String nineDigitStr = String.valueOf(generateNineDigitNumber());
		String substring = nineDigitStr.substring(1);
		return Integer.parseInt("7".concat(substring));
	}
	
	private int generateNineDigitNumber() {
		long timeSeed = System.nanoTime(); // to get the current date time value

		double randSeed = Math.random() * 1000; // random number generation

		long midSeed = (long) (timeSeed * randSeed); // mixing up the time and
														// rand number.

		// variable timeSeed
		// will be unique

		// variable rand will
		// ensure no relation
		// between the numbers

		String s = midSeed + "";
		String subStr = s.substring(0, 9);

		int finalSeed = Integer.parseInt(subStr); // integer value
		//int max = Integer.MAX_VALUE;

		//System.out.println(finalSeed);
		return finalSeed;
	}

}
