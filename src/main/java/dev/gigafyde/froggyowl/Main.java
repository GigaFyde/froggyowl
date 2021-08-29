package dev.gigafyde.froggyowl;

import dev.gigafyde.froggyowl.commands.CommandList;
import dev.gigafyde.froggyowl.core.Client;
import dev.gigafyde.froggyowl.listeners.ServerLogListener;
import dev.gigafyde.froggyowl.listeners.SlashListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;


public class Main {
    public static JDA jda;

    public static void main(String[] args) {
        try {
            Client client = new Client();
            jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                    .addEventListeners(client, new ServerLogListener(), new SlashListener())
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();
            new CommandList(client);
            jda.awaitReady();
            ServerLogListener.serverlog = jda.getTextChannelById(System.getenv("SERVERLOG_CHANNEL_ID"));
            Guild guild = jda.getGuildById("806138124987531264");
            assert guild != null;
            guild.upsertCommand(new CommandData("support", "Provides the link to the support form")).queue();
            guild.upsertCommand(new CommandData("apply", "Provides the link to the apply form")).queue();
            guild.upsertCommand(new CommandData("appeal", "Provides the link to the appeal form")).queue();
            guild.upsertCommand(new CommandData("ip", "Provides the server ip")).queue();
            guild.upsertCommand(new CommandData("store", "Provides the link to the store")).queue();
        } catch (Exception ignored) {
        }
    }
}
