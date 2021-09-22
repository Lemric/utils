package com.lemric.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RegexUtilTest {


    @Test
    public void test() {

        assertTrue(Pcre.preg_match("^/hello/(?<name>[^/]++)$", "/hello/o"));
    }


}