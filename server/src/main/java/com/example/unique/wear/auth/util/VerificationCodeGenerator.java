package com.example.unique.wear.auth.util;

import java.util.Random;

public class VerificationCodeGenerator {
    public static String generateCode() {
        Random rand = new Random();
        int code = 10000 + rand.nextInt(900000);
        return String.valueOf(code);
    }
}
