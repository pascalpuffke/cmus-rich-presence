package de.mineclashtv;

import de.mineclashtv.utils.ArgumentHandler;
import de.mineclashtv.utils.ConfigurationFile;
import de.mineclashtv.utils.Updater;
import net.arikia.dev.drpc.DiscordRPC;

import java.io.IOException;

public class Main {

    /** When set to <code>true</code>, DiscordRPC won't be initialized and verbose console output gets enabled. */
    public static boolean debug;
    /** When set to <code>true</code>, all console output gets disabled. */
    public static boolean quiet;
    /** How often to update DiscordRPC in ms */
    public static int interval;
    /** While set to <code>true</code>, the update loop will run. */
    public static boolean run = false;
    private static final String VER = "1.9.1";
    private static final String ID = "718109162923360327";
    /** Text to display when user hovers over icon */
    public static final String ICON_TEXT = String.format("[v%s] C* music player", VER);
    public static final ConfigurationFile configurationFile = new ConfigurationFile();
    
    public static void main(String[] args) {
        // The DiscordRPC library does not currently support macOS, so display this error message instead of pretending to work
        if(!System.getProperty("os.name").equals("Linux")) {
            System.err.print("You are not using Linux. DiscordRPC is broken in macOS. (If you use Windows, this program would be useless anyway).\nTerminating...\n");
            System.exit(1);
        }

        try {
            configurationFile.initialize();

            debug = (boolean) configurationFile.getValue("debug");
            quiet = (boolean) configurationFile.getValue("quiet");
            interval = (int) configurationFile.getValue("interval");
        } catch(IOException exception) {
            System.err.println("Could not access config file!");
            exception.printStackTrace();
        }

        try {
            ArgumentHandler.parseArguments(args);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

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