# Contributing to Shhh Brightness Toggle

Thank you for your interest in improving Shhh Brightness Toggle.

## Ways to Contribute
- Bug Reports: If you run into a bug or crash, please open an issue on GitHub with steps to reproduce it and a log if possible.
- Feature Requests: If you have an idea for an improvement, feel free to open a feature request.
- Pull Requests: Bug fixes and improvements are welcome.

## Development Guidelines
1. Target: Minecraft 26.3, Java 25, Fabric Loader 0.20+.
2. Client-Side Only: All code belongs in the client source set (`src/client/java`). Never send network packets to servers or require server-side code.
3. Compatibility: Keep mixin injections minimal and non-destructive. Do not break compatibility with mods like Sodium or Iris.
4. Clean Code: Keep the codebase readable, well-commented, and lightweight.

## Building from Source
Use JDK 25. Set `JAVA_HOME` to your JDK 25 directory, then run the Gradle build.

**Windows (Command Prompt):**
```cmd
set "JAVA_HOME=C:\Path\To\Your\JDK-25"
gradlew.bat build
```

**Linux / macOS:**
```bash
export JAVA_HOME=/path/to/your/jdk-25
./gradlew build
```

The compiled JAR will be in `build/libs/`.
