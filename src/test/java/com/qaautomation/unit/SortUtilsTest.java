package com.qaautomation.unit;

import com.qaautomation.utils.SortUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortUtilsTest {

    @Test
    void detectsAscendingOrder() {
        assertTrue(SortUtils.isSorted(List.of(1, 2, 3), true));
        assertFalse(SortUtils.isSorted(List.of(1, 3, 2), true));
    }

    @Test
    void detectsDescendingOrder() {
        assertTrue(SortUtils.isSorted(List.of("c", "b", "a"), false));
        assertFalse(SortUtils.isSorted(List.of("a", "c", "b"), false));
    }

    @Test
    void allowsEqualNeighbours() {
        assertTrue(SortUtils.isSorted(List.of(7.99, 15.99, 15.99, 29.99), true));
    }

    @Test
    void emptyAndSingleItemListsAreSorted() {
        assertTrue(SortUtils.isSorted(List.<Integer>of(), true));
        assertTrue(SortUtils.isSorted(List.of(42), false));
    }
}
