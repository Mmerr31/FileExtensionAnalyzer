package com.fileanalyzer.service;

import com.fileanalyzer.model.FileType;
import com.fileanalyzer.model.MagicNumber;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с базой данных типов файлов.
 * Загружает и управляет информацией о магических числах различных форматов.
 */
public class DatabaseService {

    private static final Logger logger = LogManager.getLogger(DatabaseService.class);
    private List<FileType> fileTypes;
    private final ObjectMapper objectMapper;

    /**
     * Конструктор сервиса базы данных.
     * Загружает базу типов файлов из JSON файла.
     */
    public DatabaseService() {
        this.objectMapper = new ObjectMapper();
        this.fileTypes = new ArrayList<>();
        loadFileTypes();
        logger.info("DatabaseService инициализирован. Загружено {} типов файлов", fileTypes.size());
    }

    /**
     * Загружает типы файлов из JSON файла в ресурсах.
     */
    private void loadFileTypes() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("filetypes.json")) {
            if (inputStream != null) {
                FileType[] typesArray = objectMapper.readValue(inputStream, FileType[].class);
                fileTypes = List.of(typesArray);
                logger.debug("Успешно загружены типы файлов из JSON");
            } else {
                logger.error("Не удалось найти filetypes.json в ресурсах");
                loadDefaultFileTypes();
            }
        } catch (Exception e) {
            logger.error("Ошибка при загрузке типов файлов из JSON", e);
            loadDefaultFileTypes();
        }
    }

    /**
     * Загружает стандартные типы файлов в случае ошибки загрузки из JSON.
     */
    private void loadDefaultFileTypes() {
        fileTypes = new ArrayList<>();

        // Добавляем несколько стандартных типов
        FileType pngType = new FileType();
        pngType.setExtension("png");
        pngType.setDescription("Portable Network Graphics");

        MagicNumber pngMagic = new MagicNumber();
        pngMagic.setOffset(0);
        pngMagic.setHex("89 50 4E 47 0D 0A 1A 0A");
        pngMagic.setBytes(hexStringToByteArray("89504E470D0A1A0A"));

        pngType.setMagicNumbers(List.of(pngMagic));
        fileTypes.add(pngType);

        logger.warn("Используются типы файлов по умолчанию");
    }

    /**
     * Ищет тип файла по магическим числам в заголовке файла.
     * Использует Stream API для обработки коллекций.
     *
     * @param fileHeader байты заголовка файла
     * @param length количество байт для анализа
     * @return Optional с найденным типом файла
     */
    public Optional<FileType> findFileTypeByMagicNumbers(byte[] fileHeader, int length) {
        if (fileHeader == null || length <= 0) {
            return Optional.empty();
        }

        // Используем Stream API для поиска (требование задания)
        Optional<FileType> result = fileTypes.stream()
                .filter(FileType::hasMagicNumbers)
                .filter(fileType -> fileType.getMagicNumbers().stream()
                        .anyMatch(magic -> matchesMagicNumber(fileHeader, length, magic)))
                .findFirst();

        // Если не найден тип по магическим числам, возвращаем TXT как fallback
        if (result.isEmpty()) {
            return fileTypes.stream()
                    .filter(fileType -> "txt".equals(fileType.getExtension()))
                    .findFirst();
        }

        return result;
    }

    /**
     * Проверяет, совпадает ли магическое число с заголовком файла.
     *
     * @param fileHeader заголовок файла
     * @param length длина для проверки
     * @param magic магическое число для сравнения
     * @return true, если совпадает
     */
    private boolean matchesMagicNumber(byte[] fileHeader, int length, MagicNumber magic) {
        try {
            int offset = magic.getOffset();
            byte[] magicBytes = magic.getBytes();

            if (offset + magicBytes.length > length) {
                return false;
            }

            for (int i = 0; i < magicBytes.length; i++) {
                if (fileHeader[offset + i] != magicBytes[i]) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.warn("Ошибка при проверке магического числа", e);
            return false;
        }
    }

    /**
     * Конвертирует HEX строку в массив байт.
     *
     * @param hexString HEX строка (без пробелов)
     * @return массив байт
     */
    private byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Возвращает все доступные типы файлов.
     *
     * @return список типов файлов
     */
    public List<FileType> getAllFileTypes() {
        return new ArrayList<>(fileTypes);
    }
}