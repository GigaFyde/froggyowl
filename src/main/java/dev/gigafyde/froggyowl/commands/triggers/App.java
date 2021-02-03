package dev.gigafyde.froggyowl.commands.triggers;

import dev.gigafyde.froggyowl.core.command.Command;
import dev.gigafyde.froggyowl.core.command.CommandEvent;

public class App extends Command {
    public App() {
        this.name = "app";
        this.triggers = new String[]{"app"};
    }

    public void execute(CommandEvent event) {
        event.getTrigger().reply("Submit a staff application here!\n\n" +
                "https://froggyowlnetwork.enjin.com/staff-application").mentionRepliedUser(false).queue();
    }
}
