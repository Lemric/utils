package com.lemric.utils;

import org.apache.commons.lang3.StringEscapeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HeaderUtils {

    /**
     * Combines an array of arrays into one associative array.
     *
     * Each of the nested arrays should have one or two elements. The first
     * value will be used as the keys in the associative array, and the second
     * will be used as the values, or true if the nested array only contains one
     * element. Array keys are lowercased.
     *
     * Example:
     *
     *     HeaderUtils::combine([["foo", "abc"], ["bar"]])
     *     // => ["foo" => "abc", "bar" => true]
     */
    public static HashMap<String, Object> combine(String[] part)
    {
        String name = part[0];
        String value = part[1];
        HashMap<String, Object> assoc = new HashMap<>();
        assoc.put(name, value);
        return  assoc;
    }
    public static HashMap<String, Object> combine(String[][] parts)
    {
        HashMap<String, Object> assoc = new HashMap<>();
        for (String[] part : parts) {
            String name = part[0];
            String value = part[1];
            assoc.put(name, value);
        }

        return assoc;
    }

    public static String[] split(String header, String separators) {
        return header.split(separators);
    }

    public static String toString(Map<String, Object> assoc, String separator)
    {
        ArrayList<String> parts = new ArrayList<>();
        for (Map.Entry<String, Object> stringObjectEntry : assoc.entrySet()) {
            Boolean value = (Boolean) stringObjectEntry.getValue();
            if(value) {
                parts.add(stringObjectEntry.getKey());
            } else {
                parts.add(stringObjectEntry.getKey() + "=" + HeaderUtils.quote((String) stringObjectEntry.getValue()));
            }
        }

        return String.join(separator + " ", parts);
    }

    public static String quote(String s)
    {
        if (s.matches("/^[a-z0-9!#$%&\"*.^_`|~-]+$/i")) {
            return s;
        }

        return "" +  StringEscapeUtils.escapeJava(s) + "";
    }

    public static long parseDate(String date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        try {
            Date d = f.parse(date);
            return d.getTime();
        } catch (ParseException ignored) {
        }

        return 0;
    }
}
