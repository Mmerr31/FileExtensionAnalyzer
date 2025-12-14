package com.fileanalyzer.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для сервиса базы данных.
 */
class DatabaseServiceTest {

    @Test
    void testDatabaseServiceInitialization() {
        DatabaseService service = new DatabaseService();
        assertNotNull(service);
        assertFalse(service.getAllFileTypes().isEmpty());
    }

    @Test
    void testFindFileType() {
        DatabaseService service = new DatabaseService();
        // Проверяем, что сервис может найти типы файлов
        assertNotNull(service.getAllFileTypes());
    }
}