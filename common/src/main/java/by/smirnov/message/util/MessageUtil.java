package by.smirnov.message.util;

import by.smirnov.enumeration.Role;
import by.smirnov.message.Message;
import by.smirnov.message.enumeration.Type;

public class MessageUtil {
    private static String greetingTemplae = "Hello, %s. You are registered in the system. " +
            "Ask your question to our agents and they will answer soon";
    private static String agentGreetingTemplate = "Hello, %s. You are registered in the system. " +
            "Please wait for questions from clients.";

    private static String reportBacktTemplate = "%s %s";

    private static Message generateRegisterMessage() {

        return null;
    }

    public static Message createReportBackeMessage(String id, String name) {
        return new Message(Type.USER, String.format(reportBacktTemplate, id, name));
    }

    public static Message createGreetingMessage(String name, Role role) {
        String greeeting = role.equals(Role.CLIENT) ? String.format(greetingTemplae, name) : String.format(agentGreetingTemplate, name);
        return new Message(Type.CONTENT, greeeting);
    }

    public static String getNameFromRegistrationMessage(Message message) {
        String[] tokens = message.getText().trim().split(" ");
        return tokens[0];
    }

    public static int getMaxChatFromRegistrationMessage(Message message) {
        String[] tokens = message.getText().trim().split(" ");
        return Integer.parseInt(tokens[1]);
    }
}
