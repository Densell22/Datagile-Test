package org.example.task2.config;

import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Утилитарный класс для настройки ChromeOptions.
 * Содержит методы для получения стандартных опций браузера,
 * которые используются в автотестах
 */
public class ChromeDriverConfig {

    //Возвращает преднастроенные ChromeOptions для автотестов.
    //Используется в тестовых классах для инициализации ChromeDriver.
    public static ChromeOptions getDefaultChromeOptions(String downloadDir) {

        // Настройки загрузки файлов через Chrome
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.prompt_for_download", false); // Отключаем диалог "Куда сохранить"
        prefs.put("plugins.always_open_pdf_externally", true); // PDF открывается как файл, не во встроенном просмотрщике
        prefs.put("download.default_directory", downloadDir); // Папка для загрузок
        prefs.put("download.directory_upgrade", true); //Гарантирует, что Chrome использует указанный путь

        // Основные настройки
        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("prefs", prefs); // Применяем настройки скачивания
        options.addArguments("--disable-popup-blocking"); // Отключаем блокировку всплывающих окон
        options.addArguments("--incognito"); // Запуск браузера в режиме инкогнито
        options.addArguments("--disable-blink-features=AutomationControlled"); //Предотвращает установку флага navigator.webdriver = true
        options.addArguments("--disable-logging"); // Отключает вывод большого количества внутренних логов браузера в консоль драйвера
        options.addArguments("--log-level=3"); // Устанавливаем уровень логов : 0 - все логи, 1 - INFO, 2 - WARNING, 3 - SEVERE
        options.addArguments("--silent"); // Минимизируем вывод логов в консоль (Сообщения о запуске браузера / Предупреждения о GPU)

        // Исключаем переключатели, вызывающие автоматизированное поведение (убираем надпись "браузер под управлением автоматизированного ПО")
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false); // Отключает расширение Selenium, которое ставит ChromeDriver (для капчи)

        // Настройки User-Agent и языка браузера
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36");
        options.addArguments("--lang=en-GB"); // Язык браузера — английский (Великобритания)

        return options;
    }
}