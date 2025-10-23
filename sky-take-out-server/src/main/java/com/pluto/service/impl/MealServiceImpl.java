package com.pluto.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.enums.CacheKey;
import com.pluto.exception.DishClassificationNotFoundException;
import com.pluto.exception.DishNotFoundException;
import com.pluto.exception.MealNotFoundException;
import com.pluto.mapper.DishMapper;
import com.pluto.mapper.MealDishMapper;
import com.pluto.mapper.MealMapper;
import com.pluto.model.Dish;
import com.pluto.model.Meal;
import com.pluto.model.MealDish;
import com.pluto.model.dto.MealAddDto;
import com.pluto.model.dto.MealListDto;
import com.pluto.service.MealService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
public class MealServiceImpl implements MealService {
	@Resource
	private MealMapper mealMapper;
	@Resource
	private MealDishMapper mealDishMapper;
	@Resource
	private DishMapper dishMapper;
	@Resource
	private RedisTemplate redisTemplate;

	@Transactional
	@Override
	public void addMeal(MealAddDto mealAddDto) {
		mealMapper.add(mealAddDto);
		Integer mealId = mealAddDto.getId();

		List<MealDish> mealDishes = new ArrayList<>();
		mealAddDto.getDishIdsAndQuantities().forEach((dishId, quantity) -> {
			MealDish mealDish = MealDish.builder()
				  .mealId(mealId)
				  .dishId(dishId)
				  .quantity(quantity)
				  .build();
			mealDishes.add(mealDish);
		});
		mealDishMapper.addBatch(mealDishes);
	}

	@Override
	public void deleteMeal(Integer id) {
		if (mealMapper.deleteById(id) == 0) {
			throw new MealNotFoundException("Meal with ID " + id + " not found.");
		}
	}

	@Override
	public void updateStatus(Integer id) {
		Meal meal = mealMapper.findById(id);
		if (meal == null) {
			throw new MealNotFoundException("Meal with ID " + id + " not found.");
		}
		Integer newStatus = meal.getStatus() == 1 ? 0 : 1;
		mealMapper.updateStatus(id, newStatus);
	}

	@Override
	public Meal getMealsWithPagination(Integer id) {
		Meal meal = mealMapper.findById(id);
		if (meal == null) {
			throw new MealNotFoundException("Meal with ID " + id + " not found.");
		}
		return meal;
	}

	@Override
	public PageInfo<Meal> getMealsWithPagination(MealListDto mealListDto) {
		PageHelper.startPage(mealListDto.getPageNo(), mealListDto.getPageSize());
		List<Meal> meals = mealMapper.findWithFilter(mealListDto);
		return new PageInfo<>(meals);
	}

	@Override
	public List<Meal> getAllMeal() {
		return mealMapper.findAll();
	}

	@Override
	public List<Dish> getDishesInMeal(Integer id) {
		// Construct cache key
		String cacheKey = CacheKey.MEAL.key(String.valueOf(id));

		// Check out cache first, return if hit
		List<Dish> dishes = (List<Dish>) redisTemplate.opsForValue().get(cacheKey);
		if (dishes != null && !dishes.isEmpty()) {
			log.info("Cache hit for meal ID: {}", id);
			return dishes;
		}

		// Cache missed, query from DB and store in cache
		List<Integer> dishIds = mealDishMapper.getDishIds(id);
		if (dishIds == null || dishIds.isEmpty()) {
			String errorMessage = String.format("Meal (id: %d) Not Found", id);
			throw new MealNotFoundException(errorMessage);
		}
		dishes = dishMapper.getBatch(dishIds);
		redisTemplate.opsForValue().set(cacheKey, dishes, CacheKey.MEAL.ttl(), CacheKey.MEAL.timeUnit());
		log.info("Cache miss for meal ID: {}. Data cached.", id);

		return dishes;
	}
}
