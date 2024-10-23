package ru.t1.clubcard.authservice;

import org.springframework.boot.SpringApplication;

public class TestClubCardAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ClubCardAuthServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
