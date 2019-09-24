package com.rainng.massproxy.manager;

public class BaseManager {
    protected boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str);
    }
}
