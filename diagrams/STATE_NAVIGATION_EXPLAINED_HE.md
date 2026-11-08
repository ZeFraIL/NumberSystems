# דיאגרמת ניווט ומצבים ומפת מחלקות וקשרים

## 1. דיאגרמה 1: ניווט ומצבים

```mermaid
stateDiagram-v2
    [*] --> SplashActivity : הפעלת האפליקציה

    state SplashActivity {
        [*] --> LogoDisplayState
        LogoDisplayState --> MainActivity : טיימר 4 שניות / לחיצה על המסך
    }

    state MainActivity {
        [*] --> ConverterFragment : לשונית 0 (ממיר) ברירת מחדל

        state ConverterFragment {
            [*] --> ConverterInputState
            ConverterInputState --> CustomResultsDialog : לחיצה על 'All Types' / קלט תקין
        }

        state CalculatorFragment {
            [*] --> CalculatorInputState
            CalculatorInputState --> CustomResultsDialog : לחיצה על 'Calculate' / קלט תקין
        }

        ConverterFragment --> CalculatorFragment : בחירת לשונית 1
        CalculatorFragment --> ConverterFragment : בחירת לשונית 0
    }

    state CustomResultsDialog {
        [*] --> ResultsDisplayState
        ResultsDisplayState --> [*] : לחיצה על 'Done' או 'X' / סגירה
    }

    MainActivity --> [*] : לחיצה על כפתור חזור
```

---

## 2. דיאגרמה 2: מפת מחלקות וקשרים

```mermaid
flowchart TD
  subgraph UI["שכבה: מסכים ו-UI"]
    SplashActivity["SplashActivity<br/>[Activity]"]
    MainActivity["MainActivity<br/>[Activity]"]
    ConverterFragment("ConverterFragment<br/>[Fragment]")
    CalculatorFragment("CalculatorFragment<br/>[Fragment]")
    CustomResultsDialog{"CustomResultsDialog<br/>[Dialog]"}
    ViewPagerAdapter[/"ViewPagerAdapter<br/>[Adapter]"/]
    ResultsAdapter[/"ResultsAdapter<br/>[Adapter]"/]
  end

  subgraph HELPERS["שכבה: מחלקות עזר ומנועים"]
    NumberConverter[["NumberConverter<br/>[Helper]"]]
    RomanConverter[["RomanConverter<br/>[Helper]"]]
    GematriaConverter[["GematriaConverter<br/>[Helper]"]]
    ExpressionEvaluator[["ExpressionEvaluator<br/>[Helper]"]]
  end

  subgraph MODEL["שכבה: מודלים ונתונים"]
    NumeralSystem["NumeralSystem<br/>[Model]"]
    ResultItem["ResultItem<br/>[Model]"]
    ConversionResult["ConversionResult<br/>[Model]"]
    CalculatorResult["CalculatorResult<br/>[Model]"]
  end

  subgraph LEGEND["מקרא"]
    L_Activity["SampleActivity<br/>[Activity]"]
    L_Fragment("SampleFragment<br/>[Fragment]")
    L_Dialog{"SampleDialog<br/>[Dialog]"}
    L_Model["SampleModel<br/>[Model]"]
    L_Adapter[/"SampleAdapter<br/>[Adapter]"/]
    L_Helper[["SampleHelper<br/>[Helper]"]]
    
    L_Nav["A -->|מעבר| B"]
    L_Call["A -.->|קריאה| B"]
    L_Data["A ==>|נתונים| B"]
  end

  %% קשרי ניווט (חץ מלא -->)
  SplashActivity -->|טיימר 4 שניות / לחיצה| MainActivity
  MainActivity -->|מכיל דרך ViewPager2| ViewPagerAdapter
  ViewPagerAdapter -->|יוצר| ConverterFragment
  ViewPagerAdapter -->|יוצר| CalculatorFragment
  ConverterFragment -->|מציג| CustomResultsDialog
  CalculatorFragment -->|מציג| CustomResultsDialog
  CustomResultsDialog -->|משתמש| ResultsAdapter

  %% קריאות מתודה (חץ מקווקוו -.-)
  ConverterFragment -.->|קורא ל-convertAll| NumberConverter
  CalculatorFragment -.->|קורא ל-evaluate| ExpressionEvaluator
  ExpressionEvaluator -.->|קורא ל-parse ו-format| NumberConverter
  NumberConverter -.->|מעביר לרומית| RomanConverter
  NumberConverter -.->|מעביר לגימטריה| GematriaConverter

  %% מעבר נתונים (חץ מודגש ==>)
  NumberConverter ==>|יוצר| ConversionResult
  ExpressionEvaluator ==>|יוצר| CalculatorResult
  ConverterFragment ==>|בונה| ResultItem
  CalculatorFragment ==>|בונה| ResultItem
  ResultsAdapter ==>|קשור ל-| ResultItem
  NumberConverter ==>|משתמש ב-| NumeralSystem

  %% עיצוב classDef
  classDef activity fill:#DBEAFE,stroke:#1E40AF,stroke-width:3px
  classDef fragment fill:#DCFCE7,stroke:#15803D,stroke-width:2px
  classDef dialog fill:#EDE9FE,stroke:#6D28D9,stroke-width:2px
  classDef model fill:#FEF9C3,stroke:#A16207,stroke-width:2px
  classDef data fill:#FCE7F3,stroke:#BE185D,stroke-width:2px
  classDef adapter fill:#FFEDD5,stroke:#C2410C,stroke-width:2px
  classDef helper fill:#F3F4F6,stroke:#6B7280,stroke-width:2px,stroke-dasharray:5 5
  classDef system fill:#E5E7EB,stroke:#9CA3AF,stroke-width:1px,stroke-dasharray:3 3

  class SplashActivity,MainActivity,L_Activity activity
  class ConverterFragment,CalculatorFragment,L_Fragment fragment
  class CustomResultsDialog,L_Dialog dialog
  class ResultItem,NumeralSystem,ConversionResult,CalculatorResult,L_Model model
  class ViewPagerAdapter,ResultsAdapter,L_Adapter adapter
  class NumberConverter,RomanConverter,GematriaConverter,ExpressionEvaluator,L_Helper helper
```

