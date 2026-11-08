# ארכיטקטורת ומבנה האפליקציה: מערכות ספירה (Number Systems)

---

## 1. התמונה הגדולה

אפליקציית "מערכות ספירה" היא כלי דיגיטלי המאפשר להמיר מספרים ולבצע חישובים מתמטיים בין 6 מערכות ספירה שונות (דואית, восьмеричная, דצימלית, הקסדצימלית, ספרות רומיות וגימטריה עברית). האפליקציה מורכבת משלושה חלקים מרכזיים: מסכי תצוגה (UI), מנוע המרה וחישוב (Logic), ומודלים להחזקת הנתונים (Data).

**אנלוגיה מהחיים האמיתיים — מסעדה מודרנית:**
תחשבו על האפליקציה כמו על מסעדה:
- **המלצרים (מסכי ה-UI):** מקבלים את ההזמנה מהלקוח (הזנת מספר או תרגיל) ומציגים לו את המנה המושלמת בסוף בחלון צף.
- **המטבח והשף (מנוע ההמרה והחישוב):** לוקחים את הקלט, בודקים שהרכיבים תקינים, ומכינים את התוצאות בכל השפות.
- **צלחות ההגשה והתפריט (מודלי הנתונים והאדפטר):** מחזיקים את הנתונים בצורה מסודרת ויפה כדי שיהיה נעים וקל לקרוא אותם.
הפרדה זו מלכתחילה מונעת בלבול, שומרת על סדר, ומאפשרת לתקן את המטבח מבלי להרוס את עיצוב חדר האוכל.

---

## 2. סקירת תיקיות וקבצים

הפרויקט מחולק למבנה מסודר של קבצים וחבילות:

### 📁 חבילת הקוד הראשית (`zeev.fraiman.numbersystems`)

- **רכיבי מסך (Activities & Fragments):**
  - `SplashActivity`: מסך פתיחה המציג את לוגו האפליקציה במשך 4 שניות לפני המעבר למסך הראשי.
  - `MainActivity`: המסך הראשי המכיל את לשוניות הניווט.
  - `ConverterFragment`: מסך הממיר ("All Types") שבו המשתמש מזין מספר ומקבל את תרגומו.
  - `CalculatorFragment`: מסך המחשבון שבו המשתמש מזין ביטוי מתמטי.

- **רכיבי תצוגה ודיאלוגים (Adapters & Dialogs):**
  - `ViewPagerAdapter`: מתאם שמציג את הפראגמנט המתאים לפי הלשונית שנבחרה.
  - `CustomResultsDialog`: חלון צף מודרני (BottomSheetDialog) המציג את התוצאות בתחתית המסך.
  - `ResultsAdapter`: מתאם שמצייר כרטיסייה מעוצבת לכל תוצאה ומאפשר העתקה בלחיצה.

- **מודלי נתונים (Models & Enums):**
  - `NumeralSystem`: רשימה קבועה (Enum) של 6 מערכות הספירה הנתמכות.
  - `ResultItem`: אובייקט המחזיק זוג נתונים (שם המערכת + הערך המומת).
  - `ConversionResult`: מודל המאחסן את תוצאות ההמרה לכל 6 המערכות.
  - `CalculatorResult`: מודל המאחסן את הביטוי והתוצאה לכל 6 המערכות.

- **מנועי לוגיקה והמרה (Engine / Converters):**
  - `NumberConverter`: המנוע המרכזי הממיר כל קלט למספר דצימלי (בסיס 10) וממנו לכל שאר המערכות.
  - `RomanConverter`: מנוע ייעודי לפענוח ופירמוט ספרות רומיות (1–3999).
  - `GematriaConverter`: מנוע ייעודי לפענוח ופירמוט אותיות עבריות (1–3999).
  - `ExpressionEvaluator`: מנוע המחשב ביטויים מתמטיים (`+`, `-`, `*`).

### 📁 תיקיית המשאבים (`res/`)
- `res/layout/`: קבצי עיצוב ה-XML של המסכים, הדיאלוג והכרטיסיות.
- `res/values/strings.xml`: קובץ הטקסטים וההודעות באנגלית.
- `res/drawable/`: תמונת הלוגו (`logo.png`).

