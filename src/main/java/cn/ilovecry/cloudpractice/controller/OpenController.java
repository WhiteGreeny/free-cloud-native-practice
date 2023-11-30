package cn.ilovecry.cloudpractice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoController
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/10 15:31
 */
@RestController
public class OpenController {
    @GetMapping("/open/hello")
    public String hello(HttpServletRequest request){
        request.getSession().setAttribute("sth","max");
        return "hello";
    }
    @GetMapping("/open/session")
    public String sessionTest(HttpServletRequest request){
        String sth=(String)request.getSession().getAttribute("sth");
        return sth;
    }
    @GetMapping("/hello")
    public String hello2(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "say hi";
    }
}
