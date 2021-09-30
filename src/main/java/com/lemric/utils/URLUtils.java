package com.lemric.utils;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtils {
    private static String lastUrlStr = null;
    private static java.net.URL url = null;
    private static Pattern p = null;
    private static String lastKey = null;
    private static String internalEncoding = "UTF-8";

    public static Map<String, Object> parse_str(String queryString) throws Exception {
        return URLUtils.parse_str(queryString, null);
    }
    public static Map<String, Object> parse_str(String queryString, String enc) throws Exception {
        int find1 = queryString.indexOf("?");
        if (find1 > -1) {
            queryString = queryString.substring(find1 + 1);
        }
        find1 = queryString.indexOf("#");
        if (find1 > -1) {
            queryString = queryString.substring(0, find1);
        }
        Map<String, Object> paramsMap = new LinkedHashMap<String, Object>();
        if (queryString != null && queryString.length() > 0) {
            int ampersandIndex, lastAmpersandIndex = 0;
            String subStr, param, value;
            String[] paramPair, values, newValues;
            do {
                ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
                if (ampersandIndex > 0) {
                    subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
                    lastAmpersandIndex = ampersandIndex;
                } else {
                    subStr = queryString.substring(lastAmpersandIndex);
                }
                paramPair = subStr.split("=");
                param = paramPair[0];
                value = paramPair.length == 1 ? "" : paramPair[1];
                try {
                    value = URLDecoder.decode(value, enc);
                } catch (Exception ignored) {
                }
                if (paramsMap.containsKey(param)) {
                    values = (String[]) paramsMap.get(param);
                    int len = values.length;
                    newValues = new String[len + 1];
                    System.arraycopy(values, 0, newValues, 0, len);
                    newValues[len] = value;
                } else {
                    newValues = new String[] { value };
                }
                paramsMap.put(param, newValues);
            } while (ampersandIndex > 0);
        }
        return paramsMap;
    }

    public static String rawUrlDecode(String s)
    {
        if (s == null)
            return "";

        int len = s.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);

            if (ch == '%' && i + 2 < len) {
                int d1 = s.charAt(i + 1);
                int d2 = s.charAt(i + 2);

                int v = 0;

                if ('0' <= d1 && d1 <= '9')
                    v = 16 * (d1 - '0');
                else if ('a' <= d1 && d1 <= 'f')
                    v = 16 * (d1 - 'a' + 10);
                else if ('A' <= d1 && d1 <= 'F')
                    v = 16 * (d1 - 'A' + 10);
                else {
                    sb.append('%');
                    continue;
                }

                if ('0' <= d2 && d2 <= '9')
                    v += (d2 - '0');
                else if ('a' <= d2 && d2 <= 'f')
                    v += (d2 - 'a' + 10);
                else if ('A' <= d2 && d2 <= 'F')
                    v += (d2 - 'A' + 10);
                else {
                    sb.append('%');
                    continue;
                }

                i += 2;
                sb.append((char) v);
            }
            else
                sb.append(ch);
        }

        return sb.toString();
    }
    public static String rawUrlEncode(String str) {
        if (str == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if ('a' <= ch && ch <= 'z'
                    || 'A' <= ch && ch <= 'Z'
                    || '0' <= ch && ch <= '9'
                    || ch == '-' || ch == '_' || ch == '.' || ch == '~') {
                sb.append(ch);
            }
            else {
                sb.append('%');
                sb.append(toHexDigit(ch >> 4));
                sb.append(toHexDigit(ch));
            }
        }

        return sb.toString();
    }

    /**
     * Gets the magic quotes value.
     */
    public static String urlencode(String str) {
        StringBuilder sb = new StringBuilder();
        urlencode(sb, str);

        return sb.toString();
    }

    /**
     * Gets the magic quotes value.
     */
    private static void urlencode(StringBuilder sb, String str) {
        int len = str.length();

        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if('\n' == ch) {
                sb.append("%0A");
            } else if ('a' <= ch && ch <= 'z') {
                sb.append(ch);
            } else if ('A' <= ch && ch <= 'Z') {
                sb.append(ch);
            } else if ('0' <= ch && ch <= '9') {
                sb.append(ch);
            } else if (ch == '-' || ch == '_' || ch == '.') {
                sb.append(ch);
            } else if (ch == ' ') {
                sb.append('+');
            } else {
                sb.append('%');
                sb.append(toHexDigit(ch / 16));
                sb.append(toHexDigit(ch));
            }
        }
    }

    /**
     * Returns the decoded string.
     */
    public static String urldecode(String s) {
        if (s == null)
            return "";

        int len = s.length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);

            if (ch == '%' && i + 2 < len) {
                int d1 = s.charAt(i + 1);
                int d2 = s.charAt(i + 2);

                int v = 0;

                if ('0' <= d1 && d1 <= '9')
                    v = 16 * (d1 - '0');
                else if ('a' <= d1 && d1 <= 'f')
                    v = 16 * (d1 - 'a' + 10);
                else if ('A' <= d1 && d1 <= 'F')
                    v = 16 * (d1 - 'A' + 10);
                else {
                    sb.append('%');
                    continue;
                }

                if ('0' <= d2 && d2 <= '9')
                    v += (d2 - '0');
                else if ('a' <= d2 && d2 <= 'f')
                    v += (d2 - 'a' + 10);
                else if ('A' <= d2 && d2 <= 'F')
                    v += (d2 - 'A' + 10);
                else {
                    sb.append('%');
                    continue;
                }

                i += 2;
                sb.append((char) v);
            }
            else if (ch == '+')
                sb.append(' ');
            else
                sb.append(ch);
        }

        return sb.toString();
    }

    private static char toHexDigit(int d) {
        d = d & 0xf;
        if (d < 10) {
            return (char) ('0' + d);
        } else {
            return (char) ('A' + d - 10);
        }
    }

    public static String evaluate(String urlStr, String partToExtract) {
        if (urlStr == null || partToExtract == null) {
            return null;
        }

        if (lastUrlStr == null || !urlStr.equals(lastUrlStr)) {
            try {
                url = new java.net.URL(urlStr);
            } catch (Exception e) {
                return null;
            }
        }
        lastUrlStr = urlStr;

        if (partToExtract.equals("HOST")) {
            return url.getHost();
        }
        if (partToExtract.equals("PATH")) {
            return url.getPath();
        }
        if (partToExtract.equals("QUERY")) {
            return url.getQuery();
        }
        if (partToExtract.equals("REF")) {
            return url.getRef();
        }
        if (partToExtract.equals("PROTOCOL")) {
            return url.getProtocol();
        }
        if (partToExtract.equals("FILE")) {
            return url.getFile();
        }
        if (partToExtract.equals("AUTHORITY")) {
            return url.getAuthority();
        }
        if (partToExtract.equals("USERINFO")) {
            return url.getUserInfo();
        }

        return null;
    }

    public static String evaluate(String urlStr, String partToExtract, String key) {
        if (!partToExtract.equals("QUERY")) {
            return null;
        }

        String query = URLUtils.evaluate(urlStr, partToExtract);
        if (query == null) {
            return null;
        }

        if (!key.equals(lastKey)) {
            p = Pattern.compile("(&|^)" + key + "=([^&]*)");
        }

        lastKey = key;
        Matcher m = p.matcher(query);
        if (m.find()) {
            return m.group(2);
        }
        return null;
    }

    public static String http_build_query(Map<String, Object> params, String numeric_prefix, String arg_separator) throws UnsupportedEncodingException {

        List<String> list_query = new ArrayList<>();
        String result = "";
        for(Map.Entry<String, Object> e : params.entrySet()){
            if(e.getKey().isEmpty() || e.getValue() == null) {
                continue;
            }
            String paramsString = e.getValue().toString();
            if(e.getValue() instanceof String[]) {
                paramsString = String.join(",", (String[]) e.getValue());
            }
            list_query.add(URLEncoder.encode(e.getKey(), internalEncoding) + "=" +URLEncoder.encode(paramsString, internalEncoding));
        }
        String[] array_query = new String[list_query.size()];
        list_query.toArray( array_query );
        result=  implodeArray(array_query,arg_separator);
        return result;
    }

    public static String implodeArray(String[] inputArray, String glueString) {

        /** Output variable */
        String output = "";

        if (inputArray.length > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(inputArray[0]);

            for (int i=1; i<inputArray.length; i++) {
                sb.append(glueString);
                sb.append(inputArray[i]);
            }

            output = sb.toString();
        }

        return output;
    }
}
