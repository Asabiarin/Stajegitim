package com.fraud.springprac.api.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public class KeyGenerator {
    public static void main(String[] args) {
        // Generate a random 512-bit (64-byte) key for HS512
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String base64EncodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated Base64 Encoded HS512 Key: " + base64EncodedKey);
    }
}