package de.mineclashtv.utils;

import de.mineclashtv.Main;

public class ArgumentHandler {

    public ArgumentHandler() {
    }

    public void parseArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--debug":
                    Main.debug = true;
                    System.out.printf("Enabled debug mode. Disabled DiscordRPC.\n");
                    break;
                case "--interval":
                    int interval = Integer.parseInt(args[i + 1]);
                    Main.interval = interval;
                    System.out.printf("Set interval to %d ms.\n", interval);
                case "":
                    break;
            }
        }
    }
}
