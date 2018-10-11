package by.smirnov.connection.impl;

import by.smirnov.connection.IncomingConnection;
import by.smirnov.io.StringInputData;
import by.smirnov.message.Message;
import by.smirnov.message.converter.StringMessageConverter;

import java.io.IOException;

public class IncomingConnectiontImpl implements IncomingConnection {

    private StringInputData in;
    private StringMessageConverter converter;

    public IncomingConnectiontImpl(StringInputData in, StringMessageConverter converter) {
        this.in = in;
        this.converter = converter;
    }

    public Message receive() throws IOException {
        Message message = converter.convert(in.readLine());
        return message;
    }

    public boolean available() throws IOException {
        return in.available();
    }
}
