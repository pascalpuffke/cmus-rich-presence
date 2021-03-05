package de.mineclashtv.utils;

import de.mineclashtv.Main;
import org.apache.commons.cli.*;

import java.io.IOException;

public class ArgumentHandler {

    public static void parseArguments(String[] args) throws IOException {
        Options options = new Options();

        // Boolean options
        Option debugOption = Option
                .builder("d")
                .longOpt("debug")
                .desc("Disables Rich Presence functionality and shows more verbose console output")
                .build();
        Option quietOption = Option
                .builder("q")
                .longOpt("quiet")
                .desc("Closes all console output streams")
                .build();
        Option helpOption = Option
                .builder("h")
                .longOpt("help")
                .desc("Shows this help screen")
                .build();

        // Additional arguments
        Option intervalOption = Option
                .builder("i")
                .longOpt("interval")
                .desc("Status refresh interval in milliseconds. Default: 1000")
                .hasArg()
                .build();
        Option topOption = Option
                .builder("t")
                .longOpt("top")
                .desc("Sets custom formatting for the top string. Default: \"%artist - %title\"")
                .hasArg()
                .build();
        Option bottomOption = Option
                .builder("b")
                .longOpt("bottom")
                .desc("Sets custom formatting for the bottom string. Default: \"from %album (%date)\"")
                .hasArg()
                .build();
        Option configOption = Option
                .builder("c")
                .longOpt("config")
                .desc("Custom config file location. Best to define an absolute path. Default: current working directory")
                .hasArg()
                .build();

        options.addOption(debugOption);
        options.addOption(quietOption);
        options.addOption(helpOption);
        options.addOption(intervalOption);
        options.addOption(topOption);
        options.addOption(bottomOption);
        options.addOption(configOption);

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);

            if(commandLine.hasOption("debug")) {
                Main.debug = true;
                System.out.println("Enabled debug mode. Disabled Rich Presence.");
            }
            if(commandLine.hasOption("quiet")) {
                Main.quiet = true;
            }
            if(commandLine.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp(
                        "cmus-rich-presence [options]",
                        "An external Discord Rich Presence provider for cmus",
                        options,
                        "\nPlease report issues at https://github.com/MineClashTV/cmus-rich-presence/issues"
                );

                System.exit(0);
            }
            if(commandLine.hasOption("interval")) {
                int value = Integer.parseInt(commandLine.getOptionValue(intervalOption.getOpt(), "1000"));
                Main.interval = value;
                System.out.printf("Set interval to %d ms.\n", value);
            }
            if(commandLine.hasOption("top")) {
                String value = commandLine.getOptionValue(topOption.getOpt(), "\"%artist - %title\"");
                Main.configurationFile.replaceValue(
                        "top_format", value
                );
            }
            if(commandLine.hasOption("bottom")) {
                String value = commandLine.getOptionValue(bottomOption.getOpt(), "\"from %album (%date)\"");

                Main.configurationFile.replaceValue(
                        "bottom_format", value
                );
            }
            if(commandLine.hasOption("config")) {
                String value = commandLine.getOptionValue(configOption.getOpt(), "");

                System.out.println(value + " owo");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
