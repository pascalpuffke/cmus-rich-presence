package de.mineclashtv.utils;

import de.mineclashtv.Main;

public class ArgumentHandler {

    private final String helpMessage =
            "Usage: java -jar cmus-rich-presence-x.x.jar [OPTION...]\n\n" +
            "\t--debug\t\t\tDisables rich presence functionality and shows more verbose console output\n" +
            "\t--interval [value]\tChanges polling interval, in ms. Default: 1000\n" +
            "\t--help\t\t\tShows this text\n\n" +
            "This program requires you to use a GNU/Linux-based operating system.\n" +
            "The DiscordRPC library this program uses doesn't yet support macOS.\n"
    ;

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
                case "--help":
                    System.out.printf(helpMessage);
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}
