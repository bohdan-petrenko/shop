package com.kate.shop.utils;

import java.util.List;

public class DaoUtils {

    public static <T> T one(List<T> list) {
        return list == null || list.isEmpty() ? null : list.get(0);
    }
}
