# Shhh Brightness Toggle 1.0.0 (Minecraft 1.8.9 Forge) Release Notes

Port of Shhh Brightness Toggle to Minecraft 1.8.9 on Minecraft Forge.

## Features
- Fullbright Toggle: Press B (default) to turn brightness on and off while playing.
- Brightness Level Stepping: Use the Up Arrow and Down Arrow keys (default) to increase or decrease brightness between 10% and 100% in 10% steps.
- Clean In-Memory Gamma: Scales brightness in memory without fake Night Vision effects, completely avoiding potion swirls, bubbles, or inventory HUD status icons.
- Zero Options Tampering: Does not touch or overwrite vanilla `options.gamma` in `options.txt`. Vanilla gamma is safely restored on world leave, disconnect, and game exit.
- Dynamic Color Feedback: Actionbar messages and the settings slider display color feedback that changes based on your brightness level (red at 10%, yellow at mid levels, green at 100%). This can be turned off in the settings if you prefer plain text.
- In-Game Settings Screen: An in-game configuration menu accessible via `/shhh` or via the Forge Mods list ("Config" button).
- In-Game Command: Use `/shhh` to open settings or `/shhh toggle` to toggle directly from chat.
- OptiFine Compatible: 100% compatible with OptiFine 1.8.9 HD U M5 and standard 1.8.9 enhancements.
- Configuration Persistence: Settings are saved to `.minecraft/config/shhh-brightness-toggle.json` and automatically reset to safe defaults if the file is missing, empty, or corrupted.
- Client-Side Only: The mod runs entirely on your client and does not need to be installed on servers.

## Requirements
- Minecraft: 1.8.9
- Minecraft Forge: 11.15.1.2318 or newer
- Java: 8 or newer

## Server Rules Warning
Some multiplayer servers do not allow fullbright or brightness adjustment mods. Check your server's rules before using this mod online. Use at your own risk.

## Support and Feedback
- Report bugs or request features: https://github.com/rafayshhh/shhh-brightness-toggle/issues
- Source code: https://github.com/rafayshhh/shhh-brightness-toggle
