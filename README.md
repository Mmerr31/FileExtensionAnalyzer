# File Analyzer - Определение и восстановление расширений файлов

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://www.oracle.com/java/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17-blue.svg)](https://openjfx.io/)
[![Gradle](https://img.shields.io/badge/Gradle-8.14-green.svg)](https://gradle.org/)

## 🔗 GitHub Repository
**Ссылка на проект:** [https://github.com/Mmerr31/FileAnalyzer](https://github.com/Mmerr31/FileAnalyzer)

## Описание проекта
Программа для определения типа файла по его содержимому и восстановления утерянного расширения.
Использует базу данных типов файлов, их расширений и "магических чисел".

## Функциональность
- Определение типа файла по содержимому
- Восстановление правильного расширения
- Графический интерфейс пользователя (JavaFX)
- Логирование в консоль и файл
- Unit-тестирование

## Поддерживаемые форматы файлов
### 📸 Изображения:
- **PNG** (.png) - Portable Network Graphics
- **JPEG** (.jpg, .jpeg) - JPEG изображения  
- **GIF** (.gif) - Graphics Interchange Format

### 📄 Документы:
- **PDF** (.pdf) - Adobe Portable Document Format
- **DOCX** (.docx) - Microsoft Word Document
- **TXT** (.txt) - Текстовые файлы

### 🎵 Аудио:
- **MP3** (.mp3) - MPEG-1 Audio Layer III

### 📦 Архивы:
- **ZIP** (.zip) - ZIP архивы

**Всего поддерживается: 9 типов файлов**

## Технологии
- Java 11+
- JavaFX 17
- Gradle
- JUnit 5
- Log4j 2
- Jackson (JSON обработка)

## Сборка и запуск

### Быстрый запуск (Windows):
```cmd
.\run.bat
```

### Запуск через Gradle (рекомендуется):
```bash
# Windows PowerShell
.\gradlew.bat run

# Linux/Mac
./gradlew run
```

### Создание JAR файлов и архивов:
```bash
# Создание JAR с зависимостями (для запуска)
.\gradlew.bat fatJar

# Создание JAR с исходниками
.\gradlew.bat sourcesJar

# Создание ZIP архива всего проекта
.\gradlew.bat projectArchive

# Файлы будут созданы в:
# build/libs/FileAnalyzerProject-1.0-SNAPSHOT-all.jar (исполняемый)
# build/libs/FileAnalyzerProject-1.0-SNAPSHOT-sources.jar (исходники)
# build/distributions/FileAnalyzerProject-1.0-SNAPSHOT-project.zip (весь проект)
```

### Другие команды:
```bash
# Сборка проекта
.\gradlew.bat build

# Запуск тестов
.\gradlew.bat test

# Генерация JavaDoc
.\gradlew.bat javadoc