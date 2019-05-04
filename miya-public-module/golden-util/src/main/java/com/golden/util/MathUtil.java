package com.golden.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**
 * 数学计算工具类
 * @author liujiangping
 *
 */
public class MathUtil {
	private static Logger log = LoggerFactory.getLogger(MathUtil.class);
	/**
	 * 四舍五入
	 * @param number 需要四舍五入的数字
	 * @param len  保留的小数位个数
	 * @return
	 */
	public static String round(double number,int len){
		String fmt = "0";
		for (int i = 0; i < len; i++) {
			if(i==0){
				fmt+=".#";
			}else{
				fmt+="#";
			}
		}
		DecimalFormat df = new DecimalFormat(fmt);
		return df.format(number);
	}
	/**
	 * 四舍五入并格式化金额，以“,”隔开
	 * @param number 需要格式化的数字
	 * @return
	 */
	public static String roundFMT(double number,int len){
		String fmt = "##,###";
		for (int i = 0; i < len; i++) {
			if(i==0){
				fmt+=".#";
			}else{
				fmt+="#";
			}
		}
		DecimalFormat df = new DecimalFormat(fmt);
		String format = df.format(number);
		return (format.startsWith("."))?("0"+format):format;
	}
	/**
	 * 四舍五入并格式化金额，以“,”隔开
	 * @param number 需要格式化的数字
	 * @param len 小数位
	 * @return
	 */
	public static String roundFMT(String number,int len){
		String result = number;
		try{
			result = roundFMT(Double.valueOf(number), len);
		}catch(Exception e){
			
		}
		return result;
	}
	/**
	 * 四舍五入并格式化金额，以“,”隔开
	 * @param number 需要格式化的数字
	 * @param len 保留的小数位数，不足的用0填充
	 * @return
	 */
	public static String roundFMTZero(double number,int len){
		String fmt = "##,###";
		for (int i = 0; i < len; i++) {
			if(i==0){
				fmt+=".0";
			}else{
				fmt+="0";
			}
		}
		DecimalFormat df = new DecimalFormat(fmt);
		String format = df.format(number);
		return (format.startsWith("."))?("0"+format):format;
	};
	/**
	 * 格式化金额，以“,”隔开,最多保留10位小数
	 * @param number 需要格式化的数字
	 * @return
	 */
	public static String roundFMT(double number){
		String fmt = "##,###.##########";
		DecimalFormat df = new DecimalFormat(fmt);
		String format = df.format(number);
		return (format.startsWith("."))?("0"+format):format;
	};
	/**
	 * 格式化金额，以“,”隔开,最多保留10位小数
	 * @param number 需要格式化的数字
	 * @return
	 */
	public static String roundFMT(String number){
		try {
			double num = Double.parseDouble(number);
			String fmt = "##,###.##########";
			DecimalFormat df = new DecimalFormat(fmt);
			String format = df.format(num);
			return (format.startsWith("."))?("0"+format):format;
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		}
		return number;
	};
	/**
	 * 格式化数字，以“,”隔开,小数位保持不变
	 * @param number
	 * @return
	 */
	public static String roundFMT2(String number){
		String num1 = number.split("\\.")[0];
		String num2 = "";
		if(number.indexOf(".") != -1){
			num2 = "." + number.split("\\.")[1];
		}
		return (roundFMT(num1)+num2);
	};
	
	/**
	 * 除法 
	 * @param dividend 除数  分子
	 * @param divisor 被除数 分母 
	 * @return 异常会返回0
	 */
	public static double divide(String dividend,String divisor){
		try {
			if(divisor==null||Double.parseDouble(divisor.trim())==0){
				return 0;
			}
			BigDecimal dividendBD = new BigDecimal((!StringUtil.isEmpty(dividend))?dividend.trim():"0");
			BigDecimal divisorBD = new BigDecimal((!StringUtil.isEmpty(divisor))?divisor.trim():"1");
			if(divisorBD.doubleValue()==0){
				divisorBD = new BigDecimal(1);
			}
			double divide = dividendBD.divide(divisorBD,BigDecimal.ROUND_HALF_UP).doubleValue();
			return divide;
		} catch (Exception e) {
			log.error(StringUtil.getTrace(e));
		}
		return 0;
	}
	/**
	 * 除法 
	 * @param dividend 除数  分子
	 * @param divisor 被除数 分母 
	 * @return 异常会返回0
	 */
	public static double divide(double dividend,double divisor){
		try {
			if(divisor==0){
				return 0;
			}
			BigDecimal dividendBD = new BigDecimal(dividend);
			BigDecimal divisorBD = new BigDecimal(divisor);
			if(divisorBD.doubleValue()==0){
				divisorBD = new BigDecimal(1);
			}
			double divide = dividendBD.divide(divisorBD,BigDecimal.ROUND_HALF_UP).doubleValue();
			return divide;
		} catch (Exception e) {
			log.error(StringUtil.getTrace(e));
		}
		return 0;
	}
	/**
	 * 除法 
	 * @param dividend 除数  分子
	 * @param divisor 被除数 分母 
	 * @param decimal  保留小数位
	 * @return 异常会返回0
	 */
	public static double divide(String dividend,String divisor,int decimal){
		try {
			if(decimal<0){
				decimal = 0;
			}
			if(divisor==null||Double.parseDouble(divisor.trim())==0){
				return 0;
			}
			BigDecimal dividendBD = new BigDecimal((!StringUtil.isEmpty(dividend))?dividend.trim():"0");
			BigDecimal divisorBD = new BigDecimal((!StringUtil.isEmpty(divisor))?divisor.trim():"1");
			if(divisorBD.doubleValue()==0){
				divisorBD = new BigDecimal(1);
			}
			double divide = dividendBD.divide(divisorBD, decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
			return divide;
		} catch (Exception e) {
			log.error(StringUtil.getTrace(e));
		}
		return 0;
	}
	/**
	 * 乘法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double multiply(double d1,double d2){
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
        BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.multiply(b2).doubleValue();
	}
	/**
	 * 四舍五入，保留小数位
	 * @param d1
	 * @param d2
	 * @param decimal 保留小数位数
	 * @return
	 */
	public static double multiply(double d1,double d2,int decimal){
		BigDecimal b1 = new BigDecimal(String.valueOf(d1));
        BigDecimal b2 = new BigDecimal(String.valueOf(d2));
        return b1.multiply(b2).setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 加法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static String add(String d1, String d2) {
		BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
		return  b1.add(b2).toString();
	}
	/**
	 * 减法   d1-d2
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static String subtract(String d1, String d2) {
		BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
		return  b1.subtract(b2).toString();
	}
	
	public static void main(String[] args) {
	}
}
