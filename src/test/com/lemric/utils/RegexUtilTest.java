package com.lemric.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RegexUtilTest {
    @Test
    public void test() {

        /*ArrayList<ArrayList<ArrayList<Object>>> matches = new ArrayList<>();
        boolean condition = Pcre.preg_match_all("\\{(!)?(\\w+)\\}", "/{w}{x}{y}{z}.{_format}", matches, PREG_SET_ORDER );
        for (ArrayList<ArrayList<Object>> match : matches) {
            for (ArrayList<Object> objects : match) {
                System.out.println(objects);
            }
        }
        assertTrue(condition);

        StringUtils.strpbrk(null, ">?");*/

        HashMap<String, Object> array1 = new HashMap<>() {{
            put("1", "aaa");
            put("2", "bbb");
            put("3", "ccc");
        }};
        HashMap<String, Object> array2 = new HashMap<>() {{
            put("1", "aaa");
            put("5", "bbb");
            put("3", "fff");
        }};

        Map<String, Object> test = ArrayUtils.array_udiff_assoc(new Function<ArrayList, Integer>() {
            @Override
            public Integer apply(ArrayList arrayList) {
                return arrayList.get(0) == arrayList.get(1) ? 0 : 1;
            }
        }, array1, array2);

        System.out.println(test);
    }


}