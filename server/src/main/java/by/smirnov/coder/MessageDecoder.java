package by.smirnov.coder;

import by.smirnov.message.Message;
import by.smirnov.message.converter.MessageJSONConverter;
import by.smirnov.message.converter.impl.MessageJSONConverterImpl;
import by.smirnov.message.util.impl.MessageGSONParser;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {
    private MessageJSONConverter converter;


    @Override
    public void init(EndpointConfig endpointConfig) {
       converter = new MessageJSONConverterImpl(new MessageGSONParser());
    }

    @Override
    public void destroy() {

    }

    @Override
    public Message decode(String s) throws DecodeException {
        Message message = converter.fromJson(s);
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }
}
