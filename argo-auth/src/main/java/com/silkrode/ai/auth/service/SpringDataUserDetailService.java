package com.silkrode.ai.auth.service;

import com.silkrode.ai.auth.dto.UserDto;
import com.silkrode.ai.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Rewrite UserDetailsService using UserRepository.
 */
@Service
@Slf4j
public class SpringDataUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userRepository.findByUsername(username);
        Assert.notNull(user, "user not found.");
        String role[] = user.getAuthorities().split(",");
        List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList(role);
        log.info(roles.toString());
        return new User(user.getUsername(), user.getPassword(), roles);
    }
}
