package by.smirnov.command.client;

import by.smirnov.command.MessageCommand;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.User;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.message.registry.MessageRegistry;

import javax.websocket.EncodeException;
import java.io.IOException;

public class ClientLeaveMessageCommand implements MessageCommand{

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {

        if (person.getStatus().equals(Status.PENDING)) {
            person.removeFromWaitingRoom();
            person.readBuffer();
            person.send(MessageRegistry.getMessage("client.pending.left"));

        } else if (person.getStatus().equals(Status.TALKING)) {

            User agent = person.getListInterlocutors().stream()
                    .filter(interlocuter -> interlocuter.getId().equals(message.getTo()))
                    .findAny()
                    .get();

            agent.send(message);
            person.unsubcribe(agent);
            agent.unsubcribe(person);
            agent.subscribeReady();
            person.send(new Message(Type.CONTENT, "You are left chats with " + agent.getName() + "."));
        }

        person.setStatus(Status.SLEEPING);
    }
}
