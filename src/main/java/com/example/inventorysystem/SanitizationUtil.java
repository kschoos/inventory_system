package com.example.inventorysystem;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class SanitizationUtil {

    // Allow only basic formatting and links
    private static final PolicyFactory SANITIZER = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    public static String sanitize(String input) {
        if (input == null) {
            throw new NullPointerException("Input for sanitization was null. This should never happen.");
        }
        String sanitizationResult = SANITIZER.sanitize(input);

        if (sanitizationResult.isEmpty()) {
            throw new RuntimeException("Sanitization result was empty.");
        }

        return sanitizationResult;
    }
}