package task1;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.task1.WeatherResponse;
import org.testng.Assert;
import org.testng.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
import static io.restassured.RestAssured.given;
import java.time.format.DateTimeFormatter;

/**
 * Класс для автотестирования API погоды.
 * Выполняет GET-запрос к projecteol.ru/api/weather/
 * Проверяет, что возвращаются корректные данные о погоде в Санкт-Петербурге
 */
@Test()
public class WeatherApiTest {

    // Базовый URL API
    private static final String BASE_URL = "https://projecteol.ru/api/weather/";

    // Токен авторизации (заменить при изменении)
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzUwMTU0NTgyLCJpYXQiOjE3NTAxNTQyODIsImp0aSI6IjE5MWE1M2ZiMGNmMzQ1Y2M5ZWY3MjI3MTBhMTgxNGQ2IiwidXNlcl9pZCI6MTIzNH0.8tdxbLtiuE9l-5CZoPi916O-vyFVYpDDdeAHRICToSw";

    // Координаты Санкт-Петербурга
    private static final double LAT = 59.93863;   //Широта
    private static final double LON = 30.31413;   //Долгота
    private static final Logger log = LoggerFactory.getLogger(WeatherApiTest.class);

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL; //Задаём базовый URI для RestAssured
    }

//    Тест получения текущей погоды в Санкт-Петербурге
//    Что проверяем:
//     - API возвращает статус 200 OK
//     - Ответ содержит данные о погоде
//     - Выводит информацию о погоде в читаемом виде
    @Test(description = "Получить текущую погоду в Санкт-Петербурге")
    public void testGetCurrentWeather() {

        // Формируем сегодняшнюю дату в формате ISO_LOCAL_DATE
        String todayFromDT = LocalDateTime.now().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        log.info("Запуск теста получения погоды");
        System.out.println("Запрашиваемая дата: " + todayFromDT);

        // Отправляем GET-запрос с параметрами
        Response response = given()
                .queryParam("lat", LAT)
                .queryParam("lon", LON)
                .queryParam("token", TOKEN)
                .queryParam("date", todayFromDT)
                .when()
                .get()
                .then()
                .statusCode(200) // Проверяем, что ответ успешный
                .extract().response();

        // Парсим JSON в список объектов WeatherResponse
        List<WeatherResponse> weatherList = response.jsonPath().getList("", WeatherResponse.class);

        // Проверяем, что данные не пустые
        Assert.assertFalse(weatherList.isEmpty(), "Нет данных о погоде");

        // Выводим результат в консоль
        WeatherResponse currentWeather = weatherList.getFirst();
        System.out.println(currentWeather);
    }
}
