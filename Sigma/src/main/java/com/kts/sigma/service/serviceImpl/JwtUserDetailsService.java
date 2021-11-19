package com.kts.sigma.service.serviceImpl;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO drugacije ce izgledati ako budemo hteli da imamo vise menadzera
        if ("admin".equals(username)) {
            return new User("admin", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>()); //sifra je "password"
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
