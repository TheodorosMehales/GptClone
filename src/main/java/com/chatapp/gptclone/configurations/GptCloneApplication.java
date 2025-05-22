package com.chatapp.gptclone.configurations;

import com.chatapp.gptclone.controllers.UsersController;
import com.chatapp.gptclone.exceptions.GlobalExceptionHandler;
import com.chatapp.gptclone.model.User;
import com.chatapp.gptclone.repositories.UsersRepository;
import com.chatapp.gptclone.services.UsersService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        UsersController.class,
        UsersService.class,
        GlobalExceptionHandler.class,
        GptCloneApplication.class,
})
@EnableJpaRepositories(basePackageClasses = {
        UsersRepository.class
})
@EntityScan(basePackageClasses = {
        User.class
})
public class GptCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(GptCloneApplication.class, args);
    }

}
