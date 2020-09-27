package de.mineclashtv;

import de.mineclashtv.utils.ArgumentHandler;
import de.mineclashtv.utils.Parser;
import de.mineclashtv.utils.DiscordRichPresenceFactory;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class Main {

    /** When set to <code>true</code>, DiscordRPC won't be initialized and verbose console output gets enabled. */
    public static boolean debug = false;
    /** When set to <code>true</code>, all console output gets disabled. </code> */
    public static boolean quiet = false;
    /** How often to update DiscordRPC in ms */
    public static int interval = 1000;
    /** While set to <code>true</code>, the update loop will run. */
    private static boolean run = false;
    private static final String id = "718109162923360327";
    /** Text to display when user hovers over icon */
    public static final String iconText = "C* music player";
    private static Parser parser;

    public static void main(String[] args) {
        // The DiscordRPC library does not currently support macOS, so display this error message instead of pretending to work
        if(!System.getProperty("os.name").equals("Linux")) {
            System.err.print("You are not using Linux. DiscordRPC is broken in macOS. (If you use Windows, this program would be useless anyway).\nTerminating...\n");
            System.exit(1);
        }

        parser = new Parser();

        new ArgumentHandler().parseArguments(args);

        if(!debug) {
            DiscordRPC.discordInitialize(id, null, true);
            if(!quiet) System.out.print("Successfully initialized DiscordRPC\n");
        }
        run = true;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            run = false;
            if(!debug) DiscordRPC.discordShutdown();
        }));

        try {
            updateLoop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printTagWarning(String fileName) {
        if(!quiet) System.out.printf("Warning: Song not tagged properly: %s\n", fileName);
    }

    private static void updateLoop() throws InterruptedException {
        while(run) {
            DiscordRichPresence discordRichPresence = DiscordRichPresenceFactory.getRichPresence(parser);

            if(discordRichPresence != null) {
                DiscordRPC.discordUpdatePresence(discordRichPresence);
            } else { // Nothing currently playing - don't display anything.
                DiscordRPC.discordClearPresence();
            }

            if(debug) {
                if (discordRichPresence != null) {
                    System.out.printf("%s %s\n", discordRichPresence.details, discordRichPresence.state);
                } else {
                    System.out.print("Song is stopped, waiting for playback...\n");
                }
            }

            // TODO figure out executors
            Thread.sleep(interval);
        }
    }
}