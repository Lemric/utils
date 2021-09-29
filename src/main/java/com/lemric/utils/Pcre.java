package com.lemric.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import com.google.code.regexp.Matcher;
import com.google.code.regexp.Pattern;

public class Pcre {

    public static final int PREG_PATTERN_ORDER = 1;
    public static final int PREG_SET_ORDER = 2;
    public static final int PREG_OFFSET_CAPTURE = 256;

    private static String groupCapturePCRE = "?P";
    private static String groupCaptureJava = "?";
    private static HashMap<String, String> delimiters = new HashMap<>() {{
        put("{", "}");
        put("/", "/");
        put("#", "#");
        put("@", "@");
        put("!", "!");
        put("%", "%");
    }};

    public static String siu = "siu";
    public static String u = "u";
    public static String iu = "iu";
    public static String ue = "ue";
    public static String spc = "~";
    public static String empty = "";
    public static String[] s_alwsp = {
            "/",
            "@",
            "!"
    };
    public static String[] SLASH_CHARACTERS = {
            "<", //0
            ">", //1
            ".", //2
            ",", //3
            "\"", //4
            "/", //5
            "'", //6
            "(", //7
            ")", //8
            "=", //9
            "&", //10
            ";", //11
            ":", //12
            "-", //13
            "!", //14
            "_", //15
            "+", //16
            "#", //17
            "[", //18
            "]", //19
            "*", //20
            "%", //22
            "(", //23
            ")" //24
    };
    public static String[] SLASH_CHARACTERS_SUBSTITUTION = {
            "\\<", //0
            "\\>", //1
            "\\.", //2
            "\\,", //3
            "\\\"", //4
            "\\/", //5
            "\\'", //6
            "\\(", //7
            "\\)", //8
            "\\=", //9
            "\\&", //10
            "\\;", //11
            "\\:", //12
            "\\-", //13
            "\\!", //14
            "\\_", //15
            "\\+", //16
            "\\#", //17
            "\\[", //18
            "\\]", //19
            "\\*", //20
            "\\%", //22
            "\\(", //23
            "\\)" //24
    };

    /** Creates a new instance of RegexFinder */
    public Pcre() {
    }

    public static boolean preg_match(String pattern, String input) {
        return preg_match(pattern, input, new HashMap<>());
    }

    public static boolean preg_match(String pattern, String input, Map<String, Object> matches) {
        return preg_match(pattern, input, matches, null);
    }
    public static boolean preg_match(String pattern, String input, Map<String, Object> matches, Integer flags) {
        Pattern r;
        if(flags != null) {
            r = Pcre.compile(pattern, flags);
        } else {
            r = Pcre.compile(pattern);
        }
        Matcher m = r.matcher(input);

        if (m.matches()) {
            Map<String, String> namedGroups = m.namedGroups();
            for (Map.Entry<String, String> stringStringEntry : namedGroups.entrySet()) {
                matches.put(stringStringEntry.getKey(), m.group(stringStringEntry.getKey()));
            }
            int groupCount = m.groupCount() + 1;
            for(int i = 0; i < groupCount; i++) {
                matches.put(String.valueOf(i), m.group(i));
            }
            return true;
        } else {
            return false;
        }
    }

    public static String[] preg_match_all(String pattern, String subject) {
        return preg_match_all(pattern, subject, null);
    }
    public static String[] preg_match_all(String pattern, String subject, Integer flags) {
        Pattern p;
        if(flags != null) {
            p = Pcre.compile(pattern, flags);
        } else {
            p = Pcre.compile(pattern);
        }
        return preg_match_all(p, subject);
    }

    public static String[] preg_match_all(Pattern p, String subject) {
        Matcher m = p.matcher(subject); // get a matcher object
        StringBuilder out = new StringBuilder();

        boolean split = false;
        while (m.find()) {
            out.append(m.group());
            out.append(spc);
            split = true;
        }

        return (split) ? out.toString().split(spc) : new String[0];
    }

