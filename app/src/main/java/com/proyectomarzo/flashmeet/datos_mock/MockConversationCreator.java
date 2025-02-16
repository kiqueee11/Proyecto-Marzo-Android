package com.proyectomarzo.flashmeet.datos_mock;

import java.util.ArrayList;
import java.util.List;

public class MockConversationCreator {
    public static List<Conversations> createConversations() {
        MockConversationData mockConversationData = MockConversationData.instance;
        MockMessagesData mockMessagesData = MockMessagesData.instance;
        mockConversationData.createConversations();
        mockMessagesData.createMessages();

        List<Conversations> conversations = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Conversations conversation = new Conversations();
            List<Messages> messages = new ArrayList<>();
            conversation.setConversationId(mockConversationData.chatIds.get(i));
            conversation.setRemitentName(mockConversationData.chatNames.get(i));
            conversation.setRemitentImageUrl(mockConversationData.chatImages.get(i));
            conversation.setChatMode(i % 2 == 0 ? ChatMode.ANONNIMOUS : ChatMode.NOT_ANONNIMOUS);

            for (int j = 0; j < 8; j++) {
                Messages message = new Messages();
                message.setMessageId(mockMessagesData.messageIds.get(j));
                message.setMessageContent(mockMessagesData.messageContents.get(j));
                message.setSenderId(mockMessagesData.senderIds.get(j));
                messages.add(message);
            }

            conversation.setMessagesList(messages);
            conversations.add(conversation);


        }


        return conversations;
    }
}
