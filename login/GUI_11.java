package login;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class GUI_11 {
    private static final int ITERATIONS = 600000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    private GUI_11() {}

    public static String hash(char[] password) {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        byte[] derived = derive(password, salt);
        return Base64.getEncoder().encodeToString(salt) + ":"
                + Base64.getEncoder().encodeToString(derived);
    }

    public static boolean verify(char[] password, String stored) {
        if (stored == null) return false;
        int separator = stored.indexOf(':');
        if (separator <= 0 || separator != stored.lastIndexOf(':')) return false;
        try {
            byte[] salt = Base64.getDecoder().decode(stored.substring(0, separator));
            byte[] expected = Base64.getDecoder().decode(stored.substring(separator + 1));
            if (salt.length != SALT_LENGTH || expected.length != KEY_LENGTH / 8) return false;
            byte[] actual = derive(password, salt);
            return MessageDigest.isEqual(expected, actual);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    private static byte[] derive(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                    .generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException ex) {
            throw new IllegalStateException("PBKDF2 해시 생성 실패", ex);
        } finally {
            spec.clearPassword();
        }
    }
}
