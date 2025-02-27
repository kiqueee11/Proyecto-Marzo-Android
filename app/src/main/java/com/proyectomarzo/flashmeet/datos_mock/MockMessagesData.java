package com.proyectomarzo.flashmeet.datos_mock;

import java.util.ArrayList;
import java.util.List;

public class MockMessagesData {

    public static MockMessagesData instance= new MockMessagesData();

    List<String> messageIds = new ArrayList<>();
    List<String> messageContents = new ArrayList<>();
    List<String> messageDates = new ArrayList<>();
    List<String> senderIds = new ArrayList<>();

    void createMessages() {
        messageIds.add("1");
        messageIds.add("2");
        messageIds.add("3");
        messageIds.add("4");
        messageIds.add("5");
        messageIds.add("6");
        messageIds.add("7");
        messageIds.add("8");

        messageContents.add("Hola");
        messageContents.add("¿Cómo estás?");
        messageContents.add("Bien, gracias");
        messageContents.add("Todo bien");
        messageContents.add("¿Quieres tomar un café?");
        messageContents.add("Sí, claro");
        messageContents.add("¿Y la cena?");
        messageContents.add("Sí, claro");

        senderIds.add("1");
        senderIds.add("2");
        senderIds.add("3");
        senderIds.add("4");
        senderIds.add("5");
        senderIds.add("6");
        senderIds.add("7");
        senderIds.add("8");




    }
}
