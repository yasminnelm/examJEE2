package com.example.examJEE.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/assign").hasRole("MANAGER")
                        .requestMatchers("/employees","/removeEmployee").hasAnyRole("MANAGER", "TECH_LEAD")
                        .requestMatchers("/api/employees","/api/projects").hasAnyRole("DEV", "TEST", "DEVOPS")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/oauth2/authorization/google")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userAuthoritiesMapper(userAuthoritiesMapper())
                        )
                )
                .logout(logout -> logout
                        .logoutSuccessHandler(oidcLogoutSuccessHandler())
                );

        return http.build();
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler successHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository());
        successHandler.setPostLogoutRedirectUri("http://localhost:8080/");
        return successHandler;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
        return CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId("786548533913-4u5b3fc5jav6e3kmksiqtdr14qo3ku1o.apps.googleusercontent.com")
                .clientSecret("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")
                .build();
    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                    Set<GrantedAuthority> grantedAuthorities = new HashSet<>(AuthorityUtils.createAuthorityList("ROLE_USER"));
                    oidcUserAuthority.getAttributes().forEach((key, value) -> {
                        if ("manager".equals(value)) {
                            grantedAuthorities.add(() -> "ROLE_MANAGER");
                        } else if ("tech_lead".equals(value)) {
                            grantedAuthorities.add(() -> "ROLE_TECH_LEAD");
                        } else if ("dev".equals(value)) {
                        grantedAuthorities.add(() -> "ROLE_DEV");
                    } else if ("test".equals(value)) {
                        grantedAuthorities.add(() -> "ROLE_TEST");
                    } else if ("devops".equals(value)) {
                        grantedAuthorities.add(() -> "ROLE_DEVOPS");
                    }
                    });
                    mappedAuthorities.addAll(grantedAuthorities);
                }
            });
            return mappedAuthorities;
        };
    }
}
