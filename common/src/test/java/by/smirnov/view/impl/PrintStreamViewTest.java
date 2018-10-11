package by.smirnov.view.impl;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.messenger.Messenger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class PrintStreamViewTest {
//    private static PipedOutputStream outputStream;
//    private static PipedInputStream pipedInputStream;
//    private static BufferedReader reader;
//    private static PrintStream stream;
//
//    @BeforeEach
//     void beforeEach(){
//        try {
//            outputStream = new PipedOutputStream();
//            pipedInputStream = new PipedInputStream(outputStream);
//            reader = new BufferedReader(new InputStreamReader(pipedInputStream));
//            stream = new PrintStream(outputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @AfterEach
//    void afterEach() {
//        try {
//            outputStream.close();
//            pipedInputStream.close();
//            reader.close();
//            stream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    void showContentMessage() {
//        Messenger messenger = mock(Messenger.class);
//        PrintStreamView view = new PrintStreamView(messenger, stream);
//
//        try {
//            view.show(new Message(Type.CONTENT, "Hello"));
//            String out = reader.readLine();
//
//            assertEquals("Hello", out);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void showEmptyContentMessage(){
//        PrintStream mockStream = mock(PrintStream.class);
//        Messenger messenger = mock(Messenger.class);
//        PrintStreamView view = new PrintStreamView(messenger, stream);
//
//        view.show(new Message(Type.CONTENT, ""));
//
//        verifyNoMoreInteractions(mockStream);
//    }

}