package com.lipop.util;

public class StringUtil {
		public static Boolean IsEmpty(String str)
		{
			if ("".equals(str) || str==null) {
				return true;
			}else{
				return false;
			}
		}
		
		public static boolean IsNotEmpty(String str)
		{
			if (!"".equals(str) && str!=null) {
				return true;
			}else{
				return false;
			}
		}
}
