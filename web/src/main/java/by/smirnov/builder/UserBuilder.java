package by.smirnov.builder;

import by.smirnov.enumeration.Status;
import by.smirnov.facade.User;
import by.smirnov.model.AgentRestUser;
import by.smirnov.model.ClientRestUser;

import javax.websocket.EncodeException;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;

public class UserBuilder {
    private static final int CLIENT_MAX_ACTIVE_CHATS = 1;

    public static User createClient(String name) {
        User client = new ClientRestUser();

        client.setName(name);
        client.setId(UUID.randomUUID().toString());
        client.setStatus(Status.SLEEPING);
        client.setMaxCountActiveChat(CLIENT_MAX_ACTIVE_CHATS);
        client.setInterlocutors(new HashSet<String>());
        client.addToBase();


        return client;
    }

    public static User createAgent(String name, int maxActiveChats) {
        User agent = new AgentRestUser();

        agent.setName(name);
        agent.setMaxCountActiveChat(maxActiveChats);
        agent.setId(UUID.randomUUID().toString());
        agent.setStatus(Status.SLEEPING);
        agent.setInterlocutors(new HashSet<String>());
        agent.addToBase();

        try {
            while (agent.subscribeReady());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }
        return agent;
    }

}
