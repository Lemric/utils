package com.lemric.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RegexUtilTest {

    @Test
    public void test() {
        assertTrue(Pcre.preg_match("^/foo/?/?$", "/foo") > 0);
    }
}