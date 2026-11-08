# Detailed Class Description: NumberConverter

#### 1. General Information
- **Class Name:** `NumberConverter`
- **Type:** Standard Utility Class (Central Conversion Engine)
- **Role:** Main converter engine. Parses input text from any of the 6 systems into a decimal `long`, checks range bounds (1–3999), and formats the decimal value to all 6 systems.
- **Interactions:** Calls `RomanConverter` and `GematriaConverter`; provides services to `ExpressionEvaluator`, `ConverterFragment`, and `CalculatorFragment`.

#### 2. Variables (Fields)

| Name | Type | Purpose | Usage Location |
|------|------|---------|----------------|
| `MIN_VALUE` | `long` (static final = 1) | Lower bound limit | Used in range validation |
| `MAX_VALUE` | `long` (static final = 3999) | Upper bound limit | Used in range validation |

#### 3. Class Methods

**Method Name:** `parseToDecimal`
- **Scope:** `public static`
- **Return Value:** `long`
- **Parameters:** `input` (`String`), `system` (`NumeralSystem`)
- **Details:** Validates non-empty string, checks regex for binary/octal/decimal/hex, delegates to `RomanConverter` or `GematriaConverter`, and validates [1..3999] bounds.

**Method Name:** `formatFromDecimal`
- **Scope:** `public static`
- **Return Value:** `String`
- **Parameters:** `number` (`long`), `system` (`NumeralSystem`)
- **Details:** Converts decimal `long` to target system string using standard conversion routines or helper classes.

**Method Name:** `convertAll`
- **Scope:** `public static`
- **Return Value:** `ConversionResult`
- **Parameters:** `input` (`String`), `sourceSystem` (`NumeralSystem`)
- **Details:** Parses input string into decimal, converts decimal to all 6 systems, and returns `ConversionResult`.

#### 8. Simplest Terms
**Real-World Analogy:** Like a global language translator who translates any input language into English first, then translates English into all other target languages.
