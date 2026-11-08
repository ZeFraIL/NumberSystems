# תיאור מפורט של המחלקה: ResultsAdapter

#### 1. מידע כללי
- **שם המחלקה:** `ResultsAdapter`
- **סוג:** Adapter (מתאם לרשימת RecyclerView)
- **תפקיד המחלקה באפליקציה:** מנהלת ומציגה את רשימת כרטיסיות התוצאות (`item_system_result.xml`) בתוך ה-`RecyclerView` בדיאלוג `CustomResultsDialog`. כוללת כפתור копирование (Copy to Clipboard) לכל פריט.
- **אינטראקציה עם רכיבים אחרים:** מופעלת על ידי `CustomResultsDialog`, משתמשת ב-`ResultItem` כמודל נתונים וב-`ClipboardManager` של מערכת Android.

#### 2. משתנים (שדות המחלקה)

| שם | סוג | ייעוד | היכן בשימוש |
|------|------|---------|-------------------|
| `items` | `List<ResultItem>` (final) | רשימת פריטי התוצאות להצגה | בשימוש ב-`setItems`, `onBindViewHolder`, ו-`getItemCount` |

#### 3. מתודות המחלקה

**שם המתודה:** `setItems`
- **סוג המתודה:** `public`
- **ערך מוחזר:** `void`
- **פרמטרים:** `newItems` (`List<ResultItem>`)
- **מה המתודה עושה:** מנקה את הרשימה הקיימת, מוסיפה את האיברים החדשים ומזמנת `notifyDataSetChanged()` לרענון הרשימה.

**שם המתודה:** `onCreateViewHolder`
- **סוג המתודה:** `public`
- **ערך מוחזר:** `ResultViewHolder`
- **מה המתודה עושה:** מנפחת את `item_system_result.xml` ויוצרת `ViewHolder` חדש.

**שם המתודה:** `onBindViewHolder`
- **סוג המתודה:** `public`
- **ערך מוחזר:** `void`
- **פרמטרים:** `holder` (`ResultViewHolder`), `position` (`int`)
- **מה המתודה עושה:** מקשרת את הנתונים (שם המערכת והערך המומת) ל-TextViews, ומגדירה ClickListener לכפתור ההעתקה `btnCopy`. בלחיצה, הנתונים מועתקים ללוח בעזרת `ClipboardManager` ומציגים הודעת Toast ("Copied to clipboard").

**שם המתודה:** `getItemCount`
- **סוג המתודה:** `public`
- **ערך מוחזר:** `int` (מספר הפריטים)

#### 4. מחזור חיים
אין (מנוהל על ידי ה-RecyclerView).

#### 5. אינטראקציה עם הממשק (UI)
מנהלת את רכיבי `item_system_result.xml`: `tvSystemName`, `tvSystemValue`, ו-`btnCopy`.

#### 6. אינטראקציה עם רכיבים אחרים
נעזרת ב-`ClipboardManager` של מערכת ההפעלה להעתקת טקסט.

#### 7. לוגיקה כללית של המחלקה
המתאם מקבל רשימת תוצאות ומייצר כרטיסייה מעוצבת עבור כל מערכת ספירה, כולל אפשרות העתקה בנגיעה אחת.

#### 8. הסבר במילים פשוטות
**אנלוגיה מהחיים:** תחשבו על בית דפוס שמקבל רשימת כתובות ומדפיס מדבקה מעוצבת לכל כתובת, ובצד המדבקה יש לחצן שמצלם אותה ישר לתוך התיק שלכם.
