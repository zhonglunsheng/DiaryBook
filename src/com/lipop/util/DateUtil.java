package com.lipop.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 日期格式转字符串
	 * @param date
	 * @param format
	 * @return
	 */
		public static String formatDate(Date date,String format)
		{
			String result="";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			if (date!=null) {
				result=sdf.format(date);
			}
			return result;
		}
		
		/**
		 * 字符串转字时间格式
		 * @param str
		 * @param format 时间的格式 如yyyy-MM-dd HH:mm:ss
		 * @return
		 * @throws Exception
		 */
		public static Date formatString(String str,String format)throws Exception
		{
			if (StringUtil.IsEmpty(str)) {
				return null;
			}
			SimpleDateFormat sdf=new SimpleDateFormat(format);
			return sdf.parse(str);
		}
		
		//获取当前时间
		public static String getCurrentDateStr()
		{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
			return sdf.format(date);
		}
}
