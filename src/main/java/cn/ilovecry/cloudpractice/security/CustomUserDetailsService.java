package cn.ilovecry.cloudpractice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * CustomUserDetailsService
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/10 16:15
 */
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return SecurityUserInfo.builder().username(username).id("1").build();
    }
}
