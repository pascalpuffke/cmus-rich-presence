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
        // Create shell object if it doesn't exist
        if(shell == null) shell = new Shell();
        // Execute command and use grep to fetch all lines containing desired tag
        String remote = shell.exec("cmus-remote -Q | grep '" + tag + "'");
        // grep might output more than one result, in which case we'll just use the first one and ignore all the
        // other ones
        String[] split = null;

        // If the remote command didn't execute successfully, return empty string.
        if(remote == null)
            return "";

        // After this, we should at least have some output from remote.
        try {
            split = remote.split(System.lineSeparator());
        } catch(NullPointerException e) { // Will be thrown if song doesn't have tag, we'll ignore this
        }

        // If split still is null, cry and give up. This should not happen though.
        assert split != null;
        // Return first grep result and remove everything that's not the desired value
        return split[0].substring(tag.length() + 1).replace(System.lineSeparator(), "");
    }

}
