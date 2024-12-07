package org.app.restaurant.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESPasswordEncoder implements PasswordEncoder {

    private static final String SECRET_KEY = "sameer";  // Secret key for XOR (must match on both ends)

    // Encode the password using XOR encryption
    @Override
    public String encode(CharSequence rawPassword) {
        return xorEncrypt(rawPassword.toString(), SECRET_KEY);
    }

    // Compare the password by decrypting the stored password and matching with the input
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Decrypt the encoded password using XOR and compare it with the raw password
        String decryptedPassword = xorEncrypt(encodedPassword, SECRET_KEY);
        return rawPassword.toString().equals(decryptedPassword);
    }

    // XOR Encrypt or Decrypt password (XOR is symmetric, so the same function is used for both)
    private String xorEncrypt(String input, String key) {
        StringBuilder result = new StringBuilder();
        int keyIndex = 0;

        // XOR each character of the input with the key
        for (int i = 0; i < input.length(); i++) {
            char encryptedChar = (char) (input.charAt(i) ^ key.charAt(keyIndex));
            result.append(encryptedChar);

            // Cycle through the key
            keyIndex = (keyIndex + 1) % key.length();
        }

        return result.toString();
    }
}