package dev.gigafyde.froggyowl.core;

import dev.gigafyde.froggyowl.core.command.CommandHandler;
import dev.gigafyde.froggyowl.core.command.CommandRegistry;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Client extends ListenerAdapter {
    private final CommandRegistry registry = new CommandRegistry();
    private final CommandHandler handler;

    public Client() {
        handler = new CommandHandler(this);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        handler.handle(event);
    }

    public boolean isOwner(User user) {
        return System.getenv("DISCORD_BOT_OWNERID").equals(user.getId());
    }

    public String getPrefix() {
        return System.getenv("DISCORD_BOT_PREFIX");
    }

    public CommandRegistry getCommandRegistry() {
        return registry;
    }
}
