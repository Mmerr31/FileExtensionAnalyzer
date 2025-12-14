package com.fileanalyzer.view;

import com.fileanalyzer.model.FileType;
import com.fileanalyzer.util.FileUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Утилитный класс для создания диалоговых окон и отображения информации о файлах.
 */
public final class FileInfoDialog {

    private FileInfoDialog() {
        // Утилитный класс не должен создавать экземпляры
    }

    /**
     * Показывает диалог с подробной информацией о файле.
     *
     * @param file файл для отображения информации
     * @param detectedType определенный тип файла (может быть null)
     */
    public static void showFileInfo(File file, FileType detectedType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация о файле");
        alert.setHeaderText("Подробная информация");

        StringBuilder info = new StringBuilder();
        info.append("Имя файла: ").append(file.getName()).append("\n");
        info.append("Путь: ").append(file.getAbsolutePath()).append("\n");
        info.append("Размер: ").append(FileUtils.formatFileSize(file.length())).append("\n");
        info.append("Последнее изменение: ").append(
            LocalDateTime.ofEpochSecond(file.lastModified() / 1000, 0, 
                java.time.ZoneOffset.systemDefault().getRules().getOffset(java.time.Instant.now()))
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        ).append("\n\n");

        if (detectedType != null) {
            info.append("Определенный тип: ").append(detectedType.getExtension().toUpperCase()).append("\n");
            info.append("Описание: ").append(detectedType.getDescription()).append("\n");
            info.append("Количество магических чисел: ").append(detectedType.getMagicNumbers().size()).append("\n");
        } else {
            info.append("Тип файла: Не определен\n");
        }

        TextArea textArea = new TextArea(info.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 0);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().setExpanded(true);
        alert.showAndWait();
    }

    /**
     * Показывает простое информационное сообщение.
     *
     * @param title заголовок
     * @param message сообщение
     */
    public static void showInfo(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    /**
     * Показывает сообщение об ошибке.
     *
     * @param title заголовок
     * @param message сообщение об ошибке
     */
    public static void showError(String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message);
    }

    /**
     * Показывает предупреждение.
     *
     * @param title заголовок
     * @param message сообщение предупреждения
     */
    public static void showWarning(String title, String message) {
        showAlert(Alert.AlertType.WARNING, title, message);
    }

    /**
     * Показывает диалоговое окно указанного типа.
     *
     * @param alertType тип диалога
     * @param title заголовок
     * @param message сообщение
     */
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}