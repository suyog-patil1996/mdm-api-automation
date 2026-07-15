package com.oorjaa.mdm.utils;

import java.util.Random;

public final class TestDataGenerator {

    private static final Random RANDOM = new Random();

    private TestDataGenerator() {
        // Utility class
    }

    /**
     * Generates a unique vendor/company name.
     */
    public static String generateVendorName() {
        return "AUTO_VENDOR_" + System.currentTimeMillis();
    }

    /**
     * Generates a unique owner name.
     */
    public static String generateOwnerName() {
        return "Automation User " + (1000 + RANDOM.nextInt(9000));
    }

    /**
     * Generates a valid Indian mobile number.
     */
    public static String generatePhoneNumber() {
        return "9" + String.format("%09d", RANDOM.nextInt(1_000_000_000));
    }

    /**
     * Generates a unique email address.
     */
    public static String generateEmail() {
        return "auto"
                + System.currentTimeMillis()
                + "@test.com";
    }

    /**
     * Generates a valid PAN number.
     * Example: ABCDE1234F
     */
    public static String generatePanNumber() {

        StringBuilder pan = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            pan.append((char) ('A' + RANDOM.nextInt(26)));
        }

        pan.append(1000 + RANDOM.nextInt(9000));

        pan.append((char) ('A' + RANDOM.nextInt(26)));

        return pan.toString();
    }

    /**
     * Generates a random 12-digit Aadhaar number.
     */
    public static String generateAadharNumber() {

        StringBuilder aadhaar = new StringBuilder();

        aadhaar.append(2 + RANDOM.nextInt(8));

        for (int i = 1; i < 12; i++) {
            aadhaar.append(RANDOM.nextInt(10));
        }

        return aadhaar.toString();
    }

    /**
     * Generates a unique UPI-linked phone number.
     */
    public static String generateUpiPhoneNumber() {
        return generatePhoneNumber();
    }
}