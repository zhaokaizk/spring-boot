package com.cache.redis;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.cache.db.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;


/**
 * redicache 工具类
 * 
 */
@Component
public class RedisUtils {
	private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

	@Autowired
	RedisTemplate redisTemplate;

	public void remove(String key) {
		redisTemplate.delete(key);
	}

	public void setObject(String key, Object value, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		ops.set(key, JsonUtils.toString(value));
		expire(key, expire);
	}

	public void setObject(String key, Object value) {
		setObject(key, value, 0);
	}

	public <T extends Object> T getObject(String key, Class<T> c, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		Object o = ops.get(key);
		if(o == null){
			return null;
		}
		expire(key, expire);
		return  JsonUtils.toObject(o.toString(), c);
	}

	public <T extends Object> T getObject(String key, Class<T> c) {
		return getObject(key, c, 0);
	}

	/**
	 * 设置长整形,expire设置为0:表示无过期时间
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 *            按秒计时的过期时间
	 */
	public void setLong(String key, long value, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		ops.set(key, value);
		expire(key, expire);
	}

	public void setLong(String key, long value) {
		setLong(key, value, 0);
	}

	public Long getLong(String key, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		Object o = ops.get(key);
		if(o == null){
			return null;
		}
		expire(key, expire);
		return Long.parseLong(o.toString());
	}

	public Long getLong(String key) {
		return getLong(key, 0);
	}

	/**
	 * 设置整型，expire设置为0:表示无过期时间
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 */
	public void setInt(String key, int value, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		ops.set(key, value);
		expire(key, expire);
	}

	public void setInt(String key, int value) {
		setInt(key, value, 0);
	}

	public Integer getInt(String key, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		Object o = ops.get(key);
		if(o == null){
			return null;
		}
		expire(key, expire);
		return Integer.parseInt(o.toString());
	}

	public Integer getInt(String key) {
		return getInt(key, 0);
	}

	public void setString(String key, String value, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		ops.set(key, value);
		expire(key, expire);
	}

	public void setString(String key, String value) {
		setString(key, value, 0);
	}

	public String getString(String key, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		Object o = ops.get(key);
		if(o != null) {
			expire(key, expire);
			return o.toString();
		}
		return null;
	}

	public String getString(String key) {
		return getString(key, 0);
	}

	public void setFloat(String key, float value, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		ops.set(key, value);
		expire(key, expire);
	}

	public void setFloat(String key, float value) {
		setFloat(key, value, 0);
	}

	public Float getFloat(String key, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		Object o = ops.get(key);
		if(o == null){
			return null;
		}
		expire(key, expire);
		return Float.parseFloat(o.toString());
	}

	public Float getFloat(String key) {
		return getFloat(key, 0);
	}

	public void setDouble(String key, double value, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		ops.set(key, value);
		expire(key, expire);
	}

	public void setDouble(String key, double value) {
		setDouble(key, value, 0);
	}

	public Double getDouble(String key, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		Object o = ops.get(key);
		if(o == null){
			return null;
		}
		expire(key, expire);
		return Double.parseDouble(o.toString());
	}

	public Double getDouble(String key) {
		return getDouble(key, 0);
	}

	public void setByte(String key, byte value, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		ops.set(key, value);
		expire(key, expire);
	}

	public void setByte(String key, byte value) {
		setByte(key, value, 0);
	}

	public Byte getByte(String key, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		Object o = ops.get(key);
		if(o == null){
			return null;
		}
		expire(key, expire);
		return Byte.parseByte(o.toString());
	}

	public Byte getByte(String key) {
		return getByte(key, 0);
	}

	public void setShort(String key, short value, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		ops.set(key, value);
		expire(key, expire);
	}

	public void setShort(String key, short value) {
		setShort(key, value, 0);
	}

	public Short getShort(String key, long expire) {
		ValueOperations<Serializable, Object> ops = redisTemplate.opsForValue();
		Object o = ops.get(key);
		if(o == null){
			return null;
		}
		expire(key, expire);
		return Short.parseShort(o.toString());
	}

	public Short getShort(String key) {
		return getShort(key, 0);
	}

	public <K, V extends Object> void setMap(String key, Map<K, V> value, long expire) {
		if (value == null || value.size() == 0) {
			// 存放到redis里的map必须有值，否则抛异常
			return;
		}
		Map<String, String> map = new HashMap<>();
		HashOperations ops = redisTemplate.opsForHash();
		Set<K> keySet = value.keySet();
		for (K k : keySet) {
			V v = value.get(k);
			map.put(JsonUtils.toString(k), JsonUtils.toString(v));

		}
		ops.putAll(key, map);
		expire(key, expire);
	}

	public <K, V extends Object> void setMap(String key, Map<K, V> value) {
		setMap(key, value, 0);
	}

