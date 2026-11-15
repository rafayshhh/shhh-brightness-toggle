<div align="center">

<img src="docs/images/icon.png" alt="Shhh Brightness Toggle Icon" width="128" height="128" />

# Shhh Brightness Toggle

**A simple client-side brightness and fullbright mod for Minecraft 1.20.1 on Fabric.**

[![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-2ea44f?style=flat-square&logo=minecraft)](https://minecraft.net/)
[![Fabric](https://img.shields.io/badge/Loader-Fabric-lightgrey?style=flat-square&logo=fabric)](https://fabricmc.net/)
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
| 1.20.1 | Fabric | Released |

---

## Screenshots

### In-Game Visibility Comparison
See the difference in dark areas like abandoned mineshafts and caves:

<p align="center">
  <img src="docs/images/showcase-comparison.jpg" alt="Without Mod vs With Mod Comparison" width="850" />
  <br/>
  <em>Side-by-side comparison: Default dark cave (left) vs. Brightness enabled at 100% (right).</em>
</p>

### Settings Screen (Mod Menu)
Adjust fullbright on or off, change the level with a slider, or toggle colored actionbar messages:

<p align="center">
  <img src="docs/images/showcase-modmenu.jpg" alt="Customization via Mod Menu" width="850" />
  <br/>
  <em>In-game settings screen opened from Mod Menu.</em>
</p>

### Keybindings Menu
Rebind any of the hotkeys directly in your normal Minecraft controls:

<p align="center">
  <img src="docs/images/showcase-keybinds.jpg" alt="Customizable Controls Menu" width="850" />
  <br/>
  <em>Hotkeys in Options -> Controls -> Key Binds under the Shhh Brightness Toggle category.</em>
</p>

---

## What the Mod Does

- **Toggle on and off with a key**: Press B (default) to turn brightness on or off whenever you need it.
- **Step brightness up and down**: Use the Up and Down arrow keys to adjust brightness between 10% and 100% in 10% steps.
- **Color-coded actionbar feedback**: When you change brightness, an on-screen message shows the current level. The text color shifts from red at low levels, to yellow at mid levels, to green at 100%. You can turn this off in settings if you prefer plain text.
- **No potion icons or particles**: Unlike older fullbright mods that applied a fake Night Vision status effect, this mod scales the lightmap night-vision calculation directly inside Minecraft's internal `LightTexture`. That means zero potion swirls on your screen and no potion icon in your inventory or HUD.
- **Zero options.txt tampering**: The mod does not modify `options.gamma` or leave high brightness numbers written to `options.txt`. When disabled, vanilla lighting runs completely untouched on the very next frame.
- **In-game config screen**: If you have Mod Menu installed, you get a clean settings screen with an on/off button, a slider, and a color toggle. Mod Menu is optional; the mod works fine without it.
- **Saves your settings**: Preferences are saved in your config folder and reload automatically next time you launch the game. If the config file ever gets corrupted or emptied, the mod resets to safe defaults without crashing.
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

## Requirements and Installation

### Requirements
- **Minecraft**: 1.20.1
- **Fabric Loader**: 0.14.21 or newer
- **Fabric API**: 0.92.12+1.20.1
- **Java**: 17 (bundled with Minecraft 1.20.1 launchers)
- **Mod Menu**: (Optional) For the in-game settings screen (version 7.2.2 for 1.20.1)

### How to Install
1. Make sure you have **[Fabric Loader](https://fabricmc.net/use/)** installed for Minecraft 1.20.1.
2. Download **[Fabric API](https://modrinth.com/mod/fabric-api)** for 1.20.1 and drop the `.jar` file into your `.minecraft/mods` folder.
3. Download `shhh-brightness-toggle-1.0.0+mc1.20.1.jar` from **[GitHub Releases](https://github.com/rafayshhh/shhh-brightness-toggle/releases)**, **[CurseForge](https://www.curseforge.com/minecraft/mc-mods/shhh-brightness-toggle)**, or **[Modrinth](https://modrinth.com/mod/shhh-brightness-toggle)** and put it into `.minecraft/mods`.
4. (Optional) Put **[Mod Menu](https://modrinth.com/mod/modmenu)** into `.minecraft/mods` if you want to open the config screen in-game.

---

## Config File

Your settings are saved in `.minecraft/config/shhh-brightness-toggle.json`:

```json
{
  "enabled": true,
  "level": 1.0,
  "coloredText": true
}
```

- `enabled` (true / false): Whether fullbright is on when you load into a world.
- `level` (0.1 to 1.0): Brightness level from 10% to 100%.
- `coloredText` (true / false): Whether actionbar messages and the settings slider use color-coded text.

---

## Compatibility and Server Rules

- **Shader Packs (Iris / OptiFine)**: Fullbright does not work while an external shader pack is active. Shaders replace the vanilla lightmap pipeline with custom GLSL lighting passes. When shader packs are disabled, fullbright works normally.
- **Sodium**: Sodium replaces vanilla's lightmap generation with its own optimized pipeline. Shhh Brightness Toggle uses non-destructive SpongePowered Mixin `@Redirect` hooks with `require = 0`, ensuring it will never crash if Sodium or another custom renderer is installed; however, fullbright does not take effect when Sodium's custom lighting pipeline takes over.
- **Server Rules**: Some multiplayer servers consider fullbright an unfair advantage. Always check server rules before using this mod online. Use at your own risk.

---

## Building from Source

If you want to build the mod from source, use JDK 17 (or newer with Java 17 release target).

**Windows (Command Prompt):**
```cmd
gradlew.bat build
```

**Linux / macOS:**
```bash
./gradlew build
```

The built JAR file will be located at:
`build/libs/shhh-brightness-toggle-1.0.0+mc1.20.1.jar`

---

## Support

Need help, have a question, or found a bug? Please open an issue on our [GitHub Issues](https://github.com/rafayshhh/shhh-brightness-toggle/issues) page.

---

## Bug Reports and Feedback

- Report bugs: [Open a Bug Report](https://github.com/rafayshhh/shhh-brightness-toggle/issues/new?template=bug_report.yml)
- Suggest features: [Open a Feature Request](https://github.com/rafayshhh/shhh-brightness-toggle/issues/new?template=feature_request.yml)
- Source code: [GitHub Repository](https://github.com/rafayshhh/shhh-brightness-toggle)

---

## Author and Credits

- **Created by**: **RafayShhh**
  - GitHub: [@rafayshhh](https://github.com/rafayshhh)
  - Modrinth: [RafayShhh](https://modrinth.com/user/RafayShhh)

---

## License

This project is open source and available under the [MIT License](LICENSE).
Not an official Minecraft product. Not approved by or associated with Mojang or Microsoft.
