package dev.gigafyde.froggyowl.commands.triggers;

import dev.gigafyde.froggyowl.core.command.Command;
import dev.gigafyde.froggyowl.core.command.CommandEvent;

public class Appeal extends Command {
    public Appeal() {
        this.name = "appeal";
        this.triggers = new String[]{"appeal"};
    }

    public void execute(CommandEvent event) {
        event.getTrigger().reply("Submit a ban appeal if you think you've been wrongfully banned!\n\n" +
                "https://froggyowlnetwork.enjin.com/banappeal").mentionRepliedUser(false).queue();
    }
}
