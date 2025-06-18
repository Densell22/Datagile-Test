package org.example.task2.utils;

import java.util.Random;

/**
 * Утилитарный класс для имитации человеческого поведения в автотестах.
 * Содержит методы для добавления рандомных пауз:
 * - между вводом символов
 * - перед важными действиями
 */
public class HumanLikeDelays {
    private static final Random random = new Random();

    //Добавление паузы 2-7 сек (перед нажатием кнопки)
    public static void thinkingPause() {
        int seconds = random.nextInt(6) + 2;
        System.out.println("Пауза: " + seconds + " секунд (человеческая имитация)");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // восстанавливаем флаг прерывания
            System.err.println("Пауза была прервана");
        }
    }

    //Добавление паузы при печати между символами 100–400 мс
    public static void typingPause() {
        int millis = random.nextInt(301) + 100;
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // восстанавливаем флаг прерывания
            System.err.println("Пауза была прервана");
        }
    }
}