	public void setMapValue(String key, Object mapKey, Object mapValue, long expire) {
		HashOperations ops = redisTemplate.opsForHash();
		ops.put(key, JsonUtils.toString(mapKey), JsonUtils.toString(mapValue));
		expire(key, expire);
	}

	/**
	 * 当key不存在时，返回的集合大小为0，不会返回null
	 * @param key
	 * @param kClass
	 * @param vClass
	 * @return
	 */
	public <K, V extends Object> Map<K, V> getMap(String key, Class<K> kClass, Class<V> vClass) {
		return getMap(key, kClass, vClass, 0);
	}

	/**
	 * 当key不存在时，返回的集合大小为0，不会返回null
	 * @param key
	 * @param kClass
	 * @param vClass
	 * @param expire
	 * @return
	 */
	public <K, V extends Object> Map<K, V> getMap(String key, Class<K> kClass, Class<V> vClass, long expire) {
		Map<K, V> map = new HashMap<>();
		HashOperations ops = redisTemplate.opsForHash();
		Map v = ops.entries(key);
		if (v != null) {
			Set keySet = v.keySet();
			for (Object k : keySet) {
				Object o = v.get(k);
				map.put( JsonUtils.toObject(k.toString(), kClass),  JsonUtils.toObject(o.toString(), vClass));
			}
			expire(key, expire);
		}

		return map;
	}

	public <T extends Object> T getMapValue(String key, Object mapKey, Class<T> valueType, long expire) {
		HashOperations ops = redisTemplate.opsForHash();
		Object v = ops.get(key, JsonUtils.toString(mapKey));
		if(v == null){
			return null;
		}
		expire(key, expire);
		long st = System.currentTimeMillis();
		T rc = JsonUtils.toObject(v.toString(), valueType);
		
		return rc;
	}

	public <T extends Object> T getMapValue(String key, Object mapKey, Class<T> valueType) {
		return getMapValue(key, mapKey, valueType, 0);
	}

	public <K extends Object> List<K> getMapKeys(String key, Class<K> c, long expire) {
		List<K> list = new ArrayList<>();
		HashOperations ops = redisTemplate.opsForHash();
		Set keys = ops.keys(key);
		for (Object k : keys) {
			list.add( JsonUtils.toObject(k.toString(), c));
		}
		expire(key, expire);
		return list;
	}
	
	public boolean existMapKey(String key, Object mapKey) {
		HashOperations ops = redisTemplate.opsForHash();
		return ops.hasKey(key, mapKey);
	}

	/**
	 * 
	 * @param key
	 * @param c
	 * @return
	 */
	public <K extends Object> List<K> getMapKeys(String key, Class<K> c) {
		return getMapKeys(key, c, 0);
	}

	/**
	 * 当key不存在时，返回的集合大小为0，不会返回null
	 * @param key
	 * @param c
	 * @param expire
	 * @return
	 */
	public <V extends Object> List<V> getMapValues(String key, Class<V> c, long expire) {
		List<V> list = new ArrayList<>();
		HashOperations ops = redisTemplate.opsForHash();
		List values = ops.values(key);
		for (Object k : values) {
			list.add( JsonUtils.toObject(k.toString(), c));
		}
		expire(key, expire);
		return list;
	}

	/**
	 * 当key不存在时，返回的集合大小为0，不会返回null
	 * @param key
	 * @param c
	 * @return
	 */
	public <V extends Object> List<V> getMapValues(String key, Class<V> c) {
		return getMapValues(key, c, 0);
	}

	public void removeMapValue(String key, Object mapKey, long expire) {
		HashOperations ops = redisTemplate.opsForHash();
		ops.delete(key, JsonUtils.toString(mapKey));
		expire(key, expire);
	}

	public void removeMapValue(String key, Object mapKey) {
		removeMapValue(key, mapKey, 0);
	}

	public <T extends Object> void addList(String key, List<T> list,long expire) {
		if(list==null || list.size()==0){
			return;
		}
		List<String> list1 = new ArrayList<>();
		for (T obj : list) {
			if(obj != null){
				list1.add(JsonUtils.toString(obj));
			}
		}
		if(list1.size() == 0){
			return;
		}
		
		ListOperations ops = redisTemplate.opsForList();
		ops.rightPushAll(key, list1);
		expire(key, expire);
	}
	

	public <T extends Object> void addList(String key, List<T> list) {
		addList(key, list, 0);
	}


	/**
	 * 当key不存在时，返回的集合大小为0，不会返回null
	 * @param key
	 * @param type
	 * @param expire
	 * @return
	 */
	public <T extends Object> List<T> getList(String key, Class<T> type,long expire) {
		// -1表示最后一个元素
		ListOperations ops = redisTemplate.opsForList();
		List list = ops.range(key, 0, -1);
		List<T> re = new ArrayList<>();
		for (Object s : list) {
			re.add( JsonUtils.toObject(s.toString(), type));
		}
		expire(key, expire);
		return re;

	}
	
