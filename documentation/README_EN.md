# 📱 Android Application Documentation (Grade 10/10) - Number Systems

### 🧾 General Information
- **Project Name:** Number Systems
- **Author(s):** Zeev Fraiman
- **Date:** February 2025
- **Language:** Java
- **Development Environment:** Android Studio
- **Android Version (minSdk / targetSdk):** minSdk: 28 / targetSdk: 37 / compileSdk: 37

---

### 🎯 Project Purpose
- **What problem the app solves:** The application provides fast and accurate number conversion and arithmetic evaluation across 6 different numeral systems and representations: Binary (2), Octal (8), Decimal (10), Hexadecimal (16), Roman numerals (I, IV, IX...), and Hebrew Gematria (א', ט"ו, תשפ"ד).
- **Why this problem is important:** Manual conversion between numeral systems, especially non-positional ones like Roman numerals and Hebrew Gematria, is complex and error-prone. The app offers a unified, rapid, and precise tool.
- **Target Audience:** Students, computer science majors, teachers, researchers, and anyone working with different number representations, Hebrew Gematria, and Roman numerals.

---

### 📌 Application Requirements

**Functional Requirements**
- **Splash Screen:** Displays the application logo (`logo.png`) for 4 seconds with a tap-to-skip option to immediately launch the main activity.
- **Number Conversion ("All Types"):** Select source numeral system using RadioGroup, enter a number, and validate input within the range of 1 to 3999.
- **Expression Calculator ("Calculator"):** Select numeral system, enter a simple math expression (addition `+`, subtraction `-`, multiplication `*`), validate inputs, and compute the result.
- **Custom Results Dialog (Custom BottomSheetDialog):** Display formatted results across all 6 systems in a styled Material 3 modal sheet.
- **One-Click Copy:** Copy button to copy any displayed result to the clipboard.

**Non-Functional Requirements**
- **Performance:** Instant calculations with zero latency using an in-memory decimal Intermediate Representation (IR).
- **Usability:** Modern, clean Material Design 3 interface utilizing View Binding and ViewPager2 with TabLayout.
- **Reliability:** Strict input bounds checking, clear error handling, and comprehensive unit test coverage.

---

### 🧠 General Architecture
- **Selected Approach:** Modular View-Controller architecture utilizing an **Intermediate Representation (IR)** pattern.
- **Why this approach was selected:** Converting through a central decimal representation (`long`) avoids writing $6 \times 6 = 36$ separate converter routines. Each system only requires one parsing method and one formatting method.
- **Core System Components:**
  1. **UI Layer:** `SplashActivity`, `MainActivity`, `ConverterFragment`, `CalculatorFragment`, `CustomResultsDialog`, `ResultsAdapter`.
  2. **Logic & Converter Layer:** `NumberConverter`, `RomanConverter`, `GematriaConverter`, `ExpressionEvaluator`.
  3. **Data & Model Layer:** `NumeralSystem`, `ConversionResult`, `CalculatorResult`, `ResultItem`.

---

### 🧩 UML Diagram

```
[SplashActivity] ──(4 sec delay / click)──> [MainActivity]
                                                  │
                                          [ViewPager2 Adapter]
                                          ┌───────┴───────┐
                                          ▼               ▼
                                 [ConverterFragment] [CalculatorFragment]
                                          │               │
                                          └───────┬───────┘
                                                  ▼
                                         [NumberConverter]
                                      ┌───────────┼───────────┐
                                      ▼           ▼           ▼
                               [RomanConverter] [GematriaConverter] [ExpressionEvaluator]
                                      └───────────┬───────────┘
                                                  ▼
                                      [CustomResultsDialog]
                                                  │
                                          [ResultsAdapter]
```

**Explanation:**
- **Decoupling:** Separating UI from converter logic facilitates easy maintenance, unit testing independent of the Android framework, and simple future extensions.
- **Scalability:** Adding a new numeral system (e.g. base 5 or base 12) only requires adding an entry to the `NumeralSystem` enum and providing its single pair of parse/format methods.

---

### 🧩 Detailed Class Description

**📌 Class: SplashActivity**
- **Role:** Application launcher screen.
- **Responsibility:** Displays logo for 4 seconds, handles tap-to-skip, and navigates to `MainActivity`.
- **Key Methods:** `onCreate()`, handler management and callback cleanup in `onDestroy()`.

**📌 Class: MainActivity**
- **Role:** Main container activity hosting navigation tabs.
- **Responsibility:** Manages `ViewPager2` and `TabLayout` for switching between Converter and Calculator tabs.
- **Key Methods:** `onCreate()`, initializing `ViewPagerAdapter` and attaching `TabLayoutMediator`.

**📌 Class: ConverterFragment**
- **Role:** UI controller for "All Types" number conversion.
- **Responsibility:** Captures input number, handles system selection, calls `NumberConverter.convertAll()`, and displays `CustomResultsDialog`.

**📌 Class: CalculatorFragment**
- **Role:** UI controller for expression calculation.
- **Responsibility:** Captures expression, handles system selection, quick operator buttons (`+`, `-`, `*`), calls `ExpressionEvaluator.evaluate()`, and displays `CustomResultsDialog`.

**📌 Class: CustomResultsDialog**
- **Role:** Custom Material 3 BottomSheetDialog for displaying results.
- **Responsibility:** Presents formatted values across all 6 systems with copy-to-clipboard functionality.

**📌 Class: NumberConverter**
- **Role:** Central conversion engine.
- **Responsibility:** Parses input string into decimal `long`, validates range (1–3999), and formats to target systems.

**📌 Class: RomanConverter**
- **Role:** Roman numeral parser and formatter.
- **Responsibility:** Strict validation, parsing, and formatting for Roman numerals in range 1–3999 (I, V, X, L, C, D, M).

**📌 Class: GematriaConverter**
- **Role:** Hebrew Gematria parser and formatter.
- **Responsibility:** Validation, parsing, and formatting for Hebrew letters (1–3999), including thousands (א', ב', ג'), special rules (15=טו, 16=טז), and punctuation (', ").

**📌 Class: ExpressionEvaluator**
- **Role:** Arithmetic expression evaluator.
- **Responsibility:** Parses operands and operator, evaluates result, and formats expression and result across all 6 systems.

---

### 🔄 Application Workflow Diagram
1. App launches on `SplashActivity`, displaying the logo for 4 seconds (or until tapped).
2. User is navigated to `MainActivity` and selects between **Converter** and **Calculator** tabs.
3. **On Converter Tab:** User selects numeral system, enters a number, and presses "All Types".
4. Input validation is performed. If invalid or out of range [1..3999], an error message is displayed.
5. If valid, `CustomResultsDialog` opens showing the number converted across all 6 systems.
6. **On Calculator Tab:** User selects numeral system, enters an expression (e.g. `XII + IX`), and presses "Calculate".
7. Expression is evaluated and displayed across all 6 systems inside `CustomResultsDialog`.

---

### 🎨 UI/UX Analysis
- **Design Rationale:** Material Design 3 interface using RadioGroups for clear system selection and modern BottomSheetDialogs to prevent main screen clutter.
- **Applied Principles:**
  - *Simplicity:* All inputs cleanly presented in dedicated tabs.
  - *Logic:* Input -> Validation -> Results Modal.
  - *Accessibility:* High contrast, readable typography, and one-tap clipboard copy.

---

### ⚙️ Thread Management
- **Handler & Looper:** `SplashActivity` uses `Handler(Looper.getMainLooper()).postDelayed(...)` for the 4-second transition delay.
- **Memory Leak & ANR Prevention:** Callbacks are cleared in `onDestroy()` (`handler.removeCallbacks(...)`) to prevent memory leaks if the user exits early. All math conversions run in $O(1)$ time in memory without blocking the UI Thread.

---

### 💾 Data Handling
- **Data Storage:** Real-time in-memory processing. No database or disk persistence is required, guaranteeing speed and privacy.
- **Data Integrity:** Strict bounds checking ($1 \le N \le 3999$) using `long` primitives to prevent overflow and conversion errors.

---

### 🌐 Network Operations
- The application operates 100% **Offline** with zero network calls or external dependencies.

---

### 🔐 Security (Basic Level)
- No sensitive or personal user data is collected, stored, or transmitted.
- All operations run locally on device.

---

### 🧪 Testing
- **Unit Tests:** Comprehensive unit tests implemented in `ConverterUnitTest.java`.
- **Tested Functionality:**
  - Roman numeral formatting & parsing (`RomanConverter`).
  - Hebrew Gematria formatting & parsing (`GematriaConverter`).
  - Invalid input handling (`testInvalidRoman`).
  - Multi-system conversion (`NumberConverter.convertAll`).
  - Expression evaluation (`ExpressionEvaluator.evaluate`).

---

### 🐞 Error Handling
- **Invalid Input:** Rejects invalid characters for the selected system (e.g. '2' in binary, '8' in octal, Latin letters in Gematria).
- **Range Violation:** Clear error message if number or calculation result is outside [1..3999].
- **Syntax Errors:** Handles missing operands or unknown operators.

---

### ⚡ Performance
- **Optimization:** Fast conversions using decimal intermediate representation.
- **Bottlenecks:** None, due to strict upper bounds (3999) and local processing.

---

### 🚀 Extensibility Options
- Support additional number bases (base 3, base 5, base 12, base 64).
- Extend bounds for non-Roman/Gematria systems.
- Add additional math functions (division, exponentiation, parentheses).
