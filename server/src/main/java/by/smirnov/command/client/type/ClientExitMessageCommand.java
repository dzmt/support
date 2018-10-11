package by.smirnov.command.client.type;

import by.smirnov.command.client.ClientMessageCommand;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.Agent;
import by.smirnov.facade.Client;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientExitMessageCommand implements ClientMessageCommand {

    @Override
    public void handle(Message message, Client person) throws IOException, EncodeException {
        person.removeClientFromBase();

        if (person.getStatus().equals(Status.TALKING)) {
            Agent agent = person.unsubcribe();
            agent.send(message);
            agent.unsubcribe(person);
            agent.subscribeFromWaitingRoom();
        }

        person.setStatus(Status.UNREGISTERED);
        person.clearBuffer();

        person.getSession().close();
    }
}
