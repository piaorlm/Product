package com.qima.product.constant;

import java.util.Comparator;
public enum SortDirection {
    ASC {
        @Override
        public <T> Comparator<T> convert(Comparator<T> comparator) {
            return comparator;
        };
    },
    DESC {
        @Override
        public <T> Comparator<T> convert(Comparator<T> comparator) {
            return comparator.reversed();
        }
    };

    abstract public <T> Comparator<T> convert(Comparator<T> comparator);
}
