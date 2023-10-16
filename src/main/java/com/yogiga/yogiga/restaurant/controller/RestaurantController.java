package com.yogiga.yogiga.restaurant.controller;

import com.yogiga.yogiga.restaurant.dto.RestaurantDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantResponseDto;
import com.yogiga.yogiga.restaurant.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
@Slf4j
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
    @Operation(summary = "키워드 기반 식당 추천 api, 9개 키워드중 선택된 키워드 = 1, 선택안된 키워드 = 0 으로 넘겨주면 추천 식당이름 반환")
    @PostMapping("/recommend")
    public Mono<List<String>> recommendRestaurants(@RequestBody List<Integer> keywordInput) {
        Mono<List<String>> recommendRestaurants = restaurantService.recommendRestaurants(keywordInput);

        recommendRestaurants.subscribe(result -> {
            log.info("Recommended restaurants: {}", result);
            // 이 부분에서 result 값을 확인하고 원하는 처리를 수행할 수 있습니다.
        });
        return recommendRestaurants;
    }

    @Operation(summary = "식당 크롤링 csv 파일 DB 저장")
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
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
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

    @Operation(summary = "식당 좋아요", description = "식당 id값 넘겨주면 좋아요 갯수 1 증가, 좋아요는 각 식당에 한번씩만 가능하다.")
    @PostMapping("/likes/{id}")
    public ResponseEntity<Integer> likeRestaurants(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.likeRestaurants(id));
    }

    @Operation(summary = "식당 싫어요", description = "식당 id값 넘겨주면 싫어요 갯수 1 증가, 싫어요는 각 식당에 한번씩만 가능하다.")
    @PostMapping("/dislikes/{id}")
    public ResponseEntity<Integer> dislikeRestaurants(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.dislikeRestaurants(id));
    }
}
