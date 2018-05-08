package com.golden.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.*;

public class Util {

    public static Iterable<String> str2Iterable(String Lists) {
        return Splitter.on(',').trimResults().omitEmptyStrings().split(Lists);
    }

    /**
     * 字符型转化为long型
     */
    public static long string2Long(String str) {
        if (!StringUtil.isEmpty(str)) {
            return Long.parseLong(str);
        } else {
            return 0L;
        }
    }

    public static Set<Long> stringArr2LongSet(String [] arr){
        Set<Long> set = new HashSet<Long>();
        for(String per : arr){
            if(!StringUtil.isEmpty(per)){
                set.add(Long.parseLong(per));
            }
        }
        return set;
    }

    public static Set<String> str2set(String str) {
        Set<String> set = new HashSet<String>();
        Iterable<String> iter = Splitter.on(',').trimResults().omitEmptyStrings().split(str==null?"":str);
        for (String per : iter) {
            set.add(per);
        }
        return set;
    }
    public static Set<String> stringToSet(List<Long> longs){
        Set<String> result = new HashSet<String>();
        for (Long l : longs){
            result.add(l.toString());
        }
        return result;
    }
    public static List<String> str2list(String str){
        return str2list(str,',');
    }

    public static List<String> str2list(String str, char split){
        List<String> list = new ArrayList<String>();
        Iterable<String> iter = Splitter.on(split).trimResults().omitEmptyStrings().split(str==null?"":str);
        for (String per : iter){
            list.add(per);
        }
        return list;
    }
    public static String long2str(List<Long> list){
        if(!ListUtil.isEmpty(list)){
            return Joiner.on(",").join(list);
        }else{
            return "";
        }
    }
    public static String longList2str(List<Long> list){
        return Joiner.on(',').join(list);
    }




    public static List<Long> str2LongList(String ids) {
        if(!StringUtil.isEmpty(ids)) {
            List<Long> longList = new ArrayList<Long>();
            Iterable<String> iter = Splitter.on(',').trimResults().omitEmptyStrings().split(ids);
            for(String per : iter){
                longList.add(Long.parseLong(per));
            }
            return longList;
        }
        return null;
    }

    public static List<Long> str2LongList(String[] ids) {
        return Lists.newArrayList(ArrayUtil.str2LongArr(ids));
    }


    public static String getJoinFromArray(String arr []){
        if(arr == null || arr.length == 0){
            return "";
        }
        return Joiner.on(',').skipNulls().join(arr);
    }


}
