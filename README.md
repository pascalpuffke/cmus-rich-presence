# cmus-rich-presence
External Discord Rich Presence plugin for the C* music player
![Preview](preview.png)

## Introduction
'Plugin' isn't technically correct, this is just a terrible Java application executing 'cmus-remote -Q' every second and setting some parts of its output as your Discord status. Especially my parsing is very likely to break, but my last two braincells couldn't think of a better method.

## Dependencies
- **Java 8**
- [**Discord-RPC**](https://github.com/Vatuu/discord-rpc) library

## Usage
I know you won't actually use this.
Clone this repo or use a prebuilt jar file if I ever compile one.
[https://discord.com/developers/applications](Create a new Application) and use the ID of your application (replace the ID variable or, when using a prebuilt jar, give it as an argument when excuting).
