package dev.gigafyde.froggyowl.core.command;

import dev.gigafyde.froggyowl.utils.Emotes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Command {
    private static final Logger log = LoggerFactory.getLogger(Command.class);
    protected String name = null;
    protected String[] triggers = null;
    protected boolean ownerOnly = false;
    protected boolean guildOnly = true;

    protected abstract void execute(CommandEvent event);

    public final void run(CommandEvent event) {
        try {
            if (ownerOnly && !event.getClient().isOwner(event.getAuthor())) return;
            if (guildOnly && !event.isFromGuild()) {
                event.getTrigger().getChannel().sendMessage(Emotes.ERROR + " **This command cannot be used in Direct Messages.**").queue();
                return;
            }
            execute(event);
        } catch (Exception e) {
            event.getChannel().sendMessage("**Apologies, something went wrong internally**").queue();
            log.warn("Unexpected exception!", e);
        }
    }
}
