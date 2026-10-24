# Prestige Modern UI

A Fabric client-side mod starter for **Minecraft Java 1.21.11**. It uses an original midnight-and-violet visual style rather than third-party client assets.

## Included in this first build
- Press **Right Shift** to open or close a custom click-GUI screen.
- A compact custom HUD panel while in-game.
- Vanilla inventory and container screens are not replaced.
- GitHub Actions attempts to build the mod and publish the JAR as a workflow artifact.

## Build

Requires Java 21 and Gradle. Run `gradle build` from the project directory. The JAR should be written to `build/libs/`.

## Install

Place the built mod JAR and a compatible Fabric API JAR in the Minecraft `mods` folder for Minecraft 1.21.11.

## Planned work

Main-menu and pause-menu replacements, animated transitions, blur, persistent theme settings, and working color controls are **not implemented yet**. This is an initial scaffold; the first automated build still needs to be checked before calling the JAR verified.
