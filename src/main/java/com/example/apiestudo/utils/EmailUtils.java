package com.example.apiestudo.utils;

public class EmailUtils {

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;

        String[] parts = email.split("@");
        String name = parts[0];
        String domain = parts[1];
        int visible = Math.min(2, name.length());

        return name.substring(0, visible) + "*******@" + domain;
    }
}
