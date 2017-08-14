package com.lipop.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * ���ڸ�ʽת�ַ���
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
		 * �ַ���ת��ʱ���ʽ
		 * @param str
		 * @param format ʱ��ĸ�ʽ ��yyyy-MM-dd HH:mm:ss
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
		
		//��ȡ��ǰʱ��
		public static String getCurrentDateStr()
		{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
			return sdf.format(date);
		}
}
