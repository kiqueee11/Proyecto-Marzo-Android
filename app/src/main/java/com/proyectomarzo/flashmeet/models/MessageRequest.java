package com.proyectomarzo.flashmeet.models;

public class MessageRequest {
    private String recipientEmail;
    private String content;

    public MessageRequest(String recipientEmail, String content) {
        this.recipientEmail = recipientEmail;
        this.content = content;
    }
}
