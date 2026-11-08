package zeev.fraiman.numbersystems;

public class NumberConverter {

    public static final long MIN_VALUE = 1;
    public static final long MAX_VALUE = 3999;

    public static long parseToDecimal(String input, NumeralSystem system) throws IllegalArgumentException {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a number");
        }

        String cleaned = input.trim();
        long value;

        try {
            switch (system) {
                case BIN:
                    if (!cleaned.matches("^[01]+$")) {
                        throw new IllegalArgumentException("Binary number must contain only '0' and '1'");
                    }
                    value = Long.parseLong(cleaned, 2);
                    break;

                case OCT:
                    if (!cleaned.matches("^[0-7]+$")) {
                        throw new IllegalArgumentException("Octal number must contain digits from '0' to '7'");
                    }
                    value = Long.parseLong(cleaned, 8);
                    break;

                case DEC:
                    if (!cleaned.matches("^[0-9]+$")) {
                        throw new IllegalArgumentException("Decimal number must contain digits from '0' to '9'");
                    }
                    value = Long.parseLong(cleaned, 10);
                    break;

                case HEX:
                    if (!cleaned.matches("^[0-9a-fA-F]+$")) {
                        throw new IllegalArgumentException("Hexadecimal number must contain digits 0-9 and letters A-F");
                    }
                    value = Long.parseLong(cleaned, 16);
                    break;

                case ROMAN:
                    value = RomanConverter.parse(cleaned);
                    break;

                case GEMATRIA:
                    value = GematriaConverter.parse(cleaned);
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported numeral system");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Number is too large or has invalid format");
        }

        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("Allowed number range: 1 to 3999");
        }

        return value;
    }

    public static String formatFromDecimal(long number, NumeralSystem system) throws IllegalArgumentException {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException("Number out of range [1..3999]");
        }

        switch (system) {
            case BIN:
                return Long.toBinaryString(number);
            case OCT:
                return Long.toOctalString(number);
            case DEC:
                return String.valueOf(number);
            case HEX:
                return Long.toHexString(number).toUpperCase();
            case ROMAN:
                return RomanConverter.format(number);
            case GEMATRIA:
                return GematriaConverter.format(number);
            default:
                throw new IllegalArgumentException("Unsupported numeral system");
        }
    }

    public static ConversionResult convertAll(String input, NumeralSystem sourceSystem) throws IllegalArgumentException {
        long decimalValue = parseToDecimal(input, sourceSystem);
        ConversionResult result = new ConversionResult(decimalValue);

        for (NumeralSystem targetSystem : NumeralSystem.values()) {
            result.put(targetSystem, formatFromDecimal(decimalValue, targetSystem));
        }

        return result;
    }
}
