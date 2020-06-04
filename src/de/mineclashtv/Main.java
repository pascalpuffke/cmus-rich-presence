package de.mineclashtv;

import de.mineclashtv.utils.Logger;
import de.mineclashtv.utils.Parser;
import de.mineclashtv.utils.Shell;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class Main {

    private static final String id = "718109162923360327";
    private static Shell shell;
    private static Parser parser;

    public static void main(String[] args) {
        Logger log = new Logger(true, System.out);
        shell = new Shell();
        parser = new Parser();

        DiscordRPC.discordInitialize(id, null, true);
        log.print("Successfully initialized DiscordRPC");

        // Main loop used to update status
        while(true) {
            try {
                if(parser.getTag(shell.getCmusRemote(), "status").equals("playing")) {
                    DiscordRichPresence discordRichPresence = new DiscordRichPresence.Builder("Playing song").setDetails(
                            parser.getTag(shell.getCmusRemote(), "artist") + " - " + parser.getTag(shell.getCmusRemote(), "title")).build();
                    DiscordRPC.discordUpdatePresence(discordRichPresence);
                } else {
                    DiscordRichPresence discordRichPresence = new DiscordRichPresence.Builder("Paused song").setDetails(
                            parser.getTag(shell.getCmusRemote(), "artist") + " - " + parser.getTag(shell.getCmusRemote(), "title")).build();
                    DiscordRPC.discordUpdatePresence(discordRichPresence);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
