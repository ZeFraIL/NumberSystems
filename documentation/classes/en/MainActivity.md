# Detailed Class Description: MainActivity

#### 1. General Information
- **Class Name:** `MainActivity`
- **Type:** Activity (Main Activity Container)
- **Role:** Serves as the primary host container for the two main application tabs: "Converter" and "Calculator".
- **Interactions:** Launched by `SplashActivity`, manages `ViewPager2` and `TabLayout` to render `ConverterFragment` and `CalculatorFragment`.

#### 2. Variables (Fields)

| Name | Type | Purpose | Usage Location |
|------|------|---------|----------------|
| `binding` | `ActivityMainBinding` | ViewBinding instance for accessing UI layout elements | Used across all activity methods |

#### 3. Class Methods

**Method Name:** `onCreate`
- **Scope:** `protected`
- **Return Value:** `void`
- **Parameters:** `savedInstanceState` (`Bundle`)
- **Details:** Inflates `activity_main.xml`, configures window insets, instantiates `ViewPagerAdapter`, binds it to `viewPager`, and attaches `TabLayoutMediator` for tab setup.

#### 4. Lifecycle Methods
- `onCreate()`: Initializes layout, adapter, and tab mediators.

#### 5. UI Interaction
- `MaterialToolbar`: App top bar displaying app title.
- `TabLayout`: Tabs for switching between Converter and Calculator.
- `ViewPager2`: Swipeable container for fragments.

#### 6. Component Interactions
- Hosts `ConverterFragment` and `CalculatorFragment` via `ViewPagerAdapter`.

#### 7. Overall Logic & Usage Scenario
Main screen providing top tab navigation and horizontal swipe gestures between converter and calculator features.

#### 8. Simplest Terms
**Real-World Analogy:** Like a 2-tab binder. `MainActivity` is the binder holding both pages together so you can easily flip between them.