---

## 3. יחסים בין החלקים

התרשים הבא מתאר כיצד הנתונים זורמים מהמשתמש דרך רכיבי המערכת עד להצגת התוצאה:

```
[משתמש מזין קלט ולוחץ על כפתור]
                │
                ▼
   [ConverterFragment / CalculatorFragment]
                │ (אימות קלט ראשוני)
                ▼
        [NumberConverter]  ◄──►  [RomanConverter / GematriaConverter]
                │ (המרה לבסיס 10 דצימלי והמרת תוצאות)
                ▼
   [ConversionResult / CalculatorResult]
                │ (בניית רשימת ResultItem)
                ▼
      [CustomResultsDialog]  ──►  [ResultsAdapter]  ──►  [כרטיסיות במסך]
```

**זרימת השלבים:**
1. המשתמש מזין קלט ב-`ConverterFragment` ולוחץ על "All Types".
2. הפראגמנט קורא למתודה `NumberConverter.convertAll()`.
3. `NumberConverter` מפענח את הקלט (בעזרת `RomanConverter` או `GematriaConverter` במידת הצורך) למספר דצימלי מרכזי.
4. המנוע ממיר את המספר הדצימלי לכל 6 המערכות ומחזיר אובייקט `ConversionResult`.
5. הפראגמנט מייצר רשימת אובייקטים של `ResultItem` וקורא ל-`CustomResultsDialog.show()`.
6. הדיאלוג נפתח ומציג את התוצאות בעזרת `ResultsAdapter`.

---

## 4. דוגמה מפרויקט זה: תרחיש המרת מספר רומי

נסקור תרחיש שבו המשתמש בוחר במערכת הרומית, מזין `XII`, ולוחץ על "All Types":

1. **קליטת אירוע הלחיצה ב-`ConverterFragment`:**
```java
private void performConversion() {
    String inputText = binding.etInputNumber.getText().toString(); // "XII"
    NumeralSystem selectedSystem = getSelectedSystem(); // ROMAN

    ConversionResult result = NumberConverter.convertAll(inputText, selectedSystem);
    // ...
}
```

2. **פענוח ב-`NumberConverter` ו-`RomanConverter`:**
```java
// בתוך RomanConverter.java:
public static long parse(String input) {
    // מפענח "XII" למספר הדצימלי 12
    // מאמת שהמספר תקין ובתוך הטווח [1..3999]
    return 12;
}
```

3. **המרה לכל המערכות והצגת הדיאלוג:**
```java
// בתוך NumberConverter.java:
for (NumeralSystem targetSystem : NumeralSystem.values()) {
    result.put(targetSystem, formatFromDecimal(12, targetSystem));
}
// מחזיר: BIN="1100", DEC="12", HEX="C", ROMAN="XII", GEMATRIA="י'"...

// הצגת התוצאה ב-CustomResultsDialog:
CustomResultsDialog.show(requireContext(), "Results in all systems:", subtitle, items);
```

---

## 5. מילון מונחים קצר

- **Activity (אקטיביטי):** מסך ראשוני או חלון ראשי באפליקציית אנדרואיד.
- **Fragment (פראגמנט):** רכיב תצוגה עצמאי ונייד היכול להיות חלק ממסך ראשי.
- **Adapter (אדפטר/מתאים):** רכיב מקשר המחבר בין מקור הנתונים לבין רכיב התצוגה (כמו RecyclerView).
- **View Binding (בינדינג):** מנגנון המאפשר גישה בטוחה וישירה לרכיבי ה-UI מתוך קוד ה-Java.
- **BottomSheetDialog:** חלון צף מודרני שנפתח בתחתית המסך להצגת מידע נוסף.
- **Enum (אינום):** טיפוס נתונים המגדיר רשימה סגורה וקבועה של ערכים (כמו 6 מערכות הספירה).
- **Intermediate Representation (IR):** טכניקת תכנון שבה ממירים קודם כל לבסיס מרכזי אחד (דצימלי) כדי לפשט המרות מרובות.
