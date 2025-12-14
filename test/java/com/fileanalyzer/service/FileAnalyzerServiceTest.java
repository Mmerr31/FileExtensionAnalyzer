package com.fileanalyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для сервиса анализа файлов.
 */
class FileAnalyzerServiceTest {

    private DatabaseService databaseService;
    private FileAnalyzerService fileAnalyzerService;

    @BeforeEach
    void setUp() {
        databaseService = new DatabaseService();
        fileAnalyzerService = new FileAnalyzerService(databaseService);
    }

    @Test
    void testServiceInitialization() {
        assertNotNull(fileAnalyzerService);
        assertNotNull(databaseService);
    }

    @Test
    void testFileTypeDeterminable() {
        // Тест проверяет базовую функциональность
        assertTrue(fileAnalyzerService.isFileTypeDeterminable(null));
    }
}