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
     * Gets current cmus status by executing <code>cmus-remote -Q</code>.
     * @return Output from cmus-remote
     */
    public String getCmusRemote() {
        processBuilder.command("sh", "-c", "cmus-remote -Q");

        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + System.lineSeparator());
            }

            int exitCode = process.waitFor();
            if(exitCode == 0)
                return output.toString();
            else logger.print("Something fucked up.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
