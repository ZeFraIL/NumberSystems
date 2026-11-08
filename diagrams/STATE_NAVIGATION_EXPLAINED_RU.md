# Диаграмма навигации состояний и карта классов

## 1. Диаграмма 1: Навигация и состояния

```mermaid
stateDiagram-v2
    [*] --> SplashActivity : Запуск приложения

    state SplashActivity {
        [*] --> LogoDisplayState
        LogoDisplayState --> MainActivity : Таймер 4 сек / Клик по экрану
    }

    state MainActivity {
        [*] --> ConverterFragment : Вкладка 0 (Конвертер) по умолчанию

        state ConverterFragment {
            [*] --> ConverterInputState
            ConverterInputState --> CustomResultsDialog : Нажатие "All Types" / Корректный ввод
        }

        state CalculatorFragment {
            [*] --> CalculatorInputState
            CalculatorInputState --> CustomResultsDialog : Нажатие "Calculate" / Корректный ввод
        }

        ConverterFragment --> CalculatorFragment : Выбор вкладки 1
        CalculatorFragment --> ConverterFragment : Выбор вкладки 0
    }

    state CustomResultsDialog {
        [*] --> ResultsDisplayState
        ResultsDisplayState --> [*] : Нажатие "Done" или "X" / Закрытие
    }

    MainActivity --> [*] : Нажатие кнопки "Назад"
```

---

## 2. Диаграмма 2: Карта классов и связей

