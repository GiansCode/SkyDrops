package me.itsmas.skydrops.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilLoc
{
    public static Set<Location> deserializeLocations(List<String> raw)
    {
        Set<Location> locations = new HashSet<>();

        raw.forEach(string ->
        {
            String[] split = string.split(";");

            World world = Bukkit.getWorld(split[0]);

            if (world == null)
            {
                Util.logErr("Missing world " + split[0]);
                return;
            }

            double x = Double.parseDouble(split[1]);
            double y = Double.parseDouble(split[2]);
            double z = Double.parseDouble(split[3]);

            locations.add(new Location(world, x, y, z));
        });

        return locations;
    }

    public static List<String> serializeLocations(Set<Location> locations)
    {
        List<String> list = new ArrayList<>();

        locations.forEach(loc ->
            list.add(loc.getWorld().getName() + ";" + loc.getBlockX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ())
        );

        return list;
    }
}
