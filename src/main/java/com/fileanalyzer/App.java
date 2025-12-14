package com.fileanalyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Главный класс приложения для анализа и восстановления расширений файлов.
 * Запускает JavaFX приложение с графическим интерфейсом.
 */
public class App extends Application {

    /**
     * Точка входа в JavaFX приложение.
     * Загружает FXML файл интерфейса и отображает главное окно.
     *
     * @param stage Основное окно приложения
     * @throws IOException если не удается загрузить FXML файл
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/MainView.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("File Analyzer - Определение и восстановление расширений файлов");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Главный метод для запуска приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch();
    }
}