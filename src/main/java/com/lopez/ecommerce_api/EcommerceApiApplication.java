package com.lopez.ecommerce_api;

import com.lopez.ecommerce_api.model.Role;
import com.lopez.ecommerce_api.model.User;
import com.lopez.ecommerce_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@RequiredArgsConstructor
public class EcommerceApiApplication {

	private final PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_CUSTOMER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(new User(null, "dhan paul", "dhan", "dhan@gmail.com", encoder.encode("1234"), new ArrayList<>()));
			userService.saveUser(new User(null, "mark james", "james", "james@gmail.com", encoder.encode("1234"), new ArrayList<>()));
			userService.saveUser(new User(null, "lyric ace", "lyric", "lyric@gmail.com", encoder.encode("1234"), new ArrayList<>()));
			userService.saveUser(new User(null, "ace portgas", "ace", "ace@gmail.com", encoder.encode("1234"), new ArrayList<>()));

			userService.addRoleToUser("dhan", "ROLE_ADMIN");
			userService.addRoleToUser("james", "ROLE_CUSTOMER");
			userService.addRoleToUser("lyric", "ROLE_CUSTOMER");
			userService.addRoleToUser("ace", "ROLE_CUSTOMER");
			userService.addRoleToUser("ace", "ROLE_CUSTOMER");
		};
	}

}
