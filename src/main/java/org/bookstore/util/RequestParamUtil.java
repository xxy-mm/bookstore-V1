package org.bookstore.util;

import org.bookstore.exception.InvalidRequestParameterException;

public final class RequestParamUtil {

    private RequestParamUtil() {

    }

    /**
     * Trims a string and returns null if it is null or empty after trimming.
     *
     * @param value the string
     * @return {@link String} the trimmed string, or null if the input is null or blank
     */
    public static String trimToNull(String value) {

        if (value == null) {
            return null;
        }

        value = value.trim();

        return value.isEmpty() ? null : value;
    }

    public static Integer getIntValue(String value) {

        value = trimToNull(value);
        if (value == null) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidRequestParameterException("Failed to parse " + value + " to Integer", e);
        }
    }

    public static Long getLongValue(String value) {

        value = trimToNull(value);
        if (value == null) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new InvalidRequestParameterException("Failed to parse " + value + " to Long", e);
        }
    }

}
