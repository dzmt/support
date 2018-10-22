package by.smirnov.command.agent.type;

import by.smirnov.command.agent.AgentMessageCommand;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.User;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.Collection;

public class AgentExitMessageCommand implements AgentMessageCommand {

    @Override
    public void handle(Message message, User person) throws IOException, EncodeException {
//        person.removeAgentFromBase();
//
//        if (person.getStatus().equals(Status.TALKING)) {
//            Collection<Client> clients = person.unsubcribeAll();
//            clients.forEach( client -> {
//                try {
//                    client.unsubcribe();
//                    client.send(message);
//                    client.setStatus(Status.SLEEPING);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (EncodeException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//
//        person.setStatus(Status.UNREGISTERED);
//
//        person.close();
    }
}
