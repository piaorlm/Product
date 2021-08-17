package com.qima.product.constant;

import lombok.Getter;

@Getter
public enum ProductClassification {
    S(5), A(4), B(3), C(2), F(1);

    ProductClassification(int rank) {
        this.rank = rank;
    }

    private int rank;
}
