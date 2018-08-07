package me.itsmas.skydrops.event;

import me.itsmas.skydrops.SkyDrops;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

class ChestListener implements Listener
{
    private final SkyDrops plugin;

    ChestListener(SkyDrops plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            Block block = event.getClickedBlock();

            if (block.getType() == Material.CHEST)
            {
                handleChestClick(event.getPlayer(), (Chest) block.getState(), event);
            }
        }
    }

    private void handleChestClick(Player player, Chest chest, PlayerInteractEvent interactEvent)
    {
        for (EnvoyEvent event : plugin.getEventManager().getActiveEvents())
        {
            if (event.isChest(chest))
            {
                interactEvent.setCancelled(true);

                String command = event.getRandomCommand();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));

                chest.getLocation().getBlock().setType(Material.AIR);

                return;
            }
        }
    }
}
