package dev.gigafyde.froggyowl.commands.triggers;

import dev.gigafyde.froggyowl.core.command.Command;
import dev.gigafyde.froggyowl.core.command.CommandEvent;

public class Ip extends Command {
    public Ip() {
        this.name = "ip";
        this.triggers = new String[]{"ip"};
    }

    public void execute(CommandEvent event) {
        event.getTrigger().reply("The ip for our Minecraft server is **mc.kingspond.net**").mentionRepliedUser(false).queue();
    }
}
