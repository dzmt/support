package by.smirnov.model;

import by.smirnov.enumeration.Status;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;
import by.smirnov.storage.MessageStorage;
import by.smirnov.websocket.ClientWebsocket;

import javax.websocket.EncodeException;
import java.io.IOException;

import static by.smirnov.enumeration.Status.UNREGISTERED;


public class ClientRestUser extends ClientWebsocket {


    @Override
    public void send(Message message) throws IOException, EncodeException {
        MessageStorage.putMessageForUser(getId(), message);
    }

    @Override
    public void close() {
        if (!getStatus().equals(UNREGISTERED)) {
            this.removeFromBase();
            MessageStorage.removeStorage(getId());

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
    }


}
