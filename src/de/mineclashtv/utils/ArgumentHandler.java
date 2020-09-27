package de.mineclashtv.utils;

import de.mineclashtv.Main;

public class ArgumentHandler {

    private final String helpMessage =
            "Usage: java -jar cmus-rich-presence-x.x.jar [OPTION...]\n\n" +
            "\t--debug, -d\t\t\tDisables rich presence functionality and shows more verbose console output\n" +
            "\t--interval [value], -i\t\tChanges polling interval, in ms. Default: 1000\n" +
            "\t--quiet, -q\t\t\tDisables all console output\n" +
            "\t--help, -h\t\t\tShows this text\n\n" +
            "This program requires you to use a GNU/Linux-based operating system.\n" +
            "The DiscordRPC library this program uses doesn't yet support macOS.\n"
    ;

    public ArgumentHandler() {
    }

    public void parseArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-d":
                case "--debug":
                    Main.debug = true;
                    System.out.print("Enabled debug mode. Disabled DiscordRPC.\n");
                    break;
                case "-i":
                case "--interval":
                    int interval = Integer.parseInt(args[i + 1]);
                    Main.interval = interval;
                    System.out.printf("Set interval to %d ms.\n", interval);
                    break;
                case "-q":
                case "--quiet":
                    Main.quiet = true;
                    break;
                case "-h":
                case "--help":
                    System.out.print(helpMessage);
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}
