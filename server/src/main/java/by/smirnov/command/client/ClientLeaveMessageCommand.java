package by.smirnov.command.client.type;

import by.smirnov.command.client.ClientMessageCommand;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.Agent;
import by.smirnov.facade.Client;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientLeaveMessageCommand implements ClientMessageCommand{

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {

        if (person.getStatus().equals(Status.PENDING)) {
            person.removeFromWaitingRoom();
            person.clearBuffer();
            person.send(new Message(Type.CONTENT, "You are left waiting room."));
        } else if (person.getStatus().equals(Status.TALKING)) {
            person.notifySubscriber(message);
            Agent agent = person.unsubcribe();
            agent.unsubcribe(person);
            agent.subscribeFromWaitingRoom();
            person.send(new Message(Type.CONTENT, "You are left chats with " + agent.getName() + "."));
        }
        person.setStatus(Status.SLEEPING);
    }
}
