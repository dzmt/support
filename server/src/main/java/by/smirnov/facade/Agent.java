package by.smirnov.facade;

import by.smirnov.enumeration.Status;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public interface Agent {

    Session getSession();

    String getId();

    Status getStatus();

    void setStatus(Status status);

    void send(Message message) throws IOException, EncodeException;

    void setName(String name);

    String getName();

    void setMaxCountActiveChat(int count);

    void addToBase();

    void subscribe(Client client);

    void unsubcribe(Client client);

    Client unsubscribeById(String id);

    void subscribeFromWaitingRoom() throws IOException, EncodeException;

    void notifySubscriber(Message message) throws IOException, EncodeException;

    void removeAgentFromBase();

    Collection<Client> unsubcribeAll();

    boolean ready();

    void sendBuffer(LinkedList<Message> messages) throws IOException, EncodeException;
}
