package org.example.task2.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Находит и кликает по ссылке "Газинформсервис" в результатах поиска Яндекса.
 * Использует JS для прокрутки и клика, чтобы обойти возможные перекрытия и блокировки от сайта.
 * Выбрасывает NoSuchElementException если элемент не найден за указанное время ожидания
 */
public class SearchResultsPage {
    private final WebDriver driver;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
    }
    //Метод для перехода на страницу https://www.gaz-is.ru после поиска
    public void clickGazIsLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Локатор для ссылки на сайт "Газинформсервис"
        By gazIsLocator = By.xpath("(//a[contains(@href, 'https://www.gaz-is.ru/')])[1]");

        // Ожидаем появления элемента
        WebElement gazIsLink = wait.until(ExpectedConditions.presenceOfElementLocated(gazIsLocator));

        // Прокручиваем до элемента (человеческая имитация поведения)
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", gazIsLink);

        // Кликаем через JavaScript — чтобы обойти защиту или перекрытие (конкретно "сделайте яндекс основным браузером окно")
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", gazIsLink);
        System.out.println("Нажали на нужный сайт");
    }
}
