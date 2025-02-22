package com.example.delivery.runner;

import com.example.delivery.entity.Role;
import com.example.delivery.entity.User;
import com.example.delivery.entity.enums.RoleName;
import com.example.delivery.repo.RoleRepository;
import com.example.delivery.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role role=new Role();
        role.setRoleName(RoleName.ROLE_SUPER_ADMIN);
        Role role1=new Role();
        role1.setRoleName(RoleName.ROLE_ADMIN);
        Role role2=new Role();
        role2.setRoleName(RoleName.ROLE_OPERATOR);
        List<Role> roles=new ArrayList<>(List.of(role,role1,role2));

        roleRepository.saveAll(roles);

        User user = User.builder()
                .email("anvar")
                .password(passwordEncoder.encode("123"))
                .roles(roles)
                .build();
        userRepository.save(user);
    }
}
