package de.mineclashtv;

import de.mineclashtv.utils.ArgumentHandler;
import de.mineclashtv.utils.Updater;
import net.arikia.dev.drpc.DiscordRPC;

public class Main {

    /** When set to <code>true</code>, DiscordRPC won't be initialized and verbose console output gets enabled. */
    public static boolean debug = false;
    /** When set to <code>true</code>, all console output gets disabled. */
    public static boolean quiet = false;
    /** How often to update DiscordRPC in ms */
    public static int interval = 1000;
    /** While set to <code>true</code>, the update loop will run. */
    public static boolean run = false;
    private static final String VER = "1.8";
    private static final String ID = "718109162923360327";
    /** Text to display when user hovers over icon */
    public static final String ICON_TEXT = String.format("[v%s] C* music player", VER);
    
    public static void main(String[] args) {
        // The DiscordRPC library does not currently support macOS, so display this error message instead of pretending to work
        if(!System.getProperty("os.name").equals("Linux")) {
            System.err.print("You are not using Linux. DiscordRPC is broken in macOS. (If you use Windows, this program would be useless anyway).\nTerminating...\n");
            System.exit(1);
        }

        ArgumentHandler.parseArguments(args);

        if(!debug) {
            DiscordRPC.discordInitialize(ID, null, true);
            printf("Successfully initialized DiscordRPC\n");
        }
        run = true;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            run = false;

            if(!debug)
                DiscordRPC.discordShutdown();
        }));

        try {
            Updater.run = true;
            Updater.updateLoop(debug, interval, ICON_TEXT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printTagWarning(String fileName) {
        printf("Warning: Song not tagged properly: %s\n", fileName);
    }

    public static void printf(String f, Object... a) {
        if(!quiet)
            System.out.printf(f, a);
    }
}