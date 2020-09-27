package de.mineclashtv.utils;

import java.io.File;

public class Parser {

    private Shell shell;

    public Parser() {

    }

    /**
     * Parses a tag from <code>cmus-remote</code> output.
     * @param tag Tag to get
     * @return Parsed value, or empty string if there is no value
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

    public boolean isPlaying() {
        return getTag("status").equals("playing");
    }

    /**
     * Checks if the currently playing song has a title tag.<br/>
     * Used to determine if we need to show the file name instead of song metadata.
     * @return <code>true</code> if the song has a title tag, <code>false</code> otherwise
     */
    public boolean hasTitle() {
        return !getTag("tag title").equals("");
    }

    public String getTitle() {
        return getTag("tag title");
    }

    public String getAlbum() {
        return getTag("tag album");
    }

    public String getArtist() {
        return getTag("tag artist");
    }

    public String getDate() {
        return getTag("tag date");
    }

    public String getPath() {
        return getTag("file");
    }

    public String getNameFromFile() {
        String file = new File(getPath()).getName();

        return file.substring(0, file.lastIndexOf("."));
    }

}
