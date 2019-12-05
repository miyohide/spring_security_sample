package com.example.spring_security_sample;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class LoginPrincipal extends User {
    public LoginPrincipal(String username, String password, String[] roles) {
        super(username, password, true, true, true, true, AuthorityUtils.createAuthorityList(roles));
    }

    public static class LoginPrincipalService implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            LoginPrincipal principal = DB.AUTH_TABLE.get(username);
            if (principal == null) {
                throw new UsernameNotFoundException("username not found: " + username);
            }
            return principal;
        }
    }

    private static class DB {
        public static final Map<String, LoginPrincipal> AUTH_TABLE = new HashMap<>();
        static {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            LoginPrincipal[] data = {
                    new LoginPrincipal("User001", passwordEncoder.encode("password"), new String[] { "USER" }),
                    new LoginPrincipal("User002", passwordEncoder.encode("password"), new String[] { "USER" }),
            };
            for (LoginPrincipal d : data) {
                AUTH_TABLE.put(d.getUsername(), d);
            }
        }
    }
}
