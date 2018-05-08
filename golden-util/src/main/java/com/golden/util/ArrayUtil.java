package com.golden.util;

public class ArrayUtil {

    /**
     * @param vs
     * @return vs==null或者vs中木有任何东西,返回true;否则返回false
     * @description 判断数组是否为空
     */
    public static <V> boolean isEmpty(V[] vs) {

        if (null == vs || 0 == vs.length) {
            return true;
        }

        for (V v : vs) {
            if (null != v) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断数组是否为空，数组中是否为字符串null
     * @param vs
     * @return
     */
    public static <V> boolean isNullArr(V[] vs) {
        if (null == vs || 0 == vs.length) {
            return true;
        }
        for (V v : vs) {
            if ("null".equals(v)) {
                return true;
            }
            if ("".equals(v)) {
                return true;
            }
            if (null != v) {
                return false;
            }
        }
        return true;
    }


    public static Long[] str2LongArr(String[] strings){
        if (strings == null) return new Long[0];
        Long[] res = new Long[strings.length];
        for(int i = 0;i<res.length;i++) res[i] = Long.valueOf(strings[i]);
        return res;
    }


}
