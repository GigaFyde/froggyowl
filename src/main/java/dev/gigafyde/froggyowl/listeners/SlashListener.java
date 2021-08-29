package dev.gigafyde.froggyowl.listeners;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashListener extends ListenerAdapter {

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        // Only accept commands from guilds
        if (event.getGuild() == null)
            return;
        switch (event.getName()) {
            case "apply":
                apply(event);
            case "appeal":
                appeal(event);
            case "ip":
                ip(event);
            case "store":
                store(event);
            case "support":
                support(event);
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }
    }

    public void support(SlashCommandEvent event) {
        event.reply("""
                Create a support ticket here!

                https://kingspond.enjin.com/support""").queue(); // This requires no permissions!
    }

    public void apply(SlashCommandEvent event) {
        event.reply("""
                Submit a staff application here!

                https://kingspond.enjin.com/staff-application""").queue(); // This requires no permissions!
    }

    public void appeal(SlashCommandEvent event) {
        event.reply("""
                Submit a ban appeal if you think you've been wrongfully banned!

                https://kingspond.enjin.com/banappeal""").queue(); // This requires no permissions!
    }

    public void ip(SlashCommandEvent event) {
        event.reply("The ip for our Minecraft server is **mc.kingspond.net**").queue(); // This requires no permissions!
    }

    public void store(SlashCommandEvent event) {
        event.reply("""
                Check out the King's Pond Store here!

                https://kingspond.enjin.com//shop""").queue(); // This requires no permissions!
    }


}
