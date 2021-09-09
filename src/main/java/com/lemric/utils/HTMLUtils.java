package com.lemric.utils;

public class HTMLUtils
{
    public static String escapeHtmlAttribute(String attribute)
    {
        return attribute
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#x27;");
    }
}
