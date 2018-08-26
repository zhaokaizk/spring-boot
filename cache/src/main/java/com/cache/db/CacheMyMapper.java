package com.cache.db;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CacheMyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
