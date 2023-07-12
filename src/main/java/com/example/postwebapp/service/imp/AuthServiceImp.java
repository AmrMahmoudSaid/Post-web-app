package com.example.postwebapp.service.imp;

import com.example.postwebapp.dtos.LoginDto;
import com.example.postwebapp.dtos.RegisterDto;
import com.example.postwebapp.entity.Role;
import com.example.postwebapp.entity.User;
import com.example.postwebapp.exception.BlogAPIExeption;
import com.example.postwebapp.repo.RoleRepository;
import com.example.postwebapp.repo.UserRepository;
import com.example.postwebapp.security.JwtTokenProvider;
import com.example.postwebapp.service.AuthService;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImp implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImp(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication =authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail() , loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByUserName(registerDto.getUsername())){
            throw  new BlogAPIExeption("Username is already exists", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(registerDto.getEmail())){
            throw  new BlogAPIExeption("Email is already exists", HttpStatus.BAD_REQUEST);

        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setUserName(registerDto.getUsername());
        user.setName(registerDto.getName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("ROLE_USER").get();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(registerDto.getPassword());
        loginDto.setUsernameOrEmail(registerDto.getEmail());
        return login(loginDto);
    }
}
