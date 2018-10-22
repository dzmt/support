package by.smirnov.command;

import by.smirnov.message.enumeration.Type;

public class StatusCommandBuilder {
    private StatusCommand command;

    public StatusCommandBuilder setStatusCommand(StatusCommand command) {
        this.command = command;
        return this;
    }

    public StatusCommandBuilder setMessageCommand(Type type, MessageCommand command) {
        this.command.setCommand(type, command);
        return this;
    }

    public StatusCommand getCommand() {
        return command;
    }
}
