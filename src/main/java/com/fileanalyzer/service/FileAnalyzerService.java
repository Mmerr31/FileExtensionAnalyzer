package com.fileanalyzer.service;

import com.fileanalyzer.model.FileType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * Сервис для анализа файлов и определения их типа по содержимому.
 * Использует базу магических чисел для идентификации форматов файлов.
 */
public class FileAnalyzerService {

    private static final Logger logger = LogManager.getLogger(FileAnalyzerService.class);
    private final DatabaseService databaseService;

    /**
     * Конструктор сервиса анализа файлов.
     *
     * @param databaseService сервис базы данных типов файлов
     */
    public FileAnalyzerService(DatabaseService databaseService) {
        this.databaseService = databaseService;
        logger.info("FileAnalyzerService инициализирован");
    }

    /**
     * Анализирует файл и определяет его тип по магическим числам.
     *
     * @param file файл для анализа
     * @return Optional с определенным типом файла, если удалось определить
     * @throws IOException если произошла ошибка при чтении файла
     */
    public Optional<FileType> analyzeFile(File file) throws IOException {
        if (file == null || !file.exists()) {
            logger.warn("Попытка анализа несуществующего файла");
            return Optional.empty();
        }

        logger.debug("Начинаю анализ файла: {}", file.getName());

        // Читаем первые 20 байт файла для анализа
        byte[] fileHeader = Files.readAllBytes(file.toPath());
        int bytesToRead = Math.min(fileHeader.length, 20);

        // Ищем совпадение в базе данных
        return databaseService.findFileTypeByMagicNumbers(fileHeader, bytesToRead);
    }

    /**
     * Определяет, является ли файл определенного типа.
     * Временная заглушка для демонстрации.
     *
     * @param file файл для проверки
     * @return true, если файл определенного типа
     */
    public boolean isFileTypeDeterminable(File file) {
        // Временная реализация
        return file != null && file.exists() && file.length() > 0;
    }
}