package com.yogiga.yogiga.restaurant.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
public class DataImportController {

    private final JobLauncher jobLauncher;
    private final Job importRestaurantJob;

    @Operation(summary = "식당 크롤링 csv 파일 DB 저장, 내부적으로만 쓰이는 api이다.")
    @PostMapping("/import")
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
}
