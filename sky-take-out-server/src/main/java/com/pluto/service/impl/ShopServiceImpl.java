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
	public void switchStatus(Integer status) {
		redisTemplate.opsForValue().set(SHOP_STATUS_KEY, status);
	}

	@Override
	public Integer getStatus() {
		return (Integer) redisTemplate.opsForValue().get(SHOP_STATUS_KEY);
	}
}