    public static boolean preg_match_all(String pattern, String subject, ArrayList<ArrayList<ArrayList<Object>>> matches, int flags) {
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(subject);
        if (flags == (PREG_OFFSET_CAPTURE) || flags == (PREG_SET_ORDER) ) {
            matches.add(new ArrayList<>());
            matches.add(new ArrayList<>());
            matches.add(new ArrayList<>());
        }


        boolean match = false;
        while(m.find()) {
            match = true;
            if (flags == (PREG_OFFSET_CAPTURE | PREG_SET_ORDER)) {
                matches.add(new ArrayList<>() {{
                    add(new ArrayList<>() {{
                        String group = m.group(0);
                        add(group == null ? "" : group);
                        add(m.start(0));
                    }});
                    add(new ArrayList<>() {{
                        String group = m.group(1);
                        add(group == null ? "" : group);
                        add(m.start(1));
                    }});
                    add(new ArrayList<>() {{
                        String group = m.group(2);
                        add(group == null ? "" : group);
                        add(m.start(2));
                    }});
                }});
            } else if (flags == (PREG_OFFSET_CAPTURE)) {
                matches.get(0).add(new ArrayList<>() {{
                    String group = m.group(0);
                    add(group == null ? "" : group);
                    add(m.start(0));
                }});
                matches.get(1).add(new ArrayList<>() {{
                    String group = m.group(1);
                    add(group == null ? "" : group);
                    add(m.start(1));
                }});
                matches.get(2).add(new ArrayList<>() {{
                    String group = m.group(2);
                    add(group == null ? "" : group);
                    add(m.start(2));
                }});
            } else if (flags == (PREG_SET_ORDER)) {
                matches.get(0).add(new ArrayList<>() {{
                    String group = m.group(0);
                    add(group == null ? "" : group);
                    add(m.start(0));
                }});
                matches.get(1).add(new ArrayList<>() {{
                    String group = m.group(1);
                    add(group == null ? "" : group);
                    add(m.start(1));
                }});
                matches.get(2).add(new ArrayList<>() {{
                    String group = m.group(2);
                    add(group == null ? "" : group);
                    add(m.start(2));
                }});
            }

        }

        return match;
    }

    public static String[] preg_match_all(String pattern, String subject, int groups) {
        Pattern p = Pcre.compile(Pcre.getPatternWithoutFlags(pattern));
        return preg_match_all(p, subject, groups);
    }

    public static String[] preg_match_all(Pattern p, String subject, int groups) {
        Matcher m = p.matcher(subject); // get a matcher object
        StringBuilder out = new StringBuilder();

        boolean split = false;
        while (m.find()) {
            out.append(m.group(groups));
            out.append(spc);
            split = true;
        }

        return (split) ? out.toString().split(spc) : new String[0];
    }



    public static String preg_replace(String pattern, String replacement, String subject) {
        Pattern p = Pcre.compile(pattern);
        return preg_replace(p, replacement, subject);
    }

    public static String preg_replace(Pattern p, String replacement, String subject) {
        return p.matcher(subject).replaceAll(replacement);
    }

    public static String str_replace(String[] oldChars, String[] newChars, String subject) {
        for (int i = 0; i < oldChars.length; i++) {
            if(subject.contains(oldChars[i])){
                if (newChars[i] != null && !newChars[i].equals(empty)) {
                    subject = subject.replace(oldChars[i], newChars[i]);
                } else {
                    subject = subject.replace(oldChars[i], empty);
                }
            }
        }

        return subject;
    }

    public static String str_replace(String[] oldChars, String newChar, String subject) {
        for (String oldChar : oldChars) {
            subject = subject.replace(oldChar, newChar);
        }

        return subject;
    }

    public static String str_replace(String oldChar, String newChar, String subject) {
        subject = subject.replace(oldChar, newChar);
        return subject;
    }

    public static String preg_replace(String[] patterns, String[] replacements, String subject) {
        for (int i = 0; i < patterns.length; i++) {
            subject = Pcre.preg_replace(patterns[i], (replacements[i] != null) ? replacements[i] : "", subject);
        }

        return subject;
    }

    public static String preg_replace(String[] patterns, String[] replacements, String subject, String[] indexof) {
        for (int i = 0; i < patterns.length; i++) {
            if (subject.contains(indexof[i])) {
                subject = Pcre.preg_replace(patterns[i], replacements[i], subject);
            }
        }

        return subject;
    }

