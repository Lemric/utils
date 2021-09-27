package com.lemric.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.lemric.utils.Pcre.PREG_OFFSET_CAPTURE;
import static com.lemric.utils.Pcre.PREG_SET_ORDER;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegexUtilTest {
    @Test
    public void test() {

        ArrayList<ArrayList<ArrayList<Object>>> matches = new ArrayList<>();
        boolean condition = Pcre.preg_match_all("\\{(!)?(\\w+)\\}", "/{w}{x}{y}{z}.{_format}", matches, PREG_SET_ORDER );
        for (ArrayList<ArrayList<Object>> match : matches) {
            for (ArrayList<Object> objects : match) {
                System.out.println(objects);
            }
        }
        assertTrue(condition);
    }


}