```mermaid
flowchart TD
  subgraph UI["Слой: Экраны и UI"]
    SplashActivity["SplashActivity<br/>[Activity]"]
    MainActivity["MainActivity<br/>[Activity]"]
    ConverterFragment("ConverterFragment<br/>[Fragment]")
    CalculatorFragment("CalculatorFragment<br/>[Fragment]")
    CustomResultsDialog{"CustomResultsDialog<br/>[Dialog]"}
    ViewPagerAdapter[/"ViewPagerAdapter<br/>[Adapter]"/]
    ResultsAdapter[/"ResultsAdapter<br/>[Adapter]"/]
  end

  subgraph HELPERS["Слой: Вспомогательные классы и движки"]
    NumberConverter[["NumberConverter<br/>[Helper]"]]
    RomanConverter[["RomanConverter<br/>[Helper]"]]
    GematriaConverter[["GematriaConverter<br/>[Helper]"]]
    ExpressionEvaluator[["ExpressionEvaluator<br/>[Helper]"]]
  end

  subgraph MODEL["Слой: Модели данных"]
    NumeralSystem["NumeralSystem<br/>[Model]"]
    ResultItem["ResultItem<br/>[Model]"]
    ConversionResult["ConversionResult<br/>[Model]"]
    CalculatorResult["CalculatorResult<br/>[Model]"]
  end

  subgraph LEGEND["Легенда"]
    L_Activity["SampleActivity<br/>[Activity]"]
    L_Fragment("SampleFragment<br/>[Fragment]")
    L_Dialog{"SampleDialog<br/>[Dialog]"}
    L_Model["SampleModel<br/>[Model]"]
    L_Adapter[/"SampleAdapter<br/>[Adapter]"/]
    L_Helper[["SampleHelper<br/>[Helper]"]]
    
    L_Nav["A -->|Переход| B"]
    L_Call["A -.->|Вызов| B"]
    L_Data["A ==>|Данные| B"]
  end

  %% Навигация (сплошная линия -->)
  SplashActivity -->|Таймер 4с / клик| MainActivity
  MainActivity -->|Содержит через ViewPager2| ViewPagerAdapter
  ViewPagerAdapter -->|Создает| ConverterFragment
  ViewPagerAdapter -->|Создает| CalculatorFragment
  ConverterFragment -->|Отображает| CustomResultsDialog
  CalculatorFragment -->|Отображает| CustomResultsDialog
  CustomResultsDialog -->|Использует| ResultsAdapter

  %% Вызовы методов (пунктир -.-)
  ConverterFragment -.->|Вызывает convertAll| NumberConverter
  CalculatorFragment -.->|Вызывает evaluate| ExpressionEvaluator
  ExpressionEvaluator -.->|Вызывает parse и format| NumberConverter
  NumberConverter -.->|Делегирует римские| RomanConverter
  NumberConverter -.->|Делегирует гематрию| GematriaConverter

  %% Передача данных (жирная линия ==>)
  NumberConverter ==>|Создает| ConversionResult
  ExpressionEvaluator ==>|Создает| CalculatorResult
  ConverterFragment ==>|Формирует| ResultItem
  CalculatorFragment ==>|Формирует| ResultItem
  ResultsAdapter ==>|Связывает| ResultItem
  NumberConverter ==>|Использует| NumeralSystem

  %% Стилизация classDef
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

## 3. Как читать диаграммы

### Как читать Диаграмму 1 (Состояния и навигация)

| Элемент | Что означает |
|---|---|
| `[*]` | Точка старта (запуск) или завершения (выход) приложения. |
| `Блок состояния` | Экран или диалог, видимый пользователю в данный момент. |
| `Стрелка перехода` | Действие пользователя или таймер, переводящий приложение в другое состояние. |
| `Метка перехода` | Событие или условие перехода (например, "Таймер 4 сек"). |
| `Составное состояние` | Главный экран-контейнер (например, `MainActivity`), содержащий внутренние состояния/вкладки. |

### Как читать Диаграмму 2 (Карта классов)

| Элемент | Что означает | Форма и цвет | Пример класса проекта |
|---|---|---|---|
| **Activity** | Экран-контейнер, инициализируемый Android OS | Прямоугольник `[ ]`, Синий | `SplashActivity`, `MainActivity` |
| **Fragment** | Переиспользуемый модуль экрана | Закругленный `( )`, Зеленый | `ConverterFragment`, `CalculatorFragment` |
| **Dialog** | Всплывающий нижний диалог | Ромб `{ }`, Фиолетовый | `CustomResultsDialog` |
| **Model / POJO** | Класс данных или Enum | Прямоугольник `[ ]`, Желтый | `ResultItem`, `NumeralSystem`, `ConversionResult` |
| **Data** | Слой хранения (БД / SharedPreferences) | Цилиндр `[( )]`, Розовый | (Н/Д — данные обрабатываются в памяти) |
| **Adapter** | Мост между данными и списком UI | Параллелограмм `[/ /]`, Оранжевый | `ViewPagerAdapter`, `ResultsAdapter` |
| **Helper / Engine** | Логика, математика, конвертация | Подпрограмма `[[ ]]`, Серый пунктир | `NumberConverter`, `RomanConverter`, `ExpressionEvaluator` |
| **System** | Встроенные классы Android Framework | Прямоугольник `[ ]`, Светло-серый пунктир | `Intent`, `Bundle`, `ViewPager2` |
| **Сплошная стрелка `-->`** | Навигация или создание | Стрелка | `SplashActivity --> MainActivity` |
| **Пунктирная стрелка `-.->`** | Вызов метода или связь | Пунктир | `ConverterFragment -.-> NumberConverter` |
| **Жирная стрелка `==>`** | Создание или передача данных | Жирная | `NumberConverter ==> ConversionResult` |

---

## 4. Подробное объяснение

### 1. Главная идея
В любой момент времени приложение находится ровно в одном состоянии (например, отображает логотип, показывает конвертер или открывает диалог с результатами). Событие (таймер или нажатие кнопки) переводит приложение в другое состояние. В теоретической информатике эта модель называется **Конечным Автоматом (Finite State Machine - FSM)**.

### 2. Бытовая аналогия
Представьте **лифт**:
- Лифт может находиться в состояниях: `Стоит на 1 этаже`, `Движется вверх`, `Двери открыты`.
- Нажатие кнопки (событие) переводит его из `Стоит` в `Движется`.
- Прибытие на этаж (условие) переводит его из `Движется` в `Двери открыты`.

### 3. Запуск приложения
1. Приложение стартует с `[*]` и открывает `SplashActivity`.
2. `SplashActivity` показывает логотип 4 секунды (4000 мс).
3. Если пользователь нажимает на экран раньше 4 секунд или по истечении таймера, происходит переход на `MainActivity`, а `SplashActivity` закрывается (`finish()`).

### 4. Внутренняя жизнь MainActivity
- `MainActivity` содержит две вкладки: `ConverterFragment` (вкладка 0) и `CalculatorFragment` (вкладка 1).
- Переключение вкладок меняет состояние между конвертером и калькулятором.
- Нажатие "All Types" в конвертере проверяет ввод и открывает `CustomResultsDialog`.
- Нажатие "Calculate" в калькуляторе вычисляет выражение и открывает `CustomResultsDialog`.
- Нажатие "Done" или "X" закрывает диалог.

### 5. Архитектура классов
- **UI:** `SplashActivity`, `MainActivity`, `ConverterFragment`, `CalculatorFragment`, `CustomResultsDialog`, `ViewPagerAdapter`, `ResultsAdapter`.
- **Helpers:** `NumberConverter`, `RomanConverter`, `GematriaConverter`, `ExpressionEvaluator` (чистая логика без UI).
- **Models:** `NumeralSystem` (enum), `ResultItem`, `ConversionResult`, `CalculatorResult`.

---

## 5. Вопросы для самопроверки

1. **Вопрос по таймеру:** Сколько секунд отображается загрузочный экран по умолчанию?
2. **Вопрос по архитектуре:** Какой класс вычисляет математические выражения вида `XII + IX`?
3. **Вопрос по поведению:** Что произойдет, если нажать на экран во время показа логотипа на `SplashActivity`?
4. **Вопрос по диапазону:** Какой допустимый диапазон чисел поддерживается приложением и почему?
5. **Вопрос по карте классов:** В чем разница между классом Модели (`ResultItem`) и классом Helper (`RomanConverter`)?
