# Contributing to Shhh Brightness Toggle

Thank you for your interest in improving **Shhh Brightness Toggle**!

## How You Can Contribute
- **Bug Reports**: If you find an issue or crash, please submit a bug report via GitHub Issues with detailed reproduction steps and logs.
- **Feature Suggestions**: Have an idea to improve the mod? Open a feature request.
- **Pull Requests**: Code contributions are welcome!

## Development Guidelines
1. **Target Version**: Minecraft 26.3, Java 25, Fabric Loader 0.20+.
2. **Client-Side Only**: All logic must remain client-side (`src/client/java`). Never send network packets to servers or require server installation.
3. **Compatibility**: Avoid destructive mixin cancels or overwrites. Ensure full compatibility with Sodium, Iris, and standard Fabric mods.
4. **Style**: Keep the codebase clean, well-commented, and lightweight.

## Building from Source
```cmd
set "JAVA_HOME=C:\Program Files\Java\jdk-25.0.5.0.3"&& gradlew.bat build
```
The compiled JAR will be located in `build/libs/`.
