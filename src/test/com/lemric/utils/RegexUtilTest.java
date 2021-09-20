package com.lemric.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegexUtilTest {

    @Test
    public void test() {
            Pcre.preg_match("{^/foo/?$}sD", "/foo");
    }
}