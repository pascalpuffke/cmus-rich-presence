package de.mineclashtv.utils;

import de.mineclashtv.Main;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordRichPresenceFactory {

    /**
     * Creates a formatted <code>DiscordRichPresence</code> object with all available information.<br/>
     * If the currently playing song does not have a title tag, it will use the full filename instead.
     *
     * @param parser Main <code>Parser</code> object for fetching parameters
     * @return Formatted <code>DiscordRichPresence</code> object if a song is playing, <code>null</code> otherwise
     */
    public static DiscordRichPresence getRichPresence(Parser parser, String iconText) {
        if(parser.isPlaying()) {
            if(parser.hasTitle()) {
                String album = parser.getAlbum();
                String date = parser.getDate();
                String artist = parser.getArtist();
                String title = parser.getTitle();

                return new DiscordRichPresence
                        .Builder("from " + album + " (" + date + ")")
                        .setDetails(artist + " - " + title)
                        .setBigImage("icon", iconText)
                        .build();
            } else { // Song isn't tagged properly; show filename
                String filePath = parser.getPath();
                String fileName = parser.getNameFromFile();

                Main.printTagWarning(filePath);

                return new DiscordRichPresence
                        .Builder(fileName)
                        .setBigImage("icon", iconText)
                        .build();
            }
        } else {
            // Nothing is playing; handled in main class
            return null;
        }
    }
}
