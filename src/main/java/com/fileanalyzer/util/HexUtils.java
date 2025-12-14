package com.fileanalyzer.util;

/**
 * Утилитный класс для работы с шестнадцатеричными данными.
 * Предоставляет методы для конвертации и сравнения hex-значений.
 */
public final class HexUtils {

    private HexUtils() {
        // Утилитный класс не должен создавать экземпляры
    }

    /**
     * Конвертирует строку hex-значений в массив байтов.
     *
     * @param hexString строка hex-значений (например, "FF D8 FF E0")
     * @return массив байтов
     * @throws IllegalArgumentException если строка содержит некорректные hex-значения
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.trim().isEmpty()) {
            return new byte[0];
        }

        String cleanHex = hexString.replaceAll("\\s+", "");
        
        if (cleanHex.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex строка должна содержать четное количество символов");
        }

        byte[] bytes = new byte[cleanHex.length() / 2];
        for (int i = 0; i < cleanHex.length(); i += 2) {
            bytes[i / 2] = (byte) Integer.parseInt(cleanHex.substring(i, i + 2), 16);
        }

        return bytes;
    }

    /**
     * Конвертирует массив байтов в hex-строку.
     *
     * @param bytes массив байтов
     * @return hex-строка (например, "FF D8 FF E0")
     */
    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(String.format("%02X", bytes[i] & 0xFF));
        }
        return sb.toString();
    }

    /**
     * Сравнивает массив байтов с hex-паттерном начиная с указанного смещения.
     *
     * @param data данные для сравнения
     * @param offset смещение в данных
     * @param hexPattern hex-паттерн для сравнения
     * @return true, если паттерн совпадает
     */
    public static boolean matchesPattern(byte[] data, int offset, String hexPattern) {
        if (data == null || hexPattern == null) {
            return false;
        }

        byte[] pattern = hexStringToBytes(hexPattern);
        
        if (offset + pattern.length > data.length) {
            return false;
        }

        for (int i = 0; i < pattern.length; i++) {
            if (data[offset + i] != pattern[i]) {
                return false;
            }
        }

        return true;
    }
}