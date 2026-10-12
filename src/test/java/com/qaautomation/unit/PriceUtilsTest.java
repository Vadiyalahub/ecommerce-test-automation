package com.qaautomation.unit;

import com.qaautomation.utils.PriceUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceUtilsTest {

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "'$29.99', 29.99",
            "'Item total: $39.98', 39.98",
            "'Tax: $3.20', 3.20",
            "'Total: $43.18', 43.18",
            "'$7', 7.00"
    })
    void parsesPriceFromPageText(String text, String expected) {
        assertEquals(new BigDecimal(expected), PriceUtils.parsePrice(text));
    }

    @Test
    void throwsWhenTextHasNoPrice() {
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.parsePrice("Free shipping"));
    }

    @Test
    void throwsWhenTextIsNull() {
        assertThrows(IllegalArgumentException.class, () -> PriceUtils.parsePrice(null));
    }

    @Test
    void sumsPricesExactly() {
        List<BigDecimal> prices = List.of(new BigDecimal("29.99"), new BigDecimal("9.99"));
        assertEquals(new BigDecimal("39.98"), PriceUtils.sum(prices));
    }

    @Test
    void sumOfNoPricesIsZero() {
        assertEquals(0, BigDecimal.ZERO.compareTo(PriceUtils.sum(List.of())));
    }
}
