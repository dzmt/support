package by.smirnov.message.util.impl;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import org.junit.jupiter.api.Test;

class MessageGSONParserTest {
    @Test
    void parse() {
        MessageGSONParser parser = new MessageGSONParser();
        Message message = new Message(Type.CONTENT, "Hello");

    }

}