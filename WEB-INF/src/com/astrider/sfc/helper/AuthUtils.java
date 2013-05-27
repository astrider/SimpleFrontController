package com.astrider.sfc.helper;

import java.util.UUID;

public final class AuthUtils {
    private static int   saltLength      = 10;
    private static int   stretchCount    = 100;
    private static int[] insertPositions = {2, 14, 52, 63, 78, 81, 93, 101, 103, 120};

    private AuthUtils() {
    }

    public static String encrypt(String password) {
        return doStretching(password, generateSalt());
    }

    public static boolean verify(String password, String encrypted) {
        String salt = extractSalt(encrypted);
        return doStretching(password, salt).equals(encrypted);
    }

    public static String generateSalt() {
        return UUID.randomUUID().toString().substring(0, saltLength);
    }

    private static String doStretching(String password, String salt) {
        String stretched = password;
        for (int i = 0; i < stretchCount; i++) {
            stretched = doEncrypt(stretched, salt);
        }
        return embedSalt(stretched, salt);
    }

    private static String doEncrypt(String password, String salt) {
        return StringUtils.getHash(salt + password);
    }

    private static String embedSalt(String hash, String salt) {
        StringBuilder builder = new StringBuilder();
        int from = 0;
        int to = 0;
        for (int i = 0; i < saltLength; i++) {
            to = insertPositions[i];
            builder.append(hash.substring(from, to));
            builder.append(salt.charAt(i));
            from = to;
        }
        builder.append(hash.substring(from));
        return builder.toString();
    }

    private static String extractSalt(String hash) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < saltLength; i++) {
            builder.append(hash.charAt(insertPositions[i] + i));
        }
        return builder.toString();
    }
}