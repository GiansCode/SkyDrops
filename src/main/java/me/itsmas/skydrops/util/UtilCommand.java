package me.itsmas.skydrops.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilCommand
{
    public static RandomMap<String> parseCommands(List<String> list)
    {
        RandomMap<String> commands = new RandomMap<>();

        list.forEach(string ->
        {
            Object[] data = getCommand(string);

            if (data != null)
            {
                commands.put((String) data[1], (double) data[0]);
            }
        });

        return commands;
    }

    public static Map<Double, String> parseRawCommands(List<String> list)
    {
        Map<Double, String> map = new HashMap<>();

        list.forEach(string ->
        {
            Object[] data = getCommand(string);

            if (data != null)
            {
                map.put((double) data[0], (String) data[1]);
            }
        });

        return map;
    }

    private static Object[] getCommand(String line)
    {
        String[] split = line.split("\\|");

        double chance;

        try
        {
            chance = Double.parseDouble(split[0]);
        }
        catch (NumberFormatException ex)
        {
            Util.logErr("Invalid number: " + split[0]);
            return null;
        }

        String command = split[1];

        return new Object[] {chance, command};
    }

    public static List<String> serializeCommands(Map<Double, String> map)
    {
        List<String> list = new ArrayList<>();

        map.forEach((key, value) -> list.add(key + "|" + value));

        return list;
    }
}
