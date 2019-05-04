package com.golden.util;

/**
 * 格式化数据类型，默认为TEXT文本类型，不做任何格式化
 */
public enum Format {
	/**文本,不做格式化*/
	TEXT,
	/**金额，格式化成千分位，￥前缀,比如￥100,000.00*/
	AMT,
	/**数字类,格式化成千分位,比如1,000,000.00*/
	NUMBER,
	/**百分比类,在值后面加个%百分号*/
	RATE,
	/**日期,格式化成YYYY-MM-DD,比如2015-07-09*/
	DATE,
	/**日期+时间,格式化成YYYY-MM-DD HH:mm:ss,比如2015-07-09 12:01:01*/
	DATETIME,
	/**时间,格式化成HH:mm:ss,比如12:01:01*/
	TIME
}
