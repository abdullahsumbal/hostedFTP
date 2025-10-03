package com.hostedftp.utils.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordUtil {
    private static final int ITERATIONS = 120000;
    private static final int KEY_LENGTH = 256; // bits
    private static final SecureRandom RAND = new SecureRandom();

    public static byte[] newSalt() {
        byte[] salt = new byte[16];
        RAND.nextBytes(salt);
        return salt;
    }

    public static byte[] hash(char[] password, byte[] salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        try {
            return skf.generateSecret(spec).getEncoded();
        } finally {
            Arrays.fill(password, '\0');
            spec.clearPassword();
        }
    }

    public static boolean verify(char[] attempted, byte[] salt, byte[] expectedHash) throws Exception {
        byte[] computed = hash(attempted, salt);
        boolean ok = constantTimeEquals(computed, expectedHash);
        Arrays.fill(attempted, '\0');
        Arrays.fill(computed, (byte)0);
        return ok;
    }

    private static boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) return false;
        int res = 0;
        for (int i = 0; i < a.length; i++) res |= a[i] ^ b[i];
        return res == 0;
    }
}
