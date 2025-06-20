# Datagile-Тестовое задание

Автоматизированные тесты для проверки поисковой выдачи с сайта https://www.yandex.ru по запросу "Газинформсервис" , перехода на сайт https://www.gaz-is.ru/ и скачивания файлов с раздела  https://www.gaz-is.ru/produkty/upravlenie-ib/ankey-idm

Также содержит API-тесты для получения данных о погоде через REST API:https://projecteol.ru/api/weather/

---

## Функционал

### 1. **UI-тест (Yandex → GazIs → Ankey IDM)**

- Открывает Яндекс
- Выполняет поиск "Газинформсервис"
- Переходит на сайт через JS-клик
- Наводит мышь на меню "Продукты" и кликает по разделу "Ankey IDM"
- Скачивает файл "Руководство пользователя" 
- Проверяет завершение загрузки
- Переименовывает его в `.pdf`
- Проверяет наличие файла
- Реализовано с помощью Page Object 

### 2. **API-тест (погода в Санкт-Петербурге)**

- Использует RestAssured для GET-запроса
- Парсит JSON через Jackson
- Выводит данные в читаемом виде
- Проводит валидацию ответа
- Выводит информацию о погоде в приятном формате
- Реализовано с помощью Pojo класса

---

## Технологии

- **Selenium 4.31.0** – UI-автоматизация браузера Chrome
- **TestNG** – фреймворк для тестирования
- **RestAssured** – для работы с API
- **Lombok** – аннотации
- **WebDriverManager** – автоматическая настройка драйвера
- **Chrome DevTools Protocol** – управление поведением браузера
- **Java 21**

---

## Структура проекта (поставьте формат edit после скачивания чтобы корректо отображалось)

src/
├── main/
│     │──java/
│     │   └──org.example/
│     │    │──task1/
│     │    │     └──WeatherResponse.java
│     │    └──task2/
│     │          │──config/
│     │          │    └──ChromeDriverConfig.java
│     │          │──pages/ # Page Object модели страниц
│     │          │    │──AnkeyIDMPage.java
│     │          │    │──GazIsMainPage.java
│     │          │    │──SearchResultsPage.java
│     │          │    └──YandexMainPage.java
│     │          └──utils # Утилитарные классы
│     │              │──BrowserUtils.java
│     │              │──FileUtils.java
│     │              │──HumanLikeDelays.java
│     │              └──WindowsUtils.java
│     └─────── resources/ 
└── test/  # Тестовые классы
     │──java/
     │    │──task1/
     │    │    └──WeatherApiTest.java
     │    └──task2/
     │        └──YandexSearchTest.java
     └─────── resources/
               └──logback.xml


## Как запустить проект
* Вариант 1: Через IntelliJ IDEA
- В IntelliJ IDEA в начальном меню нажмите clone repository
- Введи URL репозитория: https://github.com/Densell22/Datagile-Test.git
- Дождитесь, пока Maven подтянет все зависимости
- В терминале maven введите команду mvn test

* Вариант 2: Через командную строку / терминал
- Клонируем репозиторий и переходим в папку проекта
   git clone https://github.com/Densell22/Datagile-Test.git
   cd Datagile-Test
- Убеждаемся что установлена Java 17+ (Проект на Java 21) , Google Chrome (локально) , Maven 3.x
   java -version
   mvn -v
- Запуск UI теста
   mvn test -Dtest=task2.YandexSearchTest
- Запуск API теста
   mvn test -Dtest=task1.WeatherApiTest


--- 

## Пример вывода в консоли
* UI-тест:
  Папка для загрузок создана: D:\...\downloads
  Открыта главная Яндекса
  Пауза: 5 секунд (человеческая имитация)
  Нажали на нужный сайт
  Вкладка 'Материалы' найдена и нажата.
  Скачивание руководства пользователя запущено.
  Загрузка завершена: D:\...\downloads\6434b841-cb14-4e65-b08b-e1c142fb3e53.tmp
  Файл переименован: Руководство пользователя Ankey IDM.pdf

* API-тест:
  🌤 Погода в Санкт-Петербурге:
  Дата и время:        2025-06-19T12:00:00
  Температура:         18.5°C
  По ощущениям:        17.8°C
  Влажность:           68%
  Атмосферное давление:101320.00 Па
  Скорость ветра:      3.60 м/с
  Направление ветра:   220°
  Видимость:           10000 м
  Облачность:          40%
  УФ-индекс:           2
  Описание погоды:     Облачно

PS ошибка WARNING: Unable to find an exact match for CDP version 137, returning the closest version; found: 135; Please update to a Selenium version that supports CDP version 137- Предупреждение , означает, что версия ChromeDriver не идеально соответствует версии браузера Chrome. В частности, ChromeDriver поддерживает протокол CDP (Chrome DevTools Protocol) версии 135, Chrome использует CDP версии 137. 
