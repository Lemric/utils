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


import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtils {
    private static String lastUrlStr = null;
    private static java.net.URL url = null;
    private static Pattern p = null;
    private static String lastKey = null;

    public static String rawUrlDecode(String url) {
        return java.net.URLDecoder.decode(url, StandardCharsets.UTF_8);
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
}
