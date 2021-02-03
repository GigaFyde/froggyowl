package dev.gigafyde.froggyowl.commands.triggers;

import dev.gigafyde.froggyowl.core.command.Command;
import dev.gigafyde.froggyowl.core.command.CommandEvent;

public class FoStore extends Command {
    public FoStore() {
        this.name = "fostore";
        this.triggers = new String[]{"fostore"};
    }

    public void execute(CommandEvent event) {
        event.getTrigger().reply("Check out the FroggyOwl Store here!\n\n" +
                "https://froggyowlnetwork.enjin.com/shop").mentionRepliedUser(false).queue();
    }
}
