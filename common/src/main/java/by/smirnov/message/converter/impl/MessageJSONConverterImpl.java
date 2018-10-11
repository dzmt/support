package by.smirnov.message.converter.impl;

import by.smirnov.message.Message;
import by.smirnov.message.converter.MessageJSONConverter;
import by.smirnov.message.util.MessageJSONParser;

public class MessageJSONConverterImpl implements MessageJSONConverter {
    private MessageJSONParser parser;

    public MessageJSONConverterImpl(MessageJSONParser parser) {
        this.parser = parser;
    }

    @Override
    public String toJson(Message message) {
        return parser.parseMessage(message);
    }

    @Override
    public Message fromJson(String msg) {
        return parser.parseString(msg);
    }
}
