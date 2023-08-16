package com.task.Bank.Verification.Number.Application.security;

import com.task.Bank.Verification.Number.Application.model.User;
import com.task.Bank.Verification.Number.Application.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).get();
        if(user == null){
            throw new UsernameNotFoundException("User not found",null);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }


//    private Collection<? extends GrantedAuthority> getAuthorities(String usernameOrEmail) {
////        return roles.stream().map(
////                role -> new SimpleGrantedAuthority(role.getRoleType().name())
////        ).collect(Collectors.toSet());
//
//        return new SimpleGrantedAuthority()
//
//    }
}
