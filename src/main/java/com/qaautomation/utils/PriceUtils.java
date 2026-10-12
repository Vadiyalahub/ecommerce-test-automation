package com.qaautomation.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Turns price text from the page (e.g. "Item total: $39.98") into numbers.
 * Uses BigDecimal because double can't store money exactly (0.1 + 0.2 != 0.3).
 */
public final class PriceUtils {

    private static final Pattern PRICE_PATTERN = Pattern.compile("\\$(\\d+(?:\\.\\d{1,2})?)");

    private PriceUtils() {
    }

    public static BigDecimal parsePrice(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Price text is null");
        }
        Matcher matcher = PRICE_PATTERN.matcher(text);
        if (!matcher.find()) {
            throw new IllegalArgumentException("No price found in: '" + text + "'");
        }
        return new BigDecimal(matcher.group(1)).setScale(2, RoundingMode.UNNECESSARY);
    }

    public static BigDecimal sum(List<BigDecimal> prices) {
        return prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
