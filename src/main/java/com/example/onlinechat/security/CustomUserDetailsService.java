package com.example.onlinechat.security;

import com.example.onlinechat.dto.UserDTO;
import com.example.onlinechat.exception.business.UserNotFoundException;
import com.example.onlinechat.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private PasswordEncoder encoder;
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDTO userDTO = userService.findById(username);

            return User.builder()
                    .username(username)
                    .password(userDTO.getPassword())
                    .build();

        } catch (UserNotFoundException userNotExistException) {
            throw new UsernameNotFoundException(username + " not found");
        }
    }

}
