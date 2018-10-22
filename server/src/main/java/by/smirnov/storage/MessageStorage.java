package by.smirnov.storage;

import by.smirnov.message.Message;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class MessageStorage {
    private static ConcurrentHashMap<String, LinkedList<Message>> storage = new ConcurrentHashMap<>();

    public static LinkedList<Message> getUserMessagesById(String id) {
        LinkedList<Message> messages = new LinkedList<>(storage.get(id));
        storage.get(id).clear();
        return messages;
    }

    public static void putMessageForUser(String id, Message message) {
        if (storage.containsKey(id)) {
            storage.get(id).add(message);
        } else {
            LinkedList<Message> messages = new LinkedList<>();
            messages.add(message);
            storage.put(id, messages);
        }
    }

    public static void removeStorage(String id) {
        storage.remove(id, storage.get(id));
    }
}
