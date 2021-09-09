package com.lemric.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String strtr(String str, String froms, String tos)
    {
        char[] str1=str.toCharArray();
        int j;
        for (int i=0,len=str.length(); i<len; ++i) {
            j=froms.indexOf(str.charAt(i));
            if (j!=-1) {
                str1[i]=tos.charAt(j);
            }
        }
        return String.valueOf(str1);
    }

    /**
     * Compares two strings, ignoring the case of ASCII characters. It treats
     * non-ASCII characters taking in account case differences. This is an
     * attempt to mimic glib's string utility function
     * <a href="http://developer.gnome.org/glib/2.28/glib-String-Utility-Functions.html#g-ascii-strcasecmp">g_ascii_strcasecmp ()</a>.
     *
     * This is a slightly modified version of java.lang.String.CASE_INSENSITIVE_ORDER.compare(String s1, String s2) method.
     *
     * @param str1  string to compare with str2
     * @param str2  string to compare with str1
     * @return      0 if the strings match, a negative value if str1 < str2, or a positive value if str1 > str2
     */
    public static int strcasecmp(String str1, String str2) {
        int n1 = str1.length();
        int n2 = str2.length();
        int min = Math.min(n1, n2);
        for (int i = 0; i < min; i++) {
            char c1 = str1.charAt(i);
            char c2 = str2.charAt(i);
            if (c1 != c2) {
                if ((int) c1 > 127 || (int) c2 > 127) { //if non-ASCII char
                    return c1 - c2;
                } else {
                    c1 = Character.toUpperCase(c1);
                    c2 = Character.toUpperCase(c2);
                    if(c1 != c2) {
                        c1 = Character.toLowerCase(c1);
                        c2 = Character.toLowerCase(c2);
                        if(c1 != c2) {
                            return c1 - c2;
                        }
                    }
                }
            }
        }
        return n1 - n2;
    }
    public static boolean pregMatch(String pattern, String content) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        return m.matches();
    }
    public static Matcher pregMatch(String pattern, String content, boolean match) {
        if(match) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(content);
            return m;
        }

        return null;
    }

    public static String trim(String text) {
        return text.trim();
    }
    public static String ltrim(String text, String trimBy) {
        int beginIndex = 0;
        int endIndex = text.length();

        while (text.substring(beginIndex, endIndex).startsWith(trimBy)) {
            beginIndex += trimBy.length();
        }
        return text.substring(beginIndex, endIndex);
    }
    public static String rtrim(String text, String trimBy) {
        int beginIndex = 0;
        int endIndex = text.length();

        while (text.substring(beginIndex, endIndex).endsWith(trimBy)) {
            endIndex -= trimBy.length();
        }

        return text.substring(beginIndex, endIndex);
    }

    public static String trim(String text, String trimBy) {
        int beginIndex = 0;
        int endIndex = text.length();

        while (text.substring(beginIndex, endIndex).startsWith(trimBy)) {
            beginIndex += trimBy.length();
        }

        while (text.substring(beginIndex, endIndex).endsWith(trimBy)) {
            endIndex -= trimBy.length();
        }

        return text.substring(beginIndex, endIndex);
    }

    public static boolean strpbrk(String str1, String str2) {
        return str2.chars().map(str1::indexOf).min().getAsInt() > 0;
    }
}
