package dev.gigafyde.froggyowl;

import dev.gigafyde.froggyowl.commands.CommandList;
import dev.gigafyde.froggyowl.core.Client;
import dev.gigafyde.froggyowl.listeners.ServerLogListener;
import java.util.Collections;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;


public class Main {
    public static JDA jda;
    public static OkHttpClient httpClient = new OkHttpClient.Builder().protocols(Collections.singletonList(Protocol.HTTP_1_1)).build();

    public static void main(String[] args) {
        try {
            Client client = new Client();
            jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                    .addEventListeners(client, new ServerLogListener())
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();
            new CommandList(client);
            jda.awaitReady();
            ServerLogListener.serverlog = jda.getTextChannelById(System.getenv("SERVERLOG_CHANNEL_ID"));
        } catch (Exception ignored) {
        }
    }
}
