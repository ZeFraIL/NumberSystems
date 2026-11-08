package zeev.fraiman.numbersystems;

public class ExpressionEvaluator {

    public static CalculatorResult evaluate(String expressionInput, NumeralSystem system) throws IllegalArgumentException {
        if (expressionInput == null || expressionInput.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter an expression (e.g., A + B)");
        }

        String input = expressionInput.trim();
        String operator;

        if (input.contains("+")) {
            operator = "+";
        } else if (input.contains("-")) {
            operator = "-";
        } else if (input.contains("*")) {
            operator = "*";
        } else if (input.contains("×")) {
            operator = "×";
        } else {
            throw new IllegalArgumentException("Expression must contain an operator: +, -, *");
        }

        int opIndex = input.indexOf(operator);
        String leftPart = input.substring(0, opIndex).trim();
        String rightPart = input.substring(opIndex + operator.length()).trim();

        if (leftPart.isEmpty() || rightPart.isEmpty()) {
            throw new IllegalArgumentException("Please specify operands on both sides of '" + operator + "'");
        }

        long op1 = NumberConverter.parseToDecimal(leftPart, system);
        long op2 = NumberConverter.parseToDecimal(rightPart, system);

        long resultDecimal;
        String normalizedOp = "×".equals(operator) ? "*" : operator;

        switch (normalizedOp) {
            case "+":
                resultDecimal = op1 + op2;
                break;
            case "-":
                resultDecimal = op1 - op2;
                break;
            case "*":
                resultDecimal = op1 * op2;
                break;
            default:
                throw new IllegalArgumentException("Unsupported operation: " + operator);
        }

        if (resultDecimal < NumberConverter.MIN_VALUE || resultDecimal > NumberConverter.MAX_VALUE) {
            throw new IllegalArgumentException("Result (" + resultDecimal + ") is out of allowed range [1..3999]");
        }

        CalculatorResult result = new CalculatorResult(op1, op2, normalizedOp, resultDecimal);

        for (NumeralSystem targetSystem : NumeralSystem.values()) {
            String op1Formatted = NumberConverter.formatFromDecimal(op1, targetSystem);
            String op2Formatted = NumberConverter.formatFromDecimal(op2, targetSystem);
            String resFormatted = NumberConverter.formatFromDecimal(resultDecimal, targetSystem);

            String exprFormatted = op1Formatted + " " + normalizedOp + " " + op2Formatted;
            result.put(targetSystem, exprFormatted, resFormatted);
        }

        return result;
    }
}
