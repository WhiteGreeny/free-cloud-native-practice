package cn.ilovecry.cloudpractice.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

/**
 * CustomUserDetailsService
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/10 16:15
 */
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final SecurityProperties securityProperties;

    public CustomUserDetailsServiceImpl(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        String password = securityProperties.getUserMap().get(username);
        if (StringUtils.hasText(password)) {
            return SecurityUserInfo.builder().username(username).password(password).id(username).build();
        }
        return null;
    }
}
