# Detailed Class Description: SplashActivity

#### 1. General Information
- **Class Name:** `SplashActivity`
- **Type:** Activity (Splash / Launcher Screen)
- **Role:** Displays the splash logo (`logo.png`) for 4 seconds upon app startup, then navigates to `MainActivity`. Clicking anywhere on the screen immediately skips the timer.
- **Interactions:** Launched by the Android system via `MAIN` and `LAUNCHER` intent-filters, then launches `MainActivity` using an `Intent`.

#### 2. Variables (Fields)

| Name | Type | Purpose | Usage Location |
|------|------|---------|----------------|
| `SPLASH_DELAY_MS` | `long` (static final) | Defines splash delay in milliseconds (4000ms = 4 seconds) | Used in `onCreate()` for timer scheduling |
| `handler` | `Handler` | Handler object for posting tasks on the main UI thread | Used for posting and cancelling `navigateRunnable` |
| `navigateRunnable` | `Runnable` | Runnable task containing navigation logic to `MainActivity` | Executed by Handler and cleaned up in `onDestroy()` |

#### 3. Class Methods

**Method Name:** `onCreate`
- **Scope:** `protected`
- **Return Value:** `void`
- **Parameters:** `savedInstanceState` (`Bundle`)
- **Details:** Enables edge-to-edge UI, inflates `activity_splash.xml` via ViewBinding, schedules `navigateRunnable` with `handler.postDelayed`, and sets click listener for fast navigation.

**Method Name:** `onDestroy`
- **Scope:** `protected`
- **Return Value:** `void`
- **Parameters:** None
- **Details:** Cancels pending Handler callbacks via `handler.removeCallbacks(navigateRunnable)` to prevent memory leaks if the user exits early.

#### 4. Lifecycle Methods
- `onCreate()`: UI setup and timer initialization.
- `onDestroy()`: Handler callback cleanup.

#### 5. UI Interaction
- `FrameLayout` container (`splashContainer`) holding `ImageView` (`ivLogo`). Clicking `splashContainer` skips the delay.

#### 6. Component Interactions
- Launches `MainActivity` via `startActivity(new Intent(SplashActivity.this, MainActivity.class))` and calls `finish()`.

#### 7. Overall Logic & Usage Scenario
App opens, shows logo for 4 seconds (or until tapped), then launches `MainActivity` and destroys itself.

#### 8. Simplest Terms
**Real-World Analogy:** Like a storefront sign. You look at the sign for 4 seconds (or push the door to enter immediately), then walk inside and close the door behind you.
