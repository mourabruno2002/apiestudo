package com.example.apiestudo.utils;

public final class PhoneNumberUtils {

    private static final String MASK = "********";

    private PhoneNumberUtils() {
        throw new UnsupportedOperationException("Utility class.");
    }

    public static String maskPhoneNumber(String phoneNumber) {

        return MASK + phoneNumber.substring(phoneNumber.length() -2);
    }
}
