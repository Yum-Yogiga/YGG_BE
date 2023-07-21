package com.yogiga.yogiga.restaurant.controller;

import com.yogiga.yogiga.restaurant.dto.RestaurantDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantResponseDto;
import com.yogiga.yogiga.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final JobLauncher jobLauncher;
    private final Job importRestaurantJob;

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
    @PostMapping("/upload")
    public ResponseEntity<String> runJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(importRestaurantJob, jobParameters);

            ExitStatus exitStatus = jobExecution.getExitStatus();
            String result = "Job Execution Status: " + exitStatus.getExitCode() + " - " + exitStatus.getExitDescription();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Job Execution Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
