package by.smirnov.facade;

import by.smirnov.enumeration.Status;
import by.smirnov.message.Message;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedList;

public interface Client {

//    void exit(Message message);
//
    void notifySubscriber(Message message) throws IOException, EncodeException;
//
//    void unsubscribe(Client agent);
//
    Agent unsubcribe();

    boolean subscribe() throws IOException, EncodeException;

    boolean hasActiveChat();

    void subscribe(Agent agent);

    void writeBuffer(Message message);

    LinkedList<Message> clearBuffer();

    Session getSession();

    String getId();

    void send(Message message) throws IOException, EncodeException;

//
//    void setRole(Role role);
//
//    int getMaxCountActiveChat();
//
    void setMaxCountActiveChat(int count);
//
//    boolean ready();
//
    Status getStatus();

    void setStatus(Status status);
//
//    void pending(Message message);

    String getName();

    void setName(String name);

    void addToBase();

    void removeClientFromBase();

    void removeFromWaitingRoom();

//
//    Message reportBack();
//
//    Message report();

}
