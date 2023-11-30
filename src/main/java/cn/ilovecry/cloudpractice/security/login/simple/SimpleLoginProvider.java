package cn.ilovecry.cloudpractice.security.login.simple;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * SimpleLoginProvider
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/10 17:51
 */
public class SimpleLoginProvider implements AuthenticationProvider {
    private final UserDetailsService customUserDetailsService;

    public SimpleLoginProvider(UserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String password=(String)authentication.getCredentials();
        String username=(String)authentication.getPrincipal();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        SimpleLoginToken simpleLoginToken = new SimpleLoginToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        simpleLoginToken.setDetails(authentication.getDetails());
        simpleLoginToken.setAuthenticated(true);
        return simpleLoginToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SimpleLoginToken.class.isAssignableFrom(authentication);
    }
}
