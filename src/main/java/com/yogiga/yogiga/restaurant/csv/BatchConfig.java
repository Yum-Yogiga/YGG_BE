package com.yogiga.yogiga.restaurant.csv;

import com.yogiga.yogiga.restaurant.entity.Restaurant;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(BatchProperties.class)

public class BatchConfig extends DefaultBatchConfiguration {
    private final ResItemProcessor resItemProcessor;
    private final EntityManagerFactory entityManagerFactory;
    Logger logger = LoggerFactory.getLogger(BatchConfig.class);


    @Bean
    public FlatFileItemReader<ResCsvDto> restaurantItemReader() {
        return new FlatFileItemReaderBuilder<ResCsvDto>()
                .name("restaurantItemReader")
                .resource(new FileSystemResource("src/main/resources/retaurant.csv"))
                .linesToSkip(1) // Skip header row
                .delimited()
                .names("name",
                        "tasty", "friendly", "specialMenu", "clean", "freshIngredients", "costEffective", "generousPortions", "greatInterior", "goodForSolo",
                        "link", "address", "tel",
                        "menu1", "price1", "menu2", "price2", "menu3", "price3", "menu4", "price4")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ResCsvDto>() {{
                    setTargetType(ResCsvDto.class);
                }})
                .build();
    }

    @Bean
    public JpaItemWriter<Restaurant> restaurantItemWriter() {
        return new JpaItemWriterBuilder<Restaurant>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }


    @Bean
    public Step restaurantStep(JobRepository jobRepository,FlatFileItemReader<ResCsvDto> reader, JpaItemWriter<Restaurant> writer, PlatformTransactionManager transactionManager) {
        return new StepBuilder("restaurantStep", jobRepository)
                .<ResCsvDto, Restaurant> chunk(10, transactionManager)
                .reader(reader)
                .processor(resItemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importRestaurantJob(JobRepository jobRepository, Step restaurantStep) {
        logger.info("식당 정보 저장 Job 실행");
        return new JobBuilder("importRestaurantJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(restaurantStep)
                .end()
                .build();
    }


}
