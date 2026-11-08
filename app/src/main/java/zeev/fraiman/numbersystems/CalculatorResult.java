package zeev.fraiman.numbersystems;

import java.util.EnumMap;
import java.util.Map;

public class CalculatorResult {

    private final long operand1;
    private final long operand2;
    private final String operator;
    private final long resultDecimal;

    private final Map<NumeralSystem, String> expressions = new EnumMap<>(NumeralSystem.class);
    private final Map<NumeralSystem, String> results = new EnumMap<>(NumeralSystem.class);

    public CalculatorResult(long operand1, long operand2, String operator, long resultDecimal) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
        this.resultDecimal = resultDecimal;
    }

    public void put(NumeralSystem system, String expressionFormatted, String resultFormatted) {
        expressions.put(system, expressionFormatted);
        results.put(system, resultFormatted);
    }

    public String getExpression(NumeralSystem system) {
        return expressions.get(system);
    }

    public String getResult(NumeralSystem system) {
        return results.get(system);
    }

    public long getOperand1() {
        return operand1;
    }

    public long getOperand2() {
        return operand2;
    }

    public String getOperator() {
        return operator;
    }

    public long getResultDecimal() {
        return resultDecimal;
    }
}
