package de.mineclashtv;

import de.mineclashtv.utils.Logger;
import de.mineclashtv.utils.Parser;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class Main {

    /** When set to <code>true</code>, DiscordRPC won't be initialized and verbose console output gets enabled. */
    private static final boolean debug = false;
    /** While set to <code>true</code>, the update loop will run. */
    private static boolean run = false;
    private static final String id = "718109162923360327";
    private static Parser parser;
    private static Logger log;

    public static void main(String[] args) {
        log = new Logger(true, System.out);
        parser = new Parser();

        if(!debug) {
            DiscordRPC.discordInitialize(id, null, true);
            log.print("Successfully initialized DiscordRPC");
        }
        run = true;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            run = false;
            if(!debug) DiscordRPC.discordShutdown();
        }));

        updateLoop();
    }

    /**
     * Main loop used to update status. Refreshes every second.
     * Also this is terribly written but who cares
     */
    private static void updateLoop() {
        while(run) {
            try {
                DiscordRichPresence discordRichPresence = null;
                if(parser.getTag("status").equals("playing")) {
                    if(parser.getTag("tag title").equals("")) // Song isn't tagged properly; show filename
                        discordRichPresence = new DiscordRichPresence.Builder(parser.getTag("file")).build();
                    else
                        discordRichPresence = new DiscordRichPresence.Builder(
                                "from " + parser.getTag("tag album") + " (" + parser.getTag("tag date") + ")").setDetails(
                                parser.getTag("tag artist") + " - " + parser.getTag("tag title")).build();
                } else if(parser.getTag("status").equals("paused")) {
                    if(parser.getTag("tag title").equals(""))
                        discordRichPresence = new DiscordRichPresence.Builder(parser.getTag("file") + " [paused]").build();
                    else
                        discordRichPresence = new DiscordRichPresence.Builder(
                                "from " + parser.getTag("tag album") + " (" + parser.getTag("tag date") + ") [paused]").setDetails(
                                parser.getTag("tag artist") + " - " + parser.getTag("tag title")).build();
                }

                if(debug) {
                    try {
                        assert discordRichPresence != null;

                        log.print(discordRichPresence.details + " " + discordRichPresence.state);
                    } catch(NullPointerException e) {
                        // A NullPointerException may get thrown when DiscordRPC isn't properly initialized and the song
                        // is stopped
                        log.print("Song is stopped, waiting for playback...");
                    }
                } else DiscordRPC.discordUpdatePresence(discordRichPresence);

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
