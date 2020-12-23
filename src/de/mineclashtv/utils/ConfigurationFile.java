package de.mineclashtv.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationFile {

    /*
    The procedure is as follows:
        1. Check if a config file already exists
            - if it does, use that;
            - otherwise, create one with the default values (DEFAULT_CONFIG string).
        2. Parse every line and search for supported keywords. Ignore comments ('#') and empty lines.
            - These are: DEBUG, QUIET, INTERVAL, TOP_FORMAT, BOTTOM_FORMAT.
        3. Put the results from step 2 into a HashMap (CONFIG_MAP)
        4. Use the values from the generated HashMap elsewhere
     */

    private final String DEFAULT_CONFIG =
            "# Set to true to disable DiscordRPC and show more verbose console output\n" +
            "DEBUG=false\n\n" +
            "# Set to true to disable console output entirely\n" +
            "QUIET=false\n\n" +
            "# Polling interval in which the program grabs the current cmus status, in milliseconds\n" +
            "INTERVAL=1000\n\n" +
            "# Sets the top format string (first line in the discord rich presence details)\n" +
            "# Supported placeholders: %artist, %title, %album, %date\n" +
            "TOP_FORMAT=\"%artist - %title\"\n\n" +
            "# Sets the bottom format string (second line in the discord rich presence details)\n" +
            "BOTTOM_FORMAT=\"from %album (%date)\"\n\n"
    ;
    private final Map<String, Object> CONFIG_MAP = new HashMap<>();

    private final Path WORKING_DIR = Paths.get(System.getProperty("user.dir"));
    private final Path CONFIG_FILE = Paths.get(WORKING_DIR.toString() + "/cmusrp.conf");

    public void initialize() throws IOException {
        if(!Files.exists(CONFIG_FILE))
            createDefault(CONFIG_FILE);
    }

    public void createDefault(Path path) throws IOException {
        Files.write(path, DEFAULT_CONFIG.getBytes());
    }

    public Object getValue(String key) throws IOException {
        if(this.CONFIG_MAP.isEmpty())
            populateConfigMap(read(CONFIG_FILE));

        return CONFIG_MAP.get(key);
    }

    public void replaceValue(String key, Object value) throws IOException {
        if(this.CONFIG_MAP.isEmpty())
            populateConfigMap(read(CONFIG_FILE));

        this.CONFIG_MAP.replace(key, value);
    }

    private String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }

    private void populateConfigMap(String data) {
        /* Maybe there is a better way to do this. */
        for(String s : data.split("\n")) {
            if(s.startsWith("DEBUG="))
                CONFIG_MAP.put("debug", Boolean.parseBoolean(s.substring(6)));
            else if(s.startsWith("QUIET="))
                CONFIG_MAP.put("quiet", Boolean.parseBoolean(s.substring(6)));
            else if(s.startsWith("INTERVAL="))
                CONFIG_MAP.put("interval", Integer.parseInt(s.substring(9)));
            else if(s.startsWith("TOP_FORMAT="))
                CONFIG_MAP.put("top_format", s.substring(12, s.length() - 1));
            else if(s.startsWith("BOTTOM_FORMAT="))
                CONFIG_MAP.put("bottom_format", s.substring(15, s.length() - 1));
            else if(!s.startsWith("#") && !s.isEmpty()) /* Ignore comments */
                System.out.println("Unknown config '" + s + "'.");
        }
    }
}
