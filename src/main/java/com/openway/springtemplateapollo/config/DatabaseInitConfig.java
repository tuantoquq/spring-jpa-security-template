package com.openway.springtemplateapollo.config;

import com.openway.springtemplateapollo.payload.request.AddUserRequest;
import com.openway.springtemplateapollo.service.RoleService;
import com.openway.springtemplateapollo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitConfig {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Bean
    CommandLineRunner initDatabase(){
        return args -> {
            //load sample role data
            roleService.addRole("user");
            roleService.addRole("admin");

            //load sample user data
            AddUserRequest sample1 = new AddUserRequest("tuantoquq@gmail.com","admin123","Vinh Phuc","male");
            AddUserRequest sample2 = new AddUserRequest("tuannha@gmail.com","admin123","Ha Noi","female");
            userService.addUser(sample1);
            userService.addUser(sample2);
        };
    }
}
