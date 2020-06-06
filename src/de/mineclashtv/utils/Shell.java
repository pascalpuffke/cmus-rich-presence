package de.mineclashtv.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {

    private ProcessBuilder processBuilder;
    private Logger logger;

    public Shell() {
        processBuilder = new ProcessBuilder();
        logger = new Logger(true, System.out);
    }

    /**
     * Executes a shell command
     * @param cmd
     * @return Output from command
     */
    public String exec(String cmd) {
        processBuilder.command("sh", "-c", cmd);

        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null)
                output.append(line + System.lineSeparator());

            int exitCode = process.waitFor();
            if(exitCode == 0)
                return output.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
