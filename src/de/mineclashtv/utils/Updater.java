package de.mineclashtv.utils;

import de.mineclashtv.Main;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class Updater {

    private static final Parser parser = Main.parser;

    public static DiscordRichPresence update() {
        DiscordRichPresence discordRichPresence = null;

        String iconText = Main.iconText;
        if(parser.getTag("status").equals("playing")) {
            if(parser.getTag("tag title").equals("")) { // Song isn't tagged properly; show filename
                String fileName = parser.getTag("file");
                discordRichPresence = new DiscordRichPresence.Builder(fileName).setBigImage("icon", iconText).build();

                System.out.printf("Warning: Song not tagged properly: %s\n", fileName);
            } else {
                String album = parser.getTag("tag album");
                String date = parser.getTag("tag date");
                String artist = parser.getTag("tag artist");
                String title = parser.getTag("tag title");

                discordRichPresence = new DiscordRichPresence
                        .Builder("from " + album + " (" + date + ")")
                        .setDetails(artist + " - " + title)
                        .setBigImage("icon", iconText).build();
            }
        } else {
           DiscordRPC.discordClearPresence();
        }

        if(Main.debug) {
            try {
                System.out.printf("%s %s\n", discordRichPresence.details, discordRichPresence.state);
            } catch(NullPointerException e) {
                // A NullPointerException may get thrown when DiscordRPC isn't properly initialized and the song is stopped
                System.out.printf("Song is stopped, waiting for playback...\n");
            }
        } else
            return discordRichPresence;

        return null;
    }
}
