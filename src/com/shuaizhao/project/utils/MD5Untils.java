package com.shuaizhao.project.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5Untils {
		public static String md5(String mes){
			try {
				MessageDigest md=MessageDigest.getInstance("md5");
				byte[] b=md.digest(mes.getBytes());
				BASE64Encoder base=new BASE64Encoder();
				return base.encode(b);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException();
			}
			
		}
}
