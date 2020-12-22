package de.mineclashtv.utils;

import de.mineclashtv.Main;

import java.io.IOException;

import static de.mineclashtv.Main.printf;

public class ArgumentHandler {

    public static final String HELP_MESSAGE =
            "Usage: java -jar cmus-rich-presence-x.x.jar [OPTION...]\n\n" +
            "\t--debug, -d\t\t\tDisables rich presence functionality and shows more verbose console output\n" +
            "\t--interval, -i\t\tChanges polling interval, in ms. Default: 1000\n" +
            "\t--quiet, -q\t\t\tDisables all console output\n" +
            "\t--help, -h\t\t\tShows this text\n" +
            "\t--top, -t\t\t\tSets custom formatting for the top string. Default: \"%artist - %title\"\n" +
            "\t--bottom, -b\t\tSets custom formatting for the bottom string. Default: \"from %album (%date)\"\n\n" +
            "This program requires you to use a GNU/Linux-based operating system.\n" +
            "The DiscordRPC library this program uses doesn't yet support macOS.\n"
    ;

    public static void parseArguments(String[] args) throws IOException {
        for(int i = 0; i < args.length; i++) {
            switch(args[i]) {
                case "-d":
                case "--debug":
                    Main.debug = true;
                    printf("Enabled debug mode. Disabled DiscordRPC.\n");
                    break;
                case "-i":
                case "--interval":
                    int interval = Integer.parseInt(args[i + 1]);
                    Main.interval = interval;
                    printf("Set interval to %d ms.\n", interval);
                    break;
                case "-q":
                case "--quiet":
                    Main.quiet = true;
                    break;
                case "-h":
                case "--help":
                    printf(HELP_MESSAGE);
                    System.exit(0);
                case "-t":
                case "--top":
                    Main.configurationFile.replaceValue(
                            "top_format", args[i + 1].substring(1, args[i + 1].length() - 1)
                    );
                    break;
                case "-b":
                case "--bottom":
                    Main.configurationFile.replaceValue(
                            "bottom_format", args[i + 1].substring(1, args[i + 1].length() - 1)
                    );
                    break;
                default:
                    break;
            }
        }
    }
}
