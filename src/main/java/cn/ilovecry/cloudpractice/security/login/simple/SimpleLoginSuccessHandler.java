package cn.ilovecry.cloudpractice.security.login.simple;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * SimpleLoginSuccessHandler
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/11 9:22
 */
public class SimpleLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getSession().setAttribute("login","success");
        response.getWriter().write("login success");
    }
}
