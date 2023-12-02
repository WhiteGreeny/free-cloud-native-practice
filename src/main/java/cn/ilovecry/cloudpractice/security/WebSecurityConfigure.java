package cn.ilovecry.cloudpractice.security;

import cn.ilovecry.cloudpractice.common.cache.CacheService;
import cn.ilovecry.cloudpractice.security.filter.JwtTokenFilter;
import cn.ilovecry.cloudpractice.security.login.simple.LoginFilter;
import cn.ilovecry.cloudpractice.security.login.simple.SimpleLoginProvider;
import cn.ilovecry.cloudpractice.security.login.simple.SimpleLoginSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


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
    private final CacheService cacheService;
    private final ObjectMapper objectMapper;

    public WebSecurityConfigure(CacheService cacheService, ObjectMapper objectMapper) {
        this.cacheService = cacheService;
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = loginFilter();
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/actuator/health", "/demo/hello").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/**", "/login").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationProvider(new SimpleLoginProvider(customUserDetailsService(securityProperties()), bcryptPasswordEncoder()))
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenFilter(cacheService), LoginFilter.class);
        DefaultSecurityFilterChain build = http.build();
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setAuthenticationSuccessHandler(new SimpleLoginSuccessHandler(cacheService, objectMapper));
        return build;
    }

    @Bean
    public UserDetailsService customUserDetailsService(SecurityProperties securityProperties) {
        return new CustomUserDetailsServiceImpl(securityProperties);
    }

    public LoginFilter loginFilter() {
        return new LoginFilter();
    }

    @Bean
    @ConfigurationProperties(prefix = "white.security")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
