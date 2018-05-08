package com.golden.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 */
public class ListUtil {


 	public static boolean isEmpty(List list){
		if(list == null || list.size() == 0){
			return true;
		}
		return false;
	}


	public static void main(String[] args) {
	}
}
