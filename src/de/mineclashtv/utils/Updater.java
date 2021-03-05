package de.mineclashtv.utils;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class Updater {

    public static boolean run = false;

    public static void updateLoop(boolean debug, int interval, String iconText) throws InterruptedException {
        Parser parser = new Parser();

        while(run) {
            DiscordRichPresence discordRichPresence = DiscordRichPresenceFactory.getRichPresence(parser, iconText);

            if (discordRichPresence == null) // Nothing currently playing - don't display anything.
                DiscordRPC.discordClearPresence();
            else
                DiscordRPC.discordUpdatePresence(discordRichPresence);

            if(debug)
                if (discordRichPresence == null)
                    System.out.println("Song is stopped, waiting for playback...");
                else
                    System.out.printf("%s %s\n", discordRichPresence.details, discordRichPresence.state);

            // TODO figure out executors
            Thread.sleep(interval);
        }
    }
}
