package dev.gigafyde.froggyowl;

import dev.gigafyde.froggyowl.listeners.ServerLogListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {
    public static JDA jda;

    public static void main(String[] args) throws Exception {
        jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                .addEventListeners(new ServerLogListener())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        jda.awaitReady();
        ServerLogListener.serverlog = jda.getTextChannelById("806141555542065202");
    }
}
