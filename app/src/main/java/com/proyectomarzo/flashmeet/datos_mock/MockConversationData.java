package com.proyectomarzo.flashmeet.datos_mock;

import java.util.ArrayList;
import java.util.List;

public class MockConversationData {
public static MockConversationData instance= new MockConversationData();
    List<String> chatIds = new ArrayList<>();
    List<String> chatNames = new ArrayList<>();
    List<String> chatImages = new ArrayList<>();
    List<String> chatModes = new ArrayList<>();


    void createConversations() {
        chatIds.add("1");
        chatIds.add("2");
        chatIds.add("3");
        chatIds.add("4");
        chatIds.add("5");
        chatIds.add("6");
        chatIds.add("7");
        chatIds.add("8");
        chatNames.add("Juan");
        chatNames.add("Pedro");
        chatNames.add("Maria");
        chatNames.add("Jose");
        chatNames.add("Ana");
        chatNames.add("Luis");
        chatNames.add("Sofia");
        chatNames.add("Pablo");
        chatImages.add("https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
        chatImages.add("https://images.pexels.com/photos/736716/pexels-photo-736716.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"); // Pedro - Hombre de perfil
        chatImages.add("https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"); // Maria - Mujer sonriendo pelo largo
        chatImages.add("https://images.pexels.com/photos/1040880/pexels-photo-1040880.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"); // Jose - Hombre con gafas
        chatImages.add("https://images.pexels.com/photos/1181686/pexels-photo-1181686.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"); // Ana - Mujer pelo corto
        chatImages.add("https://images.pexels.com/photos/936033/pexels-photo-936033.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"); // Luis - Hombre barba
        chatImages.add("https://images.pexels.com/photos/1559486/pexels-photo-1559486.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"); // Sofia - Mujer rubia
        chatImages.add("https://images.pexels.com/photos/2729192/pexels-photo-2729192.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"); // Pablo - Hombre joven casual


    }

}
