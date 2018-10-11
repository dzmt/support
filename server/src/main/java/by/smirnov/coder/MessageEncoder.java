package by.smirnov.coder;


import by.smirnov.message.Message;
import by.smirnov.message.converter.MessageJSONConverter;
import by.smirnov.message.converter.impl.MessageJSONConverterImpl;
import by.smirnov.message.util.impl.MessageGSONParser;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class MessageEncoder implements Encoder.Text<Message>{
    private MessageJSONConverter converter;

    @Override
    public String encode(Message message) throws EncodeException {
        return converter.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        converter = new MessageJSONConverterImpl(new MessageGSONParser());
    }

    @Override
    public void destroy() {

    }
}
