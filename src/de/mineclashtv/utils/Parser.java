package de.mineclashtv.utils;

public class Parser {

    public Parser() {

    }

    public String getValue(String input, int line) {
        // Splitting input string in single lines
        String[] split = input.split(System.lineSeparator());
        // return specified line
        return split[line];
    }

    public String getTag(String input, String tag) {
        // TODO this terrible mess
        switch(tag) {
            case "status":
                return getValue(input, 0).replace("status ", "");
            case "file":
                return getValue(input, 1).replace("file ", "");
            case "duration":
                return getValue(input, 2).replace("duration ", "");
            case "position":
                return getValue(input, 3).replace("position ", "");
            case "artist":
                return getValue(input, 4).replace("tag artist ", "");
            case "album":
                return getValue(input, 5).replace("tag album ", "");
            case "title":
                return getValue(input, 6).replace("tag title ", "");
            case "date":
                return getValue(input, 7).replace("tag date ", "");
            case "genre":
                return getValue(input, 8).replace("tag genre ", "");
            case "tracknumber":
                return getValue(input, 9).replace("tag tracknumber ", "");
        }

        return null;
    }
}
