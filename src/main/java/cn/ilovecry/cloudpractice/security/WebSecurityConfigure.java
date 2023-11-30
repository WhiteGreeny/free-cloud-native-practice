package cn.ilovecry.cloudpractice.security;

import cn.ilovecry.cloudpractice.security.login.simple.LoginFilter;
import cn.ilovecry.cloudpractice.security.login.simple.SimpleLoginProvider;
import cn.ilovecry.cloudpractice.security.login.simple.SimpleLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;


/**
 * WebSecurityConfigure
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/10 15:05
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigure {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = loginFilter();
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/actuator/health", "/demo/hello","/open/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(new SimpleLoginProvider(customUserDetailsService()))
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);
        DefaultSecurityFilterChain build = http.build();
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setAuthenticationSuccessHandler(new SimpleLoginSuccessHandler());
        return build;
    }

    @Bean
    public UserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    public LoginFilter loginFilter() {
        return new LoginFilter();
    }
}
