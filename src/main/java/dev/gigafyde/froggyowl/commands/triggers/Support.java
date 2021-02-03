package dev.gigafyde.froggyowl.commands.triggers;

import dev.gigafyde.froggyowl.core.command.Command;
import dev.gigafyde.froggyowl.core.command.CommandEvent;

public class Support extends Command {
    public Support() {
        this.name = "support";
        this.triggers = new String[]{"support"};
    }

    public void execute(CommandEvent event) {
        event.getTrigger().reply("Create a support ticket here!\n\n" +
                "https://froggyowlnetwork.enjin.com/support").mentionRepliedUser(false).queue();
    }
}
