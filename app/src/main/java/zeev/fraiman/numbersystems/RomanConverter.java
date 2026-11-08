package zeev.fraiman.numbersystems;

import java.util.LinkedHashMap;
import java.util.Map;

public class RomanConverter {

    public static final long MIN_VALUE = 1;
    public static final long MAX_VALUE = 3999;

    private static final LinkedHashMap<String, Integer> ROMAN_NUMERALS = new LinkedHashMap<>();

    static {
        ROMAN_NUMERALS.put("M", 1000);
        ROMAN_NUMERALS.put("CM", 900);
        ROMAN_NUMERALS.put("D", 500);
        ROMAN_NUMERALS.put("CD", 400);
        ROMAN_NUMERALS.put("C", 100);
        ROMAN_NUMERALS.put("XC", 90);
        ROMAN_NUMERALS.put("L", 50);
        ROMAN_NUMERALS.put("XL", 40);
        ROMAN_NUMERALS.put("X", 10);
        ROMAN_NUMERALS.put("IX", 9);
        ROMAN_NUMERALS.put("V", 5);
        ROMAN_NUMERALS.put("IV", 4);
        ROMAN_NUMERALS.put("I", 1);
    }

    public static String format(long number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException("Number out of range [1..3999]");
        }

        StringBuilder sb = new StringBuilder();
        long temp = number;

        for (Map.Entry<String, Integer> entry : ROMAN_NUMERALS.entrySet()) {
            while (temp >= entry.getValue()) {
                sb.append(entry.getKey());
                temp -= entry.getValue();
            }
        }

        return sb.toString();
    }

    public static long parse(String input) throws IllegalArgumentException {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input is empty");
        }

        String cleaned = input.trim().toUpperCase();

        if (!cleaned.matches("^[IVXLCDM]+$")) {
            throw new IllegalArgumentException("Invalid characters in Roman numeral");
        }

        long result = 0;
        int prevValue = 0;

        for (int i = cleaned.length() - 1; i >= 0; i--) {
            char c = cleaned.charAt(i);
            int value = getCharValue(c);

            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
                prevValue = value;
            }
        }

        if (result < MIN_VALUE || result > MAX_VALUE) {
            throw new IllegalArgumentException("Number out of range [1..3999]");
        }

        if (!format(result).equals(cleaned)) {
            throw new IllegalArgumentException("Invalid Roman numeral format");
        }

        return result;
    }

    private static int getCharValue(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }
}
