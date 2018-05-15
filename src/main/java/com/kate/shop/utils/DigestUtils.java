package com.kate.shop.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.stream.IntStream;

public final class DigestUtils {
    private DigestUtils() {}

    // TODO later when you will need to check passwords - make hash of the given password and com[are it with instance, stored in the database by String.equals method
    public static String sha1(String msg) {
        return digest(msg, "SHA-1");
    }

    private static String digest(String msg, String salt, String algorithm) {
        Objects.requireNonNull(salt);
        return digest(digest(msg, algorithm) + salt, algorithm);
    }

    private static String digest(String msg, String algorithm) {
        Objects.requireNonNull(msg, "msg");
        Objects.requireNonNull(algorithm, "algorithm");
        return byteArrayToHexString(digest(msg.getBytes(StandardCharsets.UTF_8), algorithm));
    }

    private static byte[] digest(byte[] msg, String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm).digest(msg);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String byteArrayToHexString(byte[] array) {
        StringBuilder result = new StringBuilder(array.length);
        IntStream.range(0, array.length)
                .forEach(i -> result.append(Integer.toString( (array[i] & 0xff) + 0x100, 16).substring(1)));
        return result.toString();
    }
}