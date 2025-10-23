package com.pluto.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pluto.enums.CacheKey;
import com.pluto.enums.ErrorMessage;
import com.pluto.enums.Status;
import com.pluto.exception.DeletionForbiddenException;
import com.pluto.exception.DishNotFoundException;
import com.pluto.mapper.DishMapper;
import com.pluto.mapper.MealDishMapper;
import com.pluto.model.Dish;
import com.pluto.model.dto.DishAddDto;
import com.pluto.model.dto.DishDetailDto;
import com.pluto.model.dto.DishListDto;
import com.pluto.service.DishService;
import com.pluto.util.DtoConverter;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class DishServiceImpl implements DishService {
	@Resource
	private DishMapper dishMapper;
	@Resource
	private AliyunOssFileUploadService fileUploadService;
	@Resource
	private DtoConverter dtoConverter;
	@Resource
	private MealDishMapper mealDishMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void add(DishAddDto dto) throws NoSuchMethodException {
		// Upload image to OSS and get the URL
		String imageUrl = fileUploadService.upload(dto.getImage());
		dto.setImage(null); // Clear the MultipartFile to avoid serialization issues

		// Convert DTO to Entity and save to DB
		Dish dish = dtoConverter.dtoToEntity(dto, Dish.class);
		dish.setImage(imageUrl);
		dishMapper.add(dish);
	}

	@Override
	public DishDetailDto findDetailById(Integer id) {
		DishDetailDto dto = dishMapper.findDetailById(id);
		if (Objects.nonNull(dto)) {
			return dto;
		} else {
			throw new DishNotFoundException("Dish with ID " + id + " not found.");
		}
	}

	@Override
	public PageInfo<DishDetailDto> listWithPaginationAndFilters(DishListDto dishListDto) {
		PageHelper.startPage(dishListDto.getPageNo(), dishListDto.getPageSize());
		return new PageInfo<>(dishMapper.findDetailsWithFilters(dishListDto));
	}

	@Override
	public void toggleStatus(List<Integer> ids) {
		List<Dish> dishes = new ArrayList<>();
		List<Integer> missingIds = new ArrayList<>();

		for (Integer id : ids) {
			Dish dish = dishMapper.findById(id);
			if (dish == null) {
				missingIds.add(id);
			} else {
				dishes.add(dish);
			}
		}

		if (!missingIds.isEmpty()) {
			throw new DishNotFoundException("Dish with ID(s) " + missingIds + " not found.");
		}

		for (Dish dish : dishes) {
			Short newStatus = (short) ((dish.getStatus() == 1) ? 0 : 1);
			dish.setStatus(newStatus);
			dishMapper.updateStatus(dish);
		}
	}

//	@Override
//	public void deleteById(Integer id) {
//		// Verify if the dish exists by checking the affected rows. It should be exactly 1.
//		if (!Objects.equals(dishMapper.deleteById(id), 1)) {
//			throw new DishNotFoundException("Dish with ID " + id + " not found.");
//		}
//	}

	@Transactional
	@Override
	public void deleteById(List<Integer> ids) {
		List<Dish> dishesInMeals = new ArrayList<>();
		List<Dish> dishesOnSale = new ArrayList<>();

		ids.forEach(id -> {
			// Verify if a dish is on sale
			Dish dish = dishMapper.findById(id);
			if (dish != null && dish.getStatus() == Status.ON_SALE.getValue()) {
				// Collect the dishes that are on sale
				dishesOnSale.add(dish);
				return;
			} else if (dish == null) {
				throw new DishNotFoundException("Dish with ID " + id + " not found.");
			}

			// Verify if a dish belongs to a meal
			if (mealDishMapper.existsByDishId(id) > 0) {
				// Collect the dishes that belong to meals
				dishesInMeals.add(dish);
			}
		});

		if (!dishesOnSale.isEmpty()) {
			throw new DeletionForbiddenException(ErrorMessage.DISH_ON_SALE.getMessage() +
				  "\n" + String.join("\n", dishesOnSale.stream().map(Dish::getName).toList()));
		}

		if (!dishesInMeals.isEmpty()) {
			throw new DeletionForbiddenException(ErrorMessage.DISH_IN_A_MEAL.getMessage() +
				  "\n" + String.join("\n", dishesInMeals.stream().map(Dish::getName).toList()));
		}

		dishMapper.deleteByIds(ids);

		// Invalidate all cache of dishes in dish classifications (for simplicity)
		String pattern = CacheKey.DISHES_DISH_CLASSIFICATION.key("*");
		clearCache(pattern);
	}

	@Override
	public void update(Dish dish) {
		Dish existing = dishMapper.findById(dish.getId());
		if (existing == null) {
			throw new DishNotFoundException("Dish with ID " + dish.getId() + " not found.");
		}
		dishMapper.update(dish);

		// Invalidate all cache of dishes in dish classifications (for simplicity)
		String pattern = CacheKey.DISHES_DISH_CLASSIFICATION.key("*");
		clearCache(pattern);
	}

	// Clear cache related to the provided cache key prefix
	private void clearCache(String pattern) {
		Set<String> keys = redisTemplate.keys(pattern);

		redisTemplate.delete(keys);
	}
}
