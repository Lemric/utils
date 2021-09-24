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


import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtils {
    private static String lastUrlStr = null;
    private static java.net.URL url = null;
    private static Pattern p = null;
    private static String lastKey = null;

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

        str = str.replace("\\\\", "\\");
        Charset charset;
        charset = StandardCharsets.UTF_8;
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
                if(ch>128){
                    byte[] ba = (ch+ "").getBytes(charset);
                    for (byte b : ba) {
                        sb.append('%');
                        char ch0 = Character.forDigit((b >> 4) & 0xF, 16);

                        if (Character.isLetter(ch0)) {
                            ch0 -= caseDiff;
                        }
                        sb.append(ch0);
                        ch0 = Character.forDigit(b & 0xF, 16);
                        if (Character.isLetter(ch0)) {
                            ch0 -= caseDiff;
                        }
                        sb.append(ch0);
                    }
                } else {
                    sb.append("%");
                    sb.append(toHexDigit(ch >> 4));
                    sb.append(toHexDigit(ch));
                }
            }
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
    static final int caseDiff = ('a' - 'A');

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
}
