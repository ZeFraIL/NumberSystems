# Detailed Class Description: GematriaConverter

#### 1. General Information
- **Class Name:** `GematriaConverter`
- **Type:** Standard Utility Class (Hebrew Gematria Engine)
- **Role:** Parses and formats Hebrew Gematria letters for range 1 to 3999, supporting thousands (א', ב', ג'), special rules (15=טו, 16=טז), and punctuation (', ").

#### 3. Class Methods
- `format(long number)`: Extracts thousands prefix, hundreds, tens, and units. Handles special cases 15 and 16, and attaches proper geresh/gershayim punctuation.
- `parse(String input)`: Strips quotes and whitespace, identifies thousands prefix, sums letter values, and validates against formatted output.

#### 8. Simplest Terms
**Real-World Analogy:** Like a Hebrew scribe who calculates the numeric value of words and ensures traditional letter combinations are formatted properly.
