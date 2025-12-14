package com.fileanalyzer.service;

import com.fileanalyzer.model.FileType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Тесты для сервиса восстановления файлов.
 */
class FileRestoreServiceTest {

    @Test
    void testServiceCreation() {
        FileRestoreService service = new FileRestoreService();
        assertNotNull(service);
    }

    @Test
    void testInvalidArguments() {
        FileRestoreService service = new FileRestoreService();
        FileType fileType = new FileType();
        fileType.setExtension("txt");

        assertThrows(IllegalArgumentException.class, () -> {
            service.restoreFileExtension(null, fileType);
        });
    }
}