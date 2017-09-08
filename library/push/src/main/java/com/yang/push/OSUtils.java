package com.yang.push;

import android.os.Build;

/**
 * Created by Shubo on 15/11/17.
 */
public class OSUtils {

    public static String M_OS;
    public static final String OS_XIAOMI = "Xiaomi";
    public static final String OS_HUAWEI = "HUAWEI";
    public static final String OS_MEIZU = "Meizu";

    static {
        M_OS = Build.MANUFACTURER;
    }

    public static String getOs() {
        return M_OS;
    }

    public static boolean isXiaomi() {
        return M_OS.equals(OS_XIAOMI);
    }

    public static boolean isMeizu() {
        return M_OS.equals(OS_MEIZU);
    }

    public static boolean isHuawei() {
        return M_OS.equals(OS_HUAWEI);
    }
}
