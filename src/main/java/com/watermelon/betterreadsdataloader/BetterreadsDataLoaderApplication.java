package com.watermelon.betterreadsdataloader;

import java.nio.file.Path;

import com.watermelon.betterreadsdataloader.author.Author;
import com.watermelon.betterreadsdataloader.author.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.watermelon.betterreadsdataloader.connection.DataStaxAstraProperties;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BetterreadsDataLoaderApplication {

    @Autowired
    AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(BetterreadsDataLoaderApplication.class, args);
	}

    @PostConstruct
    public void start() {
        Author author = new Author();
        author.setId("id");
        author.setName("Name");
        author.setPersonalName("personalName");
        authorRepository.save(author);
    }

    @Bean
     public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }

}
