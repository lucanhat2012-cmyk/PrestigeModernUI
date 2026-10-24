# Prestige Modern UI

An original client-side Fabric UI mod for **Minecraft Java 1.21.11**. It uses a custom midnight palette and accent themes rather than third-party client assets.

## Features

- Reworked **title screen** with themed panel and custom Singleplayer, Multiplayer, Settings, Prestige UI Settings, and Quit buttons.
- Reworked **pause screen** with themed panel, Back to Game, Settings, and Prestige UI Settings.
- Press **Right Shift** in a world to open or close the custom in-game settings panel.
- Custom HUD badge with the player's name and selected accent color.
- Five persistent accent themes: Violet, Mint, Amber, Rose, and Blue.
- HUD on/off toggle; settings are saved to `config/prestige-modern-ui.properties`.
- Three configurable panel opacity levels shared by the custom settings, title, and pause panels.
- Toggleable UI fade-in animation, with the setting saved between launches.
- One-click **Reset Defaults** control for restoring the default theme, opacity, HUD, and animation settings.
- Animated fade-in on the in-game panel.
- Vanilla inventory and container screens are intentionally left unchanged.

## Requirements

- Minecraft Java **1.21.11**
- Fabric Loader compatible with 1.21.11
- Fabric API for 1.21.11
- Java 21 (only needed to build from source)

## Install

1. Open the [GitHub Actions builds](https://github.com/lucanhat2012-cmyk/PrestigeModernUI/actions).
2. Open the latest successful **Build mod** run and download the `prestige-modern-ui` artifact.
3. Extract the artifact ZIP and place the contained mod JAR in your Minecraft `mods` folder.
4. Install Fabric API for 1.21.11 too, then launch the Fabric profile.

## Build from source

Run `gradle build --no-daemon` with Java 21. The compiled JAR is written to `build/libs/`.

## Status

The automated workflow verifies compilation and publishes a JAR artifact on successful builds. In-game testing across different resolutions and other mods is still recommended. Shader-based background blur is not included. The current animation setting controls the in-game settings panel fade-in; additional animation types and in-game testing remain future work.
