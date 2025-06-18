package org.example.task2.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

/**
 * Утилитарный класс для работы с вкладками браузера.
 * Содержит методы для:
 * - ожидания новой вкладки
 * - переключения между ними
 * - закрытия лишних вкладок
 */
public class WindowUtils {

    //Метод ожидает открытия новой вкладки и переключается на неё.
    //Закрывает исходную вкладку после переключения.
    public static void waitForNewTabAndSwitch(WebDriver driver) {

        // Сохраняем handle текущей вкладки
        String originalWindow = driver.getWindowHandle();

        // Ожидаем, пока появится новая вкладка (ожидание до 10 секунд)
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);

        // Получаем все доступные вкладки
        Set<String> windowHandles = driver.getWindowHandles();

        // Перебираем вкладки и ищем ту, которая не является оригинальной
        for (String window : windowHandles) {
            if (!window.equals(originalWindow)) {

                // Переключаемся на новую вкладку
                driver.switchTo().window(window);
                break;
            }
        }

        // Закрываем исходную вкладку
        driver.switchTo().window(originalWindow);
        driver.close();
        System.out.println("Исходная вкладка закрыта.");

        // Возвращаемся к новой вкладке
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
        System.out.println("Теперь активна новая вкладка. URL: " + driver.getCurrentUrl());
    }
}
