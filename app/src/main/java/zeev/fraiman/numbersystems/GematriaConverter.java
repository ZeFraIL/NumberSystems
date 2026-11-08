package zeev.fraiman.numbersystems;

import java.util.HashMap;
import java.util.Map;

public class GematriaConverter {

    public static final long MIN_VALUE = 1;
    public static final long MAX_VALUE = 3999;

    private static final Map<Character, Integer> GEMATRIA_VALUES = new HashMap<>();

    static {
        GEMATRIA_VALUES.put('א', 1);
        GEMATRIA_VALUES.put('ב', 2);
        GEMATRIA_VALUES.put('ג', 3);
        GEMATRIA_VALUES.put('ד', 4);
        GEMATRIA_VALUES.put('ה', 5);
        GEMATRIA_VALUES.put('ו', 6);
        GEMATRIA_VALUES.put('ז', 7);
        GEMATRIA_VALUES.put('ח', 8);
        GEMATRIA_VALUES.put('ט', 9);
        GEMATRIA_VALUES.put('י', 10);
        GEMATRIA_VALUES.put('כ', 20);
        GEMATRIA_VALUES.put('ך', 20);
        GEMATRIA_VALUES.put('ל', 30);
        GEMATRIA_VALUES.put('מ', 40);
        GEMATRIA_VALUES.put('ם', 40);
        GEMATRIA_VALUES.put('נ', 50);
        GEMATRIA_VALUES.put('ן', 50);
        GEMATRIA_VALUES.put('ס', 60);
        GEMATRIA_VALUES.put('ע', 70);
        GEMATRIA_VALUES.put('פ', 80);
        GEMATRIA_VALUES.put('ף', 80);
        GEMATRIA_VALUES.put('צ', 90);
        GEMATRIA_VALUES.put('ץ', 90);
        GEMATRIA_VALUES.put('ק', 100);
        GEMATRIA_VALUES.put('ר', 200);
        GEMATRIA_VALUES.put('ש', 300);
        GEMATRIA_VALUES.put('ת', 400);
    }

    public static String format(long number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException("Number out of range [1..3999]");
        }

        StringBuilder result = new StringBuilder();
        long temp = number;

        // Thousands (1000..3999)
        if (temp >= 1000) {
            long thousands = temp / 1000;
            if (thousands == 1) result.append("א'");
            else if (thousands == 2) result.append("ב'");
            else if (thousands == 3) result.append("ג'");
            temp = temp % 1000;
        }

        StringBuilder mainPart = new StringBuilder();

        // Hundreds
        while (temp >= 400) {
            mainPart.append('ת');
            temp -= 400;
        }
        if (temp >= 300) { mainPart.append('ש'); temp -= 300; }
        if (temp >= 200) { mainPart.append('ר'); temp -= 200; }
        if (temp >= 100) { mainPart.append('ק'); temp -= 100; }

        // Tens & Units
        if (temp == 15) {
            mainPart.append("טו");
            temp = 0;
        } else if (temp == 16) {
            mainPart.append("טז");
            temp = 0;
        } else {
            // Tens
            if (temp >= 90) { mainPart.append('צ'); temp -= 90; }
            else if (temp >= 80) { mainPart.append('פ'); temp -= 80; }
            else if (temp >= 70) { mainPart.append('ע'); temp -= 70; }
            else if (temp >= 60) { mainPart.append('ס'); temp -= 60; }
            else if (temp >= 50) { mainPart.append('נ'); temp -= 50; }
            else if (temp >= 40) { mainPart.append('מ'); temp -= 40; }
            else if (temp >= 30) { mainPart.append('ל'); temp -= 30; }
            else if (temp >= 20) { mainPart.append('כ'); temp -= 20; }
            else if (temp >= 10) { mainPart.append('י'); temp -= 10; }

            // Units
            if (temp >= 9) { mainPart.append('ט'); temp -= 9; }
            else if (temp >= 8) { mainPart.append('ח'); temp -= 8; }
            else if (temp >= 7) { mainPart.append('ז'); temp -= 7; }
            else if (temp >= 6) { mainPart.append('ו'); temp -= 6; }
            else if (temp >= 5) { mainPart.append('ה'); temp -= 5; }
            else if (temp >= 4) { mainPart.append('ד'); temp -= 4; }
            else if (temp >= 3) { mainPart.append('ג'); temp -= 3; }
            else if (temp >= 2) { mainPart.append('ב'); temp -= 2; }
            else if (temp >= 1) { mainPart.append('א'); temp -= 1; }
        }

        // Add geresh or gershayim punctuation to mainPart
        if (mainPart.length() == 1) {
            mainPart.append('\'');
        } else if (mainPart.length() > 1) {
            mainPart.insert(mainPart.length() - 1, '"');
        }

        result.append(mainPart);
        return result.toString();
    }

    public static long parse(String input) throws IllegalArgumentException {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input is empty");
        }

        String raw = input.trim();
        StringBuilder cleanLetters = new StringBuilder();

        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == '\'' || c == '"' || c == '׳' || c == '״' || Character.isWhitespace(c)) {
                continue;
            }
            if (!GEMATRIA_VALUES.containsKey(c)) {
                throw new IllegalArgumentException("Unknown Gematria character: " + c);
            }
            cleanLetters.append(c);
        }

        if (cleanLetters.length() == 0) {
            throw new IllegalArgumentException("Input does not contain Hebrew letters");
        }

        boolean hasThousandsGeresh = false;
        long thousandsValue = 0;

        if (raw.length() >= 2 && (raw.charAt(1) == '\'' || raw.charAt(1) == '\"' || raw.charAt(1) == '׳') && cleanLetters.length() > 1) {
            char firstChar = raw.charAt(0);
            if (firstChar == 'א') { thousandsValue = 1000; hasThousandsGeresh = true; }
            else if (firstChar == 'ב') { thousandsValue = 2000; hasThousandsGeresh = true; }
            else if (firstChar == 'ג') { thousandsValue = 3000; hasThousandsGeresh = true; }
        }

        long sum = 0;
        int startIndex = hasThousandsGeresh ? 1 : 0;
        for (int i = startIndex; i < cleanLetters.length(); i++) {
            char c = cleanLetters.charAt(i);
            Integer val = GEMATRIA_VALUES.get(c);
            if (val != null) {
                sum += val;
            }
        }

        long total = thousandsValue + sum;

        if (total < MIN_VALUE || total > MAX_VALUE) {
            throw new IllegalArgumentException("Number out of range [1..3999]");
        }

        String expectedFormatted = format(total);
        String expectedClean = expectedFormatted.replaceAll("['\"׳״\\s]", "");
        String actualClean = cleanLetters.toString();

        if (!expectedClean.equals(actualClean)) {
            throw new IllegalArgumentException("Invalid Gematria format");
        }

        return total;
    }
}
