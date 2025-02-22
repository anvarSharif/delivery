package com.example.delivery.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final MyFilter myFilter;

    public SecurityConfig( MyFilter myFilter) {
        this.myFilter = myFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,CustomUserDetailService customUserDetailService) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(req ->
                req
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/super-admin/**").hasRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.POST,"/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/operator/**").hasRole("OPERATOR")
                        .anyRequest()
                        .authenticated());

        http.formLogin((manager) -> {
            manager.loginPage("/login").usernameParameter("email").defaultSuccessUrl("/");
        });
        http.logout((manager)->{
            manager.logoutUrl("/logout").logoutSuccessUrl("/");
        });


        http.userDetailsService(customUserDetailService);
        http.addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailService customUserDetailService){
        var daoAuthenticationProvider=new DaoAuthenticationProvider(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        return daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailService customUserDetailService){
        return new ProviderManager(authenticationProvider(customUserDetailService ));
    }

}
