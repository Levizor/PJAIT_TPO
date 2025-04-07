package pl.edu.pja.tpo_05;

import java.util.Arrays;

public class Converter {
    public static int MIN_BASE = 2;
    public static int MAX_BASE = 100;
    public static int PRECISION = 10;
    public static char[] DIGITS = initDigits();

    public static String convert(String value, int fromBase, int toBase) throws IllegalArgumentException {
        value = value.trim();
        if (fromBase < MIN_BASE || fromBase > MAX_BASE ) {
            throw new IllegalArgumentException("fromBase outside the allowed range");
        }

        if (toBase < MIN_BASE || toBase > MAX_BASE) {
            throw new IllegalArgumentException("toBase outside the allowed range");
        }

        boolean isNegative = value.startsWith("-");
        if (isNegative) {
            value = value.substring(1);
        }

        String[] split = value.split("\\.");
        String intPart = split[0];
        String fracPart = split.length > 1 ? split[1] : "";

        double base10 = convertToBase10(intPart, fracPart, fromBase);

        long intPart10 = (long) base10;
        double fracPart10 = base10 - intPart10;

        intPart = toBaseN(intPart10, toBase);
        fracPart = toBaseNFrac(fracPart10, toBase);

        String result = intPart + (fracPart.isEmpty() ? "" : "." + fracPart);
        return isNegative ? "-" + result : result;
    }

    private static double convertToBase10(String intPart, String fracPart, int base) {
        double result = 0;

        for (int i = 0; i < intPart.length(); i++) {
            int digit = charToDigit(intPart.charAt(i));
            if (digit >= base) throw new IllegalArgumentException("Invalid numeric value:  '" + intPart.charAt(i) + "' for base " + base);
            result = result * base + digit;
        }

        double fraction = 0;
        double divisor = base;
        for (int i = 0; i < fracPart.length(); i++) {
            int digit = charToDigit(fracPart.charAt(i));
            if (digit >= base) throw new IllegalArgumentException("Invalid numeric value:  '" + fracPart.charAt(i) + "' for base " + base);
            fraction += digit / divisor;
            divisor *= base;
        }

        return result + fraction;
    }

    private static String toBaseN(long value, int base) {
        if (value == 0) return "0";

        StringBuilder result = new StringBuilder();
        while (value > 0) {
            int digit = (int)(value % base);
            result.append(DIGITS[digit]);
            value /= base;
        }
        return result.reverse().toString();
    }

    private static String toBaseNFrac(double frac, int base) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < PRECISION && frac > 0; i++) {
            frac *= base;
            int digit = (int) frac;
            result.append(DIGITS[digit]);
            frac -= digit;
        }
        return result.toString();
    }

    private static int charToDigit(char c) {
        for (int i = 0; i < DIGITS.length; i++) {
            if (DIGITS[i] == c) return i;
        }
        throw new IllegalArgumentException("Unsupported character: " + c);
    }

    private static char[] initDigits() {
        char[] digits = new char[MAX_BASE];
        int index = 0;

        for (char c = '0'; c <= '9'; c++) digits[index++] = c;
        // skip symbols before A
        for (char c = 'A'; c <= 126; c++) {
            digits[index++] = c;
        }
        for (char c = 192; index < MAX_BASE; c++) {
            digits[index++] = c;
        }

        System.out.println(Arrays.toString(digits));
        return digits;
    }
}