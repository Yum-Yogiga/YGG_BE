package com.yogiga.yogiga.restaurant.cotroller;

import com.yogiga.yogiga.restaurant.dto.RestaurantDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantResponseDto;
import com.yogiga.yogiga.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @Operation(summary = "id로 특정 식당 조회")
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getResById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getResById(id));
    }

    @Operation(summary = "모든 식당 page 단위로 조회")
    @GetMapping("/all")
    public ResponseEntity<Page<RestaurantResponseDto>> getAllRes(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(restaurantService.getAllRes(pageable));
    }

    @Operation(summary = "식당 등록")
    @PostMapping
    public ResponseEntity<Long> createRestaurant(@Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDto));
    }

    @Operation(summary = "식당 정보 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Long> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantDto));
    }

    @Operation(summary = "식당 삭제")
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}
