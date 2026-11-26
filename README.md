<div align="center">

<img src="docs/images/icon.png" alt="Shhh Brightness Toggle Icon" width="128" height="128" />

# Shhh Brightness Toggle

**A simple client-side brightness and fullbright mod for Minecraft 1.20.1 on Minecraft Forge.**

[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-2ea44f?style=flat-square&logo=minecraft)](https://minecraft.net/)
[![Forge](https://img.shields.io/badge/Loader-Forge-orange?style=flat-square)](https://files.minecraftforge.net/)
[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)](https://adoptium.net/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)
[![GitHub](https://img.shields.io/badge/GitHub-Repository-181717?style=flat-square&logo=github)](https://github.com/rafayshhh/shhh-brightness-toggle)

<br/>

*See clearly in caves, ravines, and underwater without potion bubbles or status icons.*

</div>

---

## Supported Versions

| Minecraft Version | Mod Loader | Status |
| :--- | :--- | :--- |
| 1.20.1 | Forge | Ready for Release |

---

## What the Mod Does

- **Toggle on and off with a key**: Press B (default) to turn brightness on or off whenever you need it.
- **Step brightness up and down**: Use the Up and Down arrow keys to adjust brightness between 10% and 100% in 10% steps.
- **Color-coded actionbar feedback**: When you change brightness, an on-screen message shows the current level. The text color shifts from red at low levels, to yellow at mid levels, to green at 100%. You can turn this off in settings if you prefer plain text.
- **No potion icons or particles**: Unlike older fullbright mods that applied a fake Night Vision status effect, this mod scales the lightmap night-vision calculation directly inside Minecraft's internal `LightTexture`. That means zero potion swirls on your screen and no potion icon in your inventory or HUD.
- **Zero options.txt tampering**: The mod does not modify `options.gamma` or leave high brightness numbers written to `options.txt`. When disabled, vanilla lighting runs completely untouched on the very next frame.
- **In-game config screen**: Accessible in-game via the Forge Mods list screen ("Config" button) or by typing `/shhh` in chat.
- **Saves your settings**: Preferences are saved in your `.minecraft/config/shhh-brightness-toggle.json` file and reload automatically next time you launch the game. If the config file ever gets corrupted or emptied, the mod resets to safe defaults without crashing.
- **Client-side only**: Everything runs on your computer. It sends no network packets to servers and does not need to be installed on any server you play on.

---

## Default Controls

You can rebind all three keys in **Options -> Controls -> Key Binds**:

| Action | Default Key | Category | What it does |
| :--- | :---: | :--- | :--- |
| **Toggle Brightness** | <kbd>B</kbd> | Shhh Brightness Toggle | Turns fullbright on or off |
| **Increase Brightness** | <kbd>↑</kbd> (Up Arrow) | Shhh Brightness Toggle | Increases brightness by 10% |
| **Decrease Brightness** | <kbd>↓</kbd> (Down Arrow) | Shhh Brightness Toggle | Decreases brightness by 10% |

---

## In-Game Commands

- `/shhh`: Opens the in-game configuration GUI.
- `/shhh toggle`: Toggles fullbright directly from chat.

---

## Requirements and Installation

### Requirements
- **Minecraft**: 1.20.1
- **Minecraft Forge**: 47.1.0 or newer (tested on 47.3.0)
- **Java**: 17 or newer

### How to Install
1. Make sure you have **Minecraft Forge 1.20.1** installed.
2. Download `shhh-brightness-toggle-1.0.0+mc1.20.1-forge.jar` and drop it into your `.minecraft/mods` folder.
3. Launch Minecraft with the Forge 1.20.1 profile.

---

## Building from Source

To build from source using JDK 17+:

```cmd
gradlew.bat build
```

The built JAR file will be located at:
`build/libs/shhh-brightness-toggle-1.0.0+mc1.20.1-forge.jar`

---

## Author and Credits

- **Created by**: **RafayShhh**
  - GitHub: [@rafayshhh](https://github.com/rafayshhh)
  - Modrinth: [RafayShhh](https://modrinth.com/user/RafayShhh)

---

## License

This project is open source and available under the [MIT License](LICENSE).
Not an official Minecraft product. Not approved by or associated with Mojang or Microsoft.
