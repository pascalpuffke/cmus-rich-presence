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

    private final Map<String, Object> CONFIG_MAP;
    private final String DEFAULT_CONFIG;
    private final Path CONFIG_FILE;

    public ConfigurationFile() {
        // As this project is stuck on Java 8, we have to do this terrible mess.
        // In newer versions of Java, we could use text blocks, but compatibility is a little more important than
        // comfort whilst developing a project that is actually used by people other than me
        this.DEFAULT_CONFIG =
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
                "BOTTOM_FORMAT=\"from %album (%date)\"\n\n";
        this.CONFIG_MAP = new HashMap<>();
        this.CONFIG_FILE = Paths.get(System.getProperty("user.dir") + "/cmusrp.conf");
    }

    public void initialize() throws IOException {
        if(!Files.exists(CONFIG_FILE))
            Files.write(CONFIG_FILE, DEFAULT_CONFIG.getBytes());

        System.out.printf(
                "Reading config file %s (%d bytes)\n",
                CONFIG_FILE.toAbsolutePath().toString(),
                Files.size(CONFIG_FILE)
        );
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
        for(String s : data.split("\n")) {
            if(!(s.startsWith("#") || s.isEmpty())) { // Exclude comments and empty lines
                String[] a = s.split("=");

                if(a.length != 2)
                    throw new IllegalArgumentException(String.format(
                            "Length does not equal 2, possible missing '=' or invalid config.\nlength=%d\na[0]='%s'",
                            a.length,
                            a[0]
                    ));

                // Parsing value by determining its type. No support for floating point numbers
                Object value =
                        // Is the first char not a decimal?
                        Character.getType(a[1].charAt(0)) != Character.DECIMAL_DIGIT_NUMBER ?
                                // Does the string equal "true"?
                                a[1].equals("true") ? true :
                                        // Does the string equal "false"?
                                        a[1].equals("false") ? false :
                                                // No decimal, no boolean - default to string
                                                a[1] :
                                // No boolean, but a decimal - parse as integer
                                Integer.parseInt(a[1]);

                // removing quotes from string
                if(value instanceof String)
                    value = ((String) value).substring(1, ((String) value).length() - 1);

                CONFIG_MAP.put(a[0].toLowerCase(), value);
            }
        }

        if(!(Boolean) CONFIG_MAP.get("quiet"))
            for(Map.Entry<String, Object> entry : CONFIG_MAP.entrySet())
                System.out.printf("%s=%s [%s]\n",
                        entry.getKey(),
                        entry.getValue().toString(),
                        entry.getValue().getClass().getSimpleName()
                );
    }
}
