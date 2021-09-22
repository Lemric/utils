package com.lemric.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Various String utilities that is available in php & ruby.
 * @author MatthewOng
 */
public class StringTools {

    private StringTools() {
    }

    public static boolean empty(String aVal) {
        if (aVal == null || aVal.length() < 1) {
            return true;
        }
        return false;
    }

    public static boolean anyEmpty(String... values) {
        for (String aVal : values) {
            if (empty(aVal)) {
                return true;
            }
        }
        return false;
    }

    public static boolean allEmpty(String... values) {
        for (String aVal : values) {
            if (empty(aVal)) {
                return false;
            }
        }
        return true;
    }

    public static String reline(String text, int sizePerLine) {
        LinkedList<String> list = toLines(text, sizePerLine);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            String aLine = (String) list.get(i);
            sb.append(aLine + "\n");
        }
        return sb.toString();
    }

    public static LinkedList<String> toLines(String text, int sizePerLine) {
        LinkedList<String> list = new LinkedList<String>();
        int len = text.length();
        int max = (int) len / sizePerLine;
        int bal = len % sizePerLine;
        for (int i = 0; i < max; i++) {
            int start = i * sizePerLine;
            int end = start + sizePerLine;
            String subStr = text.substring(start, end).replace("\n", "");
            list.add(subStr);
        }
        if (bal > 0) {
            String subStr = text.substring(max * sizePerLine);
            list.add(subStr);
        }
        return list;
    }

    public static int strPosition(String string, String search, boolean isCaseSensitive) {
        if (empty(string) || empty(search)) {
            return -1;
        }
        if (isCaseSensitive) {
            return string.indexOf(search);
        } else {
            string = string.toLowerCase();
            search = search.toLowerCase();
            return string.indexOf(search);
        }
    }

    public static String substr(String string, int offset) {
        if (empty(string)) {
            return "";
        }
        int off = Math.min(offset, string.length() - 1);
        return string = string.substring(offset);
    }

    public static String substr(String string, int offset, int length) {
        if (empty(string)) {
            return "";
        }
        int off = Math.min(offset, string.length() - 1);
        int len = Math.min(length, string.length() - 1);
        return string = string.substring(offset, len);
    }

    public static String subString(String string, String searchVal) {
        return subString(string, searchVal, true);
    }

    public static String subString(String string, String search, boolean isCaseSensitive) {
        if (empty(string)) {
            return "";
        }
        if (empty(search)) {
            return string;
        }
        int idx = -1;
        if (isCaseSensitive) {
            idx = string.indexOf(search);
        } else {
            String lstr = string.toLowerCase(Locale.getDefault());
            String lsearch = search.toLowerCase(Locale.getDefault());
            idx = lstr.indexOf(lsearch);
        }
        if (idx == -1) {
            return "";
        }
        return string.substring(idx);
    }

    public static String repeat(char aChar, int count) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            sb.append(aChar);
        }
        return sb.toString();
    }

    public static String repeat(String value, int count) {
        StringBuilder sb = new StringBuilder(value.length() * count);
        for (int i = 0; i < count; i++) {
            sb.append(value);
        }
        return sb.toString();
    }

    public static String cropString(String string, String fromHere, String toHere) {
        int frm = string.indexOf(fromHere);
        if (frm == -1) {
            return "";
        }
        int startIdx = frm + fromHere.length();
        int to = string.indexOf(toHere, startIdx);
        if (to == -1) {
            return "";
        }
        return string.substring(startIdx, to);
    }

    public static String rjust(String string, int length) {
        return padding(string, ' ', length, true);
    }

    public static String rjust(String string, int length, String padding) {
        return rightJustify(string, length, padding);
    }

    public static String rightJustify(String string, int length, String padding) {
        return padding(string, padding, length, false);
    }

    public static String ljust(String string, int length) {
        return padding(string, ' ', length, false);
    }

    public static String ljust(String string, int length, String padding) {
        return leftJustify(string, length, padding);
    }

    public static String leftJustify(String string, int length, String padding) {
        return padding(string, padding, length, true);
    }

    public static String toLower(String string) {
        if (empty(string)) {
            return "";
        }
        return string.toLowerCase(Locale.getDefault());
    }

    public static String toUpper(String string) {
        if (empty(string)) {
            return "";
        }
        return string.toUpperCase(Locale.getDefault());
    }

    /**
     *  uppercase alphabetic characters converted to lowercase and vs.
     * @param string, this value with case changed.
     * @return  case of value being changed.
     */
    public static String swapCase(String string) {
        if (empty(string)) {
            return "";
        }
        char[] chars = string.toCharArray();
        int idx = 0;
        for (char ch : chars) {
            if (Character.isLowerCase(ch)) {
                chars[idx] = Character.toUpperCase(ch);
            }
            if (Character.isUpperCase(ch)) {
                chars[idx] = Character.toLowerCase(ch);
            }
            idx++;
        }
        return new String(chars);
    }

    public static int skipChar(String string, char ch, int start) {
        int max = string.length();
        for (int i = start; i < max; i++) {
            if (string.charAt(i) != ch) {
                return i;
            }
        }
        return -1;
    }

    public static String padding(String string, String newVal, int newSize, boolean isLead) {
        if (empty(string)) {
            return "";
        }
        int padCnt = (newSize - string.length()) / newVal.length();
        int modCnt = (newSize - string.length()) % newVal.length();
        if (padCnt > 0) { // pad only when size is smaller than newSize
            StringBuilder sb = new StringBuilder();
            if (isLead) {
                for (int i = 0; i < padCnt; i++) {
                    sb.append(newVal);
                }
                if (modCnt > 1) {
                    sb.append(newVal);
                }
                sb.append(string);
                int extra = sb.length() - newSize;
                if (extra > 0) {
                    sb.delete(0, extra);
                }
                return sb.toString();
            } else {
                // trailing.
                sb.append(string);
                for (int i = 0; i < padCnt; i++) {
                    sb.append(newVal);
                }
                if (modCnt > 1) {
                    sb.append(newVal);
                }
                int extra = sb.length() - newSize;
                if (extra > 0) {
                    sb.delete(sb.length() - extra - 1, sb.length() - 1);
                }
                return sb.toString();
            }
        }
        return string; // no padding needed.
    }

    public static String padding(String string, char ch, int newSize, boolean isLead) {
        if (empty(string)) {
            return "";
        }
        int padCnt = newSize - string.length();
        if (padCnt > 0) { // pad only when size is smaller than newSize
            StringBuilder sb = new StringBuilder();
            if (isLead) {
                for (int i = 0; i < padCnt; i++) {
                    sb.append(ch);
                }
                sb.append(string);
                return sb.toString();
            } else {
                // trailing.
                sb.append(string);
                for (int i = 0; i < padCnt; i++) {
                    sb.append(ch);
                }
                return sb.toString();
            }
        }
        return string; // no padding needed.
    }

    public static String padZeroes(String string, int newSize, boolean isLead) {
        return padding(string, '0', newSize, isLead);
    }

    public static String replace(String string, String pattern, String newValue, boolean isCaseSensitive) {
        pattern = getIgnoreCasePatt(pattern, isCaseSensitive);
        Pattern patt = Pattern.compile(pattern);
        Matcher mat = patt.matcher(string);

        StringBuffer sb = new StringBuffer();
        while (mat.find()) {
            mat.appendReplacement(sb, newValue);
        }
        mat.appendTail(sb);
        return sb.toString();
    }

    public static List<String> splitPattern(String pattern, String string) {
        ArrayList<String> array = new ArrayList<String>();
        String[] strs = string.split(pattern);
        for (String str : strs) {
            array.add(str);
        }
        return array;
    }

    public static int compare(String string1, String string2, boolean isCaseSensitive) {
        if (string1 == string2) {
            return 0;
        }
        if (empty(string1)) {
            return -1;
        }
        if (isCaseSensitive) {
            return string1.compareTo(string2);
        } else {
            return string1.compareToIgnoreCase(string2);
        }
    }

    public static String reverse(String string) {
        StringBuilder sb = new StringBuilder(string);
        sb = sb.reverse();
        return sb.toString();
    }

    public static int countWords(String string) {
        Pattern patt = Pattern.compile("\\b\\w+\\b");
        Matcher mat = patt.matcher(string);
        int cnt = 0;
        while (mat.find()) {
            cnt++;
        }
        return cnt;
    }

    public static boolean containValue(String string, String value) {
        if (empty(string)) {
            return false;
        }
        if (empty(value)) {
            return false;
        }
        return string.contains(value);
    }

    public static String reverseWords(String string) {
        if (string == null || string.length() < 1) {
            return "";
        }
        // not exactly
        String[] words = string.split("[\\s\\n]");
        LinkedList<String> list = new LinkedList<String>();
        for (String word : words) {
            list.add(word);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i));
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static boolean isSpaceLine(String string) {
        if (empty(string)) {
            return true;
        }
        string = string.trim();
        return (string.length() < 1);
    }

    /**
     * short cut for squashSpace
     * @param string same as squashSpace
     */
    public static String sspc(String string) {
        return squashSpace(string);
    }

    /**
     * Remove multiple space characters [ \t\n\x0B\f\r] into only one space.
     * @param string value to operate on.
     * @return return only single space string value.
     */
    public static String squashSpace(String string) {
        if (empty(string)) {
            return "";
        }
        return string.replaceAll("\\s+", " ");
    }

    public static String ltrim(String string, String chars) {
        return leftTrim(string, chars);
    }

    public static String leftTrim(String string, String charlist) {
        if (empty(string)) {
            return "";
        }
        if (empty(charlist)) {
            return string; // no changes.
        }
        StringBuilder sb = new StringBuilder();
        sb.append("^[");
        sb.append(charlist);
        sb.append("]+");
        return string.replaceAll(sb.toString(), "");
    }

    public static String ltrim(String string) {
        return leftTrim(string);
    }

    public static String leftTrim(String string) {
        if (empty(string)) {
            return "";
        }
        return string.replaceAll("^\\s+", "");
    }

    private static String getIgnoreCasePatt(String pattern, boolean ignoreCase) {
        if (ignoreCase) {
            pattern = "(?i)" + pattern;
        }
        return pattern;
    }

    public static String remove(String string, int posBegin) {
        if (string.length() - 1 > posBegin) {
            return string.substring(0, posBegin - 1);
        } else {
            return string; // return the same string.
        }
    }

    public static String removeLast(String string, int charsCount) {
        int pos = string.length() - charsCount;
        if (pos > 1) {
            return string.substring(0, pos - 1);
        } else {
            return string; // return the same string.
        }
    }

    public static String removeFirst(String string, String pattern) {
        return removeFirst(string, pattern, false);
    }

    public static String removeFirst(String string, String pattern, boolean ignoreCase) {
        //pattern = "(" + pattern + ")";
        pattern = getIgnoreCasePatt(pattern, ignoreCase);
        Pattern patt = Pattern.compile(pattern);
        Matcher matc = patt.matcher(string); // get a matcher object
        StringBuffer sb = new StringBuffer();
        if (matc.find()) {
            matc.appendReplacement(sb, "");
        }
        matc.appendTail(sb);
        return sb.toString();
    }

    public static String removes(String string, String[] patterns) {
        if (empty(string)) {
            return "";
        }
        for (String aVal : patterns) {
            string = remove(string, aVal);
        }
        return string;
    }

    public static String remove(String string, String... patterns) {
        if (empty(string)) {
            return "";
        }
        for (String aVal : patterns) {
            string = remove(string, aVal);
        }
        return string;
    }

    public static String remove(String string, String pattern) {
        return remove(string, pattern, false);
    }

    public static String remove(String string, String pattern, boolean ignoreCase) {
        if (empty(string)) {
            return "";
        }
        //pattern = "(" + pattern + ")";
        pattern = getIgnoreCasePatt(pattern, ignoreCase);
        return string.replaceAll(pattern, "");
    }

    public static String replace(String string, String oldValPatt, String newVal, int count) {
        if (empty(string)) {
            return "";
        }
        if (empty(oldValPatt)) {
            return string;
        }
        Pattern patt = Pattern.compile(oldValPatt);
        Matcher mat = patt.matcher(string);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (mat.find()) {
            i++;
            //String fnd = mat.group();
            mat.appendReplacement(sb, newVal);
            if (i >= count) {
                break; //
            }
        }
        mat.appendTail(sb);
        return sb.toString();
    }

    public static String trimFirstXmlTag(String string, String tagName) {
        String tagPattern = tagName + "\b[^>]*>(.*?)</" + tagName + ">";
        String retVal = removeFirst(string, tagPattern);
        return retVal;
    }

    public static String trimAllXmlTag(String string, String tagName) {
        String tagPattern = tagName + "\b[^>]*>(.*?)</" + tagName + ">";
        String retVal = remove(string, tagPattern);
        return retVal;
    }

    public static String trimAllXmlTag(String string) {
        String tagPattern = "<([A-Z][A-Z0-9]*)\b[^>]*>(.*?)</\1>";
        String retVal = remove(string, tagPattern);
        return retVal;
    }

    public static String rtrim(String string, String chars) {
        return rightTrim(string, chars);
    }

    public static String rightTrim(String string, String charlist) {
        if (empty(string)) {
            return "";
        }
        if (empty(charlist)) {
            return string;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(charlist);
        sb.append("]+$");

        return string.replaceAll(sb.toString(), "");
    }

    public static String rtrim(String string) {
        return rightTrim(string);
    }

    public static String rightTrim(String string) {
        if (empty(string)) {
            return "";
        }
        return string.replaceAll("\\s+$", "");
    }

    public static String trim(String string) {
        string = ltrim(string);
        return rtrim(string);
    }

    public static String uppercaseFirstChar(String string) {
        if (empty(string)) {
            return "";
        }
        Pattern patt = Pattern.compile("\\b\\w+\\b");
        Matcher mat = patt.matcher(string);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        if (mat.find()) {
            String str0 = mat.group();
            char ch = string.toUpperCase(Locale.getDefault()).charAt(0);
            String newStr = ch + str0.substring(1);
            mat.appendReplacement(sb, newStr);
        }
        mat.appendTail(sb);
        return sb.toString();
    }

    public static String uppercaseWords(String string) {
        if (empty(string)) {
            return "";
        }
        Pattern patt = Pattern.compile("\\b\\w+\\b");
        Matcher mat = patt.matcher(string);
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (mat.find()) {
            String str0 = mat.group();
            char ch = str0.toUpperCase(Locale.getDefault()).charAt(0);
            String newStr = ch + str0.substring(1);
            mat.appendReplacement(sb, newStr);
        }
        mat.appendTail(sb);
        return sb.toString();
    }

    public static char[] copyToChars(char[] cbuf, int off, int len) {
        int start = Math.min(off, cbuf.length - 1);
        int end = Math.min(len, cbuf.length);
        char[] buff = new char[len];
        System.arraycopy(cbuf, start, buff, 0, len);
        return buff;
    }

    public static String copyToString(char[] cbuf, int off, int len) {
        int start = Math.min(off, cbuf.length - 1);
        int end = Math.min(len, cbuf.length);
        StringBuilder sb=new StringBuilder();
        for(int i=start; i<end; i++){
            char ch=cbuf[i];
            sb.append(ch);
        }
        return sb.toString();
    }


    private static final Pattern CAMEL_CASE_SPLITTER = Pattern.compile("([a-z][0-9]*)([A-Z])");

    public static String getRubyCasedName(String javaCasedName) {
        Matcher m = CAMEL_CASE_SPLITTER.matcher(javaCasedName);
        return m.replaceAll("$1_$2").toLowerCase();
    }

    private static void prnln(String msg) {
        System.out.println(msg);
    }

    public static void main(String[] args) throws Exception {
        String testStr = repeat("  this is    a test.  ", 3);
        prnln("testStr           :" + testStr);
        prnln("count             :" + countWords(testStr));
        prnln("subString         :" + subString(testStr, " is "));
        prnln("squashSpace       :" + squashSpace(testStr));
        prnln("reverseWords()    :" + reverseWords(testStr) + "<<");
        prnln("uppercaseFirstChar:" + uppercaseFirstChar(testStr));
        prnln("uppercaseWord     :" + uppercaseWords(testStr));

        testStr = "  this is a test.  ";
        prnln("testStr           :" + testStr + "<<" + testStr.length());
        String ret = ljust(testStr, 30, "#!@");
        prnln("ljust             :" + ret + "<<" + ret.length());
        ret = rjust(testStr, 30, "#!@");
        prnln("rjust             :" + ret + "<<" + ret.length());
        ret = ljust(testStr, 30);
        prnln("ljust             :" + ret + "<<" + ret.length());
        ret = rjust(testStr, 30);
        prnln("rjust             :" + ret + "<<" + ret.length());
        prnln("reverse()         :" + reverse(testStr) + "<<");
        prnln("reverseWords()    :" + reverseWords(testStr) + "<<");
    }
}