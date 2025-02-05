package com.proyectomarzo.flashmeet.datos_mock;


import java.util.ArrayList;
import java.util.List;

public class Conversations {

    private String conversationId;
    private String remitentName;
     private String remitentImageUrl;
     private ChatMode chatMode;

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    private List<Messages> messagesList= new ArrayList<>();

     public ChatMode getChatMode() {
         return chatMode;
     }

     public void setChatMode(ChatMode chatMode) {
         this.chatMode = chatMode;
     }

     public String getRemitentName() {
         return remitentName;
     }

     public void setRemitentName(String remitentName) {
         this.remitentName = remitentName;
     }

     public String getRemitentImageUrl() {
         return remitentImageUrl;
     }

     public void setRemitentImageUrl(String remitentImageUrl) {
         this.remitentImageUrl = remitentImageUrl;
     }

     public String getConversationId() {
         return conversationId;
     }

     public void setConversationId(String conversationId) {
         this.conversationId = conversationId;
     }



}
