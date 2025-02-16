package com.proyectomarzo.flashmeet.models;

public class Message {
    private Long id;
    private String senderEmail;
    private String recipientEmail;
    private String content;
    private String timestamp;

    public Message(Long id, String senderEmail, String recipientEmail, String content, String timestamp) {
        this.id = id;
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getContent() {
        return content;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
