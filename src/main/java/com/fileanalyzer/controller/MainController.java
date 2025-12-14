package com.fileanalyzer.controller;

import com.fileanalyzer.model.FileType;
import com.fileanalyzer.service.DatabaseService;
import com.fileanalyzer.service.FileAnalyzerService;
import com.fileanalyzer.service.FileRestoreService;
import com.fileanalyzer.view.FileInfoDialog;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Optional;

/**
 * Контроллер главного окна приложения.
 * Управляет взаимодействием пользователя с графическим интерфейсом.
 */
public class MainController {

    private static final Logger logger = LogManager.getLogger(MainController.class);
    private File selectedFile;
    private FileType detectedFileType;

    // Сервисы
    private final DatabaseService databaseService;
    private final FileAnalyzerService fileAnalyzerService;
    private final FileRestoreService fileRestoreService;

    // FXML элементы
    @FXML
    private TextField filePathField;
    @FXML
    private Label fileTypeLabel;
    @FXML
    private Label extensionLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button analyzeButton;
    @FXML
    private Button restoreButton;
    @FXML
    private TextArea logTextArea;
    @FXML
    private ProgressIndicator progressIndicator;

    /**
     * Конструктор контроллера.
     * Инициализирует сервисы приложения.
     */
    public MainController() {
        this.databaseService = new DatabaseService();
        this.fileAnalyzerService = new FileAnalyzerService(databaseService);
        this.fileRestoreService = new FileRestoreService();
    }

    /**
     * Инициализация контроллера.
     * Вызывается после загрузки FXML файла.
     */
    @FXML
    public void initialize() {
        logger.info("MainController initialized");
        logToUI("Приложение запущено. Выберите файл для анализа.");
        analyzeButton.setDisable(true);
        restoreButton.setDisable(true);
        progressIndicator.setVisible(false);
    }

    /**
     * Анализирует файл с использованием реальных сервисов.
     */
    private void analyzeFileWithServices() {
        try {
            progressIndicator.setVisible(true);
            logToUI("Начинаю анализ файла: " + selectedFile.getName());

            Optional<FileType> fileTypeOptional = fileAnalyzerService.analyzeFile(selectedFile);

            if (fileTypeOptional.isPresent()) {
                detectedFileType = fileTypeOptional.get();

                fileTypeLabel.setText(detectedFileType.getExtension().toUpperCase() + " File");
                extensionLabel.setText("." + detectedFileType.getExtension());
                descriptionLabel.setText(detectedFileType.getDescription());

                logToUI("Файл определен как: " + detectedFileType.getExtension());
                restoreButton.setDisable(false);
            } else {
                fileTypeLabel.setText("Неизвестный тип");
                extensionLabel.setText(".unknown");
                descriptionLabel.setText("Не удалось определить тип файла по содержимому");
                logToUI("Не удалось определить тип файла");
                restoreButton.setDisable(true);
            }

            logToUI("Анализ завершен успешно");

        } catch (Exception e) {
            logger.error("Ошибка при анализе файла", e);
            showAlert(Alert.AlertType.ERROR, "Ошибка анализа",
                    "Не удалось проанализировать файл: " + e.getMessage());
            logToUI("Ошибка анализа: " + e.getMessage());
        } finally {
            progressIndicator.setVisible(false);
        }
    }

