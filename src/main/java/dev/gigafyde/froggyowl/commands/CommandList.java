package dev.gigafyde.froggyowl.commands;

import dev.gigafyde.froggyowl.commands.basic.Ping;
import dev.gigafyde.froggyowl.core.Client;
import dev.gigafyde.froggyowl.core.command.CommandRegistry;

public class CommandList extends CommandRegistry {
    public CommandList(Client client) {
        CommandRegistry registry = client.getCommandRegistry();

        //basic commands
        registry.addCommand(new Ping());
    }
}
