package com.example.apiestudo.utils;

public final class EmailUtils {

    private static final String MASK = "********";

    private EmailUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String maskEmail(String email) {
        int atIndex = email.indexOf("@");

        if (atIndex <= 0 || atIndex == (email.length() - 1)) {
            return email;
        }

        String name = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        if (name.length() <= 2) {
            return email;
        }

        return name.substring(0, 2) + MASK + domain;
    }
}
