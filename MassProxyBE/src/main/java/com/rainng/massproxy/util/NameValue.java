package com.rainng.massproxy.util;

import lombok.Data;

@Data
public class NameValue<N, V> {
    private N name;
    private V value;

    public NameValue() {

    }

    public NameValue(N name, V value) {
        this.name = name;
        this.value = value;
    }
}
