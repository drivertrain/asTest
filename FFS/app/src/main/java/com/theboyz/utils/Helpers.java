package com.theboyz.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Helpers
{
    public static <T> List<String> getListFromIterator(Iterator<T> iterator)
    {
        List<String> rVal = new ArrayList<>();
        while (iterator.hasNext())
            rVal.add((String) iterator.next());
        return rVal;
    }//End getListFromIterator
}
