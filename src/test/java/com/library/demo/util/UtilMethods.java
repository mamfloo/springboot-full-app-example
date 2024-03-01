package com.library.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilMethods {

    // Method to convert object to JSON string
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
