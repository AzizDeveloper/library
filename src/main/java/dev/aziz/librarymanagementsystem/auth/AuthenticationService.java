package dev.aziz.librarymanagementsystem.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aziz.librarymanagementsystem.config.JwtService;
import dev.aziz.librarymanagementsystem.entity.Reader;
import dev.aziz.librarymanagementsystem.entity.Role;
import dev.aziz.librarymanagementsystem.entity.Token;
import dev.aziz.librarymanagementsystem.entity.TokenType;
import dev.aziz.librarymanagementsystem.entity.UserDetailsImpl;
import dev.aziz.librarymanagementsystem.repository.ReaderRepository;
import dev.aziz.librarymanagementsystem.repository.RoleRepository;
import dev.aziz.librarymanagementsystem.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ReaderRepository repository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Role userRole = roleRepository.findByNameEqualsIgnoreCase("USER");

        var reader = Reader.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .username(request.getUsername())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(userRole))
                .build();
        UserDetailsImpl user = new UserDetailsImpl(reader);
        var savedUser = repository.save(reader);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var reader = repository.findByEmailEqualsIgnoreCase(request.getEmail())
                .orElseThrow();
        UserDetailsImpl user = new UserDetailsImpl(reader);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(reader);
        saveUserToken(reader, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(Reader user, String jwtToken) {
        var token = Token.builder()
                .reader(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Reader user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByReader(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var reader = this.repository.findByEmailEqualsIgnoreCase(userEmail)
                    .orElseThrow();
            UserDetailsImpl user = new UserDetailsImpl(reader);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(reader);
                saveUserToken(reader, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

}
