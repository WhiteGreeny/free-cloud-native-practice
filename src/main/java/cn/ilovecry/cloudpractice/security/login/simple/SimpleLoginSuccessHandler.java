package cn.ilovecry.cloudpractice.security.login.simple;

import cn.ilovecry.cloudpractice.common.R;
import cn.ilovecry.cloudpractice.common.cache.CacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * SimpleLoginSuccessHandler
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/11 9:22
 */
public class SimpleLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final CacheService cacheService;
    private final ObjectMapper objectMapper;

    public SimpleLoginSuccessHandler(CacheService cacheService, ObjectMapper objectMapper) {
        this.cacheService = cacheService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String token = UUID.randomUUID().toString();
        cacheService.set(token, authentication.getPrincipal(),30, TimeUnit.MINUTES);
        response.getWriter().write(objectMapper.writeValueAsString(R.success(token)));
    }
}
