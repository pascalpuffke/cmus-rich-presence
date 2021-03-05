# cmus-rich-presence
External Discord Rich Presence provider for the C* music player. Linux only.
![Preview](preview.png)

## Usage
- Download a prebuilt jar from [Releases](https://github.com/MineClashTV/cmus-rich-presence/releases)
- Start the program by executing ```java -jar cmus-rich-presence-x.x.jar```

## Options
- ```--debug``` ```-d```: Disables rich presence functionality and shows more verbose console output
- ```--interval [value]``` ```-i [value]```: Changes polling interval, in ms. Default: 1000
- ```--quiet``` ```-q```: Disables console output
- ```--help``` ```-h```: Shows the help screen and then exits
- ```--top [value]``` ```-t [value]```: Sets the top format string. It's recommended to change this in the cmusrp.conf file. Default: "%artist - %title"
- ```--bottom [value]``` ```-b [value]```: Sets the bottom format string. It's recommended to change this in the cmusrp.conf file. Default: "from %album (%year)" 

Note that command line arguments have priority over the cmusrp.conf configuration file.

## Configuration file
This program creates a configuration file when it doesn't already exist in the same directory as the jar file.

It was made as an easier way to customize how this tool behaves and displays information. By default, it looks like this:
```
DEBUG=false
QUIET=false
INTERVAL=1000
TOP_FORMAT="%artist - %title"
BOTTOM_FORMAT="from %album (%date)"
```

The syntax should be pretty straight forward. See the Options paragraph for more information about the different values.

You can customize the top and bottom format using these placeholders:
    - %artist
    - %title
    - %album
    - %date

Comments are supported by starting a line with the '#' character.

**Note: ** If you don't like the way this program handles its configuration file, please continue using version 1.8 and wait for 2.0 to come out, where I will put the config file in more appropriate places (or even custom ones).
As of now, this is only an admittedly janky solution to finally have this functionality implemented.

## Build and development dependencies
- **Java 8**
- [**Discord-RPC**](https://github.com/Vatuu/discord-rpc) library
- [**Apache Commons CLI 1.4**](https://commons.apache.org/proper/commons-cli/) library
- These come extracted in the .jar file and are only required for development
