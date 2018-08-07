package me.itsmas.skydrops.command;

import me.itsmas.skydrops.SkyDrops;
import me.itsmas.skydrops.command.commands.AddCommandCommand;
import me.itsmas.skydrops.command.commands.AddLocationCommand;
import me.itsmas.skydrops.command.commands.CreateCommand;
import me.itsmas.skydrops.command.commands.ReloadCommand;
import me.itsmas.skydrops.command.commands.StartCommand;
import me.itsmas.skydrops.command.commands.StopCommand;
import me.itsmas.skydrops.message.Message;
import me.itsmas.skydrops.util.Util;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class SkyDropsCommand implements CommandExecutor
{
    private final SkyDrops plugin;

    public SkyDropsCommand(SkyDrops plugin)
    {
        this.plugin = plugin;

        addCommands();
    }

    private final Set<SubCommand> subCommands = new HashSet<>();

    private void addCommands()
    {
        addSubCommand(new CreateCommand(plugin));
        addSubCommand(new AddLocationCommand(plugin));
        addSubCommand(new AddCommandCommand(plugin));
        addSubCommand(new StartCommand(plugin));
        addSubCommand(new ReloadCommand(plugin));
        addSubCommand(new StopCommand(plugin));
    }

    private void addSubCommand(SubCommand subCommand)
    {
        subCommands.add(subCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        handleCommands(sender, args);
        return true;
    }

    private void handleCommands(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            Util.message(sender, Message.HELP);
            return;
        }

        String arg = args[0];

        for (SubCommand subCommand : subCommands)
        {
            if (ArrayUtils.contains(subCommand.getAliases(), arg))
            {
                if (!(sender instanceof Player) && subCommand.isPlayerCommand())
                {
                    Util.message(sender, Message.PLAYER_ONLY);
                    return;
                }

                if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission()))
                {
                    Util.message(sender, Message.NO_PERMISSION);
                    return;
                }

                String[] newArgs = Util.trimArgs(args);
                subCommand.execute(sender, newArgs);

                return;
            }
        }

        Util.message(sender, Message.INVALID_COMMAND);
    }
}
