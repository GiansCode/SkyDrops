package me.itsmas.skydrops.util;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMap<E>
{
    private NavigableMap<Double, E> map = new TreeMap<>();
    private double total = 0;

    public void put(E element, double weight)
    {
        total += weight;

        map.put(total, element);
    }

    public int size()
    {
        return map.size();
    }

    public E nextRandom()
    {
        double random;
        E value = null;

         do
         {
              random = ThreadLocalRandom.current().nextDouble(total) + 1;

              Map.Entry<Double, E> ceiling = map.ceilingEntry(random);

              if (ceiling != null)
              {
                  value = ceiling.getValue();
              }
         } while (value == null);


        return map.ceilingEntry(random).getValue();
    }

    public Set<Map.Entry<Double, E>> entries()
    {
        return map.entrySet();
    }
}
