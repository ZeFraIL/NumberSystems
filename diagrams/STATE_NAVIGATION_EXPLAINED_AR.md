# مخطط التنقل والحالات وخريطة الفئات

## 1. المخطط 1: التنقل والحالات

```mermaid
stateDiagram-v2
    [*] --> SplashActivity : تشغيل التطبيق

    state SplashActivity {
        [*] --> LogoDisplayState
        LogoDisplayState --> MainActivity : مؤقت 4 ثوانٍ / نقر الشاشة
    }

    state MainActivity {
        [*] --> ConverterFragment : التبويب 0 (المحول) افتراضيًا

        state ConverterFragment {
            [*] --> ConverterInputState
            ConverterInputState --> CustomResultsDialog : النقر على 'All Types' / إدخال صالح
        }

        state CalculatorFragment {
            [*] --> CalculatorInputState
            CalculatorInputState --> CustomResultsDialog : النقر على 'Calculate' / إدخال صالح
        }

        ConverterFragment --> CalculatorFragment : اختيار التبويب 1
        CalculatorFragment --> ConverterFragment : اختيار التبويب 0
    }

    state CustomResultsDialog {
        [*] --> ResultsDisplayState
        ResultsDisplayState --> [*] : النقر على 'Done' أو 'X' / إغلاق
    }

    MainActivity --> [*] : النقر على زر الرجوع
```

---

## 2. المخطط 2: خريطة الفئات والعلاقات

```mermaid
flowchart TD
  subgraph UI["طبقة: الواجهات والشاشات"]
    SplashActivity["SplashActivity<br/>[Activity]"]
    MainActivity["MainActivity<br/>[Activity]"]
    ConverterFragment("ConverterFragment<br/>[Fragment]")
    CalculatorFragment("CalculatorFragment<br/>[Fragment]")
    CustomResultsDialog{"CustomResultsDialog<br/>[Dialog]"}
    ViewPagerAdapter[/"ViewPagerAdapter<br/>[Adapter]"/]
    ResultsAdapter[/"ResultsAdapter<br/>[Adapter]"/]
  end

  subgraph HELPERS["طبقة: الفئات المساعدة والمحركات"]
    NumberConverter[["NumberConverter<br/>[Helper]"]]
    RomanConverter[["RomanConverter<br/>[Helper]"]]
    GematriaConverter[["GematriaConverter<br/>[Helper]"]]
    ExpressionEvaluator[["ExpressionEvaluator<br/>[Helper]"]]
  end

  subgraph MODEL["طبقة: النماذج والبيانات"]
    NumeralSystem["NumeralSystem<br/>[Model]"]
    ResultItem["ResultItem<br/>[Model]"]
    ConversionResult["ConversionResult<br/>[Model]"]
    CalculatorResult["CalculatorResult<br/>[Model]"]
  end

  subgraph LEGEND["دليل المخطط"]
    L_Activity["SampleActivity<br/>[Activity]"]
    L_Fragment("SampleFragment<br/>[Fragment]")
    L_Dialog{"SampleDialog<br/>[Dialog]"}
    L_Model["SampleModel<br/>[Model]"]
    L_Adapter[/"SampleAdapter<br/>[Adapter]"/]
    L_Helper[["SampleHelper<br/>[Helper]"]]
    
    L_Nav["A -->|تنقل| B"]
    L_Call["A -.->|استدعاء| B"]
    L_Data["A ==>|بيانات| B"]
  end

  %% اتصالات التنقل (خط متصل -->)
  SplashActivity -->|مؤقت 4 ثوانٍ / نقر| MainActivity
  MainActivity -->|يستضيف عبر ViewPager2| ViewPagerAdapter
  ViewPagerAdapter -->|ينشئ| ConverterFragment
  ViewPagerAdapter -->|ينشئ| CalculatorFragment
  ConverterFragment -->|يعرض| CustomResultsDialog
  CalculatorFragment -->|يعرض| CustomResultsDialog
  CustomResultsDialog -->|يستخدم| ResultsAdapter

  %% استدعاء الدوال (خط منقط -.-)
  ConverterFragment -.->|يستدعي convertAll| NumberConverter
  CalculatorFragment -.->|يستدعي evaluate| ExpressionEvaluator
  ExpressionEvaluator -.->|يستدعي parse و format| NumberConverter
  NumberConverter -.->|يفوض الروماني| RomanConverter
  NumberConverter -.->|يفوض الجمل| GematriaConverter

  %% نقل البيانات (خط عريض ==>)
  NumberConverter ==>|ينشئ| ConversionResult
  ExpressionEvaluator ==>|ينشئ| CalculatorResult
  ConverterFragment ==>|يبني| ResultItem
  CalculatorFragment ==>|يبني| ResultItem
  ResultsAdapter ==>|يربط| ResultItem
  NumberConverter ==>|يستخدم| NumeralSystem

  %% تنسيق classDef
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

## 3. كيفية قراءة المخططات

### كيفية قراءة المخطط 1 (الحالات والتنقل)

| العنصر | المعنى |
|---|---|
| `[*]` | نقطة البداية (تشغيل التطبيق) أو النهاية (الخروج). |
| `مربع الحالة` | شاشة أو نافذة معروضة للمستخدم حالياً. |
| `سهم الانتقال` | حدث أو مؤقت ينقل التطبيق من حالة لأخرى. |
| `نص الانتقال` | الحدث أو الشرط المسبب للانتقال (مثل "مؤقت 4 ثوانٍ"). |

---

## 4. أسئلة للاختبار الذاتي

1. **سؤال المؤقت:** كم ثانية تعرض شاشة البدء بشكل افتراضي قبل الانتقال لـ `MainActivity`؟
2. **سؤال الهندسة المعمارية:** ما هي الفئة المساعدة المسؤولة عن حساب التعابير الرياضية مثل `XII + IX`؟
3. **سؤال السلوك:** ماذا يحدث إذا نُقر على الشاشة أثناء عرض الشعار في `SplashActivity`؟
4. **سؤال الفئات:** ما الفرق بين فئة النموذج (`ResultItem`) والفئة المساعدة (`RomanConverter`)؟
