package me.itsmas.skydrops.message;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.util.Util;

import java.util.Arrays;

public enum Message
{
    HELP,
    COMMAND_USAGE,
    NO_PERMISSION,
    INVALID_COMMAND,
    PLAYER_ONLY,
    ADDED_EVENT,
    ALREADY_EXISTS,
    INVALID_EVENT,
    ADDED_LOCATION,
    INVALID_NUMBER,
    TOO_MANY_CHESTS,
    INVALID_INPUT,
    NO_LOCATIONS,
    NO_COMMANDS,
    EVENT_STARTED,
    EVENT_STARTED_TIMER,
    ALREADY_ACTIVE,
    NOT_ACTIVE,
    EVENT_ENDED,
    PLUGIN_RELOADED,
    ADDED_COMMAND,
    STOPPED_EVENT,
    STOPPED_EVENT_BROADCAST;

    private String msg;

    public String value()
    {
        return msg;
    }

    private void setValue(String msg)
    {
        this.msg = msg;
    }

    /**
     * Initialise the plugin messages
     * @param plugin The plugin instance
     */
    public static void init(SkyDrops plugin)
    {
        Arrays.stream(values()).forEach(message ->
        {
            String raw = plugin.getConfig("messages." + message.name().toLowerCase());

            if (raw == null)
            {
                Util.logErr("Unable to find message value for message '" + message.name() + "'");

                raw = message.name();
            }

            message.setValue(Util.colour(raw));
        });
    }
}
