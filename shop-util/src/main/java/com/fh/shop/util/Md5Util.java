package com.fh.shop.util;

import java.security.MessageDigest;
import java.util.UUID;

public class
Md5Util {
	public final static String sout(String member,String secre){
		return md5(member+secre);
	}


	public final static String md5(String s){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			// 使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
				// 将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static String encodePassword(String password, String salt) {
		return md5(md5(password)+salt);
	}



	public static void main(String[] args) {
		String info = md5("u(*)(*HJHTUTFGF3egg788_!!!");
		String s = UUID.randomUUID().toString();
		System.out.println(s);
		String info2 = md5(md5("123")+","+s);
		System.out.println(info);
		System.out.println(info2);
	}

}
