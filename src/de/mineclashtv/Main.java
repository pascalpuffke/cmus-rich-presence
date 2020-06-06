package de.mineclashtv;

import de.mineclashtv.utils.Logger;
import de.mineclashtv.utils.Parser;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class Main {

    private static String id = "718109162923360327";
    private static Parser parser;
    private static Logger log;

    public static void main(String[] args) {
        log = new Logger(true, System.out);
        parser = new Parser();

        DiscordRPC.discordInitialize(id, null, true);
        log.print("Successfully initialized DiscordRPC");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> DiscordRPC.discordShutdown()));

        // Main loop used to update status
        // TODO there must be a way to make this less dumb
        while(true) {
            try {
                DiscordRichPresence discordRichPresence = null;
                if(parser.getTag("status").equals("playing")) {
                    if(parser.getTag("tag title").equals("")) // Song isn't tagged properly; show filename
                        discordRichPresence = new DiscordRichPresence.Builder(parser.getTag("file")).build();
                    else
                        discordRichPresence = new DiscordRichPresence.Builder(
                            "from " + parser.getTag("tag album") + " (" + parser.getTag("tag date") + ")").setDetails(
                                    parser.getTag("tag artist") + " - " + parser.getTag("tag title")).build();
                } else if(parser.getTag("status").equals("paused")) {
                    if(parser.getTag("tag title").equals(""))
                        discordRichPresence = new DiscordRichPresence.Builder(parser.getTag("file") + " [paused]").build();
                    else
                        discordRichPresence = new DiscordRichPresence.Builder(
                                "from " + parser.getTag("tag album") + " (" + parser.getTag("tag date") + ") [paused]").setDetails(
                                parser.getTag("tag artist") + " - " + parser.getTag("tag title")).build();
                }
                DiscordRPC.discordUpdatePresence(discordRichPresence);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
