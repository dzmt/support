package by.smirnov.view.impl;

import by.smirnov.message.Message;
import by.smirnov.messenger.Messenger;
import by.smirnov.view.View;

import java.io.PrintStream;

public class PrintStreamView implements View {
    private Messenger messenger;
    private PrintStream stream;
    private String template = "[%s] %s";

    public PrintStreamView(Messenger messenger, PrintStream stream) {
        this.messenger = messenger;
        this.stream = stream;
    }

    @Override
    public void show(Message message) {
        switch (messenger.getStatus()) {
            case UNREGISTERED:
            case READY: {
                stream.println(message.getText());
            }
            break;
            case TALKING: {
                if (message.getFrom() != null) {
                    String show = String.format(template, messenger.getInterlocutor().getName(), message.getText());
                    stream.println(show);
                } else {
                    stream.println(message.getText());
                }

            }
            break;
        }
    }
}
