package org.example.task2.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object для главной страницы сайта www.gaz-is.ru.
 * Содержит методы для открытия меню "Продукты" и перехода к разделу Ankey IDM.
 */
public class GazIsMainPage {
    private final WebDriver driver;

    public GazIsMainPage(WebDriver driver) {
        this.driver = driver;
    }

    //Метод для открытия выпадающего списка продуктов с наведением на раздел и кликом
    public void openProductsMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Локатор для меню "Продукты"
        By productsLinkLocator = By.xpath("//a[@href='#div109' and text()='Продукты']");

        // Ожидаем появления элемента
        WebElement productsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(productsLinkLocator));

        // Имитируем наведение мыши
        new Actions(driver).moveToElement(productsLink).perform();

        // Кликаем
        productsLink.click();
        System.out.println("Клик по 'Продукты'");
    }

    //Метод для перехода на страницу Ankey IDM из меню "Продукты" с использованием JS-клика
    public AnkeyIDMPage goToAnkeyIDM() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Локатор для AnkeyIDM в продуктах
        By ankeyIDMLinkLocator = By.cssSelector("a[href='/produkty/upravlenie-ib/ankey-idm']");

        // Ищем и ждём кликабельности
        WebElement ankeyIDMLink = wait.until(ExpectedConditions.elementToBeClickable(ankeyIDMLinkLocator));

        // Кликаем через JS по разделу
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", ankeyIDMLink);

        System.out.println("Перешли на страницу Ankey IDM");
        return new AnkeyIDMPage(driver);
    }
}