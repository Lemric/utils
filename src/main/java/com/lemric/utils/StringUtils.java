package com.lemric.utils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final int INT_FALSE = -1;

    private static boolean empty(String string) {
        if (string == null || string.length() < 1) {
            return true;
        }
        return false;
    }

    /**
     * UTF-8 aware alternative to strpos
     * Find position of first occurrence of a string
     * @param string - string String being examined
     * @param string - string String being searced for (non-regexp)
     * @return mixed Number of characters before the first match or INT_FALSE on failure
     * @see //www.php.net/strpos
     */
    public static int strpos(String string, String search) {
        if (empty(string)) {
            return INT_FALSE;
        }
        if (empty(search)) {
            return INT_FALSE;
        }
        return string.indexOf(search);
    }

    /**
     * UTF-8 aware alternative to strpos
     * Find position of first occurrence of a string
     * @param string - string String being examined
     * @param string - string String being searced for
     * @param offset - int Optional, specifies the position from which the search should be performed
     * @return mixed Number of characters before the first match or INT_FALSE for no match.
     * @see //www.php.net/strpos
     */
    public static int strpos(String string, String search, int offset) {
        if (empty(string)) {
            return INT_FALSE;
        }
        if (empty(search)) {
            return INT_FALSE;
        }
        int off = Math.min(offset, string.length() - 1);
        return string.indexOf(search, off);
    }

    /**
     * UTF-8 aware alternative to strrpos.
     * Finds position of last occurrence of a string,
     * a.k.a: String.lastIndexOf
     * @param string - string String being examined
     * @param string - string String being searced for
     * @return mixed Number of characters before the last match or INT_FALSE on failure
     * @see //www.php.net/strrpos
     */
    public static int strrpos(String string, String search) {
        if (empty(string)) {
            return INT_FALSE;
        }
        if (empty(search)) {
            return INT_FALSE;
        }
        return string.lastIndexOf(search);
    }

    /**
     * UTF-8 aware alternative to substr
     * Return part of a string given character offset (and optionally length)
     * @param string - string value to operate on.
     * @param offset integer number of UTF-8 characters offset (from left)
     * @param length integer length in UTF-8 characters from offset
     * @return mixed string or "" if failure
     * @see //www.php.net/substr
     */
    public static String substr(String string, int offset, int length) {
        if (empty(string)) {
            return "";
        }
        if (offset < 0 || length < 1) {
            return "";
        }
        int start = Math.min(string.length() - 1, offset);
        int end = Math.min(string.length() - 1, offset + length - 1);
        return string.substring(start, end);
    }

    /**
     * UTF-8 aware alternative to strtlower
     * Make a string lowercase
     * Note: The concept of a characters "case" only exists is some alphabets
     * such as Latin, Greek, Cyrillic, Armenian and archaic Georgian - it does
     * not exist in the Chinese alphabet, for example. See Unicode Standard
     * Annex #21: Case Mappings
     *
     * @param string value to be changed
     * @return mixed either string in lowercase or "" is UTF-8 invalid
     * @see //www.php.net/strtolower
     */
    public static String strtolower(String string) {
        if (empty(string)) {
            return "";
        }
        return string.toLowerCase(Locale.getDefault());
    }

    /**
     * UTF-8 aware alternative to strtoupper
     * Make a string uppercase
     * Note: The concept of a characters "case" only exists is some alphabets
     * such as Latin, Greek, Cyrillic, Armenian and archaic Georgian - it does
     * not exist in the Chinese alphabet, for example. See Unicode Standard
     * Annex #21: Case Mappings
     * @param string value to be changed
     * @return mixed either string in uppercase or FALSE is UTF-8 invalid
     * @see //www.php.net/strtoupper
     */
    public static String strtoupper(String string) {
        if (empty(string)) {
            return "";
        }
        return string.toUpperCase(Locale.getDefault());
    }

    /**
     * UTF-8 aware alternative to strlen
     * Returns the number of characters in the string (NOT THE NUMBER OF BYTES),
     * @param string UTF-8 string
     * @return int number of UTF-8 characters in string
     * @see //www.php.net/strlen
     */
    public static int strlen(String string) {
        if (empty(string)) {
            return 0;
        }
        return string.length();
    }

    /**
     * UTF-8 aware alternative to str_replace
     * @param string string to search
     * @param oldValPatt existing string to replace
     * @param newVal new string to replace with
     * @see //www.php.net/str_ireplace
     */
    public static String str_replace(String string, String oldValPatt, String newVal) {
        return str_replace(string, oldValPatt, newVal, Integer.MAX_VALUE);
    }

    /**
     * UTF-8 aware alternative to str_replace
     * @param string string to search
     * @param oldValPatt existing string to replace
     * @param newVal new string to replace with
     * @param count int value to be passed by referene
     * @see //www.php.net/str_ireplace
     */
    public static String str_replace(String string, String oldValPatt, String newVal, int count) {
        return StringTools.replace(string, oldValPatt, newVal, count);
    }

    /**
     * UTF-8 aware alternative to str_ireplace
     * Case-insensitive version of str_replace
     * @param string - string to search
     * @param oldValPatt - existing string to replace
     * @param newVal - new string to replace with
     * @see //www.php.net/str_ireplace
     */
    public static String str_ireplace(String string, String oldValPatt, String newVal) {
        return str_ireplace(string, oldValPatt, newVal, Integer.MAX_VALUE);
    }

    /**
     * UTF-8 aware alternative to str_ireplace
     * Case-insensitive version of str_replace
     * @param string string to search
     * @param oldValPatt existing string to replace
     * @param newVal new string to replace with
     * @param count int value to be passed by referene
     * @see //www.php.net/str_ireplace
     */
    public static String str_ireplace(String string, String oldValPatt, String newVal, int count) {
        oldValPatt = "(?i)" + oldValPatt;
        return str_replace(string, oldValPatt, newVal, count);
    }

    /**
     * UTF-8 aware alternative to ucfirst
     * Make a string's first character uppercase
     * @param string - string to change
     * @return string with first character as upper case (if applicable)
     * @see //www.php.net/ucfirst
     */
    public static String ucfirst(String string) {
        return StringTools.uppercaseFirstChar(string);
    }

    /**
     * UTF-8 aware alternative to ucwords
     * Uppercase the first character of each word in a string
     * @param string - string to change
     * @return string with first char of each word uppercase
     * @see //www.php.net/ucwords
     */
    public static String ucwords(String string) {
        return StringTools.uppercaseWords(string);
    }

    /**
     * UTF-8 aware replacement for rtrim()
     * Strip whitespace (or other characters) from the end of a string
     * Note: you only need to use this if you are supplying the charlist
     * optional arg and it contains UTF-8 characters. Otherwise rtrim will
     * work normally on a UTF-8 string
     * @param string the string to be trimmed
     * @return string the trimmed string
     * @see //www.php.net/rtrim
     */
    public static String rtrim(String string) {
        return StringTools.rightTrim(string);
    }

    /**
     * UTF-8 aware replacement for rtrim()
     * Strip whitespace (or other characters) from the end of a string
     * Note: you only need to use this if you are supplying the charlist
     * optional arg and it contains UTF-8 characters. Otherwise rtrim will
     * work normally on a UTF-8 string
     * @param string the string to be trimmed
     * @param charlist the optional charlist of additional characters to trim
     * @return string the trimmed string
     * @see //www.php.net/rtrim
     */
    public static String rtrim(String string, String charlist) {
        return StringTools.rtrim(string, charlist);
    }

    /**
     * UTF-8 aware replacement for ltrim()
     * Strip whitespace (or other characters) from the beginning of a string
     * Note: you only need to use this if you are supplying the charlist
     * optional arg and it contains UTF-8 characters. Otherwise ltrim will
     * work normally on a UTF-8 string
     * @param string the string to be trimmed
     * @return string the trimmed string
     * @see //www.php.net/ltrim
     */
    public static String ltrim(String string) {
        return StringTools.ltrim(string);
    }

    /**
     * UTF-8 aware replacement for ltrim()
     * Strip whitespace (or other characters) from the beginning of a string
     * Note: you only need to use this if you are supplying the charlist
     * optional arg and it contains UTF-8 characters. Otherwise ltrim will
     * work normally on a UTF-8 string
     * @param string the string to be trimmed
     * @param charlist the optional charlist of additional characters to trim
     * @return string the trimmed string
     * @see //www.php.net/ltrim
     */
    public static String ltrim(String string, String charlist) {
        return StringTools.ltrim(string, charlist);
    }

    /**
     * UTF-8 aware replacement for trim()
     * Strip whitespace (or other characters) from the beginning and end of a string
     * Note: you only need to use this if you are supplying the charlist
     * optional arg and it contains UTF-8 characters. Otherwise trim will
     * work normally on a UTF-8 string
     * @param string the string to be trimmed
     * @return string the trimmed string
     * @see //www.php.net/trim
     */
    public static String trim(String string) {
        string = ltrim(string);
        return rtrim(string);
    }

    /**
     * UTF-8 aware replacement for trim()
     * Strip whitespace (or other characters) from the beginning and end of a string
     * Note: you only need to use this if you are supplying the charlist
     * optional arg and it contains UTF-8 characters. Otherwise trim will
     * work normally on a UTF-8 string
     * @param string the string to be trimmed
     * @param charlist the optional charlist of additional characters to trim
     * @return string the trimmed string
     * @see //www.php.net/trim
     */
    public static String trim(String string, String charlist) {
        string = ltrim(string, charlist);
        return rtrim(string, charlist);
    }

    /**
     * UTF-8 aware substr_replace
     * Replace text within a portion of a string
     * @param string the value to operate on.
     * @param newVal the replacement string
     * @param start int postion in string to start.
     * @param length int length to operate on.
     * @see //www.php.net/substr_replace
     */
    public static String substr_replace(String string, String newVal, int start, int length) {
        if (empty(string)) {
            return "";
        }
        if (empty(newVal)) {
            return string;
        }
        String sub0 = string.substring(0, start);
        int end = Math.min(start + length - 1, string.length() - 1);
        String sub1 = string.substring(end);
        return sub0 + newVal + sub1;
    }

    public static int strcmp(String string1, String string2) {
        if (string1 == string2) {
            return 0;
        }
        if (empty(string1)) {
            return -1;
        }
        return string1.compareTo(string2);
    }

    /**
     * UTF-8 aware alternative to strcasecmp
     * A case insensivite string comparison
     * @param string1 string 1 to compare
     * @param string2 string 2 to compare
     * @return int < 0 if str1 is less than str2; > 0 if str1 is greater than str2, and 0 if they are equal.
     * @see //www.php.net/strcasecmp
     */
    public static int strcasecmp(String string1, String string2) {
        if (string1 == string2) {
            return 0;
        }
        if (empty(string1)) {
            return -1;
        }
        return string1.compareToIgnoreCase(string2);
    }

    /**
     * UTF-8 aware alternative to strspn
     * Find length of initial segment matching mask
     * @param string the haystack
     * @param mask the mask
     * @param start location to start
     * @param length location to stop
     * @see //www.php.net/strspn
     */
    public static int strspn(String string, String mask, int start, int length) {
        if (empty(string) || empty(mask)) {
            return -1;
        }
        if (start < 0 || length < 0) {
            return -1;
        }
        int len = Math.min(start + length - 1, string.length() - 1);
        int idx = Math.min(start, string.length() - 1);
        String sub0 = string.substring(idx, len);
        return sub0.indexOf(mask);
    }

    /**
     * UTF-8 aware alternative to strcspn
     * Find length of initial segment NOT matching mask
     * @param string - string to operate from.
     * @param mask - the mask
     * @param start - int Optional starting character position (in characters)
     * @param length - int Optional length
     * @return int the length of the initial segment of str1 which does not contain any of the characters in str2
     * @see //www.php.net/strcspn
     */
    public static int strcspn(String string, String mask, int start, int length) {
        if (empty(string) || empty(mask)) {
            return -1;
        }
        int len = Math.min(start + length - 1, string.length() - 1);
        int idx = Math.min(start, string.length() - 1);
        String sub0 = string.substring(idx, len);
        return sub0.lastIndexOf(mask);
    }

    /**
     * UTF-8 aware alternative to stristr
     * Returns all of haystack from the first occurrence of needle to the end.
     * needle and haystack are examined in a case-insensitive manner
     * Find first occurrence of a string using case insensitive comparison
     * @param string - string to operate from.
     * @param search - value to search for.
     * @return string the sub string
     * @see //www.php.net/stristr
     */
    public static String stristr(String string, String search) {
        return StringTools.subString(string, search, true);
    }

    public static String strcistr(String string, String search) {
        return StringTools.subString(string, search, false);
    }

    /**
     * UTF-8 aware alternative to strrev
     * Reverse a string
     * @param string String to be reversed
     * @return string The string in reverse character order
     * @see //www.php.net/strrev
     */
    public static String strrev(String string) {
        return StringTools.reverse(string);
    }

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
    public static boolean strpbrk(final String s,final String accept) {
        for(int i=0;i< s.length();i++) {
            if(accept.indexOf(s.charAt(i))!=-1) {
                return true;
            }
        }
        return false;
    }
}
