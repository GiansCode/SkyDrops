package me.itsmas.skydrops.command.commands;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.command.SubCommand;
import me.itsmas.skydrops.event.EnvoyEvent;
import me.itsmas.skydrops.message.Message;
import me.itsmas.skydrops.permissions.Permission;
import me.itsmas.skydrops.util.Util;
import me.itsmas.skydrops.util.UtilString;
import org.bukkit.command.CommandSender;

public class AddCommandCommand extends SubCommand
{
    public AddCommandCommand(SkyDrops plugin)
    {
        super(plugin, false, Permission.COMMAND_ADD_COMMAND, "<chance> <event> <command...>", "addcommand", "addcmd");
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        if (args.length < 3)
        {
            help(sender);
            return;
        }

        double chance;

        try
        {
            chance = Double.parseDouble(args[0]);
        }
        catch (NumberFormatException ex)
        {
            sender.sendMessage(Message.INVALID_NUMBER.value().replace("%number%", args[0]));
            return;
        }

        EnvoyEvent event = plugin.getEventManager().getEvent(args[1]);

        if (event == null)
        {
            Util.message(sender, Message.INVALID_EVENT);
            return;
        }

        String command = UtilString.combine(args, 2);

        event.addCommand(chance, command);

        sender.sendMessage(Message.ADDED_COMMAND.value().replace("%name%", event.getName()));
    }
}
