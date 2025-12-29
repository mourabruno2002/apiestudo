package com.example.apiestudo.utils;

public class PhoneNumberUtils {

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 8) return phone;

        return "********" + phone.substring(phone.length() -2);
    }
}
