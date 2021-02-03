package dev.gigafyde.froggyowl.commands.basic;

import dev.gigafyde.froggyowl.core.command.Command;
import dev.gigafyde.froggyowl.core.command.CommandEvent;
import dev.gigafyde.froggyowl.utils.Emotes;
import net.dv8tion.jda.api.EmbedBuilder;

public class Ping extends Command {
    public Ping() {
        this.name = "ping";
        this.triggers = new String[]{"ping"};
    }

    public void execute(CommandEvent event) {
        long currentTime = System.currentTimeMillis();
        event.getTrigger().reply("Pinging...").mentionRepliedUser(false).queue(message -> {
            message.editMessage(new EmbedBuilder().setDescription(Emotes.HEARTBEAT + " " + event.getJDA().getGatewayPing() + " ms\n\n" + Emotes.PINGPONG + " " + (System.currentTimeMillis() - currentTime) + " ms").build()).override(true).queue();
        });
    }
}
