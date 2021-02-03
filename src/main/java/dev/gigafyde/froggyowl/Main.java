package dev.gigafyde.froggyowl;

import dev.gigafyde.froggyowl.core.Client;
import dev.gigafyde.froggyowl.database.Database;
import dev.gigafyde.froggyowl.listeners.ServerLogListener;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static JDA jda;
    public static Database database;
    public static OkHttpClient httpClient = new OkHttpClient.Builder().protocols(Collections.singletonList(Protocol.HTTP_1_1)).build();

    public static void main(String[] args) throws Exception {
        database = new Database();
        jda = JDABuilder.createDefault(System.getenv("TOKEN"))
                .addEventListeners(new Client(), new ServerLogListener())
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .build();
        jda.awaitReady();
        ServerLogListener.serverlog = jda.getTextChannelById(System.getenv("SERVERLOG_CHANNEL_ID"));
        TimerTask timerTask = new StatusTimer();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 600, 900000);
        log.info("Started statustimer");
    }
}
