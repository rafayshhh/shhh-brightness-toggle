<div align="center">

<img src="docs/images/icon.png" alt="Shhh Brightness Toggle Icon" width="128" height="128" />

# Shhh Brightness Toggle

**A simple client-side brightness and fullbright mod for Minecraft 1.12.2 on Minecraft Forge.**

[![Minecraft](https://img.shields.io/badge/Minecraft-1.12.2-2ea44f?style=flat-square&logo=minecraft)](https://minecraft.net/)
[![Forge](https://img.shields.io/badge/Loader-Forge-orange?style=flat-square)](https://files.minecraftforge.net/)
[![Java](https://img.shields.io/badge/Java-8-blue?style=flat-square&logo=openjdk)](https://adoptium.net/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)
[![GitHub](https://img.shields.io/badge/GitHub-Repository-181717?style=flat-square&logo=github)](https://github.com/rafayshhh/shhh-brightness-toggle)

<br/>

*See clearly in caves, ravines, and underwater without potion bubbles or status icons.*

</div>

---

## Supported Versions

| Minecraft Version | Mod Loader | Status |
| :--- | :--- | :--- |
| 1.12.2 | Forge | Released |

---

## What the Mod Does

- **Toggle on and off with a key**: Press B (default) to turn brightness on or off whenever you need it.
- **Step brightness up and down**: Use the Up and Down arrow keys to adjust brightness between 10% and 100% in 10% steps.
- **Color-coded actionbar feedback**: When you change brightness, an on-screen message appears right above your hotbar. The text color shifts from red at low levels, to yellow at mid levels, to green at 100%. You can turn this off in settings if you prefer plain text.
- **No potion icons or particles**: Operates cleanly without applying fake Night Vision status effects, ensuring zero potion swirls on your screen and no potion icon in your inventory or HUD.
- **Zero options.txt tampering**: Operates in memory and restores your vanilla brightness setting automatically on world leave, disconnect, or game shutdown.
- **In-game config screen**: Access the settings GUI via the `/shhh` command or via the Forge Mods list screen ("Config" button).
- **OptiFine Compatible**: Fully compatible with OptiFine 1.12.2 HD U and standard 1.12.2 visual enhancements.
- **Saves your settings**: Preferences are saved in `.minecraft/config/shhh-brightness-toggle.json` and recover automatically if corrupted.
- **Client-side only**: Everything runs on your computer. It sends no network packets to servers and does not need to be installed on any server you play on.

---

## Default Controls

You can rebind all three keys in **Options -> Controls**:

| Action | Default Key | Category | What it does |
| :--- | :---: | :--- | :--- |
| **Toggle Brightness** | <kbd>B</kbd> | Shhh Brightness Toggle | Turns fullbright on or off |
| **Increase Brightness** | <kbd>↑</kbd> (Up Arrow) | Shhh Brightness Toggle | Increases brightness by 10% |
| **Decrease Brightness** | <kbd>↓</kbd> (Down Arrow) | Shhh Brightness Toggle | Decreases brightness by 10% |

---

## Commands

- `/shhh` or `/shhh config`: Opens the in-game settings GUI.
- `/shhh toggle`: Toggles fullbright on or off from the chat console.

---

## Requirements and Installation

### Requirements
- **Minecraft**: 1.12.2
- **Minecraft Forge**: 14.23.5.2847 or newer
- **Java**: 8 or newer

### How to Install
1. Make sure you have **Minecraft Forge 1.12.2** installed.
2. Download `shhh-brightness-toggle-1.0.0+mc1.12.2.jar` and drop it into your `.minecraft/mods` folder.
3. Launch Minecraft with the Forge 1.12.2 profile.

---

## Compatibility and Server Rules

- **OptiFine**: Fully compatible with OptiFine 1.12.2.
- **Server Rules**: Some multiplayer servers consider fullbright an unfair advantage. Always check server rules before using this mod online. Use at your own risk.

---

## Building from Source

To build from source:

```cmd
gradlew.bat build
```

The built JAR file will be located at:
`build/libs/shhh-brightness-toggle-1.0.0+mc1.12.2.jar`

---

## Author and Credits

- **Created by**: **RafayShhh**
  - GitHub: [@rafayshhh](https://github.com/rafayshhh)
  - Modrinth: [RafayShhh](https://modrinth.com/user/RafayShhh)

---

## License

This project is open source and available under the [MIT License](LICENSE).
Not an official Minecraft product. Not approved by or associated with Mojang or Microsoft.
