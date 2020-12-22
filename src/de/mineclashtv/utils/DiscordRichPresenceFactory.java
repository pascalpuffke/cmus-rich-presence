package de.mineclashtv.utils;

import de.mineclashtv.Main;
import net.arikia.dev.drpc.DiscordRichPresence;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordRichPresenceFactory {

    public static Map<String, String> format = new HashMap<>();

    static {
        try {
            format.put("bottom", (String) Main.configurationFile.getValue("bottom_format"));
            format.put("top", (String) Main.configurationFile.getValue("top_format"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

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

                String top = format.get("top")
                        .replace("%album", album)
                        .replace("%date", date)
                        .replace("%artist", artist)
                        .replace("%title", title);
                String bottom = format.get("bottom")
                        .replace("%album", album)
                        .replace("%date", date)
                        .replace("%artist", artist)
                        .replace("%title", title);

                return new DiscordRichPresence
                        .Builder(bottom)
                        .setDetails(top)
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
