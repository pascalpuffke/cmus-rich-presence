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
                String top = replacePlaceholders(format.get("top"), parser);
                String bottom = replacePlaceholders(format.get("bottom"), parser);

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

    private static String replacePlaceholders(String s, Parser parser) {
        return s
                .replace("%album", parser.getAlbum())
                .replace("%date", parser.getDate())
                .replace("%artist", parser.getArtist())
                .replace("%title", parser.getTitle());
    }
}
