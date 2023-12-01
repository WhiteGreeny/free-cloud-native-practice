package cn.ilovecry.cloudpractice.security.login.simple;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SimpleLoginProvider
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/10 17:51
 */
public class SimpleLoginProvider implements AuthenticationProvider {
    private final UserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SimpleLoginProvider(UserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String password = (String) authentication.getCredentials();
        String username = (String) authentication.getPrincipal();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        SimpleLoginToken simpleLoginToken = new SimpleLoginToken(userDetails, null, userDetails.getAuthorities());
        simpleLoginToken.setDetails(authentication.getDetails());
        simpleLoginToken.setAuthenticated(true);
        return simpleLoginToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SimpleLoginToken.class.isAssignableFrom(authentication);
    }
}
