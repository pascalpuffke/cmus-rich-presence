package de.mineclashtv;

import de.mineclashtv.utils.ArgumentHandler;
import de.mineclashtv.utils.ConfigurationFile;
import de.mineclashtv.utils.Updater;
import net.arikia.dev.drpc.DiscordRPC;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {

    /** When set to <code>true</code>, DiscordRPC won't be initialized and verbose console output gets enabled. */
    public static boolean debug;
    /** When set to <code>true</code>, all console output gets disabled. */
    public static boolean quiet;
    /** How often to update DiscordRPC in ms */
    public static int interval;
    /** While set to <code>true</code>, the update loop will run. */
    public static boolean run = false;
    public static final String VER = "2.0.0-dev";
    public static final String ID = "718109162923360327";
    /** Text to display when user hovers over icon */
    public static final String ICON_TEXT = String.format("[v%s] C* music player", VER);
    public static ConfigurationFile configurationFile = new ConfigurationFile();
    
    public static void main(String[] args) {
        // The DiscordRPC library does not currently support macOS, so display this error message instead of pretending to work
        if(!System.getProperty("os.name").equals("Linux")) {
            System.err.print("You are not using Linux. DiscordRPC is broken in macOS. (If you use Windows, this program would be useless anyway).\nTerminating...\n");
            System.exit(1);
        }

        initializeConfig();

        if(quiet) {
            System.out.close();

            /* Probably a terrible idea. */
            System.err.close();
        }

        try {
            ArgumentHandler.parseArguments(args);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        if(!debug) {
            DiscordRPC.discordInitialize(ID, null, true);
            System.out.println("Successfully initialized DiscordRPC");
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

    private static void initializeConfig() {
        try {
            configurationFile.initialize();

            debug = (boolean) configurationFile.getValue("debug");
            quiet = (boolean) configurationFile.getValue("quiet");
            interval = (int) configurationFile.getValue("interval");
        } catch(IOException exception) {
            System.err.println("Could not access config file!");
        }
    }

    public static void printTagWarning(String fileName) {
        System.out.printf("Warning: Song not tagged properly: %s\n", fileName);
    }

}