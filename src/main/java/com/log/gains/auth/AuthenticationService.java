package com.log.gains.auth;

import com.log.gains.jwt.JwtService;
import com.log.gains.exception.EmailAlreadyExistsException;
import com.log.gains.exception.UsernameAlreadyTakenException;
import com.log.gains.exception.UsernameEmailException;
import com.log.gains.user.Role;
import com.log.gains.user.User;
import com.log.gains.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register (RegistrationRequest request) {

        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Account with such email is already registered");
        }
        if (repository.findByUsername(request.getEmail()).isPresent()) {
            throw new UsernameEmailException("Cannot use this email as it has already been set as an username before");
        }
        if (repository.findByEmail(request.getUsername()).isPresent()) {
            throw new UsernameEmailException("Cannot use this username as it has already been set as an email before");
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse login (LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = repository.findByUsername(request.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
