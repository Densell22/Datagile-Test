package org.example.task2.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v134.browser.Browser;


import java.util.Optional;

/**
 * Утилитарный класс для настройки поведения браузера через Chrome DevTools Protocol (CDP).
 * Содержит методы, которые:
 * - Разрешают автоматическое скачивание файлов
 * - Указывают папку загрузок
 * - Отключают всплывающие окна подтверждения
 */
public class BrowserUtils {

    //Настраивает параметры загрузки файлов через Chrome DevTools
    //Гарантирует, что все файлы будут сохранены в указанной директории без диалогов подтверждения.
    public static void setDownloadBehavior(WebDriver driver, String downloadDir) {

        // Получаем доступ к DevTools протоколу браузера через ChromeDriver
        DevTools devTools = ((ChromeDriver) driver).getDevTools();

        // Создаём новую DevTools-сессию
        devTools.createSession();

        // Настраиваем поведение скачивания через CDP
        devTools.send(Browser.setDownloadBehavior(
                Browser.SetDownloadBehaviorBehavior.ALLOW, //Разрешаем все загрузки
                Optional.empty(),                          //browserContextId (не указан)
                Optional.of(downloadDir),                  //Указываем путь к папке загрузок внутри проекта
                Optional.of(true)                    //Включаем отслеживание событий загрузки
        ));
    }
}