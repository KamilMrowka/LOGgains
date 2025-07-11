package com.log.gains.auth;

import com.log.gains.exception.*;
import com.log.gains.jwt.JwtService;
import com.log.gains.user.Role;
import com.log.gains.user.User;
import com.log.gains.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register (RegistrationRequest request) {

        validateEmail(request.getEmail());
        validatePassword(request.getPassword());
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Account with such email is already registered");
        }
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken");
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
        boolean existsUser = repository.findByUsername(request.getUsername()).isPresent();
        if (!existsUser) {
            throw new UserNotFoundException("Username not registered");
        }
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

    public void validateEmail (String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new EmailNotValidException("Not a valid email");
        }
    }

    public void validatePassword(String password) {
        //At least 6 characters, at least 1 capital letter, at least one lowercase letter, at least one number
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new PasswordNotValidException("Not a valid password");
        }
    }
}
