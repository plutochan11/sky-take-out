package com.pluto.controller.user;

import com.pluto.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Shop", description = "Shop related APIs")
@RestController("userShopController")
@RequestMapping("/user/shop")
public class ShopController {
	@Resource
	private ShopService shopService;

	@Operation(summary = "Get shop status", description = "Retrieve the current shop status where 1 stands for open and 0 stands for closed")
	@GetMapping("/status")
	public ResponseEntity<Integer> getStatus() {
		Integer status = shopService.getStatus();
		return ResponseEntity.ok(status);
	}
}
