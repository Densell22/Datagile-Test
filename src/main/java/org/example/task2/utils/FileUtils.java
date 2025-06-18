package org.example.task2.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Утилитарный класс для работы с файлами.
 * Содержит методы:
 * - создания и очистки папки загрузок
 * - переименования первого файла в указанное имя
 * Полезно при тестировании скачивания файлов через браузер
 */
public class FileUtils {

    //Метод для создания папки downloads в корне проекта и ее очистки перед использованием
    public static String createTempDownloadDirectory() throws IOException {

        // Формируем путь: user.dir + downloads
        Path downloadsPath = Paths.get(System.getProperty("user.dir"), "downloads");

        // Если такая папка уже существует — очищаем её
        if (Files.exists(downloadsPath)) {
            try (Stream<Path> paths = Files.walk(downloadsPath)) {

                // Сортируем в обратном порядке, чтобы сначала удалить внутренние файлы, потом папки
                paths.sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                System.err.println("Не удалось удалить файл: " + path);
                            }
                        });
            }
        }
        // Создание папки (если не существует)
        Files.createDirectories(downloadsPath);
        System.out.println("Папка для загрузок создана: " + downloadsPath);

        // Возвращаем путь как строку, чтобы использовать в ChromeOptions
        return downloadsPath.toString();
    }

    //Метод для переименования файла (и смены формата после скачивания)
    public static boolean renameFirstMatchingFile(String dirPath, String newName) {
        File folder = new File(dirPath); // Рабочая папка
        File[] files = folder.listFiles(); // Ищем все файлы в папке

        // Проверяем, есть ли вообще файлы
        if (files == null || files.length == 0) {
            System.out.println("Подходящие файлы не найдены.");
            return false;
        }

        // Берём первый файл из списка
        File oldFile = files[0];
        File newFile = new File(folder, newName);

        //Пробуем переименовать файл
        boolean renamed = oldFile.renameTo(newFile);
        if (renamed) {
            System.out.println("Файл переименован: " + newFile.getName());
        } else {
            System.err.println("Не удалось переименовать файл.");
        }

        return renamed;
    }
}

