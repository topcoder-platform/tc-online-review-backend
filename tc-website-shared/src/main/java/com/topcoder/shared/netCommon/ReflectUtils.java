package com.topcoder.shared.netCommon;

public final class ReflectUtils {
    private ReflectUtils() {
    }

    public static Object newInstance(String name) {
        Class cl;
        try {
            cl = Class.forName(name);
        } catch (LinkageError var7) {
            var7.printStackTrace();
            return null;
        } catch (ClassNotFoundException var8) {
            var8.printStackTrace();
            return null;
        }

        try {
            return cl.newInstance();
        } catch (IllegalAccessException var3) {
            var3.printStackTrace();
            return null;
        } catch (InstantiationException var4) {
            var4.printStackTrace();
            return null;
        } catch (LinkageError var5) {
            var5.printStackTrace();
            return null;
        } catch (SecurityException var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static Object newInstance(Class cl) {
        try {
            return cl.newInstance();
        } catch (IllegalAccessException var2) {
            var2.printStackTrace();
            return null;
        } catch (InstantiationException var3) {
            var3.printStackTrace();
            return null;
        } catch (LinkageError var4) {
            var4.printStackTrace();
            return null;
        } catch (SecurityException var5) {
            var5.printStackTrace();
            return null;
        }
    }
}
