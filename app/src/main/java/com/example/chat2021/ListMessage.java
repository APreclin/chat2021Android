package com.example.chat2021;

import java.util.ArrayList;

public class ListMessage {
    ArrayList<Message> messages;

    @Override
    public String toString() {
        return "ListMessage{" +
                "messages=" + messages +
                '}';
    }

    public boolean add(Message m) {
        return messages.add(m);

    }

    public Message get(int index) {
        return messages.get(index);
    }
}
