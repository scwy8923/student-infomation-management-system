package com.shuaizhao.project.utils;

import com.google.gson.Gson;

public class JsonUtil {
	static {
		gson = new Gson();
	}
	private static Gson gson;

	public static String toJson(Object object) {
		return getGson().toJson(object);
	}
	
	private static Gson getGson(){
		if(gson==null){
			gson=new Gson();
		}
		return gson;
	}
}
