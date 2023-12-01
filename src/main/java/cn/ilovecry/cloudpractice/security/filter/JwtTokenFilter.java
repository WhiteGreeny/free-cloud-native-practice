package cn.ilovecry.cloudpractice.security.filter;

import cn.ilovecry.cloudpractice.common.cache.CacheService;
import cn.ilovecry.cloudpractice.security.login.simple.SimpleLoginToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * JwtTokenFilter
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/12/1 16:08
 */
public class JwtTokenFilter extends OncePerRequestFilter {
    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String TYPE = "Bearer";
    private final static String DEFAULT_UNAUTHORIZED_MESSAGE = "登录状态失效";
    private final CacheService cacheService;

    public JwtTokenFilter(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(obtainToken(request))
                .filter(StringUtils::hasText)
                .map(cacheService::get)
                .ifPresent(user -> {
                    Authentication authentication = new SimpleLoginToken(user, null, null);
                    authentication.setAuthenticated(true);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
        filterChain.doFilter(request, response);
    }

    private String obtainToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                .filter(header -> header.startsWith(TYPE))
                .map(header -> header.substring(TYPE.length() + 1))
                .orElse(null);
    }
}
