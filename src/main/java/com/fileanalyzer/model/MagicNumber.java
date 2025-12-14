package com.fileanalyzer.model;

/**
 * Класс, представляющий магическое число для идентификации типа файла.
 * Содержит смещение и последовательность байтов для сравнения.
 */
public class MagicNumber {
    private int offset;
    private byte[] bytes;
    private String hex;

    /**
     * Конструктор по умолчанию.
     */
    public MagicNumber() {
    }

    /**
     * Конструктор с параметрами.
     *
     * @param offset смещение в байтах от начала файла
     * @param bytes массив байтов магического числа
     * @param hex шестнадцатеричное представление (для удобства чтения)
     */
    public MagicNumber(int offset, byte[] bytes, String hex) {
        this.offset = offset;
        this.bytes = bytes;
        this.hex = hex;
    }

    // Геттеры и сеттеры
    /**
     * Возвращает смещение магического числа от начала файла.
     *
     * @return смещение в байтах
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Устанавливает смещение магического числа.
     *
     * @param offset новое смещение в байтах
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * Возвращает массив байтов магического числа.
     *
     * @return массив байтов
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Устанавливает массив байтов магического числа.
     *
     * @param bytes новый массив байтов
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Возвращает шестнадцатеричное представление магического числа.
     *
     * @return строка в HEX формате
     */
    public String getHex() {
        return hex;
    }

    /**
     * Устанавливает шестнадцатеричное представление.
     *
     * @param hex новая строка в HEX формате
     */
    public void setHex(String hex) {
        this.hex = hex;
        // Автоматически конвертируем HEX в байты
        if (hex != null && !hex.trim().isEmpty()) {
            this.bytes = hexStringToByteArray(hex.replaceAll("\\s+", ""));
        }
    }

    /**
     * Конвертирует HEX строку в массив байт.
     *
     * @param hexString HEX строка (без пробелов)
     * @return массив байт
     */
    private byte[] hexStringToByteArray(String hexString) {
        try {
            int len = hexString.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                        + Character.digit(hexString.charAt(i + 1), 16));
            }
            return data;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    /**
     * Возвращает длину магического числа в байтах.
     *
     * @return длина в байтах
     */
    public int getLength() {
        return bytes != null ? bytes.length : 0;
    }

    @Override
    public String toString() {
        return "MagicNumber{" +
                "offset=" + offset +
                ", hex='" + hex + '\'' +
                ", length=" + getLength() +
                '}';
    }
}