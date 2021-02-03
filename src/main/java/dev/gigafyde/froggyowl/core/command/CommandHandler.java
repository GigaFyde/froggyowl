package dev.gigafyde.froggyowl.core.command;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import dev.gigafyde.froggyowl.core.Client;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandHandler {
    private static final Executor POOL = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("CommandThread-%d").build());
    private static final Logger log = LoggerFactory.getLogger("CommandHandler");

    private final String[] mentions;
    private final Client client;

    public CommandHandler(Client client) {
        this.client = client;
        String id = System.getenv("DISCORD_BOT_ID");
        mentions = new String[]{
                "<@" + id + ">",
                "<@!" + id + ">"
        };
    }

    public void handle(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String content = event.getMessage().getContentRaw();
        if (content.startsWith(client.getPrefix())) {
            handleCommand(content.substring(client.getPrefix().length()).trim(), event.getMessage());
        } else if (content.startsWith(mentions[0])) {
            handleMention(content.substring(mentions[0].length()).trim(), event.getMessage());
        } else if (content.startsWith(mentions[1])) {
            handleMention(content.substring(mentions[1].length()).trim(), event.getMessage());
        }
    }

    private void handleMention(String cmdAndArgs, Message trigger) {
        if (cmdAndArgs.isEmpty()) {
            trigger.getChannel().sendMessage("**My prefix is:** `" + client.getPrefix() + "`").queue();
            return;
        }
        handleCommand(cmdAndArgs, trigger);
    }

    private void handleCommand(String cmdAndArgs, Message trigger) {
        String[] parts = cmdAndArgs.split("\\s+", 2);
        Command command = client.getCommandRegistry().getCommand(parts[0].toLowerCase());
        if (command != null) {
            POOL.execute(() -> {
                CommandEvent event = new CommandEvent(client, trigger, parts.length == 1 ? "" : parts[1]);
                try {
                    command.run(event);
                } catch (Throwable t) {
                    log.error("COMMAND FAILED", t);
                }
            });
        }
    }
}
