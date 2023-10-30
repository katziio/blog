package com.mountblue.blog.config;

import com.mountblue.blog.Util.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Security {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/post/add/**")
                        .hasAnyAuthority(String.valueOf(Role.AUTHOR),String.valueOf(Role.ADMIN))
                        .requestMatchers("post/delete/**")
                        .hasAnyAuthority(String.valueOf(Role.AUTHOR),String.valueOf(Role.ADMIN))
                        .requestMatchers("/comment/create").permitAll()
                        .requestMatchers("/comment/delete").hasAnyAuthority(String.valueOf(Role.AUTHOR),String.valueOf(Role.ADMIN))
                        .requestMatchers("/user/**").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
