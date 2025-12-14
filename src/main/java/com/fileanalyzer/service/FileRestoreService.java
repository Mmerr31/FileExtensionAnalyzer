package com.fileanalyzer.service;

import com.fileanalyzer.model.FileType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Сервис для восстановления правильного расширения файла.
 * Переименовывает файлы, добавляя корректное расширение на основе анализа.
 */
public class FileRestoreService {

    private static final Logger logger = LogManager.getLogger(FileRestoreService.class);

    /**
     * Восстанавливает расширение файла на основе определенного типа.
     *
     * @param originalFile исходный файл
     * @param fileType определенный тип файла
     * @return новый файл с правильным расширением
     * @throws IOException если произошла ошибка при копировании/переименовании
     */
    public File restoreFileExtension(File originalFile, FileType fileType) throws IOException {
        if (originalFile == null || !originalFile.exists()) {
            throw new IllegalArgumentException("Файл не существует или равен null");
        }

        if (fileType == null || fileType.getExtension() == null) {
            throw new IllegalArgumentException("Тип файла не определен");
        }

        String extension = fileType.getExtension();
        if (extension.startsWith(".")) {
            extension = extension.substring(1);
        }

        Path originalPath = originalFile.toPath();
        String fileName = originalFile.getName();
        String newFileName;

        // Удаляем старое расширение, если есть
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0) {
            newFileName = fileName.substring(0, lastDotIndex) + "." + extension;
        } else {
            newFileName = fileName + "." + extension;
        }

        // Создаем новый путь
        Path newPath = originalPath.getParent().resolve(newFileName);

        // Копируем файл с новым именем
        Files.copy(originalPath, newPath, StandardCopyOption.REPLACE_EXISTING);

        logger.info("Расширение восстановлено: {} -> {}", originalFile.getName(), newFileName);

        return newPath.toFile();
    }

    /**
     * Создает резервную копию файла перед восстановлением.
     *
     * @param file файл для резервного копирования
     * @return файл резервной копии
     * @throws IOException если произошла ошибка при копировании
     */
    public File createBackup(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("Файл не существует");
        }

        String backupName = file.getName() + ".backup";
        Path backupPath = file.toPath().getParent().resolve(backupName);

        Files.copy(file.toPath(), backupPath, StandardCopyOption.REPLACE_EXISTING);

        logger.debug("Создана резервная копия: {}", backupName);

        return backupPath.toFile();
    }
}