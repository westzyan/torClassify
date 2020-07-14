package com.tor.utils;

public class RandomColorUtils {
    public String generateColor() {
        StringBuilder str = new StringBuilder();
        str.append("#");
        for (int i = 0; i < 6; i++) {
            int tmp = (int) ((16) * Math.random());
            str.append(Integer.toHexString(tmp));
        }
        return str.toString();
    }
}
