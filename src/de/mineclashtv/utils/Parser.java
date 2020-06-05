package de.mineclashtv.utils;

public class Parser {

    private Shell shell;

    public Parser() {

    }

    public String getTag(String tag) {
        // Create shell object if it doesn't exist
        if(shell == null) shell = new Shell();
        String remote = shell.exec("cmus-remote -Q | grep '" + tag + "'");
        String[] split = remote.split(System.lineSeparator());

        return split[0].substring(tag.length() + 1).replace(System.lineSeparator(), "");
    }

}
