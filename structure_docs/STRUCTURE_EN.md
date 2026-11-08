# Application Architecture & Structure: Number Systems

---

## 1. The Big Picture

The "Number Systems" application is a digital tool that enables fast number conversion and mathematical calculations across 6 different numeral systems (Binary, Octal, Decimal, Hexadecimal, Roman Numerals, and Hebrew Gematria). The application consists of three primary layers: User Interface Screens (UI), Conversion & Calculation Logic (Engine), and Data Models.

**Real-World Analogy — A Modern Restaurant:**
Think of the application as a restaurant:
- **The Waiters (UI Screens):** Take the order from the customer (entering a number or expression) and present the formatted dish in a floating dialog at the end.
- **The Kitchen & Chef (Conversion & Math Engine):** Take the input, validate the ingredients, and prepare the results in all languages.
- **The Serving Plates & Menu (Data Models & Adapters):** Hold the data neatly formatted so it is clean and easy to read.
This separation prevents clutter, keeps code organized, and allows altering the kitchen logic without disrupting the dining area design.

---

## 2. Directory & File Overview

The project is structured into clear packages and directories:

### 📁 Main Package (`zeev.fraiman.numbersystems`)

- **Screen Components (Activities & Fragments):**
  - `SplashActivity`: Launcher screen displaying the app logo for 4 seconds before transitioning to the main screen.
  - `MainActivity`: Main activity hosting the navigation tabs.
  - `ConverterFragment`: Number converter screen ("All Types") where the user enters a number to convert.
  - `CalculatorFragment`: Expression calculator screen where the user enters arithmetic expressions.

- **UI & Dialog Components (Adapters & Dialogs):**
  - `ViewPagerAdapter`: Fragment adapter managing tab pages inside `ViewPager2`.
  - `CustomResultsDialog`: Modern floating bottom sheet (`BottomSheetDialog`) presenting results.
  - `ResultsAdapter`: RecyclerView adapter binding result cards with a one-touch copy button.

- **Data Models (Models & Enums):**
  - `NumeralSystem`: Enum defining the 6 supported numeral systems.
  - `ResultItem`: Data model holding a system name and converted value pair.
  - `ConversionResult`: Model holding conversion results for all 6 systems.
  - `CalculatorResult`: Model holding expression and calculation results for all 6 systems.

- **Logic & Conversion Engines (Engine / Converters):**
  - `NumberConverter`: Central engine parsing input to decimal (base 10) and formatting to all target systems.
  - `RomanConverter`: Dedicated parser and formatter for Roman numerals (1–3999).
  - `GematriaConverter`: Dedicated parser and formatter for Hebrew Gematria (1–3999).
  - `ExpressionEvaluator`: Engine evaluating arithmetic expressions (`+`, `-`, `*`).

### 📁 Resource Directory (`res/`)
- `res/layout/`: XML layout files for activities, fragments, dialogs, and cards.
- `res/values/strings.xml`: English text strings and UI messages.
- `res/drawable/`: Application logo image (`logo.png`).

---

## 3. Relationships Between Parts

The diagram below illustrates how data flows from user input through the system components to result display:

```
[User enters input and taps button]
                │
                ▼
   [ConverterFragment / CalculatorFragment]
                │ (Initial validation)
                ▼
        [NumberConverter]  ◄──►  [RomanConverter / GematriaConverter]
                │ (Converts to decimal IR & formats targets)
                ▼
   [ConversionResult / CalculatorResult]
                │ (Constructs ResultItem list)
                ▼
      [CustomResultsDialog]  ──►  [ResultsAdapter]  ──►  [On-screen Cards]
```

**Step-by-Step Sequence:**
1. User enters input in `ConverterFragment` and taps "All Types".
2. Fragment calls `NumberConverter.convertAll()`.
3. `NumberConverter` parses input (using `RomanConverter` or `GematriaConverter` if needed) into an intermediate decimal `long`.
4. Engine converts decimal value into all 6 target systems and returns `ConversionResult`.
5. Fragment builds a list of `ResultItem` objects and invokes `CustomResultsDialog.show()`.
6. Dialog opens and renders formatted cards using `ResultsAdapter`.

---

## 4. Project Example: Converting a Roman Numeral

Scenario where user selects Roman numeral system, enters `XII`, and taps "All Types":

1. **Click Handling in `ConverterFragment`:**
```java
private void performConversion() {
    String inputText = binding.etInputNumber.getText().toString(); // "XII"
    NumeralSystem selectedSystem = getSelectedSystem(); // ROMAN

    ConversionResult result = NumberConverter.convertAll(inputText, selectedSystem);
    // ...
}
```

2. **Parsing in `NumberConverter` & `RomanConverter`:**
```java
// Inside RomanConverter.java:
public static long parse(String input) {
    // Parses "XII" to decimal integer 12
    // Validates correctness and bounds [1..3999]
    return 12;
}
```

3. **Converting & Displaying Results:**
```java
// Inside NumberConverter.java:
for (NumeralSystem targetSystem : NumeralSystem.values()) {
    result.put(targetSystem, formatFromDecimal(12, targetSystem));
}
// Output: BIN="1100", DEC="12", HEX="C", ROMAN="XII", GEMATRIA="י'"...

// Render in CustomResultsDialog:
CustomResultsDialog.show(requireContext(), "Results in all systems:", subtitle, items);
```

---

## 5. Short Glossary

- **Activity:** Primary screen or window in an Android application.
- **Fragment:** Reusable, modular UI section hosted inside an Activity.
- **Adapter:** Bridge component binding data sources to UI views (e.g. RecyclerView).
- **View Binding:** Compile-time mechanism providing type-safe access to layout views.
- **BottomSheetDialog:** Floating dialog sheet presented at the bottom of the screen.
- **Enum:** Data type defining a fixed set of constants (e.g. 6 numeral systems).
- **Intermediate Representation (IR):** Architectural pattern converting input into a central base (decimal) first to simplify multi-system conversions.
