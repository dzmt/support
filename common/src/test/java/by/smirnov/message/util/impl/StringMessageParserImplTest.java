package by.smirnov.message.util.impl;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringMessageParserImplTest {
    private static StringMessageParserImpl parser;

    @BeforeAll
    static void beforeAll() {
        parser = new StringMessageParserImpl();
    }

    @Test
    void parseValidContentMessage() {
        assertEquals(new Message(Type.CONTENT, "Hello"), parser.parse("Hello"));
    }

    @Test
    void parseValidClientRegisterCommand() {
        Message message = parser.parse("/register Dzmitry");
        assertEquals(new Message(Type.REGISTER, "Dzmitry 1"), message);
    }

    @Test
    void parseValidAgentRegisterCommand() {
        Message message = parser.parse("/register Dzmitry");
        System.out.println(message.getText());
        assertEquals(new Message(Type.REGISTER, "Dzmitry 1"), message);
    }


    @Test
    void parseValidClientRegisterCommandWithOptionalParameters() {
        Message message = parser.parse("/register Dzmitry and some other text");
        assertEquals(new Message(Type.REGISTER, "Dzmitry 1"), message);
    }

    @Test
    void parseValidRegisterCommandWithOptionalParameters() {
        Message message = parser.parse("/register Dzmitry and some other text");
        assertEquals(new Message(Type.REGISTER, "Dzmitry 1"), message);
    }

    @Test
    void parseValidExtiCommand() {
        Message message = parser.parse("/exit");
        assertEquals(new Message(Type.EXIT, "exit"), message);
    }

}