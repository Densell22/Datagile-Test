package org.example.task2.pages;

import org.example.task2.utils.HumanLikeDelays;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/** Page Object для главной страницы Яндекса www.yandex.ru.
 Содержит методы для открытия главной страницы и поиска сайтов.
 */
public class YandexMainPage {
    private final WebDriver driver;

    //Первоначальная страница
    public YandexMainPage(WebDriver driver) {
        this.driver = driver;
    }

    //Метод для открытия главной страницы Яндекс
    public void open() {
        driver.get("https://www.yandex.ru/");
        System.out.println("Открыта главная Яндекса");
    }

    //Метод для поиска в браузере
    public void search(String query) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Ждём появления iframe
        WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("ya-search-iframe-283dku")));
        driver.switchTo().frame(iframeElement);

        // Находим поле поиска
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("text")));

        // Имитируем посимвольное введение запроса человеком с паузой между буквами
        // (в целом можно обойтись без этого, это чтобы капча реже приставала)
        for (char c : query.toCharArray()) {
            searchInput.sendKeys(String.valueOf(c));
            HumanLikeDelays.typingPause();
        }

        // Пауза как будто пользователь думает
        HumanLikeDelays.thinkingPause();

        // Нажимаем кнопку "Найти"
        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        // Возвращаемся к основному контенту
        driver.switchTo().defaultContent();
    }
}