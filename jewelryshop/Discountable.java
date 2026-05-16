package com.jewelryshop;

public interface Discountable {
    double applyDiscount(double percentage); // existing method

    double getDiscount();
    void setDiscount(double percentage);
}
