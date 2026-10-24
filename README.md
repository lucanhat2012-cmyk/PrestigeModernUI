# Prestige Modern UI

An original client-side Fabric UI mod for **Minecraft Java 1.21.11**. The visual style is a custom midnight interface with configurable accent colors; it does not reuse proprietary client assets.

## Current implementation

- **Right Shift** opens/closes the custom in-game panel.
- Custom HUD badge with a matching accent color.
- Five persistent accent themes: Violet, Mint, Amber, Rose, and Blue.
- HUD on/off toggle in the panel; settings are saved to `config/prestige-modern-ui.properties`.
- Animated fade-in when the panel opens.
- Vanilla inventory and container screens remain untouched.
- GitHub Actions compiles the mod and uploads a JAR artifact when the build succeeds.

## Requirements

- Minecraft Java **1.21.11**
- Fabric Loader compatible with 1.21.11
- Fabric API for 1.21.11
- Java 21 (only needed to build from source)

## Install

1. Open the latest successful build at [GitHub Actions](https://github.com/lucanhat2012-cmyk/PrestigeModernUI/actions).
2. Open the completed **Build mod** run and download the `prestige-modern-ui` artifact.
3. Extract the downloaded artifact ZIP and put the contained mod JAR in your Minecraft `mods` folder.
4. Ensure Fabric API for 1.21.11 is also installed, then launch the Fabric profile.

## Build from source

Run `gradle build --no-daemon` with Java 21. The compiled JAR is written to `build/libs/`.

## Scope and status

This is an actively developed initial version, not a full client replacement. Main-menu and pause-menu redesigns, blur, and additional settings controls are not included yet. The build workflow is the source of truth for whether the current revision compiles successfully.
