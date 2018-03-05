package com.shuaizhao.project.utils;

import java.util.regex.Pattern;

public class CheckUtil {
	public static boolean checkPhone(String phone) {
		if (phone.length() != 11) {
			return false;
		}
		Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
		if (p.matcher(phone).matches()) {
			return true;
		}
		return false;
	}

	public static boolean checkPassword(String password) {
		Pattern p = Pattern
				.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
		if (p.matcher(password).matches()) {
			return true;
		}
		return false;
	}
}
