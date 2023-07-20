package com.yogiga.yogiga;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class YogigaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(YogigaApplication.class, args);
		JobLauncher jobLauncher = context.getBean(JobLauncher.class);
		Job job = context.getBean("importRestaurantJob", Job.class);

		try {
			JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.toJobParameters();

			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}
}
