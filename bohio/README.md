# Bohío
Shared Android library module for Rama.

## Setup

Add submodule
```bash
git submodule add https://github.com/rama-io/bohio.git bohio
git submodule update --init --recursive
```

Add in the app's `settings.gradle`:
```kotlin
include(":app")
include(":bohio")

// Activate For Release
//project(":bohio").projectDir = file("bohio")

// Activate For Bohio Development (and locate your local source)
project(":bohio").projectDir = file("../25_mod__bohio")
```

Add in the app's `app/build.gradle`:
```kotlin
dependencies {
    implementation(project(":bohio"))
}
```

Make sure the app's theme extends `Theme.Rama.Base` in `themes.xml`.
```xml
<resources>
    <style name="Theme.Teyin" parent="Theme.Rama.Base">
        <item name="bgColor">@color/bg_1</item>
    </style>
</resources>
```

For F-Droid, add `submodules: true` to the relevant build entry in the app's metadata so `git submodule update --init --recursive` runs after checkout. Keep the `bohio` repo public and avoid rewriting history on any commit a published app version's submodule pointer references.

## Maintenance

Update submodule
```bash
git submodule update --remote bohio
```
