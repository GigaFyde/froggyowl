package dev.gigafyde.froggyowl.commands.triggers;

import dev.gigafyde.froggyowl.core.command.Command;
import dev.gigafyde.froggyowl.core.command.CommandEvent;

public class FoStore extends Command {
    public FoStore() {
        this.name = "kpstore";
        this.triggers = new String[]{"kpstore"};
    }

    public void execute(CommandEvent event) {
        event.getTrigger().reply("""
                Check out the King's Pond Store here!

                https://kingspond.enjin.com//shop""").mentionRepliedUser(false).queue();
    }
}
