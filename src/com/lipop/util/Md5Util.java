package com.lipop.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Md5Util {
		public static String EncoderPwdByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException
		{
			MessageDigest Md5 = MessageDigest.getInstance("MD5");
			BASE64Encoder base64En = new BASE64Encoder();
			return base64En.encode(Md5.digest(str.getBytes("utf-8")));
		}
}
