package by.smirnov.message.registry;

import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class MessageRegistry {
    private String path;
    private Properties properties;
    private HashMap<String, Message> registry;

    public MessageRegistry(String path) {
        this.path = path;
    }

    public void load() throws IOException{
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        properties = new Properties();
        properties.load(classLoader.getResourceAsStream(path));
    }

    public void createRegistry() {
        registry = new HashMap<>();
        properties
                .stringPropertyNames()
                .stream()
                .forEach(key -> {
                    String text = properties.getProperty(key);
                    Message message = createContentMessage(text);
                    registry.put(key, message);
                });
    }

    public Message getMessage(String name) {
        return registry.get(name);
    }

    private Message createContentMessage(String text) {
        return new Message(Type.CONTENT, text);
    }


}
