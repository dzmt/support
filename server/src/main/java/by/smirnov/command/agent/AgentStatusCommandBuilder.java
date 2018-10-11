package by.smirnov.command.agent;

import by.smirnov.message.enumeration.Type;

public class AgentStatusCommandBuilder {
    private AgentStatusCommand command;

    public AgentStatusCommandBuilder setStatusCommand(AgentStatusCommand command) {
        this.command = command;
        return this;
    }

    public AgentStatusCommandBuilder setMessageCommand(Type type, AgentMessageCommand command) {
        this.command.setCommand(type, command);
        return this;
    }

    public AgentStatusCommand getCommand() {
        return command;
    }
}