    /**
     * Восстанавливает расширение файла с использованием сервиса.
     */
    private void restoreFileWithService() {
        try {
            if (detectedFileType == null) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Тип файла не определен");
                return;
            }

            logToUI("Восстанавливаю расширение для файла...");
            File restoredFile = fileRestoreService.restoreFileExtension(selectedFile, detectedFileType);

            logToUI("Файл сохранен как: " + restoredFile.getName());
            showAlert(Alert.AlertType.INFORMATION, "Успех",
                    "Расширение восстановлено! Новый файл: " + restoredFile.getName());

        } catch (Exception e) {
            logger.error("Ошибка при восстановлении файла", e);
            showAlert(Alert.AlertType.ERROR, "Ошибка восстановления",
                    "Не удалось восстановить расширение: " + e.getMessage());
            logToUI("Ошибка восстановления: " + e.getMessage());
        }
    }

    // Обновляем обработчики кнопок:
    @FXML
    private void handleAnalyzeButton() {
        if (!validateSelectedFile()) {
            return;
        }
        analyzeFileWithServices();
    }

    @FXML
    private void handleRestoreButton() {
        if (!validateSelectedFile()) {
            return;
        }
        restoreFileWithService();
    }

    @FXML
    private void handleInfoButton() {
        if (!validateSelectedFile()) {
            return;
        }
        FileInfoDialog.showFileInfo(selectedFile, detectedFileType);
    }

    /**
     * Валидирует выбранный файл.
     *
     * @return true, если файл валиден
     */
    private boolean validateSelectedFile() {
        if (selectedFile == null || !selectedFile.exists()) {
            FileInfoDialog.showError("Ошибка", "Файл не выбран или не существует");
            return false;
        }
        return true;
    }
    /**
     * Обработчик нажатия кнопки "Обзор...".
     * Открывает диалог выбора файла.
     */
    @FXML
    private void handleBrowseButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл для анализа");

        // Фильтры для удобства
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Все файлы", "*.*"),
                new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("Документы", "*.pdf", "*.docx", "*.txt")
        );

        Stage stage = (Stage) filePathField.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            selectedFile = file;
            filePathField.setText(file.getAbsolutePath());
            analyzeButton.setDisable(false);
            logToUI("Выбран файл: " + file.getName());
            clearResults();
        }
    }

    /**
     * Заглушка для имитации анализа файла.
     * В реальной реализации здесь будет вызов FileAnalyzerService.
     */
    private void simulateFileAnalysis() {
        try {
            Thread.sleep(1000); // Имитация долгой операции

            String fileName = selectedFile.getName().toLowerCase();

            if (fileName.contains(".png") || fileName.endsWith(".png")) {
                fileTypeLabel.setText("PNG Image");
                extensionLabel.setText(".png");
                descriptionLabel.setText("Portable Network Graphics - растровый формат хранения графической информации");
                logToUI("Файл определен как PNG изображение");
            } else if (fileName.contains(".pdf") || fileName.endsWith(".pdf")) {
                fileTypeLabel.setText("PDF Document");
                extensionLabel.setText(".pdf");
                descriptionLabel.setText("Adobe Portable Document Format - формат электронных документов");
                logToUI("Файл определен как PDF документ");
            } else if (fileName.contains(".zip") || fileName.endsWith(".zip")) {
                fileTypeLabel.setText("ZIP Archive");
                extensionLabel.setText(".zip");
                descriptionLabel.setText("ZIP - формат сжатия данных и архивации файлов");
                logToUI("Файл определен как ZIP архив");
            } else if (fileName.contains(".txt") || fileName.endsWith(".txt")) {
                fileTypeLabel.setText("Text File");
                extensionLabel.setText(".txt");
                descriptionLabel.setText("Текстовый файл");
                logToUI("Файл определен как текстовый файл");
            } else {
                fileTypeLabel.setText("Неизвестный тип");
                extensionLabel.setText(".unknown");
                descriptionLabel.setText("Не удалось определить тип файла по содержимому");
                logToUI("Не удалось определить тип файла");
            }

            restoreButton.setDisable(false);
            logToUI("Анализ завершен успешно");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logToUI("Анализ прерван");
        }
    }

    /**
     * Очищает результаты предыдущего анализа.
     */
    private void clearResults() {
        fileTypeLabel.setText("Не определен");
        extensionLabel.setText("Не определено");
        descriptionLabel.setText("-");
        restoreButton.setDisable(true);
    }

    /**
     * Добавляет сообщение в лог-панель интерфейса.
     *
     * @param message сообщение для логирования
     */
    private void logToUI(String message) {
        String timestamp = java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
        logTextArea.appendText("[" + timestamp + "] " + message + "\n");

        // Прокручиваем вниз
        logTextArea.setScrollTop(Double.MAX_VALUE);
    }

    /**
     * Показывает диалоговое окно с сообщением.
     *
     * @param alertType тип окна (ERROR, WARNING, INFORMATION)
     * @param title заголовок окна
     * @param content содержимое сообщения
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        logToUI(title + ": " + content);
    }
}