package com.lemric.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static String pregQuote(String pStr) {
        return pStr.replaceAll("[.\\\\+*?\\[\\^\\]$(){}=!<>|:\\-]", "\\\\$0");
    }

    public static void pregMatch(String pattern, String subject) {
        Set<String> namedGroups = getNamedGroupCandidates(pattern);
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(subject);
        int groupCount = m.groupCount();

        int matchCount = 0;

        if (m.find()) {
            // Remove invalid groups
            Iterator<String> i = namedGroups.iterator();
            while (i.hasNext()) {
                try {
                    m.group(i.next());
                } catch (IllegalArgumentException e) {
                    i.remove();
                }
            }

            matchCount += 1;
            System.out.println("Match " + matchCount + ":");
            System.out.println("=" + m.group() + "=");
            System.out.println();
            printMatches(m, namedGroups);

            while (m.find()) {
                matchCount += 1;
                System.out.println("Match " + matchCount + ":");
                System.out.println("=" + m.group() + "=");
                System.out.println();
                printMatches(m, namedGroups);
            }
        }
    }

    private static void printMatches(Matcher matcher, Set<String> namedGroups) {
        for (String name: namedGroups) {
            String matchedString = matcher.group(name);
            if (matchedString != null) {
                System.out.println(name + "=" + matchedString + "=");
            } else {
                System.out.println(name + "_");
            }
        }

        System.out.println();

        for (int i = 1; i < matcher.groupCount(); i++) {
            String matchedString = matcher.group(i);
            if (matchedString != null) {
                System.out.println(i + "=" + matchedString + "=");
            } else {
                System.out.println(i + "_");
            }
        }

        System.out.println();
    }

    private static Set<String> getNamedGroupCandidates(String regex) {
        Set<String> namedGroups = new TreeSet<String>();

        Matcher m = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>").matcher(regex);

        while (m.find()) {
            namedGroups.add(m.group(1));
        }

        return namedGroups;
    }
    public static ArrayList<ArrayList<ArrayList<Object>>> preMatchAll(String pattern, String subject) {
        ArrayList<ArrayList<ArrayList<Object>>> list = new ArrayList<>();

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(subject);
        while(m.find()) {
            list.add(new ArrayList<>() {{
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