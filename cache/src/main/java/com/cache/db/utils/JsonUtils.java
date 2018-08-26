package com.cache.db.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {

	/**
	 * 任意类型转换为json的String类型，规则：简单类型直接toString，其他的转换为json格式后toString（此方法的目的是：只往redis里存String格式，按照我们自己的格式转换其他地方提供的任意对象，做到公用）
	 * 此方法和toString方法返回结果是一样的，不要删除本方法，可以用来做参考
	 * @param obj
	 * @return
	 */
	public static String toString1(Object obj) {
		if(obj == null){
			return null;
		}
		String s = null;
		if (obj instanceof String) {
			s = obj.toString();
		} else if (obj instanceof Number) {
			s = String.valueOf(obj);
		} else {
			s = JSONObject.toJSONString(obj);
		}
		return s;
	}
	
	/**
	 * 把任意对转换成字符串格式，此方法和toString1方法转出来的结果是一样的
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if(obj == null){
			return null;
		}
		if(obj instanceof String){
			return obj.toString();
		}
		return JSONObject.toJSONString(obj);
	}
	

	/**
	 * 把字符串转换成指定的类型，和toString方法成对出现，反转本类里的toString方法得到的字符串为指定类型
	 * 此方法和toObject方法返回结果是一样的，不要删除本方法，可以用来做参考
	 * @param str
	 * @param c
	 * @return
	 */
	public static <T extends Object> T toObject1(String str, Class<T> c) {
		// c.forName("").newInstance();
		if(str == null){
			return null;
		}
		Object obj = null;
		if (c.isAssignableFrom(String.class)) {
			obj = str;
		} else if (c.isAssignableFrom(Integer.class)) {
			obj = Integer.parseInt(str);
		} else if (c.isAssignableFrom(Float.class)) {
			obj = Float.parseFloat(str);
		} else if (c.isAssignableFrom(Double.class)) {
			obj = Double.parseDouble(str);
		} else if (c.isAssignableFrom(Long.class)) {
			obj = Long.parseLong(str);
		} else if (c.isAssignableFrom(Short.class)) {
			obj = Short.parseShort(str);
		} else if (c.isAssignableFrom(Byte.class)) {
			obj = Byte.parseByte(str);
		} else {
			obj = JSONObject.parseObject(str, c);
		}
		return (T) obj;
	}
	
	/**
	 * 把json字符串转换成指定对象，和toObject1结果是一样的
	 * @param str
	 * @param c	必须要指定Class类型，否则简单的类型（string，int等）转成json对象会出问题,为了避免出错，如果不知道类型，直接讲str原样返回
	 * @return
	 */
	public static <T extends Object> T toObject(String str, Class<T> c) {
		// c.forName("").newInstance();
		if(str == null){
			return null;
		}
		if(c == null){
			return (T) str;
		}
		if(c.isAssignableFrom(String.class)){
			return (T) str;
		}
//		if(c.isAssignableFrom(JSONObject.class)){
//			return (T) JSONObject.parseObject(str);
//		}
		return JSONObject.parseObject(str, c);
	}
	
	/**
	 * json字符串转换成list
	 * @param key
	 * @param c
	 * @return
	 */
	public static <T extends Object> List<T> toList(String str,Class<T> c){
		if(str == null){
			return null;
		}
		return JSONObject.parseArray(str, c);
	}
	
	/**
	 * json字符串转换为map
	 * @param key
	 * @param mapKeyClass
	 * @param mapValueClass
	 * @return
	 */
	public static <V,T extends Object> Map<T, V> toMap(String str,Class<T> mapKeyClass,Class<V> mapValueClass){
		if(str == null){
			return null;
		}
		Map<T, V> map1 = new HashMap<>();
//		JSONObject js = JSONObject.parseObject(str);
		Map js = JSONObject.parseObject(str,Map.class);
		Set<String> keySet = js.keySet();
		for (String k : keySet) {
			Object o = js.get(k);
			map1.put(toObject(k, mapKeyClass), o==null?null:toObject(o.toString(), mapValueClass));
		}
		return map1;
	}
}
