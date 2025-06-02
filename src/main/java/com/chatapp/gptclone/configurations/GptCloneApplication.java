package com.chatapp.gptclone.configurations;

import com.chatapp.gptclone.controllers.MessagesController;
import com.chatapp.gptclone.controllers.ThreadsController;
import com.chatapp.gptclone.controllers.UsersController;
import com.chatapp.gptclone.exceptions.GlobalExceptionHandler;
import com.chatapp.gptclone.model.User;
import com.chatapp.gptclone.repositories.UsersRepository;
import com.chatapp.gptclone.services.MessagesService;
import com.chatapp.gptclone.services.ThreadsService;
import com.chatapp.gptclone.services.UsersService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = "com.chatapp.gptclone"
)
@ComponentScan(basePackageClasses = {
        UsersController.class,
        UsersService.class,
        ThreadsController.class,
        ThreadsService.class,
        MessagesController.class,
        MessagesService.class,
        GlobalExceptionHandler.class,
        GptCloneApplication.class,
})
@EnableJpaRepositories(
        basePackages = "com.chatapp.gptclone.repositories"
)
@EntityScan(basePackages = "com.chatapp.gptclone.model"
)
public class GptCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(GptCloneApplication.class, args);
    }

}
