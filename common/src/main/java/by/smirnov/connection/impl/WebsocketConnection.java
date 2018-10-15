package by.smirnov.connection.impl;
import by.smirnov.connection.IncomingConnection;
import by.smirnov.connection.OutboundConnection;
import by.smirnov.message.Message;
import by.smirnov.message.converter.MessageJSONConverter;
import by.smirnov.message.converter.impl.MessageJSONConverterImpl;
import by.smirnov.message.util.impl.MessageGSONParser;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;


import javax.websocket.*;

@ClientEndpoint
public class WebsocketConnection implements OutboundConnection, IncomingConnection {
    private URI endpointURI;
    private Session userSession;
    private LinkedList<Message> messages = new LinkedList<>();
    private MessageJSONConverter converter = new MessageJSONConverterImpl(new MessageGSONParser());


    public WebsocketConnection(URI endpointURI) {
        this.endpointURI = endpointURI;
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("You are disconnected from server. Goodbye");
    }

    @OnError
    public void onError(Session session, Throwable thr) {
        System.out.println("Server is not available now. Press enter for exit. Goodbye");
    }

    @Override
    public void connectToServer() throws DeploymentException, IOException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.connectToServer(this, endpointURI);
    }

    @OnMessage
    public void onMessage(String msg) {
        Message message = converter.fromJson(msg);
        messages.add(message);
    }

    @Override
    public void send(Message message) throws IOException {
        String gson = converter.toJson(message);
        userSession.getBasicRemote().sendText(gson);
    }

    @Override
    public boolean isOpen() throws IOException {
        return userSession.isOpen();
    }

    @Override
    public Message receive() throws IOException {
        return messages.poll();
    }

    @Override
    public boolean available() throws IOException {
        return messages.size() > 0;
    }

    @Override
    public void close() throws IOException {
        userSession.close();
    }
}