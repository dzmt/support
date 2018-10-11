package by.smirnov.websocket;

import by.smirnov.base.UserBase;
import by.smirnov.coder.MessageDecoder;
import by.smirnov.coder.MessageEncoder;
import by.smirnov.enumeration.Role;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import static by.smirnov.enumeration.Status.SLEEPING;
import static by.smirnov.enumeration.Status.TALKING;
import static by.smirnov.enumeration.Status.UNREGISTERED;

@ServerEndpoint(value="/agent", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class AgentWebsocket implements Agent {

    private static final Logger logger = LogManager.getLogger(AgentWebsocket.class);
    private static UserBase base = new UserBase();

    private Session session;
    private Status status;
    private Role role;
    private String name;

    private int maxActiveChats;
    private HashMap<String, Client> interlocutors;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("connection is established with person id[ " + session.getId() + "]");

        this.session = session;
        status = UNREGISTERED;
        interlocutors = new HashMap<>();

        try {
            session.getBasicRemote().sendObject(new Message(Type.CONTENT, "You have connected to the system. " +
                    "For registration enter command '/register <your_name>'"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (EncodeException e) {
            e.printStackTrace();
        }

        session.addMessageHandler(DirectorChainHandler.buildAgentChain(this));
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        logger.info("agent close connection with server");
    }

    @OnError
    public void error(Session session, Throwable error) {
        logger.error(error);

        Message exit = new Message(Type.EXIT, "exit");
        exit.setFrom(getId());

        removeAgentFromBase();
        if (status.equals(Status.TALKING)) {
            Collection<Client> clients = unsubcribeAll();
            clients.forEach( client -> {
                try {
                    client.unsubcribe();
                    client.send(exit);
                    client.setStatus(Status.SLEEPING);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodeException e) {
                    e.printStackTrace();
                }
            });
        }

        setStatus(Status.UNREGISTERED);

        logger.info("agent[" + getId() + "] status[" + getStatus() + "] - safety exit.");
    }

    @Override
    public void notifySubscriber(Message message) throws IOException, EncodeException {
        interlocutors.get(message.getTo()).send(message);
    }

    @Override
    public void subscribe(Client client) {
        interlocutors.put(client.getId(), client);
        if (!status.equals(TALKING)) {
            status = TALKING;
        }
    }

    @Override
    public void unsubcribe(Client client) {
        interlocutors.remove(client.getId(), client);
        if (interlocutors.size() == 0) {
            status = SLEEPING;
        }
    }

    @Override
    public Client unsubscribeById(String id) {
        Client client = interlocutors.get(id);
        interlocutors.remove(id, client);
        if (interlocutors.size() == 0) {
            status = SLEEPING;
        }
        return client;
    }

    @Override
    public void removeAgentFromBase() {
        base.removeAgent(this);
    }

    @Override
    public Collection<Client> unsubcribeAll() {
        Collection<Client> clients = new ArrayList<>(interlocutors.values());
        interlocutors.clear();
        return clients;
    }

    @Override
    public void subscribeFromWaitingRoom() throws IOException, EncodeException {
        String template = "%s %s";
        if (base.hasWaitingClients()) {
            Client client = base.getWaitingClient();
            interlocutors.put(client.getId(), client);
            client.setStatus(TALKING);
            this.setStatus(TALKING);
            client.subscribe(this);
            client.send(new Message(Type.INTERLOCUTOR, String.format(template, getId(), getName())));
            send(new Message(Type.INTERLOCUTOR, String.format(template, client.getId(), client.getName())));
            sendBuffer(client.clearBuffer());
        }
    }

    @Override
    public void sendBuffer(LinkedList<Message> messages) throws IOException, EncodeException {
        while (!messages.isEmpty()) {
            send(messages.poll());
        }
    }

    @Override
    public boolean ready() {
        return maxActiveChats > interlocutors.size();
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void send(Message message) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(message);
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
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setMaxCountActiveChat(int count) {
        this.maxActiveChats = count;
    }

    @Override
    public void addToBase() {
        base.addAgent(this);
    }
}