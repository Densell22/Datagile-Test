package org.example.task1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * POJO-класс для маппинга JSON-ответа от API погоды.
 * Содержит данные о погоде в Санкт-Петербурге.
 * Используется в тестах для валидации ответа API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherResponse {
    private String dt_forecast;    //Дата и время прогноза (в формате строки)
    private double temp_2_cel;     //Температура в градусах Цельсия
    private double temp_feels_cel; //Ощущаемая температура в градусах Цельсия
    private int vlaga_2f;          //Влажность воздуха (%)
    private double pres_surf;      //Атмосферное давление на уровне поверхности (Па)
    private double wind_speed_10;  //Скорость ветра на высоте 10 м (м/с)
    private String description;    //Описание погоды
    private int vidimost_surf;     //Видимость на поверхности в метрах
    private int oblachnost_atmo;   //Облачность (%)
    private int wind_dir_10;       //Направление ветра на высоте 10 м (в градусах)
    private double uv_index;       //УФ-индекс


    @Override
    //Переопределенный метод toString() для красивого вывода в консоль
    public String toString() {
        return String.format("""
            🌤 Погода в Санкт-Петербурге:
            Дата и время:        %s
            Температура:         %.1f°C
            По ощущениям:        %.1f°C
            Влажность:           %d%%
            Атмосферное давление:%.2f Па
            Скорость ветра:      %.2f м/с
            Направление ветра:   %d°
            Видимость:           %d м
            Облачность:          %d%%
            УФ-индекс:           %.0f
            Описание погоды:     %s
            """,
                dt_forecast,
                temp_2_cel,
                temp_feels_cel,
                vlaga_2f,
                pres_surf,
                wind_speed_10,
                wind_dir_10,
                vidimost_surf,
                oblachnost_atmo,
                uv_index,
                (description == null || description.isEmpty()) ? "Нет данных" : description
        );
    }
}