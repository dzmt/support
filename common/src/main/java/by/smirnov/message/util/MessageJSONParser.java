package by.smirnov.message.util;

import by.smirnov.message.Message;

public interface MessageJSONParser {

    String parseMessage(Message message);

    Message parseString(String message);
}
