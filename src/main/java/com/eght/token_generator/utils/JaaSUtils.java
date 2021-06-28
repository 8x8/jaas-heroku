package com.eght.token_generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JaaSUtils {
    private static final String KID_FORMAT = "(.+)(/)(.+)";
    private static final Pattern KID_PATTERN = Pattern.compile(KID_FORMAT);

    public static String extractAppId(String kid) {
        Matcher matcher = KID_PATTERN.matcher(kid);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new RuntimeException("Invalid kid format");
    }
}
