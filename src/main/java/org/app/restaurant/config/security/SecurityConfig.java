package org.app.restaurant.config.security;

import org.app.restaurant.constatnts.RoleType;
import org.app.restaurant.service.CustomUserDetailsServices;
import org.app.restaurant.utils.AESPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] PUBLIC_END_POINTS = {
            "/menu/fetch-menus",
            "/h2-console/**",
            "/auth/authenticate",
            "/user/new-user",
            "/user/new-users",
            "/taxes/fetch-taxes",
            "/coupons/fetch-coupons"
    };

    private static final String[] ADMIN_END_POINTS = {
            "/coupons/remove-coupon/**",
            "/menu/delete-menu/**",
            "/orders/cancel-order/**",
            "/taxes/remove-tax/**",
            "/user/delete-user/**",
    };

    private static final String[] USER_END_POINTS = {
            "/coupons/**",
            "/menu/**",
            "/orders/**",
            "/taxes/**",
            "/user/**",
    };


    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomUserDetailsServices customUserDetailsServices;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request-> request
                        .requestMatchers(PUBLIC_END_POINTS).permitAll()
                        .requestMatchers(ADMIN_END_POINTS).hasAuthority(RoleType.ADMIN.getRoleName())
                        .requestMatchers(USER_END_POINTS).hasAnyAuthority(RoleType.ADMIN.getRoleName(), RoleType.USER.getRoleName())
                        .anyRequest().authenticated())
                .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );
        //httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request-> request.anyRequest().permitAll());
        return httpSecurity.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsServices);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new AESPasswordEncoder(); // Use the custom AES password encoder
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
