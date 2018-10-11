package by.smirnov.message.converter.impl;

import by.smirnov.message.Message;
import by.smirnov.message.converter.StringMessageConverter;
import by.smirnov.message.util.StringMessageParser;

public class StringMessageConverterImpl implements StringMessageConverter {
    private StringMessageParser parser;

    public StringMessageConverterImpl(StringMessageParser parser) {
        this.parser = parser;
    }

    @Override
    public Message convert(String msg) {
        return parser.parse(msg);
    }
}
