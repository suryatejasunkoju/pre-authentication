package com.roytuts.preauthentication1.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userDao.getUser(username);
//        if (user == null) {// should have proper handling of Exception
//            throw new UsernameNotFoundException("User '" + username + "' not found.");
//        }

        //username variable value is the value of attribute which is set as
        // setPrincipalRequestHeader in RequestHeaderAuthenticationFilter bean.
        System.out.println("-------------");
        System.out.println("username="+username);


        GrantedAuthority grantedAuthority1 = new SimpleGrantedAuthority("ROLE_USER");
//        GrantedAuthority grantedAuthority2 = new SimpleGrantedAuthority("ROLE_ADMIN");
        UserDetails details1 = new org.springframework.security.core.userdetails.User(
                "surya",
                "password",
                Arrays.asList(grantedAuthority1)
        );
        return details1;
    }
}