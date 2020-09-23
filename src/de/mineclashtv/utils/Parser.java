package de.mineclashtv.utils;

public class Parser {

    private Shell shell;

    public Parser() {

    }

    /**
     * Parses a tag from <code>cmus-remote</code> output.
     * @param tag Tag to get
     * @return Hopefully correctly parsed value
     */
    public String getTag(String tag) {
        if(shell == null) shell = new Shell();
        String remote = shell.exec("cmus-remote -Q | grep '" + tag + "'");
        String[] split = null;

        if(remote == null)
            return "";

        try {
            split = remote.split(System.lineSeparator());
        } catch(NullPointerException ignored) { // Will be thrown if song doesn't have tag, we'll ignore this
        }

        return split[0].substring(tag.length() + 1).replace(System.lineSeparator(), "");
    }

}
