package com.works.archisproject.service.impl;

import com.works.archisproject.dto.request.AuthRequest;
import com.works.archisproject.dto.request.CreateUserRequest;
import com.works.archisproject.entity.User;
import com.works.archisproject.exception.UserNotFoundException;
import com.works.archisproject.exception.applicationException.ApplicationNotFoundException;
import com.works.archisproject.exception.emailException.EmailAlreadyRegisteredException;
import com.works.archisproject.exception.emailException.EmailNotFoundException;
import com.works.archisproject.repository.UserRepository;
import com.works.archisproject.util.AppLogger;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AppLogger appLogger;

    public UserService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, JwtService jwtService, AppLogger appLogger) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.appLogger = appLogger;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailEqualsIgnoreCase(email);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public User getByUsername(String email) {
        User user = userRepository.findByEmailEqualsIgnoreCase(email).orElseThrow(() -> {
            appLogger.logError("This email address:" + email + " is not found");
            throw new EntityNotFoundException("This email address:" + email + " is not found");
        });
        return user;
    }

    public User createUser(CreateUserRequest request) {

        User newUser = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .authorities(request.authorities())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .createdTime(LocalDateTime.now())
                .build();
        Optional<User> byEmailEqualsIgnoreCase = userRepository.findByEmailEqualsIgnoreCase(newUser.getEmail());
        if (byEmailEqualsIgnoreCase.isPresent()) {
            appLogger.logError("This email: " + newUser.getEmail() + " is already registered.");
            throw new EmailAlreadyRegisteredException("This email: " + newUser.getEmail() + " is already registered.");
        }else {
            return userRepository.save(newUser);
        }
    }

    @Transactional
    public String generateToken(AuthRequest request) {

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(request.email());
            }else {
                // Yetkilendirme başarısız olduğunda istisna fırlatmamız gerekmeyebilir, belki de null döndürebiliriz.
                return null;
            }
        } catch (AuthenticationException ex) {
            appLogger.logInfo("Authentication failed for email: {}" + request.email());
            throw new EmailNotFoundException("Authentication failed for email: " + request.email());
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String deleteUserById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.deleteById(id);
            appLogger.logInfo("User delete successfully with this id: " + id);
            return "User delete successfully with this id: " + id;
        }else {
            appLogger.logError("User not found with this id: " + id);
            throw new UserNotFoundException("User not found with this id: " + id);
        }
    }

}
