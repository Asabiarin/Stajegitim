package com.fraud.springprac.api.security;

public class SecurityConstants {
    public static final String JWT_SECRET = "Zh9pXf+5QbPiQlIbgVE7iCUFm0N1fELlanIbXl6fL0HjlHWo6VRyEr4ORB3wMO1tQT5JO8sja/9lONpRUxmp+w==";
//    public static final long JWT_INTERNAL_TOKEN_EXPIRATION = 600000; // 600000
    public static final long JWT_SLIDING_WINDOW_EXPIRATION = 180000; // YARIM SAAT 1800000
    public static final long JWT_ABSOLUTE_EXPIRATION = 86400000; //86400000 1 GÜN
}
