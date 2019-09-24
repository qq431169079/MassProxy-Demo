package com.rainng.massproxy.util;

import lombok.Data;

@Data
public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
}