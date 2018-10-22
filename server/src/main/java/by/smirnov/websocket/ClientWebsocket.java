package by.smirnov.websocket;

import by.smirnov.base.UserBase;
import by.smirnov.coder.MessageDecoder;
import by.smirnov.coder.MessageEncoder;
import by.smirnov.enumeration.Status;
import by.smirnov.facade.User;
import by.smirnov.handler.DirectorChainHandler;
import by.smirnov.message.Message;
import by.smirnov.message.registry.MessageRegistry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static by.smirnov.enumeration.Status.*;

@ServerEndpoint(value="/client", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class ClientWebsocket extends User {

    private static final Logger logger = LogManager.getLogger(ClientWebsocket.class);

    private static UserBase base = new UserBase();

    private transient Session session;

    private Status status;
    private String name;
    private String id;
    private int maxCountActiveChat;

    private Set<String> interlocutors;
    private LinkedList<Message> buffer;

    public ClientWebsocket() {
        interlocutors = new HashSet<>();
        buffer = new LinkedList<>();
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("connection is established with person id[ " + session.getId() + "]");

        this.session = session;
        status = UNREGISTERED;

        id = UUID.randomUUID().toString();

        try {
            send(MessageRegistry.getMessage("connection"));
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

        if (!getStatus().equals(UNREGISTERED)) {
            try {
                this.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        logger.info("client[" + getId() + "] status[" + getStatus() + "] - safety exit.");
    }

    @OnError
    public void error(Session session, Throwable error) {
        logger.error(error);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setMaxCountActiveChat(int count) {
        this.maxCountActiveChat = count;
    }

    @Override
    public int getMaxCountActiveChat() {
        return maxCountActiveChat;
    }

    @Override
    public Set<String> getInterlocutors() {
        return interlocutors;
    }

    @Override
    public void setInterlocutors(Set<String> interlocutors) {
        this.interlocutors = interlocutors;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void addToBase() {
        base.addClient(this);
    }

    @Override
    public void addToWaitingRoom() {
        base.addToWaitingRoom(this);
    }

    @Override
    public void send(Message message) throws IOException, EncodeException {
        session.getBasicRemote().sendObject(message);
    }

    @Override
    public void subscribe(User agent) {
        interlocutors.add(agent.getId());
    }

    @Override
    public void unsubcribe(User agent) {
        interlocutors.remove(agent.getId());
    }

    @Override
    public void notifySubscriber(Message message) throws IOException, EncodeException {
        if (interlocutors.contains(message.getTo())) {
            base.getAgentById(message.getTo()).send(message);
        }
    }

    @Override
    public boolean subscribeReady() throws IOException, EncodeException {
        User readyAgent = base.getReadyAgent();
        if (readyAgent != null) {
            subscribeSequence(readyAgent);
            while (!buffer.isEmpty())
                readyAgent.send(buffer.poll());
            return true;
        }
        return false;
    }

    @Override
    public List<User> getListInterlocutors() {
        return base.getAgents().stream()
                .filter(user ->
                    interlocutors.contains(user.getId()))
                .collect(Collectors.toList());
    }

    public int getQuantityInterlocutors() {
        return interlocutors.size();
    }

    @Override
    public boolean ready() {
        return status.equals(PENDING);
    }

    @Override
    public void removeFromBase() {
        base.removeClient(this);
    }

    @Override
    public void removeFromWaitingRoom() {
        base.removeFromWaitingRoom(this);
    }

    @Override
    public void writeBuffer(Message message) {
        buffer.add(message);
    }

    @Override
    public LinkedList<Message> readBuffer() {
        LinkedList<Message> msgBuffer = new LinkedList<>(buffer);
        buffer.clear();
        return msgBuffer;
    }
}