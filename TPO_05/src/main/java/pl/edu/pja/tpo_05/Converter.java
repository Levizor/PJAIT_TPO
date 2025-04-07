package pl.edu.pja.tpo_05;

import java.math.BigInteger;

public class Converter {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 100;
    private static final char[] DIGITS = initDigits();

    public static String convert(Long value, int fromBase, int toBase) throws IllegalArgumentException {
        validateBase(fromBase);
        validateBase(toBase);

        boolean isNegative = value < 0;
        BigInteger absValue = BigInteger.valueOf(value).abs();

        // Convert to base 10 if necessary
        BigInteger base10Value = fromBase == 10 ? absValue : fromBaseN(value.toString(), fromBase);

        // Convert to target base
        String converted = toBaseN(base10Value, toBase);

        return isNegative ? "-" + converted : converted;
    }

    private static void validateBase(int base) throws IllegalArgumentException {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException("Base must be between 2 and 100.");
        }
    }

    private static char[] initDigits() {
        char[] digits = new char[MAX_BASE];
        int index = 0;

        for (char c = '0'; c <= '9' && index < MAX_BASE; c++) digits[index++] = c;
        for (char c = 'A'; c <= 'Z' && index < MAX_BASE; c++) digits[index++] = c;
        for (char c = 'a'; c <= 'z' && index < MAX_BASE; c++) digits[index++] = c;
        for (char c = 33; c <= 126 && index < MAX_BASE; c++) {
            if (!Character.isLetterOrDigit(c)) digits[index++] = c;
        }

        return digits;
    }

    private static BigInteger fromBaseN(String value, int fromBase) {
        boolean isNegative = value.startsWith("-");
        String cleanValue = isNegative ? value.substring(1) : value;

        BigInteger result = BigInteger.ZERO;
        BigInteger base = BigInteger.valueOf(fromBase);

        for (int i = 0; i < cleanValue.length(); i++) {
            char c = cleanValue.charAt(i);
            int digit = charToDigit(c);
            if (digit >= fromBase) {
                throw new IllegalArgumentException("Invalid numeric value for the given base: digit '" + c + "' for base " + fromBase);
            }
            result = result.multiply(base).add(BigInteger.valueOf(digit));
        }

        return isNegative ? result.negate() : result;
    }

    private static int charToDigit(char c) {
        for (int i = 0; i < DIGITS.length; i++) {
            if (DIGITS[i] == c) return i;
        }
        throw new IllegalArgumentException("Unsupported character: " + c);
    }

    private static String toBaseN(BigInteger value, int base) {
        if (value.equals(BigInteger.ZERO)) return "0";

        StringBuilder result = new StringBuilder();
        BigInteger baseBI = BigInteger.valueOf(base);

        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divRem = value.divideAndRemainder(baseBI);
            result.append(DIGITS[divRem[1].intValue()]);
            value = divRem[0];
        }

        return result.reverse().toString();
    }
}
