package de.mineclashtv.utils;

import java.io.File;

public class Parser {

    private Shell shell;

    public Parser() {

    }

    /**
     * Parses a tag from <code>cmus-remote</code> output.
     * @param tag Tag to get
     * @return  Empty string if <code>cmus-remote</code> is not available,
     *          otherwise parsed tag from <code>cmus-remote -Q</code>
     */
    public String getTag(String tag) {
        if(shell == null)
            shell = new Shell();

        String remote = shell.exec("cmus-remote -Q | grep -F '" + tag + "'");

        if(remote == null)
            return "";

        return remote.split("\n")[getLineIndexOf(remote, tag)]
                .substring(tag.length() + 1)
                .replace("\n", ""); // could be replaced by extending the substring call, but i'm too lazy to change it
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

    private int getLineIndexOf(String remoteOutput, String tag) {
        String[] split = remoteOutput.split("\n");
        for(int i = 0; i < split.length; i++) {
            if(split[i].contains(tag + " "))
                return i;
        }

        return -1;
    }
}
