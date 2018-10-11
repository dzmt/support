package by.smirnov.message.util.impl;

import by.smirnov.message.Message;
import by.smirnov.message.util.MessageJSONParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageGSONParser implements MessageJSONParser {
    private GsonBuilder builder = new GsonBuilder();

    @Override
    public String parseMessage(Message message) {
        Gson gson = builder.create();
        return gson.toJson(message);
    }

    @Override
    public Message parseString(String message) {
        Gson gson = builder.create();
        return gson.fromJson(message, Message.class);
    }
}