---

## 3. איך לקרוא את הדיאגרמות

### איך לקרוא את דיאגרמה 1 (ניווט ומצבים)

| אלמנט בדיאגרמה | מה זה אומר |
|---|---|
| `[*]` | נקודת התחלה (הפעלת האפליקציה) או סיום (יציאה). |
| `מצב (State)` | מסך או דיאלוג המוצג למשתמש ברגע נתון. |
| `חץ מעבר` | אירוע או טיימר המעביר את האפליקציה ממצב אחד לאחר. |
| `כיתוב מעבר` | האירוע או התנאי המפעיל (למשל, "טיימר 4 שניות"). |
| `מצב מורכב` | מסך קונטיינר (כמו `MainActivity`) המכיל מצבים פנימיים/לשוניות. |

### איך לקרוא את דיאגרמה 2 (מפת מחלקות)

| אלמנט בדיאגרמה | מה זה אומר | צורה וצבע | דוגמה מפרויקט זה |
|---|---|---|---|
| **Activity** | מסך קונטיינר המופעל על ידי אנדרואיד | מלבן `[ ]`, כחול | `SplashActivity`, `MainActivity` |
| **Fragment** | רכיב מסך מודולרי | מלבן מעוגל `( )`, ירוק | `ConverterFragment`, `CalculatorFragment` |
| **Dialog** | חלון דיאלוג צף בתחתית המסך | מעוין `{ }`, סגול | `CustomResultsDialog` |
| **Model / POJO** | מחלקת נתונים או Enum | מלבן `[ ]`, צהוב | `ResultItem`, `NumeralSystem`, `ConversionResult` |
| **Data** | שכבת אחסון (בסיס נתונים/הגדרות) | צילינדר `[( )]`, ורוד | (אין — העיבוד מבוצע בזיכרון) |
| **Adapter** | מקשר בין נתונים לרשימת UI | מקבילית `[/ /]`, כתום | `ViewPagerAdapter`, `ResultsAdapter` |
| **Helper / Engine** | מנוע לוגיקה, חישוב ואימות | תת-תוכנית `[[ ]]`, אפור מקווקו | `NumberConverter`, `RomanConverter`, `ExpressionEvaluator` |
| **System** | מחלקות מובנות של אנדרואיד | מלבן `[ ]`, אפור-בהיר מקווקו | `Intent`, `Bundle`, `ViewPager2` |
| **חץ מלא `-->`** | ניווט או יצירת אובייקט | חץ רגיל | `SplashActivity --> MainActivity` |
| **חץ מקווקוו `-.->`** | קריאה למתודה / שימוש | חץ מקווקוו | `ConverterFragment -.-> NumberConverter` |
| **חץ מודגש `==>`** | יצירה או העברת נתונים | חץ מודגש | `NumberConverter ==> ConversionResult` |

---

## 4. הסבר מפורט

### 1. הרעיון המרכזי
בכל רגע נתון האפליקציה נמצאת בדיוק במצב אחד (למשל, מציגה את הלוגו, מציגה את הממיר או מציגה את חלון התוצאות). אירוע (כמו טיימר או לחיצה) מעביר אותה למצב אחר. במדעי המחשב מודל זה נקרא **אוטומט סופי (Finite State Machine - FSM)**.

### 2. אנלוגיה יומיומית
תחשבו על **מעלית**:
- המעלית נמצאת במצבים: `עומדת בקומה 1`, `זזה למעלה`, `דלתות פתוחות`.
- לחיצה על כפתור (אירוע) מעבירה אותה מ-`עומדת` ל-`זזה`.
- הגעה לקומה (תנאי) מעבירה אותה מ-`זזה` ל-`דלתות פתוחות`.

### 3. הפעלת האפליקציה
1. האפליקציה מתחילה ב-`[*]` ומפעילה את `SplashActivity`.
2. `SplashActivity` מציגה את הלוגו במשך 4 שניות (4000 מילישניות).
3. אם המשתמש לוחץ על המסך לפני חלוף 4 השניות או כשהטיימר מסתיים, האפליקציה עוברת ל-`MainActivity` וסוגרת את `SplashActivity`.

---

## 5. שאלות לבדיקה עצמית

1. **שאלת זמן:** כמה שניות מוצג מסך הפתיחה כברירת מחדל לפני מעבר ל-`MainActivity`?
2. **שאלת ארכיטקטורה:** איזו מחלקת עזר אחראית על חישוב ביטויים מתמטיים כמו `XII + IX`?
3. **שאלת התנהגות:** מה קורה אם המשתמש לוחץ על המסך בזמן הצגת הלוגו ב-`SplashActivity`?
4. **שאלת טווח:** מהו טווח המספרים התקין הנתמך ב-`NumberConverter` ומדוע?
5. **שאלת מפת מחלקות:** מה ההבדל בין מחלקת מודל (`ResultItem`) למחלקת עזר (`RomanConverter`)?
