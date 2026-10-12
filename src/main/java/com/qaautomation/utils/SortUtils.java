package com.qaautomation.utils;

import java.util.List;

public final class SortUtils {

    private SortUtils() {
    }

    /**
     * Checks that a list is in order. Equal neighbours are allowed
     * (two products can have the same price).
     */
    public static <T extends Comparable<? super T>> boolean isSorted(List<T> values, boolean ascending) {
        for (int i = 1; i < values.size(); i++) {
            int comparison = values.get(i - 1).compareTo(values.get(i));
            if (ascending ? comparison > 0 : comparison < 0) {
                return false;
            }
        }
        return true;
    }
}
