package dev.gigafyde.froggyowl.commands;

import dev.gigafyde.froggyowl.commands.basic.Ping;
import dev.gigafyde.froggyowl.commands.triggers.App;
import dev.gigafyde.froggyowl.commands.triggers.Appeal;
import dev.gigafyde.froggyowl.commands.triggers.FoStore;
import dev.gigafyde.froggyowl.commands.triggers.Ip;
import dev.gigafyde.froggyowl.commands.triggers.Support;
import dev.gigafyde.froggyowl.core.Client;
import dev.gigafyde.froggyowl.core.command.CommandRegistry;

public class CommandList extends CommandRegistry {
    public CommandList(Client client) {
        CommandRegistry registry = client.getCommandRegistry();

        //basic commands
        registry.addCommand(new Ping());

        //Trigger commands
        registry.addCommand(new App());
        registry.addCommand(new Appeal());
        registry.addCommand(new FoStore());
        registry.addCommand(new Ip());
        registry.addCommand(new Support());
    }
}
