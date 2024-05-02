package ru.saiev.technesistask.service;

import javafx.animation.FadeTransition;
import javafx.scene.control.Labeled;
import javafx.util.Duration;

public class NoteService {

    /**
     * Метод заставляет сообщение появиться и потом плавно затухать в течение определенного времени.
     * @param millis время затухания
     * @param messageText текст сообщения
     */
    public static void timedMessage(Labeled label, int millis, String messageText){
        label.setOpacity(1);
        label.setText(messageText);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), label);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.play();
    }
}
