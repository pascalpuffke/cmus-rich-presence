# cmus-rich-presence
External Discord Rich Presence plugin for the C* music player. Linux only.
![Preview](preview.png)

## Usage
- Clone this repo or download a prebuilt jar from [Releases](https://github.com/MineClashTV/cmus-rich-presence/releases)
- Start the program by executing ```java -jar cmus-rich-presence-x.x.jar```

## Options
- ```--debug``` ```-d```: Disables rich presence functionality and shows more verbose console output
- ```--interval [value]``` ```-i [value]```: Changes polling interval, in ms. Default: 1000
- ```--quiet``` ```-q```: Disables console output
- ```--help``` ```-h```: Shows the help screen and then exits

## Future plans
- Display current and remaining time. This is a little difficult though, as it would require coming up with a different updating method and I don't know how to realize this yet
- Easy customization options

## Dependencies
- **Java 8**
- [**Discord-RPC**](https://github.com/Vatuu/discord-rpc) library
