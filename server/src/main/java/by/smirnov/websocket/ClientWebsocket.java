package by.smirnov.websocket;

import by.smirnov.base.UserBase;
import by.smirnov.coder.MessageDecoder;
import by.smirnov.coder.MessageEncoder;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.Agent;
import by.smirnov.hander.DirectorChainHandler;
import by.smirnov.message.Message;
import by.smirnov.facade.Client;
import by.smirnov.message.enumeration.Type;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;

import static by.smirnov.enumeration.Status.*;

@ServerEndpoint(value="/client", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class ClientWebsocket implements Client {

    private static final Logger logger = LogManager.getLogger(ClientWebsocket.class);

    private static UserBase base = new UserBase();

    private Session session;
    private Status status;
    private String name;
    private int maxActiveChats;

    private Agent interlocutor;
    private LinkedList<Message> buffer;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("connection is established with person id[ " + session.getId() + "]");

        this.session = session;
        status = UNREGISTERED;
        interlocutor = null;
        buffer = new LinkedList<>();

        try {
            session.getBasicRemote().sendObject(new Message(Type.CONTENT, "You have connected to the system. " +
                    "For registration enter command '/register <your_name>"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }

        session.addMessageHandler(DirectorChainHandler.buildClientChain(this));
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        logger.info("user close connection with server");

        if (!status.equals(UNREGISTERED)) {
            Message exit = new Message(Type.EXIT, "exit");
            exit.setFrom(getId());

            removeClientFromBase();
            clearBuffer();

            if (status.equals(TALKING)) {
                try {
                    Agent agent = unsubcribe();
                    agent.send(exit);
                    agent.unsubcribe(this);
                    agent.subscribeFromWaitingRoom();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            }

            setStatus(UNREGISTERED);
        }
        logger.info("client[" + getId() + "] status[" + getStatus() + "] - safety exit.");
    }

    @OnError
    public void error(Session session, Throwable error) {
        logger.error(error);
    }

    @Override
    public void subscribe(Agent agent) {
        interlocutor = agent;
    }

    @Override
    public boolean subscribe() throws IOException, EncodeException {
        String template = "%s %s";
        if (base.hasReadyAgent()) {
            Agent agent = base.getReadyAgent();
            interlocutor = agent;
            agent.subscribe(this);
            this.send(new Message(Type.INTERLOCUTOR, String.format(template, agent.getId(), agent.getName())));
            agent.send(new Message(Type.INTERLOCUTOR, String.format(template, getId(), getName())));
            setStatus(TALKING);
            return true;
        } else {
            status = PENDING;
            base.addToWaitingRoom(this);
            return false;
        }
    }

    @Override
    public Agent unsubcribe() {
        Agent agent = interlocutor;
        interlocutor = null;
        status = SLEEPING;
        return agent;
    }

    @Override
    public void notifySubscriber(Message message) throws IOException, EncodeException {
        interlocutor.send(message);
    }

    @Override
    public boolean hasActiveChat() {
        return false;
    }

    @Override
    public void writeBuffer(Message message) {
        buffer.add(message);
        logger.info("client[" + getId() + "] status[" + getStatus() + "] add message to buffer - count of messages: " + buffer.size());
    }

    @Override
    public LinkedList<Message> clearBuffer() {
        LinkedList<Message> clear = new LinkedList<>(buffer);
        buffer.clear();
        return clear;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public String getId() {
        return session.getId();
    }

    @Override
    public void send(Message message) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void removeClientFromBase() {
        base.removeClient(this);
    }

    @Override
    public void removeFromWaitingRoom() {
        base.removeFromWaitingRoom(this);
    }

    @Override
    public void setMaxCountActiveChat(int count) {
        maxActiveChats = count;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void addToBase() {
        base.addClient(this);
    }
}