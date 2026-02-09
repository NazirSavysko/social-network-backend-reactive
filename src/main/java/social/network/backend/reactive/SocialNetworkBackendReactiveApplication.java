package social.network.backend.reactive;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class SocialNetworkBackendReactiveApplication {

    public static void main(String[] args) {
        run(SocialNetworkBackendReactiveApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
