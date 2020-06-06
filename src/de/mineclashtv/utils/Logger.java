package de.mineclashtv.utils;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * External logging APIs are bloat.
 * @author MineClashTV
 */
public class Logger {

    private boolean showTime;
    private PrintStream printStream;

    public Logger(boolean showTime, PrintStream printStream) {
        this.showTime = showTime;
        this.printStream = printStream;
    }

    public void print(String s) {
        if (showTime)
            printStream.println("[" + getTime() + "] " + s);
        else
            printStream.println(s);
    }

    private String getTime() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}
