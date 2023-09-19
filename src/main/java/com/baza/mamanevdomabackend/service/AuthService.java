package com.baza.mamanevdomabackend.service;

import com.baza.mamanevdomabackend.dto.LoginDto;
import com.baza.mamanevdomabackend.dto.RegisterDto;
import com.baza.mamanevdomabackend.entity.ConfirmationToken;
import com.baza.mamanevdomabackend.entity.Parent;
import com.baza.mamanevdomabackend.exception.APIException;
import com.baza.mamanevdomabackend.exception.VerifyEmailException;
import com.baza.mamanevdomabackend.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
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
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final ParentService parentService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenRepository tokenRepository;


    public String login(LoginDto loginDto) {
        Parent parent = parentService.findParentByEmail(loginDto.getEmail());

        if (parent.isEnabled()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "User login successfully!";
        }

        throw new VerifyEmailException("Please verify email");
    }

    public String register(RegisterDto registerDto) {
        if (parentService.existsByEmail(registerDto.getEmail())) {
            throw new APIException("Email is already exist!");
        }

        Parent parent = Parent.builder()
                .email(registerDto.getEmail())
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .isEnabled(false)
                .build();

        parentService.create(parent);
        sendEmailToUser(parent);

        return "Verify email by the link sent on your email address";
    }

    public String confirmEmail(String confirmationToken) {
        Optional<ConfirmationToken> tokenOptional = tokenRepository.findByConfirmationToken(confirmationToken);

        if (tokenOptional.isPresent()) {
            Parent parent = parentService.findParentByEmail(tokenOptional.get().getParent().getEmail());
            parent.setEnabled(true);
            parentService.update(parent);

            return "Email verified successfully!";
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

        System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());
    }
}
