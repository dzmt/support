package by.smirnov.message.converter;

import by.smirnov.message.Message;

public interface MessageJSONConverter {

    String toJson(Message message);

    Message fromJson(String msg);
}