	/**
	 * 当key不存在时，返回的集合大小为0，不会返回null
	 * @param key
	 * @param type
	 * @return
	 */
	public <T extends Object> List<T> getList(String key, Class<T> type) {
		return getList(key, type, 0);
	}

	/**
	 * 获取list的数量
	 * @param key
	 * @return
	 */
	public long getListSize(String key,long expire){
		ListOperations ops = redisTemplate.opsForList();
		Long size = ops.size(key);
		if(size == null){
			size = 0L;
		}
		expire(key, expire);
		return size;
	}


	
	/**
	 * 在名称为key的list头添加一个值为value的 元素，当key不存在时创建key并写入value
	 * 
	 * @param key
	 * @param value
	 * @return 
	 * @return 
	 */
	public boolean addListValueFirst(String key, Object value,long expire){
		ListOperations ops = redisTemplate.opsForList();
		Long n = ops.leftPush(key, JsonUtils.toString(value));
		expire(key, expire);
		return n == 0 ? false: true; 
	}
	
	/**
	 * 在名称为key的list头添加一个值为value的 元素，当key不存在时不添加
	 * 
	 * @param key
	 * @param value
	 * @return 
	 */
	public boolean addListValueFirstKeyExsit(String key, Object value,long expire){
		ListOperations ops = redisTemplate.opsForList();
		Long n = ops.leftPushIfPresent(key, JsonUtils.toString(value));
		expire(key, expire);
		return n == 0 ? false: true; 
	}
	
	/**
	 * 在名称为key的list尾部添加一个值为value的 元素
	 * 
	 * @param key
	 * @param value
	 * @return 
	 */
	public boolean addListValueEnd(String key, Object value,long expire){
		ListOperations ops = redisTemplate.opsForList();
		Long n = ops.rightPush(key, JsonUtils.toString(value));
		expire(key, expire);
		return n == 0 ? false: true; 
	}
	
	/**
	 * 在名称为key的list尾部添加一个值为value的 元素，当key不存在时不添加
	 * 
	 * @param key
	 * @param value
	 * @return 
	 */
	public boolean addListValueEndKeyExsit(String key, Object value,long expire){
		ListOperations ops = redisTemplate.opsForList();
		Long n = ops.rightPushIfPresent(key, JsonUtils.toString(value));
		expire(key, expire);
		return n == 0 ? false: true; 
	}
	
	/**
	 * 把元素存放到指定的位置，原来位置上的元素被覆盖掉（删除掉了）
	 * @param key
	 * @param index	下标
	 * @param value
	 * @param expire
	 */
	public void addListValue(String key,long index,Object value,long expire){
		ListOperations ops = redisTemplate.opsForList();
		ops.set(key, index, JsonUtils.toString(value));
		expire(key, expire);
	}
	
	/**
	 * 获取指定下标的元素
	 * @param key		键
	 * @param index		下标，从0开始，也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * @param type		返回元素对象的类型
	 * @param expire	过期值
	 * @return
	 */
	public <T extends Object> T getListValue(String key,long index,Class<T> type,long expire){
		ListOperations ops = redisTemplate.opsForList();
		Object obj = ops.index(key, index);
		if(obj == null){
			return null;
		}
		T v =  JsonUtils.toObject(obj.toString(), type);
		return v;
	}
	
	
	
	/**
	 * 删除名称为key的list中的首元素
	 * 
	 * @param key
	 * @return 
	 * @return 
	 */
	public boolean removeListValueFirst(String key,long expire){
		ListOperations ops = redisTemplate.opsForList();
		Object obj = ops.leftPop(key);
		expire(key, expire);
		return obj == null ? false: true; 
	}
	
	/**
	 * 删除名称为key的list中的尾元素
	 * 
	 * @param key
	 * @return 
	 * @return 
	 */
	public boolean removeListValueEnd(String key,long expire){
		ListOperations ops = redisTemplate.opsForList();
		Object obj = ops.rightPop(key);
		expire(key, expire);
		return obj == null ? false: true; 
	}

	/**
	 * 删除名称为key的list中的value元素
	 * 
	 * @param key
	 * @param value
	 * @param count 	count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
						count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
						count = 0 : 移除表中所有与 value 相等的值。
	 * @return 
	 */
	public boolean removeListValue(String key,Object value,int count,long expire){
		ListOperations ops = redisTemplate.opsForList();
		Long obj = ops.remove(key, count, JsonUtils.toString(value));
		expire(key, expire);
		return obj == null ? false: true; 
	}

