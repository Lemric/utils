package com.lemric.utils;

import java.util.*;
import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;

import static com.lemric.utils.CArray.array;

public class RegexUtil {

    public final static int PREG_PATTERN_ORDER =  1;
    public final static int PREG_SET_ORDER =  2;
    public final static int PREG_OFFSET_CAPTURE =  256;
    public final static int PREG_SPLIT_NO_EMPTY =  1;
    public final static int PREG_SPLIT_DELIM_CAPTURE =  2;
    public final static int PREG_SPLIT_OFFSET_CAPTURE =  4;
    public final static int PREG_GREP_INVERT =  1;
    public final static int PREG_NO_ERROR =  0;
    public final static int PREG_INTERNAL_ERROR =  1;
    public final static int PREG_BACKTRACK_LIMIT_ERROR =  2;
    public final static int PREG_RECURSION_LIMIT_ERROR =  3;
    public final static int PREG_BAD_UTF8_ERROR =  4;
    public final static int PREG_BAD_UTF8_OFFSET_ERROR =  5;
    public final static String PCRE_VERSION =  "7.9 2009-04-11";
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

    // REF.PHP:https://www.php.net/manual/en/function.preg-match-all.php
    // $flags = PREG_PATTERN_ORDER
    public static boolean preg_match_all(String pattern, String subject, CArray<CArray> matcheResult, int flags) {
        return RegexUtil.preg_match_all(pattern, subject, matcheResult, flags, 0);
    }
    public static boolean preg_match_all(String pattern, String subject, CArray<CArray> matcheResult, int flags, int offset) {
        if (matcheResult == null) {
            throw new IllegalArgumentException("matcheResult can not be null !");
        }
        Pattern regexp = Pattern.compile(pattern);
        Matcher matcher = regexp.matcher(subject);
        boolean result = false;
        if ((PREG_SET_ORDER & flags) == PREG_SET_ORDER) {
            Map<String, String> namedGrps = matcher.namedGroups();
            Map<String, String> flipGrps = new IMap();
            for (Map.Entry<String, String> e : namedGrps.entrySet()) {
                flipGrps.put(e.getValue(), e.getKey());
            }
            int hits = 0;
            while (matcher.find()) {
                if (!matcheResult.containsKey(hits)) {
                    matcheResult.put(hits, new LinkedHashMap<Object, String>());
                }
                int cnt = matcher.groupCount();
                for (int idx = 0; idx <= cnt; idx++) {
                    if ((PREG_OFFSET_CAPTURE & flags) == PREG_OFFSET_CAPTURE) {
                        matcheResult.get(hits).add(array(matcher.group(idx), matcher.start(idx)));
                    } else {
                        matcheResult.get(hits).add(matcher.group(idx));
                    }
                    if (flipGrps.containsKey(idx)) {
                        if ((PREG_OFFSET_CAPTURE & flags) == PREG_OFFSET_CAPTURE) {
                            matcheResult.get(hits).put(flipGrps.get(idx),array(matcher.group(idx), matcher.start(idx)));
                        } else {
                            matcheResult.get(hits).put(flipGrps.get(idx),matcher.group(idx));
                        }
                    }
                }
                hits++;
                result = true;
            }
        } else {
            while (matcher.find()) {
                int cnt = matcher.groupCount();
                for (int idx = 0; idx <= cnt; idx++) {
                    if (!matcheResult.containsKey(idx)) {
                        matcheResult.put(idx, new CArray<String>());
                    }
                    if ((PREG_OFFSET_CAPTURE & flags) == PREG_OFFSET_CAPTURE) {
                        matcheResult.get(idx).add(array(matcher.group(idx), matcher.start(idx)));
                    } else {
                        matcheResult.get(idx).add(matcher.group(idx));
                    }
                }
                for(String name: regexp.groupNames()) {
                    if (!matcheResult.containsKey(name)) {
                        matcheResult.put(name, new CArray<String>());
                    }
                    if ((PREG_OFFSET_CAPTURE & flags) == PREG_OFFSET_CAPTURE) {
                        matcheResult.get(name).add(array(matcher.group(name), matcher.start(name)));
                    } else {
                        matcheResult.get(name).add(matcher.group(name));
                    }
                }
                result = true;
            }
        }
        return result;
    }

}