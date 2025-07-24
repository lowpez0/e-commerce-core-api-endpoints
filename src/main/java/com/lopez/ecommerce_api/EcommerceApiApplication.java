package com.lopez.ecommerce_api;

import com.lopez.ecommerce_api.dto.RequestProduct;
import com.lopez.ecommerce_api.enums.CartItemStatus;
import com.lopez.ecommerce_api.model.CartItem;
import com.lopez.ecommerce_api.model.Role;
import com.lopez.ecommerce_api.model.User;
import com.lopez.ecommerce_api.repository.CartItemRepository;
import com.lopez.ecommerce_api.service.AuthService;
import com.lopez.ecommerce_api.service.cart_service.CartService;
import com.lopez.ecommerce_api.service.user_service.UserService;
import com.lopez.ecommerce_api.service.product_service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableRetry
public class EcommerceApiApplication {

	private PasswordEncoder encoder;

	@Autowired
    public EcommerceApiApplication(PasswordEncoder encoder) {
        this.encoder = encoder;
		System.out.println("SKIBIDI\n\n\n\n\n\n\n\n\n\n");
    }

    public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserService userService,
										ProductService productService,
										AuthService authService,
										CartService cartService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_CUSTOMER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));


			userService.saveUser(new User(null, "dhan paul", "dhan", "dhan@gmail.com", encoder.encode("1234"), new ArrayList<>()));
			userService.saveUser(new User(null, "mark james", "james", "james@gmail.com", encoder.encode("1234"), new ArrayList<>()));
			userService.saveUser(new User(null, "lyric ace", "lyric", "lyric@gmail.com", encoder.encode("1234"), new ArrayList<>()));
			userService.saveUser(new User(null, "ace portgas", "ace", "ace@gmail.com", encoder.encode("1234"), new ArrayList<>()));

			cartService.addCartToUser("dhan");
			cartService.addCartToUser("james");
			cartService.addCartToUser("lyric");
			cartService.addCartToUser("ace");

			userService.addRoleToUser("dhan", "ROLE_ADMIN");
			userService.addRoleToUser("james", "ROLE_CUSTOMER");
			userService.addRoleToUser("lyric", "ROLE_CUSTOMER");
			userService.addRoleToUser("ace", "ROLE_CUSTOMER");

			productService.saveProduct(new RequestProduct("Galaxy S25", "6.8\" AMOLED, 200MP camera", 1199.99, 15, "MOBILE"));
			productService.saveProduct(new RequestProduct("iPhone 16 Pro", "A18 chip, titanium design", 1299.00, 10, "MOBILE"));
			productService.saveProduct(new RequestProduct("Pixel 8 Pro", "Google Tensor G3, AI features", 999.00, 8, "MOBILE"));

			productService.saveProduct(new RequestProduct("LG OLED C3", "65\" 4K Smart TV, 120Hz", 1799.50, 5, "TV"));
			productService.saveProduct(new RequestProduct("Samsung QN90B", "Neo QLED 55\" with HDR", 1299.99, 7, "TV"));
			productService.saveProduct(new RequestProduct("Sony Bravia XR", "83\" 8K Mini-LED", 4999.00, 3, "TV"));

			productService.saveProduct(new RequestProduct("Dell UltraSharp 32", "4K USB-C monitor", 899.99, 12, "MONITOR"));
			productService.saveProduct(new RequestProduct("LG UltraGear 27", "240Hz gaming monitor", 649.50, 9, "MONITOR"));
			productService.saveProduct(new RequestProduct("Apple Studio Display", "5K Retina with camera", 1599.00, 6, "MONITOR"));

			productService.saveProduct(new RequestProduct("AirPods Pro 2", "Active noise cancellation", 249.00, 25, "ACCESSORIES"));
			productService.saveProduct(new RequestProduct("Logitech MX Keys", "Wireless ergonomic keyboard", 99.95, 18, "ACCESSORIES"));
			productService.saveProduct(new RequestProduct("Samsung T7 SSD", "1TB portable SSD", 89.99, 30, "ACCESSORIES"));

		};
	}

}
