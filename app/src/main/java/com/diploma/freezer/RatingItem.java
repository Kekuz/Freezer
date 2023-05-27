package com.diploma.freezer;

import androidx.annotation.NonNull;

public class RatingItem {
    private String count, sum, name;

    RatingItem(){}

    public RatingItem(String name, String count, String sum) {
        this.name = name;
        this.count = count;
        this.sum = sum;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " " + count + " "+ sum;
    }
}

