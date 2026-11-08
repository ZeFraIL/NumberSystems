package zeev.fraiman.numbersystems;

public enum NumeralSystem {
    BIN("Binary (2)"),
    OCT("Octal (8)"),
    DEC("Decimal (10)"),
    HEX("Hexadecimal (16)"),
    ROMAN("Roman Numerals"),
    GEMATRIA("Gematria (Hebrew)");

    private final String displayName;

    NumeralSystem(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
