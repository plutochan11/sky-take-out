package com.pluto.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.context.RequestContext;
import com.pluto.enums.CacheKey;
import com.pluto.exception.DishClassificationNotFoundException;
import com.pluto.mapper.DishClassificationDishMapper;
import com.pluto.mapper.DishClassificationMapper;
import com.pluto.mapper.DishMapper;
import com.pluto.model.Dish;
import com.pluto.model.DishClassification;
import com.pluto.model.dto.DishClassificationAddDto;
import com.pluto.model.dto.DishClassificationListDto;
import com.pluto.service.DishClassificationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DishClassificationServiceImpl implements DishClassificationService {
	@Resource
	private DishMapper dishMapper;
	@Resource
	private DishClassificationMapper dishClassificationMapper;
	@Resource
	private DishClassificationDishMapper dishClassificationDishMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public List<DishClassification> findAll() {
		return dishClassificationMapper.findAll();
	}

	@Transactional
	@Override
	public void add(DishClassificationAddDto dishClassificationAddDto) {
		dishClassificationMapper.add(dishClassificationAddDto);
		Integer dishClassificationId = dishClassificationAddDto.getId();
		dishClassificationAddDto.getDishIds().forEach(dishId ->
			  dishClassificationDishMapper.add(dishClassificationId, dishId));

		Integer manipulatorId = RequestContext.getRequestUserId();
		if (Objects.nonNull(manipulatorId)) {
			log.info("A dish classification(id: {}, name: {}) is added by admin(id: {}).",
				  dishClassificationId, dishClassificationAddDto.getName(), manipulatorId);
		} else {
			log.info("A dish classification(id: {}, name: {}) is added by an unknown admin.",
				  dishClassificationId, dishClassificationAddDto.getName());
		}

	}

	@Override
	public PageInfo<DishClassification> findWithPaginationAndFilter(
		  DishClassificationListDto dishClassificationListDto) {
		PageHelper.startPage(dishClassificationListDto.getPageNo(),
			  dishClassificationListDto.getPageSize());
		return new PageInfo<>(dishClassificationMapper
			  .findWithFilter(dishClassificationListDto));
	}

	@Override
	public void toggleStatus(Integer id) {
		DishClassification dishClassification = dishClassificationMapper.findById(id);
		if (dishClassification == null) {
			throw new DishClassificationNotFoundException(
				  "Dish classification with ID " + id + " not found.");
		}
		if (dishClassification.getStatus() == 1) {
			dishClassificationMapper.updateStatus(id, 0);
		} else if (dishClassification.getStatus() == 0) {
			dishClassificationMapper.updateStatus(id, 1);
		} else {
			throw new IllegalStateException("Invalid status value: " +
				  dishClassification.getStatus() +
				  " for dish classification with ID " + id);
		}
	}

	@Override
	public void delete(Integer id) {
		if (!Objects.equals(1, dishClassificationMapper.deleteById(id))) {
			throw new DishClassificationNotFoundException(
				  "Dish classification with ID " + id + " not found.");
		}
	}

	@Transactional
	@Override
	public void addDishes(DishClassificationAddDto dto) {
		Integer id = dto.getId();
		List<Integer> dishIds = dto.getDishIds();
		dishClassificationDishMapper.addDishesToClassification(id, dishIds);
		dishIds.forEach(dishId -> dishMapper.updateClassificationIdById(dishId, id));

		// Invalidate cache for this dish classification
		// Assemble cache key
		String cacheKey = CacheKey.DISHES_DISH_CLASSIFICATION.key(String.valueOf(id));
		// Invalidate cache
		redisTemplate.delete(cacheKey);
		log.info("Cache invalidated for dishes of dish classification (ID: {}) after adding dishes", id);
	}

	@Override
	public List<Dish> getDishes(Integer id) {
		// Construct cache key
		String cacheKey = CacheKey.DISHES_DISH_CLASSIFICATION.key(String.valueOf(id));

		// Check out cache first, return if hit
		List<Dish> dishes = (List<Dish>) redisTemplate.opsForValue().get(cacheKey);
		if (dishes != null && !dishes.isEmpty()) {
			log.info("Cache hit, fetched dishes of dish classification (ID: {}) from cache", id);
			return dishes;
		}

		// If cache missed, query from DB and store in cache
		List<Integer> dishIds = dishClassificationDishMapper.getDishIds(id);
		if (dishIds == null || dishIds.isEmpty()) {
			String errorMessage = String.format("Dish Classification (ID: %d) Not Found", id);
			throw new DishClassificationNotFoundException(errorMessage);
		}
		dishes = dishMapper.getBatch(dishIds);
		redisTemplate.opsForValue().set(cacheKey, dishes, CacheKey.DISH_CLASSIFICATION.ttl(), CacheKey.DISH_CLASSIFICATION.timeUnit());
		log.info("Cache miss, fetched dishes of dish classification (ID: {}) from database", id);
		return dishes;
	}
}
