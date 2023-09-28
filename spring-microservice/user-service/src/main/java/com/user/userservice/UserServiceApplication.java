package com.user.userservice;

import com.user.userservice.enums.RoleName;
import com.user.userservice.model.Role;
import com.user.userservice.model.User;
import com.user.userservice.repository.RoleRepository;
import com.user.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
//@EnableFeignClients
@EnableDiscoveryClient
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<Role> roleList = new ArrayList<>();
        roleList.add(new Role(1, RoleName.USER));
        roleList.add(new Role(2, RoleName.ADMIN));

        roleRepository.saveAll(roleList);

        List<User> userList = new ArrayList<>();

        userList.add(new User(1, "abc", "abc@gmail.com", "abc def", "abc", new Role(1, RoleName.USER)));

        userRepository.saveAll(userList);
    }
}
