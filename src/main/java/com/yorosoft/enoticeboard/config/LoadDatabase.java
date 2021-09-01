package com.yorosoft.enoticeboard.config;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.yorosoft.enoticeboard.dto.AuthorDTO;
import com.yorosoft.enoticeboard.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class LoadDatabase {

    private final AuthorService authorService;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            generateRandomAuthorDTOs().forEach(authorService::save);
        };
    }

    private List<AuthorDTO> generateRandomAuthorDTOs() {
        List<AuthorDTO> authorDTOs = new ArrayList<AuthorDTO>();
        Faker faker = null;
        Name name = null;
        for (int i = 0; i < 100; i++) {
            faker = Faker.instance();
            name = faker.name();

            AuthorDTO authorDTO = AuthorDTO.builder()
                    .firstName(name.firstName())
                    .lastName(name.lastName())
                    .build();

            authorDTOs.add(authorDTO);
        }
        return authorDTOs;
    }
}
