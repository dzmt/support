package by.smirnov.io;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

class StringInputDataTest {
    private String HELLO = "Hello";
    private BufferedReader HELLO_READER = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(HELLO.getBytes())));

    @Test
    void readLine() {
        StringInputData in = new StringInputData(HELLO_READER);
        try {
            assertEquals(HELLO, in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void available() {
        StringInputData in = new StringInputData(HELLO_READER);
        try {
            assertTrue(in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}