    public static String preg_replace(Pattern[] patterns, String[] replacements, String subject, String[] indexof) {
        //Simplified preg_replace multiple using an indexOf array. If indexOf[i] exists in subject we will perform the regular expression at i
        for (int i = 0; i < patterns.length; i++) {
            if (subject.contains(indexof[i])) {
                subject = Pcre.preg_replace(patterns[i], replacements[i], subject);
            }
        }

        return subject;
    }

    public static String preg_replace(String[] patterns, String replacement, String subject) {
        for (String pattern : patterns) {
            subject = Pcre.preg_replace(pattern, replacement, subject);
        }

        return subject;
    }

    public static String preg_replace(Pattern[] patterns, String replacement, String subject) {
        for (Pattern pattern : patterns) {
            subject = Pcre.preg_replace(pattern, replacement, subject);
        }

        return subject;
    }

    public static String[] preg_split(String pattern, String subject) {
        return subject.split(pattern);
    }

    public static String findMatch(String pattern, String regex, String text) {
        Pattern p = Pcre.compile(regex);
        Matcher m = p.matcher(text); // get a matcher object

        if(m.find()) {
            //return m.group();
            Pattern p2 = Pcre.compile(pattern);
            Matcher m2 = p2.matcher(m.group());
            if(m2.find())
                return m2.group();
            else return "";
        }
        else{
            return "";
        }
    }

    public static String preg_quote(String str) {
        return Pcre.str_replace(Pcre.SLASH_CHARACTERS, Pcre.SLASH_CHARACTERS_SUBSTITUTION, str);
    }

    public static Pattern compile(String pattern, int flags) {
        return Pattern.compile(Pcre.getPatternWithoutFlags(pattern), flags);
    }

    public static Pattern compile(String pattern) {
        return Pattern.compile(Pcre.getPatternWithoutFlags(pattern));
    }

    public static String getPatternWithoutFlags(String pattern) {

        for (String s_alws : s_alwsp) {
            if (pattern.startsWith(s_alws) && pattern.endsWith(s_alws + siu)) {
                pattern = pattern.substring(1, pattern.length() - 4);
            } else if (pattern.startsWith(s_alws) && pattern.endsWith(s_alws + u)) {
                pattern = pattern.substring(1, pattern.length() - 2);
            } else if (pattern.startsWith(s_alws) && pattern.endsWith(s_alws + iu)) {
                pattern = pattern.substring(1, pattern.length() - 3);
            } else if (pattern.startsWith(s_alws) && pattern.endsWith(s_alws + ue)) {
                pattern = pattern.substring(1, pattern.length() - 3);
            }
        }

        return pattern;
    }

    public static String parseRegExp(String regexp) {
        if(regexp.length() > 0) {
            String delimiterOpen = String.valueOf(regexp.charAt(0));
            String delimiterClose = delimiters.get(delimiterOpen);
            String regexpWithoutOptions = StringUtils.substringBeforeLast(regexp, delimiterClose) + delimiterClose;
            if(regexpWithoutOptions.length() > 0 && regexpWithoutOptions.length() != regexp.length()) {
                String delimiterClose2 = String.valueOf(regexpWithoutOptions.charAt(regexpWithoutOptions.length() -1));
                if (delimiterClose2.equals(delimiterClose)) {
                    regexp = regexpWithoutOptions.replace(delimiterOpen, "")
                            .replace(delimiterClose2, "")
                            .replace(groupCapturePCRE + "<", groupCaptureJava + "<");
                }
            }
        }
        return regexp.replace("\\/", "/");
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Integer> getNamedGroups(Pattern regex)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        Method namedGroupsMethod = Pattern.class.getDeclaredMethod("namedGroups");
        namedGroupsMethod.setAccessible(true);

        Map<String, Integer> namedGroups = null;
        namedGroups = (Map<String, Integer>) namedGroupsMethod.invoke(regex);

        if (namedGroups == null) {
            throw new InternalError();
        }

        return Collections.unmodifiableMap(namedGroups);
    }
}