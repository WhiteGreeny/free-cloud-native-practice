package cn.ilovecry.cloudpractice.security.login.simple;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * SimpleLoginToken
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/7/10 17:47
 */
public class SimpleLoginToken extends AbstractAuthenticationToken {
    private Object principal;
    private Object credentials;
    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public SimpleLoginToken(Object principal,String password,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = password;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
