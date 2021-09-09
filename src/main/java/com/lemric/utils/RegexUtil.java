package com.lemric.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static String pregQuote(String pStr) {
        return pStr.replaceAll("[.\\\\+*?\\[\\^\\]$(){}=!<>|:\\-]", "\\\\$0");
    }

    public static ArrayList<ArrayList<ArrayList<Object>>> preMatchAll(String pattern, String subject) {
        ArrayList<ArrayList<ArrayList<Object>>> list = new ArrayList<>();

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(subject);
        while(m.find()) {
            list.add(new ArrayList<ArrayList<Object>>() {{
                add(new ArrayList<>() {{
                    add(m.group(0));
                    add(m.start(0));
                }});
                add(new ArrayList<>() {{
                    add(m.group(1));
                    add(m.start(1));
                }});
                add(new ArrayList<>() {{
                    add(m.group(2));
                    add(m.start(2));
                }});
            }});

        }

        return list;
    }

}