	/**
	 * 获取排序set里的元素
	 * @param key
	 * @param start	起始位置，0开始
	 * @param end	结束为止，-1表示全部（包含结束位置）
	 * @param expire
	 * @return 
	 */
	public<T extends Object> List<T> getSortSet(String key,long start,long end,Class<T> c,long expire){
		ZSetOperations ops = redisTemplate.opsForZSet();
		Set set = ops.range(key, start, end);
		List<T> re = new ArrayList<>();
		for (Object s : set) {
			re.add(JsonUtils.toObject(s.toString(), c));
		}
		expire(key, expire);
		return re;
	}

	/**
	 * 添加元素到有序集合（sortSet）里面
	 * 
	 * @param key
	 * @param socore 可以是整数值或双精度浮点数
	 * @param value
	 */
	public void addSortSetValue(String key,Number score,Object value,long expire){
		ZSetOperations ops = redisTemplate.opsForZSet();
		ops.add(key, JsonUtils.toString(value), score.doubleValue());
		expire(key, expire);
	}
	
	/**
	 * 为有序集 key 的成员 value 的 score 值加上增量 inscreScore,可以通过传递一个负数值 inscreScore ，让 score 减去相应的值
	 * 
	 * @param key
	 * @param inscreScore
	 * @param value
	 */
	public void increSortSetValue(String key,Number inscreScore,Object value,long expire){
		ZSetOperations ops = redisTemplate.opsForZSet();
		ops.incrementScore(key, JsonUtils.toString(value), inscreScore.doubleValue());
		expire(key, expire);
	}
	
	
	/**
	 * 添加元素到有序集合（sortSet）里面
	 * 
	 * @param key
	 * @param scoreValue [socre,value,socre,value.....]
	 * @throws Exception 
	 */
	public <T extends Object> void addSortSetValue(String key,List<T> scoreValue,long expire) throws Exception{
		ZSetOperations ops = redisTemplate.opsForZSet();
		Set<TypedTuple> set = new HashSet<>();
		int size = scoreValue.size();
		if(size % 2 != 0){
			throw new Exception("scoreValue必须成对出现");
		}
		for (int i=0;i<size;i+=2) {
			T score = scoreValue.get(i);
			if(!(score instanceof Number)){
				throw new Exception("score必须为数字类型");
			}
			T value = scoreValue.get(i+1);
			TypedTuple obj = new DefaultTypedTuple(JsonUtils.toString(value), ((Number)score).doubleValue());
			set.add(obj);
		}
		ops.add(key, set);
		expire(key, expire);
		
	}
	
//	public <T extends Object> void addSortSetValue1(String key,List<T> scoreValue,long expire) throws Exception{
//		ZSetOperations ops = redisTemplate.opsForZSet();
//		int size = scoreValue.size();
//		if(size % 2 != 0){
//			throw new Exception("scoreValue必须成对出现");
//		}
//		for (int i=0;i<size;i+=2) {
//			T score = scoreValue.get(i);
//			if(!(score instanceof Number)){
//				throw new Exception("score必须为数字类型");
//			}
//			T value = scoreValue.get(i+1);
//			ops.add(key,RedisUtils.toString(value), ((Number)score).doubleValue());
//		}
//		expire(key, expire);
//		
//	}
	
	
	public void removeSortSetValue(String key,Object value,long expire){
		ZSetOperations ops = redisTemplate.opsForZSet();
		ops.remove(key, JsonUtils.toString(value));
		expire(key, expire);
	}

	
	public <T extends Object> void removeSortSetValue(String key,List<T> value,long expire){
		int i = 0;
		Object[] objs = new Object[value.size()];
		for (Object obj : value) {
			objs[i++] = JsonUtils.toString(obj);
		}
		ZSetOperations ops = redisTemplate.opsForZSet();
		ops.remove(key, objs);
		expire(key, expire);
	}
	
	public void removeSortSetValue(String key,int startIndex,int endIndex,long expire){
		ZSetOperations ops = redisTemplate.opsForZSet();
		ops.removeRange(key, startIndex, endIndex);
		expire(key, expire);
	}

	public void increment(String key,long incrNum,long expire) {
		ValueOperations ops = redisTemplate.opsForValue();
		ops.increment(key, incrNum);
		expire(key, expire);
	}
	
	public void increment(String key,double incrNum,long expire) {
		ValueOperations ops = redisTemplate.opsForValue();
		ops.increment(key, incrNum);
		expire(key, expire);
	}
	
	/**
	 * 批量删除key
	 *
	 * @param pattern
	 *            表达式，* 表示删除所有的键，不支持集群
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 */
	public boolean isExists(final String key) {
		return redisTemplate.hasKey(key);
	}

	public void expire(String key, long seconds) {
		if (seconds <= 0) {
			return;
		}
		redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}
}
