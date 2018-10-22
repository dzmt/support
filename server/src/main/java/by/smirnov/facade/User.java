package by.smirnov.facade;

import by.smirnov.enumeration.Status;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.message.util.MessageUtil;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static by.smirnov.enumeration.Status.TALKING;
import static by.smirnov.enumeration.Status.UNREGISTERED;

public abstract class User {

    public abstract Status getStatus();

    public abstract void setStatus(Status status);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getId();

    public abstract void setId(String id);

    public abstract int getMaxCountActiveChat();

    public abstract void setMaxCountActiveChat(int maxCountActiveChat);

    public abstract Set<String> getInterlocutors();

    public abstract void setInterlocutors(Set<String> interlocutors);

    public abstract Session getSession();

    public abstract void addToBase();

    public abstract void addToWaitingRoom();

    public abstract void send(Message message) throws IOException, EncodeException;

    public abstract void subscribe(User agent);

    public void subscribeSequence(User person) throws IOException, EncodeException {
        this.subscribe(person);
        this.send(MessageUtil.createInterlocutorMessage(person.getId(), person.getName()));
        this.setStatus(TALKING);
        person.send(MessageUtil.createInterlocutorMessage(getId(), getName()));
        person.subscribe(this);
        person.setStatus(TALKING);
    }

    public abstract void unsubcribe(User agent);

    public abstract void notifySubscriber(Message message) throws IOException, EncodeException;

    public abstract boolean subscribeReady() throws IOException, EncodeException;

    public abstract List<User> getListInterlocutors();

    public abstract int getQuantityInterlocutors();

    public abstract boolean ready();

    public abstract void removeFromBase();

    public abstract void removeFromWaitingRoom();

    public void close() throws IOException {
        if (!getStatus().equals(UNREGISTERED)) {
            this.removeFromBase();

            if (getQuantityInterlocutors() > 0) {
                Message message = new Message(Type.EXIT, "exit");
                message.setFrom(getId());
                getListInterlocutors().forEach(interlocutor -> {
                    unsubcribe(interlocutor);
                    interlocutor.unsubcribe(this);
                    try {
                        interlocutor.send(message);
                        interlocutor.subscribeReady();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (EncodeException e) {
                        e.printStackTrace();
                    }

                });
            }
            setStatus(Status.UNREGISTERED);
        }
        getSession().close();
    }

    public abstract void writeBuffer(Message message);

    public abstract LinkedList<Message> readBuffer();
}
