# Detailed Class Description: RomanConverter

#### 1. General Information
- **Class Name:** `RomanConverter`
- **Type:** Standard Utility Class (Roman Numeral Engine)
- **Role:** Parses and formats Roman numerals in range 1 to 3999 with strict format validation.

#### 3. Class Methods
- `format(long number)`: Uses a greedy algorithm with mapped numeral pairs (M, CM, D, CD, C, XC, L, XL, X, IX, V, IV, I) to format decimal values to Roman numerals.
- `parse(String input)`: Parses Roman string right-to-left, checking subtractive rules, and verifies validity by re-formatting the parsed decimal value.

#### 8. Simplest Terms
**Real-World Analogy:** Like a Roman banker exchanging coins according to strict rules and double-checking that no coin was placed in an invalid order.
