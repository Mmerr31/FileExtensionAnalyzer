package com.fileanalyzer.model;

import java.util.List;

/**
 * Класс, представляющий тип файла с его характеристиками.
 * Содержит расширение, описание и магические числа для идентификации.
 */
public class FileType {
    private String extension;
    private String description;
    private List<MagicNumber> magicNumbers;

    /**
     * Конструктор по умолчанию.
     */
    public FileType() {
    }

    /**
     * Конструктор с параметрами.
     *
     * @param extension расширение файла (например, "png", "pdf")
     * @param description описание формата файла
     * @param magicNumbers список магических чисел для идентификации
     */
    public FileType(String extension, String description, List<MagicNumber> magicNumbers) {
        this.extension = extension;
        this.description = description;
        this.magicNumbers = magicNumbers;
    }

    // Геттеры и сеттеры с JavaDoc
    /**
     * Возвращает расширение файла.
     *
     * @return расширение файла
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Устанавливает расширение файла.
     *
     * @param extension новое расширение файла
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Возвращает описание формата файла.
     *
     * @return описание формата
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает описание формата файла.
     *
     * @param description новое описание формата
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Возвращает список магических чисел для идентификации файла.
     *
     * @return список магических чисел
     */
    public List<MagicNumber> getMagicNumbers() {
        return magicNumbers;
    }

    /**
     * Устанавливает список магических чисел.
     *
     * @param magicNumbers новый список магических чисел
     */
    public void setMagicNumbers(List<MagicNumber> magicNumbers) {
        this.magicNumbers = magicNumbers;
    }

    /**
     * Проверяет, имеет ли данный тип файла магические числа для идентификации.
     *
     * @return true, если есть магические числа, иначе false
     */
    public boolean hasMagicNumbers() {
        return magicNumbers != null && !magicNumbers.isEmpty();
    }

    @Override
    public String toString() {
        return "FileType{" +
                "extension='" + extension + '\'' +
                ", description='" + description + '\'' +
                ", magicNumbersCount=" + (magicNumbers != null ? magicNumbers.size() : 0) +
                '}';
    }
}