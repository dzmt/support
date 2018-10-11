package by.smirnov.message.converter.impl;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.message.util.StringMessageParser;
import by.smirnov.message.util.impl.StringMessageParserImpl;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class StringMessageConverterImplTest {
    @Test
    void convert() {
        StringMessageParser parser = new StringMessageParserImpl();
//        StringMessageParser parser = mock(StringMessageParserImpl.class);
//        when(parser.parse("Hello")).thenReturn(new Message(Type.CONTENT, "Hello"));

        StringMessageConverterImpl converter = new StringMessageConverterImpl(parser);

        assertEquals(new Message(Type.CONTENT, "Hello"), converter.convert("Hello"));
    }

}