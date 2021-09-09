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
            ArrayList<ArrayList<Object>> list2 = new ArrayList<>();
            for(int i = 0; i < m.groupCount(); i++) {
                int e = i;
                list2.add(new ArrayList<>() {{
                    add(m.group(e));
                    add(m.start(e));
                }});
            }
            list.add(list2);

        }

        return list;
    }

}