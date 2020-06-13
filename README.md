# cmus-rich-presence
External Discord Rich Presence plugin for the C* music player
![Preview](preview.png)

## What works
- Parsing should be good enough to not cause any issues, but I have only tested it using my library which is for the most part tagged properly. If you still encounter parsing issues, please tell me!
- Backend works perfectly thanks to [**this awesome library**](https://github.com/Vatuu/discord-rpc)
- Debug mode which disables DiscordRPC and gives more console output

## Future plans
- Display current and remaining time. This is a little difficult though, as it would require coming up with a different updating method and I don't know how to realize this yet
- Better support for songs that aren't tagged properly (properly means artist, title, album and year information)
- Commandline args parsing support
- Easy customization options
- Less stupid code

## Dependencies
- **Java 8**
- [**Discord-RPC**](https://github.com/Vatuu/discord-rpc) library

## Usage
- Clone this repo or download a prebuilt jar from [Releases](https://github.com/MineClashTV/cmus-rich-presence/releases)
- Profit
