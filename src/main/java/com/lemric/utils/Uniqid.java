package com.lemric.utils;

import java.nio.ByteBuffer;
import java.security.SecureRandom;

public final class Uniqid {

    public static String uniqid(String prefix, boolean more_entropy) {
        long time = System.currentTimeMillis();
        String uniqid = "";
        if(!more_entropy) {
            uniqid = String.format("%s%08x%05x", prefix, time / 1000, time);
        } else {
            uniqid = Uniqid.uniqid(prefix);
        }

        return uniqid ;
    }
    public static String uniqid(String prefix) {
        long time = System.currentTimeMillis();
        String uniqid = "";
        String format = String.format("%s%08x%05x", prefix, time / 1000, time);
        SecureRandom sec = new SecureRandom();
        byte[] sbuf = sec.generateSeed(8);
        ByteBuffer bb = ByteBuffer.wrap(sbuf);
        uniqid = format;
        uniqid += "." + String.format("%.8s", ""+bb.getLong()*-1);

        return uniqid ;
    }
}