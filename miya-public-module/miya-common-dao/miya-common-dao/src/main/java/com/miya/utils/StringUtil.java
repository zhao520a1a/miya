package com.miya.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 */
public class StringUtil {
	private static Logger log = LoggerFactory.getLogger(StringUtil.class);
	/**
	 * 判断字符是否为空串或为null
	 * @param str
	 * @return 返回true代表字符串不为空串且不为null，false代表字符串为空串或者为null
	 */
	public static boolean isNotEmpty(String str){
		if(str==null){
			return false;
		}
		if(str.trim().equals("")){
			return false;
		}
		return true;
	}
	
	/**
	 * 生成^B分隔符,linux环境能看见
	 * @return
	 */
	public static String getSliptCode(){
		String bin = "0010";
		String ret = "";
		int ib = Integer.parseInt(bin, 2);
		while (ib != 0) {
			ret = (char) (ib & 0xff) + ret;
			// 右移10位，8位+10，高位补0
			ib = ib >>> 10;
		}
		return ret;
	}
	
	/**
	 * 获取详细的异常信息
	 * @param t
	 * @return
	 */
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
	
	/**
	 * 字符串不为空返回当前值，为null或者空串返回defStr
	 * @param str
	 * @param defStr
	 * @return
	 */
	public static String isEmpty(String str, String defStr){
		if(isNotEmpty(str)){
			return str;
		}else{
			return defStr;
		}
	}
	
	/**
	 * 数字字符串自增和方法getNextNumber一样，
	 * 当传入满9字符串后，下一个自增序列的首位为大写字母A，剩余部分按照数字自增，
	 * 首位字符一直增至大写字母Z，其他部分一直自增至满9字符串，自增序列结束
	 * 比如000开始自增的话，最后一个序列是Z99，整个的序列数量就是1000+26*100=3600
	 * @param serialNO
	 * @return
	 */
	public static String getNextSerialNO(String serialNO){
		if(!isNotEmpty(serialNO)){
			return serialNO;
		}
		serialNO = serialNO.trim();
		String regex = "[a-zA-Z0-9]*";
		if(!Pattern.matches(regex, serialNO)){
			log.info("传入的serialNO只能包含数字和字母！参数："+serialNO+"不符合！");
			return serialNO;
		}
		int len = serialNO.length();
		if(Pattern.matches("Z9{"+(len-1)+"}", serialNO)){
			log.info("传入的serialNO["+serialNO+"]已经是满9字符串了!不能获得下一个同长度的自增序列！");
			throw new RuntimeException("传入的serialNO["+serialNO+"]已经是满9字符串了!不能获得下一个同长度的自增序列！");
		}
		
		//全是数字，但不是满9
		if(Pattern.matches("[0-9]*", serialNO)&&!Pattern.matches("9{"+len+"}", serialNO)){
			return getNextNumber(serialNO);
		}
		char ca0 = serialNO.charAt(0);
		if(ca0=='9'){
			return appendLate("0", "A", len-1);
		}else{
			String latenum = serialNO.substring(1);
			if(Pattern.matches("9{"+(len-1)+"}", latenum)){
				ca0 = (char)((int)ca0+1);
				return appendLate("0", String.valueOf(ca0), len-1);
			}else{
				return String.valueOf(ca0)+getNextNumber(latenum);
			}
		}
	}
	/**
	 * 获取字符串数字的下一个自增数字字符串，返回的字符串长度与传入的参数字符串长度一样，前面不够的补0
	 * 比如传入参数00001，返回字符串00002，如果传入99999，则没有下一个自增数字了，会抛出异常
	 * @param serialNO 传入的序列
	 * @return serialNO如果为null或者空串，则返回本身；如果除了数字，还包含其他字符类型，则返回本身
	 */
	public static String getNextNumber(String serialNO){
		if(!isNotEmpty(serialNO)){
			return serialNO;
		}
		serialNO = serialNO.trim();
		String regex = "[0-9]*";
		if(!Pattern.matches(regex, serialNO)){
			log.info("传入的serialNO只能包含数字！参数："+serialNO+"不符合！");
			return serialNO;
		}
		int len = serialNO.length();
		String regex2 = "9{"+len+"}";
		if(Pattern.matches(regex2, serialNO)){
			log.info("传入的serialNO["+serialNO+"]已经是满9字符串了!不能获得下一个同长度的自增序列！");
			throw new RuntimeException("传入的serialNO["+serialNO+"]已经是满9字符串了!不能获得下一个同长度的自增序列！");
		}
		int num = Integer.parseInt(serialNO);
		int nextnum = num+1;
		String nextno = String.valueOf(nextnum);
		int count = len - nextno.length();
		for(int i=0;i<count;i++){
			nextno = "0"+nextno;
		}
		return nextno;
	}
	/**
	 * 在某个字符串前面拼接指定数量的字符串
	 * @param preStr 拼接在前面的字符串 
	 * @param appendStr  要拼接的字符串
	 * @param appendNum  需要拼接的数量
	 * @return
	 */
	public static String appendPre(String preStr,String appendStr,int appendNum){
		for(int i=0;i<appendNum;i++){
			appendStr = preStr+appendStr;
		}
		return appendStr;
	}
	/**
	 * 在某个字符串后面拼接指定数量的字符串
	 * @param lateStr 拼接在后面的字符串 
	 * @param appendStr  要拼接的字符串
	 * @param appendNum  需要拼接的数量
	 * @return
	 */
	public static String appendLate(String lateStr,String appendStr,int appendNum){
		for(int i=0;i<appendNum;i++){
			appendStr = appendStr+lateStr;
		}
		return appendStr;
	}
	/**
	 * 替换特殊字符，比如';等
	 * @param loginfo sql语句用 
	 * @return
	 */
	public static String replaceSpecialChars(String loginfo) {
		return loginfo.replaceAll("'", "").replaceAll(";", ",");
	}
	/**
	 * 将数组转化为字符串
	 * @param strs
	 * @return
	 */
	public static String parseArray(Object[] strs){
		StringBuffer sb = new StringBuffer("[");
		for (Object str : strs) {
			sb.append(str+",");
		}
		sb.append("]");
		return sb.toString();
	}
	public static void main(String[] args) {
	}
}
