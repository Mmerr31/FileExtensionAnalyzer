package com.fileanalyzer.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

/**
 * Утилитный класс для работы с файлами.
 * Предоставляет методы для чтения, копирования и анализа файлов.
 */
public final class FileUtils {

    private FileUtils() {
        // Утилитный класс не должен создавать экземпляры
    }

    /**
     * Читает заголовок файла (первые N байтов).
     *
     * @param file файл для чтения
     * @param maxBytes максимальное количество байтов для чтения
     * @return массив байтов заголовка файла
     * @throws IOException если произошла ошибка при чтении файла
     */
    public static byte[] readFileHeader(File file, int maxBytes) throws IOException {
        if (file == null || !file.exists() || !file.isFile()) {
            throw new IOException("Файл не существует или не является файлом: " + file);
        }

        byte[] allBytes = Files.readAllBytes(file.toPath());
        int bytesToRead = Math.min(allBytes.length, maxBytes);
        
        return Arrays.copyOf(allBytes, bytesToRead);
    }

    /**
     * Создает копию файла с новым расширением.
     *
     * @param sourceFile исходный файл
     * @param newExtension новое расширение (без точки)
     * @return новый файл с измененным расширением
     * @throws IOException если произошла ошибка при копировании
     */
    public static File copyFileWithNewExtension(File sourceFile, String newExtension) throws IOException {
        if (sourceFile == null || !sourceFile.exists()) {
            throw new IOException("Исходный файл не существует");
        }

        String baseName = getFileNameWithoutExtension(sourceFile.getName());
        String newFileName = baseName + "." + newExtension;
        
        File targetFile = new File(sourceFile.getParent(), newFileName);
        
        // Если файл с таким именем уже существует, добавляем суффикс
        int counter = 1;
        while (targetFile.exists()) {
            String numberedFileName = baseName + "_" + counter + "." + newExtension;
            targetFile = new File(sourceFile.getParent(), numberedFileName);
            counter++;
        }

        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return targetFile;
    }

    /**
     * Получает имя файла без расширения.
     *
     * @param fileName полное имя файла
     * @return имя файла без расширения
     */
    public static String getFileNameWithoutExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == 0) {
            return fileName;
        }

        return fileName.substring(0, lastDotIndex);
    }

    /**
     * Получает расширение файла.
     *
     * @param fileName имя файла
     * @return расширение файла (без точки) или пустую строку
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastDotIndex + 1);
    }

    /**
     * Проверяет, является ли файл допустимым для анализа.
     *
     * @param file файл для проверки
     * @return true, если файл можно анализировать
     */
    public static boolean isValidForAnalysis(File file) {
        return file != null && 
               file.exists() && 
               file.isFile() && 
               file.canRead() && 
               file.length() > 0;
    }

    /**
     * Форматирует размер файла в человекочитаемый вид.
     *
     * @param bytes размер в байтах
     * @return отформатированная строка размера
     */
    public static String formatFileSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        }
        
        String[] units = {"KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double size = bytes;
        
        while (size >= 1024 && unitIndex < units.length - 1) {
            size /= 1024;
            unitIndex++;
        }
        
        return String.format("%.1f %s", size, units[unitIndex]);
    }
}