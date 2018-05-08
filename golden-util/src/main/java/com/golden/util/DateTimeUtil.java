package com.golden.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 日期工具类
 *
 */
public class DateTimeUtil {
	private static Logger log = LoggerFactory.getLogger(DateTimeUtil.class);


	/**
	 * 判断是否为日期
	 *
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static boolean isDate(String strDate, String pattern) {
		try {
			new SimpleDateFormat(pattern).parse(strDate);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	/**
	 * 获得当天日期
	 * @param pattern
	 * @return
	 */
	public static String getCurrentday(String pattern) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));  //jdk1.8版
		//Calendar   cal   =   Calendar.getInstance();
		//String currentday = new SimpleDateFormat( pattern).format(cal.getTime());
		//return currentday;
	}


	public static int getBetweenDays(long obj1, long obj2) {
		int days = 0;
		days = (int) (obj1 - obj2) / 1000 / 3600 / 24;
		return days;
	}


	/**
	 * 获得昨天日期
	 * @param pattern
	 * @return
	 */
	public static String getYesterday(String pattern){
		return  LocalDateTime.now().plusDays(-1).format(DateTimeFormatter.ofPattern(pattern)).toString();
		//Calendar   cal   =   Calendar.getInstance();
		//cal.add(Calendar.DATE,   -1);
		//String yesterday = new SimpleDateFormat( pattern).format(cal.getTime());
		//return yesterday;
	}

	/**
	 * 获得上一月日期
	 * @param pattern
	 * @return
	 */
	public static String getLastMonth(String pattern){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.MONTH,   -1);
		String lastMonth = new SimpleDateFormat( pattern).format(cal.getTime());
		return lastMonth;
	}
	/**
	 * 获得N个月之后的日期
	 * @param pattern
	 * @param add 正数时可获得add个月之后的日期，负数时获得add个月之前的日期
	 * @return
	 */
	public static String getAddMonth(String pattern, int add){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.MONTH,   add);
		String lastMonth = new SimpleDateFormat( pattern).format(cal.getTime());
		return lastMonth;
	}
	/**
	 * 获得指定月份N月之后或之前的月份
	 * @param month
	 * @param pattern
	 * @param add
	 * @return
	 */
	public static String getAddMonth(String month,String pattern, int add){
		String result = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
			Date date = sdf.parse(month);

			Calendar   cal   =   Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MONTH,   add);
			result = new SimpleDateFormat( pattern).format(cal.getTime());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return result;
	}
	/**
	 * 获得N天之后或之前的日期,今天日期不算在内
	 * @param pattern
	 * @param add 正数时可获得add天之后的日期，负数时获得add天之前的日期
	 * @return
	 */
	public static String getAddDate(String pattern, int add){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   add);
		String date = new SimpleDateFormat( pattern).format(cal.getTime());
		return date;
	}
	/**
	 * 获得指定日期N天之后或之前的日期
	 * @param dateString 指定日期，格式 yyyyMMdd
	 * @param pattern
	 * @param add
	 * @return
	 */
	public static String getAddDate(String dateString, String pattern, int add){
		String result = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
			Date date = sdf.parse(dateString);

			Calendar   cal   =   Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE,   add);
			result = new SimpleDateFormat( pattern).format(cal.getTime());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		return result;
	}

	/**
	 * 通过指定的分钟数minNUm，得到距离现在minNum的过去时间
	 *
	 * @param minNum
	 * @param pattern
	 * @return
	 */
	public static String getAddMin(Integer minNum, String pattern) {
		return  LocalDateTime.now().plusMinutes(minNum).format(DateTimeFormatter.ofPattern(pattern)).toString();
	}
	
	/**
	 * 获得指定日期月份的最后一天
	 * @param dateString 指定日期
	 * @param dateStringPattern 指定日期的格式
	 * @param pattern 返回日期的格式
	 * @return
	 */
	public static String getLastDayOfMonth(String dateString, String dateStringPattern, String pattern){
		String result = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(dateStringPattern);  
			Date date = sdf.parse(dateString);

			Calendar   cal   =   Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
			result = new SimpleDateFormat( pattern).format(cal.getTime());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
		return result;
	}



	/**
	 * 格式化日期
	 * @param date 要格式化的日期
	 * @param pattern 格式
	 * @return
	 */
	public static String formatDate(Date date,String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	/**
	 * 将字符串转化为日期
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseString(String date,String pattern) throws ParseException{
		return new SimpleDateFormat(pattern).parse(date);
	}
	/**
	 * 得到去年年份
	 * @return
	 */
	public static String getLastYear() {
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.YEAR,   -1);
		String lastYear = new SimpleDateFormat("yyyy").format(cal.getTime());
		return lastYear;
	}
	/**
	 * 得到上一季度 季份   格式 年份+1 2 3 4  对应四个季度
	 * @return
	 */
	public static String getLastQuarter() {
		String currentMonth = new SimpleDateFormat("yyyyMM").format(new Date());
		String quarter = "";
		String year = currentMonth.substring(0, 4);
		String month = currentMonth.substring(4);
		int mon = Integer.parseInt(month);
		if(mon>=1&&mon<=3){//上一季是去年第四季
			year = getLastYear();
			quarter = "4";
		}else if(mon>=4&&mon<=6){//上一季是当年第一季
			quarter = "1";
		}else if(mon>=7&&mon<=9){//上一季是当年第二季
			quarter = "2";
		}else{//上一季是当年第三季
			quarter = "3";
		}
		return year+quarter;
	}
	
	/**
	 * 获取当年年份
	 * @return
	 */
	public static int getYear(){
		Calendar cal = Calendar.getInstance();
		int i = cal.get(Calendar.YEAR);
		return i;
	}
	
	/**
	 * 得到指定月份的下一月
	 * @param month
	 * @param pattern
	 * @return
	 */
	public static String getNextMonth(String month, String pattern) {
		int yearindex = pattern.indexOf("yyyy");
		String year = month.substring(yearindex, yearindex+4);
		int monindex = pattern.indexOf("MM");
		String mon = month.substring(monindex, monindex+2);
		int mon_num = Integer.parseInt(mon);
		int next_mon = (mon_num==12)?1:(mon_num+1);
		int year_num = Integer.parseInt(year);
		int next_year = (mon_num==12)?(year_num+1):year_num;
		pattern = pattern.replaceAll("yyyy", String.valueOf(next_year)).replaceAll("MM", (next_mon<10)?("0"+next_mon):String.valueOf(next_mon));
		return pattern;
	}
	/**
	 * 得到某年某周的最后一天
	 * @param year 年份
	 * @param week 周数
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getLastDayOfWeek(cal.getTime());
	}
	/**
	 * 取得某个日期所在周的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}
	/**
	 * 得到某年某周的第一天
	 * @param year 年份
	 * @param week 周数
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getFirstDayOfWeek(cal.getTime());
	}
	/**
	 * 取得某个日期所在周的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}
	/**
	 * 将存在某个时间段内的周日期转化成日期区间
	 * @param weekdate 周日期  比如201409  2014年第9周
	 * @param startdate 起始日期 
	 * @param enddate 结束日期
	 * @param fmt 日期格式   起始和结束日期格式必须与此相符
	 * @return  返回格式20141001~20141007
	 */
	public static String transformWeekToDate(String weekdate,String startdate,String enddate,String fmt){
		try {
			String regex = "[0-9]{6}";
			if(!Pattern.matches(regex, weekdate)){
				log.info("周日期不是六位格式！");
				return weekdate;
			}
			
			String year = weekdate.substring(0, 4);
			String week = weekdate.substring(4, 6);
			Date tDate = getLastDayOfWeek(Integer.parseInt(year),0);
			String strTdate = formatDate(tDate, "yyyyMMdd");
			Integer iweek = Integer.parseInt(week);
			if (!strTdate.endsWith("0101")){//isSub参数控制周是否减1，如果遇到20121001-20121017这样特别的日期，就不进行减1操作
				iweek = iweek-1;
			}
			Date _startDate = parseString(startdate, fmt);
			Date _endDate = parseString(enddate, fmt);
			Date start =getFirstDayOfWeek(Integer.parseInt(year),iweek);
			Date end = getLastDayOfWeek(Integer.parseInt(year),iweek);
			if(_startDate.getTime() > start.getTime()){
					start = _startDate;
			}
			if(_endDate.getTime() < end.getTime()){
					end = _endDate;
			}
			if (start.getTime()>end.getTime()){
				start = getFirstDayOfWeek(Integer.parseInt(year),Integer.parseInt(week));
				end = getLastDayOfWeek(Integer.parseInt(year),Integer.parseInt(week));
			}
			String startWeek = DateTimeUtil.formatDate(start, "yyyyMMdd");
			String endWeek = DateTimeUtil.formatDate(end, "yyyyMMdd");
			return startWeek+"~"+endWeek;
		} catch (ParseException e) {
			log.error(StringUtil.getTrace(e));
			return null;
		}
	}
	
	/**
	 * 字符串转换为日期,支持转换格式 
	 * <br>yyyyMMdd
	 * <br>yyyy-MM-dd 
	 * <br>yyyy/MM/dd 
	 * <br>yyyy\MM\dd 
	 * <br>yyyy-MM-dd HH:mm:ss
	 * <br>yyyy/MM/dd HH:mm:ss
	 * <br>yyyyMMdd HH:mm:ss 
	 * @param date 传入的日期
	 * @return
	 */
	public static Date parseStrToDate(String date){
		if(null==date||date.trim().equals("")){
			return null;
		}
		date = date.trim();
		try {
			String reg1 = "^\\d{4}-\\d{2}-\\d{2}$";//yyyy-MM-dd
			if(Pattern.matches(reg1, date)){
				return new SimpleDateFormat("yyyy-MM-dd").parse(date);
			}
			String reg2 = "^\\d{4}/\\d{2}/\\d{2}$";//yyyy/MM/dd
			if(Pattern.matches(reg2, date)){
				return new SimpleDateFormat("yyyy/MM/dd").parse(date);
			}
			String reg3 = "^\\d{4}\\\\d{2}\\\\d{2}$";//yyyy\MM\dd
			if(Pattern.matches(reg3, date)){
				return new SimpleDateFormat("yyyy\\MM\\dd").parse(date);
			}
			String reg4 = "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";//yyyy-MM-dd HH:mm:ss
			if(Pattern.matches(reg4, date)){
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
			}
			String reg5 = "^\\d{4}/\\d{2}/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";//yyyy/MM/dd HH:mm:ss
			if(Pattern.matches(reg5, date)){
				return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date);
			}
			String reg6 = "^\\d{4}\\d{2}\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";//yyyyMMdd HH:mm:ss
			if(Pattern.matches(reg6, date)){
				return new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(date);
			}
			String reg7 = "^\\d{4}\\d{2}\\d{2}$";//yyyyMMdd
			if(Pattern.matches(reg7, date)){
				return new SimpleDateFormat("yyyyMMdd").parse(date);
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return null;
	}


	public static void main(String[] args) {
	}
}
