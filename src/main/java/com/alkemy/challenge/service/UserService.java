package com.alkemy.challenge.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.challenge.dto.user.AuthenticationDto;
import com.alkemy.challenge.dto.user.AuthenticationResponseDto;
import com.alkemy.challenge.dto.user.RegisterDto;
import com.alkemy.challenge.dto.user.UserUpdateDto;
import com.alkemy.challenge.exception.EmailAlreadyInUseException;
import com.alkemy.challenge.exception.NotFoundException;
import com.alkemy.challenge.mapper.GenericModelMapper;
import com.alkemy.challenge.models.Role;
import com.alkemy.challenge.models.User;
import com.alkemy.challenge.respository.UserRepository;
import com.alkemy.challenge.security.jwt.CustomAuthenticatorManager;
import com.alkemy.challenge.security.jwt.CustomUserDetailsService;
import com.alkemy.challenge.security.jwt.JwtUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements Serializable {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final GenericModelMapper mapper;
    private final CustomAuthenticatorManager authenticatorManager;
	private final JwtUtils jwtUtils;
	private final CustomUserDetailsService userDetailsService;

    @Transactional
    public User create(RegisterDto dto) {
        checkEmailAvailability(dto.getEmail());
        User user = mapper.map(dto, User.class);

        user.setPassword(encoder.encode(dto.getPassword()));
        user.setDeleted(false);

        Role userRole;

        if (getAllUsers().isEmpty() && roleService.getAll().size() < 1) {
            userRole = roleService.createFromUser("ADMIN");
        } else if (roleService.getAll().size() == 1){
            userRole = roleService.createFromUser("USER");
        } else {
            userRole = roleService.getById(2L);
        }
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));

        return userRepository.save(user);
    }

    @Transactional
    public User update(UserUpdateDto dto, Long id) {
        User user = userRepository.findById(id).get();

        user.setName(dto.getNewName());
        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return user;
    }

    @Transactional
    public Boolean detele(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    private void checkEmailAvailability(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyInUseException(email + " is already in use by another user");
        }
    }

    public AuthenticationResponseDto authenticate(AuthenticationDto dto){
        final Authentication authentication = authenticatorManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));

        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated()) {

            SecurityContextHolder.getContext().setAuthentication(authentication);

			final UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getEmail());
			final String jwt = jwtUtils.generateToken(userDetails);

            return new AuthenticationResponseDto(jwt);
        } else{
            throw new NotFoundException("User not found, please check the data entered");
        }
    }

}
