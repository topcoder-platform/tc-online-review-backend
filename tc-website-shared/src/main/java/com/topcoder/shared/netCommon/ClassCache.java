package com.topcoder.shared.netCommon;

import java.util.HashMap;

public class ClassCache {
    private static HashMap classes = new HashMap();

    public ClassCache() {
    }

    public static Class findClass(String className) throws ClassNotFoundException {
        Class clazz = (Class) classes.get(className);
        if (clazz == null) {
            clazz = Class.forName(className);
            synchronized (ClassCache.class) {
                HashMap temp = (HashMap) classes.clone();
                temp.put(className, clazz);
                classes = temp;
            }
        }

        return clazz;
    }
}
