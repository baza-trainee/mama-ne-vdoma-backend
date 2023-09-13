package com.baza.mamanevdomabackend.security;

import com.baza.mamanevdomabackend.service.ParentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppUserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private Map<String, AppUser> users = new HashMap<>();

    private final ParentService parentService;

    @Autowired
    public AppUserService(PasswordEncoder passwordEncoder, ParentService parentService) {
        this.passwordEncoder = passwordEncoder;
        this.parentService = parentService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.get(username);
    }

    @PostConstruct
    public void createHardcodedUsers() {
        var bob = AppUser.builder()
                .username("bil")
                .password(passwordEncoder.encode("321"))
                .authorities(List.of(new SimpleGrantedAuthority("read")))
                .build();
        createUser(bob);
    }

    private void createUser(AppUser user) {
        users.putIfAbsent(user.getName(), user);
    }

    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler() {
        return userRequest -> {
            LoginProvider provider = getProvider(userRequest);

            DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            return AppUser
                    .builder()
                    .username(oAuth2User.getAttribute("login"))
                    .name(oAuth2User.getAttribute("login"))
                    .userId(oAuth2User.getName())
                    .imageURL(oAuth2User.getAttribute("avatar_url"))
                    .provider(provider)
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .attributes(oAuth2User.getAttributes())
                    .authorities(oAuth2User.getAuthorities())
                    .build();
        };
    }

    private LoginProvider getProvider(OAuth2UserRequest userRequest) {
        return LoginProvider.valueOf(
                userRequest.getClientRegistration().getRegistrationId().toUpperCase());
    }

    public OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler() {
        return userRequest -> {
            LoginProvider provider = getProvider(userRequest);

            OidcUserService delegate = new OidcUserService();
            OidcUser oidcUser = delegate.loadUser(userRequest);

            return AppUser
                    .builder()
                    .provider(provider)
                    .username(oidcUser.getEmail())
                    .name(oidcUser.getFullName())
                    .email(oidcUser.getEmail())
                    .userId(oidcUser.getName())
                    .imageURL(oidcUser.getPicture())
                    .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                    .attributes(oidcUser.getAttributes())
                    .authorities(oidcUser.getAuthorities())
                    .build();
        };
    }
}
