package com.example.vladimir.firebasedemo;

import java.util.Date;

/**
 * Created by Vladimir on 15.9.2017..
 */

public class ChatMessage {


    private String messageText;
    private String messageUser;
    private long messageTime;

    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        messageTime = new Date().getTime();
    }

    public String getMessageText() {
        return messageText;
    }

}
