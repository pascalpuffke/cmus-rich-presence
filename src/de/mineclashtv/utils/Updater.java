package de.mineclashtv.utils;

import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

import static de.mineclashtv.Main.printf;

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
                    printf("Song is stopped, waiting for playback...\n");
                else
                    printf("%s %s\n", discordRichPresence.details, discordRichPresence.state);

            // TODO figure out executors
            Thread.sleep(interval);
        }
    }
}
