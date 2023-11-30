package cn.ilovecry.cloudpractice.security.login.simple;

import cn.ilovecry.cloudpractice.security.LoginDTO;
import cn.ilovecry.cloudpractice.util.SerializeUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

/**
 * LoginFilter
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/10 16:21
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/auth/login",
            "POST");

    public LoginFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public LoginFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        LoginDTO loginDTO = SerializeUtil.getObjectMapper().readValue(request.getInputStream().readAllBytes(), LoginDTO.class);
        SimpleLoginToken usernamePasswordAuthenticationToken = new SimpleLoginToken(loginDTO.getUsername(), loginDTO.getPassword(),null);
        this.setDetails(request, usernamePasswordAuthenticationToken);
        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
    }

    protected void setDetails(HttpServletRequest request, SimpleLoginToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
