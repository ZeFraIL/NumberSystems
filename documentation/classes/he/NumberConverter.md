# תיאור מפורט של המחלקה: NumberConverter

#### 1. מידע כללי
- **שם המחלקה:** `NumberConverter`
- **סוג:** Standard Class / Utility Engine (מנוע המרות מרכזי)
- **תפקיד המחלקה באפליקציה:** משמשת כמנוע ההמרה הראשי. מפענחת (Parsing) קלט טקסט מכל אחת מ-6 המערכות למספר דצימלי, מודדת אימות גבולות (1–3999), וממירה (Formatting) את המספר הדצימלי לכל 6 המערכות.
- **אינטראקציה עם רכיבים אחרים:** קוראת ל-`RomanConverter` ו-`GematriaConverter`, ומספקת שירותים ל-`ExpressionEvaluator`, `ConverterFragment` ו-`CalculatorFragment`.

#### 2. משתנים (שדות המחלקה)

| שם | סוג | ייעוד | היכן בשימוש |
|------|------|---------|-------------------|
| `MIN_VALUE` | `long` (static final) | הערך המינימלי המותר באפליקציה (`1`) | בשימוש באימות הקלט |
| `MAX_VALUE` | `long` (static final) | הערך המקסימלי המותר באפליקציה (`3999`) | בשימוש באימות הקלט |

#### 3. מתודות המחלקה

**שם המתודה:** `parseToDecimal`
- **סוג המתודה:** `public static`
- **ערך מוחזר:** `long` (המספר בדצימלי)
- **פרמטרים:** `input` (`String`), `system` (`NumeralSystem`)
- **מה המתודה עושה:** 
  1. בודקת שהקלט אינו ריק.
  2. מבצעת אימות תוים קפדני לפי המערכת (Regex לדואיצית `[01]+`, אוקטלית `[0-7]+`, דצימלית `[0-9]+`, הקסדצימלית `[0-9a-fA-F]+`).
  3. עבור רומית וגימטריה, קוראת ל-`RomanConverter.parse` ו-`GematriaConverter.parse`.
  4. בודקת שהמספר שהתקבל נמצא בטווח המותר [1..3999]. אם לא, זורקת `IllegalArgumentException`.

**שם המתודה:** `formatFromDecimal`
- **סוג המתודה:** `public static`
- **ערך מוחזר:** `String` (הייצוג במערכת היעד)
- **פרמטרים:** `number` (`long`), `system` (`NumeralSystem`)
- **מה המתודה עושה:** ממירה את המספר הדצימלי למחרוזת במערכת המבוקשת (`Long.toBinaryString`, `Long.toOctalString`, `Long.toHexString`, `RomanConverter.format`, `GematriaConverter.format`).

**שם המתודה:** `convertAll`
- **סוג המתודה:** `public static`
- **ערך מוחזר:** `ConversionResult`
- **פרמטרים:** `input` (`String`), `sourceSystem` (`NumeralSystem`)
- **מה המתודה עושה:** מפענחת את מספר המקור לדצימלי, ואז עוברת על כל ערכי ה-Enum של `NumeralSystem` וממירה את המספר לכל אחת מ-6 המערכות.

#### 4–7. לוגיקה כללית
המחלקה מיישמת את דפוס ה-Intermediate Representation (IR). כל מספר מתורגם קודם כל לבסיס 10, ומשם לכל שאר הבסיסים.

#### 8. הסבר במילים פשוטות
**אנלוגיה מהחיים:** תחשבו על מתרגם מרכזי באו"ם — במקום לתרגם ישירות בין 36 זוגות שפות, הוא מתרגם קודם כל את הדברים לאנגלית (בסיס 10), ומהאנגלית מתרגם לכל שאר 5 השפות.
