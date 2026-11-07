package mundia.amine.pratique_3_app_web_jee;

import mundia.amine.pratique_3_app_web_jee.entities.Product;
import mundia.amine.pratique_3_app_web_jee.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Pratique3AppWebJeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pratique3AppWebJeeApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder()
                    .name("Computer")
                    .price(3500)
                    .quantity(5)
                    .build());
            productRepository.save(Product.builder()
                    .name("Printer")
                    .price(3000)
                    .quantity(10)
                    .build());
            productRepository.save(Product.builder()
                    .name("Screen")
                    .price(2500)
                    .quantity(16)
                    .build());

            productRepository.findAll().forEach(p-> {
                System.out.println(p.toString());
            });
        };
    }
}
