package com.pluto.service.impl;

import com.pluto.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {
	private static final String SHOP_STATUS_KEY = "shop:status";
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void switchStatus(Short status) {
		redisTemplate.opsForValue().set(KEY, status);
	}

	@Override
	public Short getStatus() {
		return (Short) redisTemplate.opsForValue().get(KEY);
	}
}
