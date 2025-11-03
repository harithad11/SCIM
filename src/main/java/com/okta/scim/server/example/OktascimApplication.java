
package com.okta.scim.server.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.okta.scim.server.example.entity.UserEntity;
import com.okta.scim.server.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OktascimApplication {

    public static void main(String[] args) {
        SpringApplication.run(OktascimApplication.class, args);
    }
}
