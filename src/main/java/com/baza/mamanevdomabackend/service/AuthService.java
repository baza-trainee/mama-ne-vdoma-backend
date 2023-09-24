package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.entity.ConfirmationToken;
import com.baza.mamanevdomabackend.entity.Parent;
import com.baza.mamanevdomabackend.exception.UserExistException;
import com.baza.mamanevdomabackend.exception.VerifyEmailException;
import com.baza.mamanevdomabackend.payload.request.LoginRequest;
import com.baza.mamanevdomabackend.payload.request.RegisterRequest;
import com.baza.mamanevdomabackend.payload.response.JWTTokenSuccessResponse;
import com.baza.mamanevdomabackend.payload.response.MessageResponse;
import com.baza.mamanevdomabackend.repository.ConfirmationTokenRepository;
import com.baza.mamanevdomabackend.security.JWTTokenProvider;
import com.baza.mamanevdomabackend.security.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final ParentService parentService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository tokenRepository;
    private final JWTTokenProvider jwtTokenProvider;


    public JWTTokenSuccessResponse login(LoginRequest loginRequest) {
        Parent parent = parentService.findParentByEmail(loginRequest.getEmail());

        if (parent.isConfirmedEmail()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
            log.info("logging with [{}]", authentication.getPrincipal());
            return new JWTTokenSuccessResponse(true, jwt);
        }

        throw new VerifyEmailException("Please verify email");
    }

    public MessageResponse register(RegisterRequest registerRequest) {
        if (parentService.existsByEmail(registerRequest.getEmail())) {
            throw new UserExistException("Email is already exist!");
        }

        Parent parent = Parent.builder()
                .email(registerRequest.getEmail())
                .nickname(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .isEnabled(false)
                .build();

        parentService.create(parent);
        sendEmailToUser(parent);

        return new MessageResponse("Verify email by the link sent on your email address");
    }

    public MessageResponse confirmEmail(String confirmationToken) {
        Optional<ConfirmationToken> tokenOptional = tokenRepository.findByConfirmationToken(confirmationToken);

        if (tokenOptional.isPresent()) {
            Parent parent = parentService.findParentByEmail(tokenOptional.get().getParent().getEmail());
            parentService.updateIsEnabledByParentEmail(true, parent.getEmail());

            return new MessageResponse("Email verified successfully!");
        }

        throw new VerifyEmailException("Error: Couldn't verify email");
    }

    private void sendEmailToUser(Parent parent) {
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .parent(parent)
                .createdDate(Date.valueOf(LocalDate.now()))
                .confirmationToken(UUID.randomUUID().toString())
                .build();

        tokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(parent.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/api/auth/confirm-account?token=" + confirmationToken.getConfirmationToken());
        new Thread(() -> emailService.sendEmail(mailMessage)).start();

        log.debug("Confirmation token: {}", confirmationToken.getConfirmationToken());
    }
}
