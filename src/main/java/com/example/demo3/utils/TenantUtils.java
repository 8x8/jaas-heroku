package com.example.demo3.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TenantUtils {
    private static final String KID_FORMAT = "(.+)(/)(.+)";
    private static final Pattern KID_PATTERN = Pattern.compile(KID_FORMAT);

    public static String extractTenant(String kid) {
        Matcher matcher = KID_PATTERN.matcher(kid);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new RuntimeException("Invalid kid format");
    }
}
