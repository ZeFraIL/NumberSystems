package zeev.fraiman.numbersystems;

import java.util.EnumMap;
import java.util.Map;

public class ConversionResult {

    private final long decimalValue;
    private final Map<NumeralSystem, String> values = new EnumMap<>(NumeralSystem.class);

    public ConversionResult(long decimalValue) {
        this.decimalValue = decimalValue;
    }

    public void put(NumeralSystem system, String value) {
        values.put(system, value);
    }

    public String get(NumeralSystem system) {
        return values.get(system);
    }

    public long getDecimalValue() {
        return decimalValue;
    }

    public Map<NumeralSystem, String> getAllValues() {
        return values;
    }
}
