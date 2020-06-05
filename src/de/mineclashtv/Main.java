package de.mineclashtv;

import de.mineclashtv.utils.Logger;
import de.mineclashtv.utils.Parser;
import de.mineclashtv.utils.Shell;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class Main {

    private static String id = "718109162923360327";
    private static Shell shell;
    private static Parser parser;

    public static void main(String[] args) {
        Logger log = new Logger(true, System.out);
        shell = new Shell();
        parser = new Parser();

        DiscordRPC.discordInitialize(id, null, true);
        log.print("Successfully initialized DiscordRPC");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> DiscordRPC.discordShutdown()));

        log.print(parser.getTag("status") + ": " + parser.getTag("tag artist") + " - " + parser.getTag("tag title") + " from " + parser.getTag("tag album"));

        // Main loop used to update status
        while(true) {
            try {
                if(parser.getTag("status").equals("playing")) {
                    log.print("Playing: " + parser.getTag("tag artist") + " - " + parser.getTag("tag title") + " from " + parser.getTag("tag album"));
                    DiscordRichPresence discordRichPresence = new DiscordRichPresence.Builder(
                            "from " + parser.getTag("tag album")).setDetails(
                                    parser.getTag("tag artist") + " - " + parser.getTag("tag title")).build();
                    DiscordRPC.discordUpdatePresence(discordRichPresence);
                } else if(parser.getTag("status").equals("paused")){
                    DiscordRichPresence discordRichPresence = new DiscordRichPresence.Builder(
                            "from " + parser.getTag("tag album") + " [paused]").setDetails(
                                    parser.getTag("tag artist") + " - " + parser.getTag("tag title")).build();
                    DiscordRPC.discordUpdatePresence(discordRichPresence);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
