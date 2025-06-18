package org.example.task2.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

/**
 * Page Object для страницы Ankey IDM на сайте gaz-is.ru.
 * Содержит методы для:
 * - Открытия вкладки "Материалы"
 * - Поиска и скачивания руководства пользователя
 * - Ожидания завершения загрузки файла
 */
public class AnkeyIDMPage {
    private final WebDriver driver;

    public AnkeyIDMPage(WebDriver driver) {
        this.driver = driver;
    }

    //Метод для перехода на вкладку материалы и клика по ссылке "Руководство пользователя Ankey IDM"
    //Использует JS-скролл, чтобы убедиться, что элемент виден.
    public void downloadManual() {
        // Локатор для вкладки "Материалы"
        By materialsTabLocator = By.xpath("//a[@id='tab-materialy']");

        // Явное ожидание: ждем, пока вкладка станет кликабельной
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        WebElement materialsTab = wait.until(ExpectedConditions.elementToBeClickable(materialsTabLocator));

        // Прокручиваем до вкладки через JS (чтобы точно попала в зону видимости)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", materialsTab);
        // Нажимаем на вкладку "Материалы"
        materialsTab.click();
        System.out.println("Вкладка 'Материалы' найдена и нажата.");

        // Локатор для ссылки "Руководство пользователя"
        By manualLinkLocator = By.xpath("//a[@class='jd_download_url' and text()='Руководство пользователя Ankey IDM']");

        // Явное ожидание: ждем, пока ссылка станет видимой и кликабельной
        WebElement manualLink = wait.until(ExpectedConditions.elementToBeClickable(manualLinkLocator));

        // Нажимаем на ссылку для скачивания руководства
        manualLink.click();
        System.out.println("Скачивание руководства пользователя запущено.");
    }

    //Метод для ожидания завершения загрузки файла в указанную папку
    //Проверяет, что размер файла перестал расти → значит, загрузка завершена.
    public void waitForDownloadToComplete(String downloadDir, int timeoutInSeconds) {

        //Создаем объект File для папки загрузок
        File folder = new File(downloadDir);

        //Создаем массив, чтобы использовать внутри лямбды
        long[] lastLength = new long[]{0L};

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));

        //Ожидание завершения загрузки
        wait.until((ExpectedCondition<Boolean>) d -> {

            // Получаем список файлов из папки
            File[] files = folder.listFiles();

            //Проверяем наличие файлов
            if (files == null || files.length == 0) {
                System.out.println("Файлы не найдены.");
                return false;
            }

            // Проверяем стабильность размера
            for (File file : files) {
                long currentLength = file.length();

                //Сравниваем текущий и предыдущий размер
                if (currentLength > 0 && currentLength == lastLength[0]) {
                    System.out.println("Загрузка завершена: " + file.getAbsolutePath());
                    return true; // Загрузка завершена если совпадает
                } else {
                    lastLength[0] = currentLength;
                    return false; // Файл ещё качается
                }
            }
            //Если ни один файл не прошел условия
            return false;
        });
    }
}