package com.pluto.controller.management.shop;

import com.pluto.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Shop", description = "Shop related APIs")
@RestController("managementShopController")
@RequestMapping("/management/shop")
public class ShopController {
	@Resource
	private ShopService shopService;

	@Operation(summary = "Switch shop status", description = "Switch the shop status between open (1) and closed (0)")
	@PutMapping("/status/{status}")
	public ResponseEntity<?> switchStatus(@PathVariable Short status) {
		shopService.switchStatus(status);
		return ResponseEntity.ok().build();
	}

	@Operation(summary = "Get shop status", description = "Retrieve the current shop status where 1 stands for open and 0 stands for closed")
	@GetMapping("/status")
	public ResponseEntity<Short> getStatus() {
		Short status = shopService.getStatus();
		return ResponseEntity.ok(status);
	}
}
