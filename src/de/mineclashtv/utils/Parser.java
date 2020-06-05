package de.mineclashtv.utils;

public class Parser {

    private Shell shell;

    public Parser() {

    }

    public String getTag(String tag) {
        // Create shell object if it doesn't exist
        if(shell == null) shell = new Shell();
        String result = shell.exec("cmus-remote -Q | grep '" + tag + "'");

        return result.substring(tag.length() + 1).replace(System.lineSeparator(), "");
    }

}
