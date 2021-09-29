package com.lemric.utils;

import java.util.*;

public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {
    public static <K,V> Map<V,K> array_flip(Map<K,V> map) {
        Map<V,K> flipped = new HashMap<>();
        for(Map.Entry<K,V> entry : map.entrySet()) {
            flipped.put(entry.getValue(), entry.getKey());
        }
        return flipped;
    }
    public static Map<String, Object> array_replace(Map<String, Object> array, Map<String, Object> ...replacements) {
        int maxLength = array.size();
        Map<String, Object> newArray = new HashMap<>(array);
        for (int i = 0; i < maxLength; i++) {
            for (Map<String, Object> replacement : replacements) {
                if(replacement.size() > maxLength) {
                    maxLength = replacement.size();
                }
            }
        }
        for (int i = 0; i < maxLength; i++) {
            for (Map<String, Object> replacement : replacements) {
                if(replacement.size() > i) {
                    newArray.put(String.valueOf(i), replacement.get(i));
                }
            }
        }

        return newArray;
    }
    public static Map<String, Object> array_replace(Map<String, Object> array, Object[] ...replacements) {
        int maxLength = array.size();
        Map<String, Object> newArray = new HashMap<>(array);
        for (int i = 0; i < maxLength; i++) {
            for (Object[] replacement : replacements) {
                if(replacement.length > maxLength) {
                    maxLength = replacement.length;
                }
            }
        }
        for (int i = 0; i < maxLength; i++) {
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
