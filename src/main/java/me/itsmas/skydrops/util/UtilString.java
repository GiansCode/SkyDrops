package me.itsmas.skydrops.util;

import java.util.StringJoiner;

public class UtilString
{
    public static String combine(String[] args, int index)
    {
        StringJoiner joiner = new StringJoiner(" ");

        for (int i = index; i < args.length; i++)
        {
            joiner.add(args[i]);
        }

        return joiner.toString();
    }
}
