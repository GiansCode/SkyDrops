package me.itsmas.skydrops.command.commands;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.command.SubCommand;
import me.itsmas.skydrops.event.EnvoyEvent;
import me.itsmas.skydrops.message.Message;
import me.itsmas.skydrops.permissions.Permission;
import me.itsmas.skydrops.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class StartCommand extends SubCommand
{
    public StartCommand(SkyDrops plugin)
    {
        super(plugin, false, Permission.COMMAND_START, "<name> <chests> [seconds]", "start");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (args.length < 2 || args.length > 3)
        {
            help(sender);
            return;
        }

        String name = args[0];

        EnvoyEvent event = plugin.getEventManager().getEvent(name);

        if (event == null)
        {
            Util.message(sender, Message.INVALID_EVENT);
            return;
        }

        if (event.isActive())
        {
            Util.message(sender, Message.ALREADY_ACTIVE);
            return;
        }

        int chests;

        try
        {
            chests = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException ex)
        {
            sender.sendMessage(Message.INVALID_NUMBER.value().replace("%number%", args[1]));
            return;
        }

        if (event.getLocationCount() == 0)
        {
            Util.message(sender, Message.NO_LOCATIONS);
            return;
        }

        if (event.getCommandsCount() == 0)
        {
            Util.message(sender, Message.NO_COMMANDS);
            return;
        }

        if (chests < 1)
        {
            Util.message(sender, Message.INVALID_INPUT);
            return;
        }

        if (chests > event.getLocationCount())
        {
            sender.sendMessage(Message.TOO_MANY_CHESTS.value().replace("%amount%", String.valueOf(event.getLocationCount())));
            return;
        }

        int seconds = 0;

        if (args.length == 3)
        {
            try
            {
                seconds = Integer.parseInt(args[2]);

                if (seconds < 0)
                {
                    sender.sendMessage(Message.INVALID_NUMBER.value().replace("%number%", args[2]));
                    return;
                }
            }
            catch (NumberFormatException ex)
            {
                Util.message(sender, Message.INVALID_NUMBER);
                return;
            }
        }

        if (seconds == 0)
        {
            Bukkit.broadcastMessage(Message.EVENT_STARTED.value().replace("%name%", event.getName()).replace("%amount%", String.valueOf(chests)));
        }
        else
        {
            Bukkit.broadcastMessage(
                    Message.EVENT_STARTED_TIMER.value().replace("%name%", event.getName()).replace("%amount%", String.valueOf(chests)).replace("%time%", String.valueOf(seconds))
            );
        }

        event.activate(chests, seconds);
    }
}
