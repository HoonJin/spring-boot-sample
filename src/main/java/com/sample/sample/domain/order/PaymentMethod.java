package com.sample.sample.domain.order;

public enum PaymentMethod {
    CARD("card"),
    CASH("cash"),
    CHECK("check"),
    VOUCHER("voucher")
    ;

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
