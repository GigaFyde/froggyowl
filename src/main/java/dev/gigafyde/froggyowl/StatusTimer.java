package dev.gigafyde.froggyowl;

import java.time.Instant;
import java.util.TimerTask;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

public class StatusTimer extends TimerTask {

    @Override
    public void run() {
        sendEmbed();
    }

    private static void sendEmbed() {
        JDA jda = Main.jda;
        JSONObject apiResult = getStatus();
        String onlineStatus = apiResult.get("online").toString().equals("true") ? "yes" : "no";
        String playerCount = "";
        if (!onlineStatus.equals("no"))
            playerCount = apiResult.getJSONObject("players").get("online").toString();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("FroggyOwl Status")
                .setDescription("Online: " + onlineStatus + "\n Players online: " + playerCount)
                .setFooter("Updates every 15 minutes | Last updated at")
                .setTimestamp(Instant.now());

        try {
            Message embedMessage = jda.getTextChannelById(System.getenv("STATUS_CHANNEL_ID")).retrieveMessageById(Main.database.getStatusMessageId()).complete();
            embedMessage.editMessage(builder.build()).override(true).queue();
        } catch (Exception e) {
            System.out.print(e);
            jda.getTextChannelById(System.getenv("STATUS_CHANNEL_ID")).sendMessage(builder.build()).override(true).queue(message1 -> Main.database.setStatusMessageId(message1.getIdLong()));
        }
    }

    public static JSONObject getStatus() {
        try {
            OkHttpClient client = Main.httpClient;
            Response response = client.newCall(
                    new Request.Builder()
                            .url("https://api.mcsrvstat.us/2/" + System.getenv("SERVER_IP"))
                            .build()).execute();
            return new JSONObject(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
