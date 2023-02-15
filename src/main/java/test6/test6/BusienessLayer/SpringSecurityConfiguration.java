package test6.test6.BusienessLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
        authenticationManager = authenticationManagerBuilder.build();


        http.csrf().disable().headers().frameOptions().disable().and()
                .authorizeHttpRequests()
                .mvcMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()
                .mvcMatchers(HttpMethod.POST,"/api/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin() // (2)
                .and()
                .httpBasic()
                .and()
                .authenticationManager(authenticationManager);
                return http.build();
    }

}