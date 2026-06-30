package dev.aziz.librarymanagementsystem.config;

import dev.aziz.librarymanagementsystem.entity.Reader;
import dev.aziz.librarymanagementsystem.entity.UserDetailsImpl;
import dev.aziz.librarymanagementsystem.exception.ReaderNotFoundException;
import dev.aziz.librarymanagementsystem.repository.ReaderRepository;
import dev.aziz.librarymanagementsystem.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ReaderRepository repository;
    private final CustomUserDetailsService customUserDetailsService;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return email -> {
//            Reader reader = repository.findByEmailEqualsIgnoreCase(email)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//            return new UserDetailsImpl(reader);
//        };
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(customUserDetailsService);
//        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

/*    @Bean
    public AuditorAware<Integer> auditorAware() {
        return new ApplicationAuditAware();
    }*/

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}