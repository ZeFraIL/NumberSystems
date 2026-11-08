# بنية وهندسة التطبيق: أنظمة العد (Number Systems)

---

## 1. الصورة الكبيرة

تطبيق "أنظمة العد" هو أداة رقمية تتيح التحويل السريع للأرقام وحساب التعابير الرياضية بين 6 أنظمة عد مختلفة (الثنائي، الثماني، العشري، الست عشري، الأرقام الرومانية، وحساب الجمل العبري). يتكون التطبيق من ثلاثة أجزاء رئيسية: شاشات واجهة المستخدم (UI)، محرك التحويل والحساب (Logic)، ونماذج البيانات (Data).

**تشبيه من الحياة الواقعية — مطعم حديث:**
تخيل التطبيق كمطعم:
- **النادل (شاشات الواجهة):** يأخذ الطلب من الزبون (إدخال رقم أو تعبير) ويقدم له الطبق المنسق في نافذة منزلقة في النهاية.
- **المطبخ والطاهي (محرك التحويل والحساب):** يأخذ المدخلات، يتحقق من صحتها، ويعد النتائج بجميع الصيغ.
- **أطباق التقديم وقائمة الطعام (نماذج البيانات والمحولات):** تحفظ البيانات منسقة بشكل نظيف ويسهل قراءتها.
هذا الفصل يمنع الفوضى، يحافظ على الترتيب، ويسمح بتعديل منطق المطبخ دون تخريب تصميم صالة الطعام.

---

## 2. نظرة عامة على المجلدات والملفات

المشروع مقسم إلى حزم وملفات منظمة:

### 📁 الحزمة الرئيسية (`zeev.fraiman.numbersystems`)

- **مكونات الشاشات (Activities & Fragments):**
  - `SplashActivity`: شاشة البدء التي تعرض الشعار لمدة 4 ثوانٍ قبل الانتقال للشاشة الرئيسية.
  - `MainActivity`: النشاط الرئيسي المستضيف لتبويبات التنقل.
  - `ConverterFragment`: شاشة محول الأرقام ("All Types").
  - `CalculatorFragment`: شاشة حاسبة التعابير الرياضية.

- **مكونات الواجهة والنوافذ (Adapters & Dialogs):**
  - `ViewPagerAdapter`: محول التبويبات لإدارة الصفحات داخل `ViewPager2`.
  - `CustomResultsDialog`: نافذة نتائج منزلقة حديثة (`BottomSheetDialog`) لعرض النتائج.
  - `ResultsAdapter`: محول RecyclerView يربط بطاقات النتائج مع زر النسخ بنقرة واحدة.

- **نماذج البيانات (Models & Enums):**
  - `NumeralSystem`: Enum يحدد أنظمة العد الـ 6 المدعومة.
  - `ResultItem`: نموذج بيانات يحمل زوج (اسم النظام + القيمة المحولة).
  - `ConversionResult`: نموذج يحفظ نتائج التحويل لجميع الأنظمة الـ 6.
  - `CalculatorResult`: نموذج يحفظ التعابير ونتائج الحسابات لجميع الأنظمة الـ 6.

- **محركات المنطق والتحويل (Engine / Converters):**
  - `NumberConverter`: المحرك المركزي الذي يحلل المدخلات إلى رقم عشري (الأساس 10) وينسقه لجميع الأنظمة.
  - `RomanConverter`: محول ومحلل مخصص للأرقام الرومانية (1–3999).
  - `GematriaConverter`: محول ومحلل مخصص لحساب الجمل العبري (1–3999).
  - `ExpressionEvaluator`: محرك حساب التعابير الرياضية (`+`, `-`, `*`).

### 📁 مجلد الموارد (`res/`)
- `res/layout/`: ملفات تصميم XML للشاشات والنوافذ والبطاقات.
- `res/values/strings.xml`: ملف النصوص والرسائل باللغة الإنجليزية.
- `res/drawable/`: صورة شعار التطبيق (`logo.png`).

---

## 3. العلاقات بين الأجزاء

المخطط أدناه يوضح كيفية تدفق البيانات من مدخلات المستخدم إلى عرض النتائج:

```
[المستخدم يدخل البيانات ويضغط الزر]
                │
                ▼
   [ConverterFragment / CalculatorFragment]
                │ (التحقق المبدئي)
                ▼
        [NumberConverter]  ◄──►  [RomanConverter / GematriaConverter]
                │ (التحويل إلى الأساس العشري وتنسيق النتائج)
                ▼
   [ConversionResult / CalculatorResult]
                │ (إنشاء قائمة ResultItem)
                ▼
      [CustomResultsDialog]  ──►  [ResultsAdapter]  ──►  [البطاقات على الشاشة]
```

**تسلسل الخطوات:**
1. يدخل المستخدم الرقم في `ConverterFragment` ويضغط "All Types".
2. يستدعي الفئة `NumberConverter.convertAll()`.
3. يحلل `NumberConverter` المدخلات إلى رقم عشري وسيط `long`.
4. يحول المحرك الرقم العشري إلى جميع الأنظمة الـ 6 ويرجع `ConversionResult`.
5. ينشئ الكائن قائمة من `ResultItem` ويستدعي `CustomResultsDialog.show()`.
6. تفتح النافذة وتعرض البطاقات المنسقة عبر `ResultsAdapter`.

---

## 4. مثال من المشروع: تحويل رقم روماني

سيناريو حيث يختار المستخدم النظام الروماني، يدخل `XII` ويضغط "All Types":

1. **معالجة النقر في `ConverterFragment`:**
```java
private void performConversion() {
    String inputText = binding.etInputNumber.getText().toString(); // "XII"
    NumeralSystem selectedSystem = getSelectedSystem(); // ROMAN

    ConversionResult result = NumberConverter.convertAll(inputText, selectedSystem);
    // ...
}
```

2. **التحليل في `NumberConverter` و `RomanConverter`:**
```java
// داخل RomanConverter.java:
public static long parse(String input) {
    // يحلل "XII" إلى الرقم العشري 12
    // يتحقق من الصحة والحدود [1..3999]
    return 12;
}
```

3. **التحويل وعرض النتائج:**
```java
// داخل NumberConverter.java:
for (NumeralSystem targetSystem : NumeralSystem.values()) {
    result.put(targetSystem, formatFromDecimal(12, targetSystem));
}
// النتيجة: BIN="1100", DEC="12", HEX="C", ROMAN="XII", GEMATRIA="י'"...

// العرض في CustomResultsDialog:
CustomResultsDialog.show(requireContext(), "Results in all systems:", subtitle, items);
```

---

## 5. قاموس مصطلحات قصير

- **Activity:** الشاشة الرئيسية أو النافذة في تطبيق أندرويد.
- **Fragment:** جزء واجهة مستقل وقابل للاستخدام داخل Activity.
- **Adapter:** مكون ينظم البيانات ويعرضها داخل عنصر UI (مثل RecyclerView).
- **View Binding:** آلية توفر وصولاً آمناً ومباشراً لعناصر الواجهة من كود Java.
- **BottomSheetDialog:** نافذة منزلقة تظهر من أسفل الشاشة.
- **Enum:** نوع بيانات يحدد مجموعة ثابتة من القيم (أنظمة العد الـ 6).
- **Intermediate Representation (IR):** نمط معماري يحول المدخلات أولاً إلى قاعدة مركزية (عشرية) لتسهيل التحويلات المتعددة.
