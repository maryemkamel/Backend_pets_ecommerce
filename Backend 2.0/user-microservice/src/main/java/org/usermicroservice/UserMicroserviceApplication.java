package org.usermicroservice;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.usermicroservice.entities.Role;
import org.usermicroservice.entities.User;
import org.usermicroservice.enums.Active;
import org.usermicroservice.enums.ERole;
import org.usermicroservice.repositories.RoleRepository;
import org.usermicroservice.repositories.UserRepository;
import org.usermicroservice.services.IUserService;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class UserMicroserviceApplication {
    private final IUserService userService;
    private final RoleRepository roleRepository;
    public static void main(String[] args) {
        SpringApplication.run(UserMicroserviceApplication.class, args);
    }

   /* @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            Role userRole = new Role(ERole.USER);
            Role adminRole = new Role(ERole.ADMIN);
            roleRepository.saveAll(List.of(adminRole,userRole));
            User toUser = User.builder()
                    .firstname("Mouad")
                    .lastname("Feth")
                    .email("emailmicroservice1994@gmail.com")
                    .numberPhone("0621403650")
                    .password("Supersasuke123")
                    .isActive(Active.ACTIVE)
                    .build();

            User userOussama = User.builder()
                    .firstname("Khalid")
                    .lastname("Taouti")
                    .email("ecommercemicroservice2024@gmail.com")
                    .numberPhone("0621403650")
                    .password("qwerty123")
                    .isActive(Active.ACTIVE)
                    .build();


            //userService.registerUser(toUser);
            //userService.registerUser(userOussama);
            //userService.addRoleToUserByEmail(ERole.ADMIN,"emailmicroservice1994@gmail.com");
            User user1 = User.builder()
                    .firstname("John")
                    .lastname("Doe")
                    .email("john.doe@example.com")
                    .numberPhone("0612345678")
                    .password("password123")
                    .isActive(Active.ACTIVE)
                    .build();

            User user2 = User.builder()
                    .firstname("Alice")
                    .lastname("Smith")
                    .email("alice.smith@example.com")
                    .numberPhone("0698765432")
                    .password("password456")
                    .isActive(Active.ACTIVE)
                    .build();

            User user3 = User.builder()
                    .firstname("Bob")
                    .lastname("Johnson")
                    .email("bob.johnson@example.com")
                    .numberPhone("0643216789")
                    .password("password789")
                    .isActive(Active.ACTIVE)
                    .build();

            User user4 = User.builder()
                    .firstname("Emily")
                    .lastname("Davis")
                    .email("emily.davis@example.com")
                    .numberPhone("0678941236")
                    .password("passwordabc")
                    .isActive(Active.ACTIVE)
                    .build();

            User user5 = User.builder()
                    .firstname("Michael")
                    .lastname("Wilson")
                    .email("michael.wilson@example.com")
                    .numberPhone("0612378456")
                    .password("passwordxyz")
                    .isActive(Active.ACTIVE)
                    .build();

           // userService.registerUser(user1);
            //userService.registerUser(user2);
            //userService.registerUser(user3);
            //userService.registerUser(user4);
            //userService.registerUser(user5);


        };

    }

    */


}
