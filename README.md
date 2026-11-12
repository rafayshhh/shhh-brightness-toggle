<div align="center">

# ☀️ Shhh Brightness Toggle

**A lightweight client-side brightness & fullbright mod for Minecraft 26.3 on Fabric.**

[![Minecraft](https://img.shields.io/badge/Minecraft-26.3-2ea44f?style=flat-square&logo=minecraft)](https://minecraft.net/)
[![Fabric](https://img.shields.io/badge/Loader-Fabric-lightgrey?style=flat-square&logo=fabric)](https://fabricmc.net/)
[![Java](https://img.shields.io/badge/Java-25-orange?style=flat-square&logo=openjdk)](https://adoptium.net/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](LICENSE)
[![Modrinth](https://img.shields.io/badge/Download-Modrinth-00AF5C?style=flat-square&logo=modrinth)](https://modrinth.com/mod/shhh-brightness-toggle)
[![GitHub](https://img.shields.io/badge/GitHub-Repository-181717?style=flat-square&logo=github)](https://github.com/RafayShhh/shhh-brightness-toggle)

<br/>

*Adjust world brightness smoothly with dynamic color feedback, without potion particles or HUD status icons.*

</div>

---

## ✨ Features

- 💡 **Instant Fullbright Toggle**: Toggle maximum visibility with a single keypress (Default: `B`).
- 🎚️ **Fine-Grained Level Stepping**: Adjust brightness from 10% to 100% in 10% steps using `Up Arrow` and `Down Arrow`.
- 🎨 **Dynamic Color Feedback**: On-screen messages and the settings slider show dynamic color feedback (red at low brightness to green at high brightness).
- 🛡️ **No Fake Potions**: Uses Minecraft 26.3's native lightmap shaders directly. No Night Vision potion icons, no swirling particles, and no blindness flashing.
- 🔄 **Safe Gamma Restoration**: Remembers your original vanilla brightness and restores it when toggling off, disconnecting from servers, leaving worlds, or closing the game.
- ⚙️ **In-Game Settings Screen**: Accessible through **Mod Menu** (or standard controls), providing slider adjustments, toggles, and color options.
- 💾 **Robust Config**: Stores preferences in `.minecraft/config/shhh-brightness-toggle.json` with self-healing recovery and dirty-checked saves.
- ⚡ **Client-Side Only**: Runs on your client. Does not require server installation and sends no network packets.

---

## ⌨️ Default Keybindings

All keybindings can be rebound in **Options ➔ Controls ➔ Key Binds**:

| Action | Default Key | Category | Description |
| :--- | :---: | :--- | :--- |
| **Toggle Brightness** | <kbd>B</kbd> | Shhh Brightness Toggle | Turns fullbright ON or OFF |
| **Increase Brightness** | <kbd>↑</kbd> *(Up Arrow)* | Shhh Brightness Toggle | Increases brightness level by +10% |
| **Decrease Brightness** | <kbd>↓</kbd> *(Down Arrow)* | Shhh Brightness Toggle | Decreases brightness level by -10% |

---

## 📦 Installation

1. Make sure you have **[Fabric Loader](https://fabricmc.net/use/)** installed for Minecraft **26.3**.
2. Download and install **[Fabric API](https://modrinth.com/mod/fabric-api)** for Minecraft 26.3 into your `.minecraft/mods` folder.
3. Download `shhh-brightness-toggle-1.0.0+mc26.3.jar` from **[Modrinth](https://modrinth.com/mod/shhh-brightness-toggle)** or **[GitHub Releases](https://github.com/RafayShhh/shhh-brightness-toggle/releases)** and place it into your `.minecraft/mods` folder.
4. *(Optional)* Install **[Mod Menu](https://modrinth.com/mod/modmenu)** to configure settings via an in-game GUI.

---

## 🛠️ Configuration

Settings are saved automatically to `.minecraft/config/shhh-brightness-toggle.json`:

```json
{
  "enabled": true,
  "level": 1.0,
  "coloredText": true
}
```

- `enabled` (*boolean*): Whether fullbright is active.
- `level` (*float*): Brightness strength from `0.1` (10%) to `1.0` (100%).
- `coloredText` (*boolean*): Enables dynamic color feedback on overlay notifications and GUI labels.

---

## 🧩 Compatibility & Server Notice

- **Compatibility**: Designed to be compatible with Sodium and Iris, but not yet fully tested with all mods. Please report issues on GitHub.
- **Server Notice**: Some servers do not allow fullbright. Check the server rules before using this mod. Use at your own risk.

---

## ⚠️ Known Risks

This mod hooks into Minecraft's internal rendering class (`LightmapRenderStateExtractor`). In future Minecraft updates, Mojang may rename, refactor, or replace this internal class. Because of this, each new Minecraft version requires testing and may require a mod update to remain functional.

---

## 🔨 Building from Source

To compile the mod yourself:

1. Clone this repository:
   ```cmd
   git clone https://github.com/RafayShhh/shhh-brightness-toggle.git
   cd shhh-brightness-toggle
   ```
2. Build with Gradle using Java 25:
   ```cmd
   set "JAVA_HOME=C:\Program Files\Java\jdk-25.0.5.0.3"&& gradlew.bat build
   ```
3. Your compiled artifact will be located at:
   `build/libs/shhh-brightness-toggle-1.0.0+mc26.3.jar`

---

## 📄 License & Legal

This project is licensed under the [MIT License](LICENSE) &copy; 2026 **RafayShhh**.

**NOT AN OFFICIAL MINECRAFT PRODUCT. NOT APPROVED BY OR ASSOCIATED WITH MOJANG.**
