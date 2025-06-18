package task2;

import lombok.SneakyThrows;
import org.example.task2.config.ChromeDriverConfig;
import org.example.task2.pages.*;
import org.example.task2.utils.BrowserUtils;
import org.example.task2.utils.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import static org.example.task2.utils.FileUtils.createTempDownloadDirectory;
import static org.example.task2.utils.WindowUtils.waitForNewTabAndSwitch;

public class YandexSearchTest {

    private WebDriver driver;
    private String downloadDir;

    @SneakyThrows
    @BeforeClass
    public void setUp() {

        //Создаём временную папку downloads внутри проекта, чтобы хранить там скачанные файлы
        downloadDir = createTempDownloadDirectory();

        //Автоматическая установка ChromeDriver через WebDriverManager
        WebDriverManager.chromedriver().setup();

        //Настройка ChromeOptions через конфигурационный класс
        ChromeOptions options = ChromeDriverConfig.getDefaultChromeOptions(downloadDir);

        //Инициализируем браузер Chrome с заданными опциями
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        //Подключаем Chrome DevTools Protocol , чтобы использовать расширенные возможности
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();

        //Настройка поведения скачивания через BrowserUtils
        BrowserUtils.setDownloadBehavior(driver, downloadDir);
    }

    @Test
    public void testSearchAndDownloadManual() {

        //Открываем пустую страницу Яндекса
        YandexMainPage yandexMainPage = new YandexMainPage(driver);
        yandexMainPage.open();

        //Вводим поисковый запрос
        yandexMainPage.search("Газинформсервис");

        //Переходим на другую вкладку
        waitForNewTabAndSwitch(driver);

        //Нажимаем на нужный сайт
        SearchResultsPage resultsPage = new SearchResultsPage(driver);
        resultsPage.clickGazIsLink();

        //Переходим на другую вкладку
        waitForNewTabAndSwitch(driver);

        //Открываем меню продукты
        GazIsMainPage gazIsMainPage = new GazIsMainPage(driver);
        gazIsMainPage.openProductsMenu();

        //Переходим в раздел AnkeyIDM
        AnkeyIDMPage ankeyIDMPage = gazIsMainPage.goToAnkeyIDM();

        // Проверяем, что URL соответствует ожидаемому
        String expectedUrl = "https://www.gaz-is.ru/produkty/upravlenie-ib/ankey-idm";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "URL страницы Ankey IDM не соответствует ожидаемому");

        //Скачиваем руководство пользователя Ankey IDM
        ankeyIDMPage.downloadManual();

        // Ждём загрузки файла
        ankeyIDMPage.waitForDownloadToComplete(downloadDir,  60);

        // Переименовываем файл
        boolean renamed = FileUtils.renameFirstMatchingFile(
                downloadDir,
                "Руководство пользователя Ankey IDM.pdf"
        );

        //Проверяем переименовался ли файл
        Assert.assertTrue(renamed, "Не удалось переименовать файл и перевести в PDF");


        // Проверяем наличие файла
        File downloadedFile = new File(downloadDir, "Руководство пользователя Ankey IDM.pdf");
        Assert.assertTrue(downloadedFile.exists(), "Файл не найден после загрузки.");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}