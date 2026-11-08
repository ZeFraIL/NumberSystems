# תיאור מפורט של המחלקה: ConverterFragment

#### 1. מידע כללי
- **שם המחלקה:** `ConverterFragment`
- **סוג:** Fragment (פראגמנט - חלק ממסך)
- **תפקיד המחלקה באפליקציה:** מנהל את המסך והלוגיקה של הממיר ("All Types"). מאפשר למשתמש לבחור מערכת ספירה, להזין מספר, לבצע אימות קלט ולהציג את התוצאות בכל 6 המערכות בתוך חלון `CustomResultsDialog`.
- **אינטראקציה עם רכיבים אחרים:** מוצג בתוך `ViewPager2` ב-`MainActivity`, קורא למנוע `NumberConverter.convertAll()`, ומציג את התוצאה דרך `CustomResultsDialog`.

#### 2. משתנים (שדות המחלקה)

| שם | סוג | ייעוד | היכן בשימוש |
|------|------|---------|-------------------|
| `binding` | `FragmentConverterBinding` | אובייקט ViewBinding לגישה לרכיבי ה-Layout של הפראגמנט | בשימוש בכל מתודות הפראגמנט |

#### 3. מתודות המחלקה

**שם המתודה:** `onCreateView`
- **סוג המתודה:** `public`
- **ערך מוחזר:** `View` (תצוגת הפראגמנט)
- **פרמטרים:** `inflater`, `container`, `savedInstanceState`
- **מה המתודה עושה:** מנפחת את ה-Layout `fragment_converter.xml` ומחזירה את ה-Root View.

**שם המתודה:** `onViewCreated`
- **סוג המתודה:** `public`
- **ערך מוחזר:** `void`
- **פרמטרים:** `view`, `savedInstanceState`
- **מה המתודה עושה:** מגדירה מאזיני לחיצה לכפתור "All Types", מאזין לשינוי טקסט ב-`EditText` (לניקוי שגיאות בזמן אמת), ומאזין לשינוי בחירת כפתור רדיו ב-`RadioGroup`.

**שם המתודה:** `getSelectedSystem`
- **סוג המתודה:** `private`
- **ערך מוחזר:** `NumeralSystem` (ערך ה-Enum של המערכת הנבחרת)
- **פרמטרים:** ללא
- **מה המתודה עושה:** בודקת איזה RadioButton מסומן ב-`RadioGroup` ומחזירה את ערך ה-Enum המתאים (`BIN`, `OCT`, `DEC`, `HEX`, `ROMAN`, `GEMATRIA`).

**שם המתודה:** `performConversion`
- **סוג המתודה:** `private`
- **ערך מוחזר:** `void`
- **פרמטרים:** ללא
- **מה המתודה עושה:** מנקה שגיאות קודמות, קוראת את הקלט, מזהה את המערכת הנבחרת, קוראת ל-`NumberConverter.convertAll()`, ואם הקלט תקין — פותחת את `CustomResultsDialog` עם התוצאות. אם מוקפצת חריגה (`IllegalArgumentException`), היא מציגה הודעת שגיאה ברורה בשדה הקלט.

#### 4. מחזור חיים (Fragment בלבד)
- `onCreateView()`: ניפוח התצוגה.
- `onViewCreated()`: הגדרת מאזינים לרכיבי ה-UI.
- `onDestroyView()`: ניקוי ה-binding למניעת דליפות זיכרון.

#### 5. אינטראקציה עם הממשק (UI)
- `RadioGroup` (`rgSystems`): בחירת מערכת הספירה.
- `TextInputLayout` & `TextInputEditText`: שדה הזנת המספר עם הודעת עזרה והצגת שגיאות.
- `MaterialButton` (`btnConvert`): כפתור "All Types" להפעלת ההמרה.

#### 6. אינטראקציה עם רכיבים אחרים
- קורא ל-`NumberConverter.convertAll(inputText, selectedSystem)`.
- קורא ל-`CustomResultsDialog.show(...)` להצגת חלון התוצאות.

#### 7. לוגיקה כללית של המחלקה
המשתמש בוחר מערכת ספירה (למשל רומית), מזין מספר (למשל `XII`) ולוחץ "All Types". הפראגמנט בודק שהקלט תקין, ממיר אותו בעזרת `NumberConverter`, ופותח חלון תוצאות מעוצב עם התוצאות בכל 6 המערכות.

#### 8. הסבר במילים פשוטות
**אנלוגיה מהחיים:** תחשבו על דלפק קבלה בבנק שבו אתם ממלאים טופס (הזנת מספר ובחירת שפה), מוסרים לפקיד (לחצן המרה), והפקיד מביא לכם דף מודפס עם הסכום מחושב בכל המטבעות.
