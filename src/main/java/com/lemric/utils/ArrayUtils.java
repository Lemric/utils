package com.lemric.utils;

import java.util.*;

public class ArrayUtils {
    private static Map<String, Object> array_replace(Map<String, Object> array, Object[] ...replacements) {

        int maxLength = array.size();
        Map<String, Object> newArray = new HashMap<>();
        for (int i = 0; i < maxLength; i++) {
            if(array.size() > i) {
                newArray.put(String.valueOf(i), array.get(i));
            }
            for (Object[] replacement : replacements) {
                if(replacement.length > i) {
                    newArray.put(String.valueOf(i), replacement[i]);
                }
            }
        }

        return newArray;
    }

    public static String[] array_merge(String[] array1, String[] array2) {
        int i = 0;
        if (array1 != null && array1.length > 0 && array2 != null && array2.length > 0) {
            String[] array3 = new String[array1.length + array2.length];
            for (String element : array1) {
                array3[i] = element;
                i++;
            }
            for (String element : array2) {
                array3[i] = element;
                i++;
            }

            return array3;
        } else if (array1 != null && array1.length > 0) {
            return array1;
        } else if (array2 != null && array2.length > 0) {
            return array2;
        } else {
            return null;
        }
    }

    public static Object[] array_unique(Object array[]) {
        SortedSet<Object> set = new TreeSet<>(Arrays.asList(array));
        return set.toArray();
    }
}
