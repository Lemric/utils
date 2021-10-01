package com.lemric.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.function.Function;

import static org.apache.commons.lang3.StringUtils.isNumeric;

public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {
    public static <K,V> Map<V,K> array_flip(Map<K,V> map) {
        Map<V,K> flipped = new HashMap<>();
        for(Map.Entry<K,V> entry : map.entrySet()) {
            flipped.put(entry.getValue(), entry.getKey());
        }
        return flipped;
    }
    public static Map<String, Integer> array_flip(String[] map) {
        Map<String, Integer> flipped = new HashMap<>();
        for(int i = 0; i < map.length; i++) {
            flipped.put(map[i], i);
        }
        return flipped;
    }
    public static <T> T array_pop(T[] array) {
        T[] arrayOld = (T[]) new Object[array.length];
        System.arraycopy(array, 0, arrayOld, 0, arrayOld.length);
        array = ArrayUtils.remove(array, array.length - 1);

        return arrayOld[array.length - 1];
    }
    public static <T> T[] unset(T[] array, int index) {
        return ArrayUtils.remove(array, index);
    }
    public static <T> boolean isset(T[] array, int index) {
        return array.length >= index+1 && array[index] != null;
    }

    public static Map<String, Object> array_replace(Map<String, Object> array, Map<String, Object> ...replacements) {
        Map<String, Object> newArray = new HashMap<>(array);
        for (Map<String, Object> replacement : replacements) {
            newArray.putAll(replacement);
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
    public static <K> Map<K, Object> array_diff_key(Map<K, Object> array1, Map<K, Object> array2, Map<K, Object> ...arrays) {
        array_diff_key__remove_common_key(array1, array2);
        for (Map<K, Object> currentArray : arrays) {
            array_diff_key__remove_common_key(array1, currentArray);
        }
        return array1;
    }

    private static <K> void array_diff_key__remove_common_key(Map<K, Object> array1, Map<K, Object> array2) {
        array1.entrySet().removeIf(ktEntry -> array2.containsKey(ktEntry.getKey()));
    }

    public static <K> Map<K, Object> array_udiff_assoc(Function<ArrayList, Integer> callback, Map<K, Object>... arrays) {
        if (arrays.length < 2) {
            return null;
        }

        if (!(arrays[0] instanceof Map)) {
            return null;
        }
        Map<K, Object> array = arrays[0];
        Map<K, Object> diffArray = new HashMap<>();
        boolean isFound = false;
        for (K entryKey : array.keySet()) {
            Object entryValue = array.get(entryKey);
            for (int k = 1; k < arrays.length && !isFound; k++) {
                if (!(arrays[k] instanceof Map)) {
                    return null;
                }
                Map<K, Object> checkArray = arrays[k];
                for (Map.Entry<K, Object> entry : checkArray.entrySet()) {
                    try {
                        boolean keyFound = entryKey.equals(entry.getKey());
                        boolean valueFound = false;
                        if (keyFound) {
                            valueFound = callback.apply(new ArrayList<Object>() {{
                                add(entryValue);
                                add(entry.getValue());
                            }}) == 0;
                        }

                        isFound = keyFound && valueFound;
                    } catch (Exception t) {
                        t.printStackTrace();
                    }

                    if (isFound) {
                        break;
                    }
                }
            }

            if (!isFound) {
                diffArray.put(entryKey, entryValue);
            }

            isFound = false;
        }

        return diffArray;
    }
}
