package com.golden.util;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.ListUtils;

import java.util.*;

/**
 * @description 集合、数组操作工具类
 */
public class Collects {


    /**
     * @param collection
     * @return collection==null或者collection中木有任何东西,返回true;否则返回false
     * @description 判断集合是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        if (null == collection || 0 == collection.size()) {
            return true;
        }
        return false;
    }


    public static Integer sum(Collection<Integer> source){
        Integer sum=0;
        for(Integer i:source){
            sum+=i;
        }
        return sum;
    }

	/**
     * 获取集合内元素自定义属性以“，”连接的字符串，若集合为null或空，则返回空字符串
     */
    public static <T> String getJoinString(Iterable<T> iterable,Function<T,String> function){
        StringBuilder ids = new StringBuilder();
        if(iterable == null||!iterable.iterator().hasNext()) return ids.toString();
        for(T per:iterable){
            ids.append(",").append(function.apply(per));
        }
        return new String(ids.substring(1));
    }


    /**
     * @param vs 为null创建没有元素的List
     * @return
     * @description 创建包含输入参数的List
     */
    public static <V> List<V> newList(V... vs) {
        List<V> result = new ArrayList<V>();
        if (null == vs) {
            return result;
        }
        for (V v : vs) {
            result.add(v);
        }
        return result;
    }

    public static <V> List<V> newList(Collection<V> c) {
        List<V> result = new ArrayList<V>();
        if (null == c || 0 == c.size()) {
            return result;
        }
        result.addAll(c);
        return result;
    }


    /**
     * @return
     * @description 创建木有元素的Map
     */
    public static <K, V> Map<K, V> newMap() {
        return new HashMap<K, V>();
    }

    /**
     * @param pairs 为null创建不包含任何键值对的Map
     * @return
     * @description 创建包含指定键值对的Map
     */
    public static <K, V> Map<K, V> newMap(Pair<K, V>... pairs) {
        Map<K, V> result = new HashMap<K, V>();
        if (null == pairs) {
            return result;
        }
        for (Pair<K, V> pair : pairs) {
            result.put(pair.getLeft(), pair.getRight());
        }
        return result;
    }

    /**
     * @param vs 为null创建不包含任何元素的Set
     * @return
     * @description 创建包含指定元素的Set
     */
    public static <V> Set<V> newSet(V... vs) {
        HashSet<V> result = new HashSet<V>();
        if (null == vs) {
            return result;
        }
        for (V v : vs) {
            result.add(v);
        }
        return result;
    }





    /**
     * @param <Left>
     * @param <Right>
     * @description
     */
    public static class Pair<Left, Right> {
        private Left left;
        private Right right;

        private Pair(Left left, Right right) {
            super();
            this.left = left;
            this.right = right;
        }

        public static <Left, Right> Pair<Left, Right> newPair(Left left, Right right) {
            return new Pair<Left, Right>(left, right);
        }

        public Left getLeft() {
            return left;
        }

        public Pair<Left, Right> setLeft(Left left) {
            this.left = left;
            return this;
        }

        public Right getRight() {
            return right;
        }

        public Pair<Left, Right> setRight(Right right) {
            this.right = right;
            return this;
        }

    }
    
//    public static <K,V> Map<Integer, List<V>> splitList(List<V> list){
//    	Map<Integer, List<V>> map =new HashMap<Integer, List<V>>();  //用map存起来新的分组后数据
//		List<V> surplusSize = new ArrayList<V>();
//		int splitSize = Constants.SPLIT_SIZE;
//		int listSize = list.size();
//		int size = listSize / splitSize * splitSize;
//		long surplus = listSize % splitSize;
//		long groupSize = listSize / splitSize;
//		int k = 0;
//		for(int i = 0;i<size;i+=splitSize){
//			List<V>  newlist = list.subList(i,i+splitSize);
//		    map.put(k, newlist);
//		    k++;
//		}
//		for (int i = 0; i < surplus; i++) {
//			surplusSize.add(list.get((int)(groupSize * splitSize + i)));
//		}
//		map.put(k+1, surplusSize);
//    	return map;
//    }
    

    

}
