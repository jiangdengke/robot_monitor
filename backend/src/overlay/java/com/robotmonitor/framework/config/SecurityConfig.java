package com.robotmonitor.framework.config;

import com.robotmonitor.framework.config.properties.PermitAllUrlProperties;
import com.robotmonitor.framework.security.filter.JwtAuthenticationTokenFilter;
import com.robotmonitor.framework.security.handle.AuthenticationEntryPointImpl;
import com.robotmonitor.framework.security.handle.LogoutSuccessHandlerImpl;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.filter.CorsFilter;

@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private PermitAllUrlProperties permitAllUrl;

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(new AuthenticationProvider[] {daoAuthenticationProvider});
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        String[] csrfIgnored = new String[] {
            "/rest/**",
            "/ws/**",
            "/ai/**",
            "/tool/**",
            "/common/**",
            "/camera/**",
            "/config/**",
            "/deepglint/**",
            "/flight/**",
            "/food/**",
            "/monitor/**",
            "/system/**",
            "/DigitalTwin/**",
            "/getInfo",
            "/error",
            "/login",
            "/robotLogin",
            "/getRouters",
            "/register",
            "/captchaImage",
            "/logout"
        };

        return httpSecurity
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(csrfIgnored)
                .csrfTokenRepository((CsrfTokenRepository) CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .headers(headers -> headers.cacheControl(cache -> cache.disable()).frameOptions(options -> options.sameOrigin()))
            .exceptionHandling(exception -> exception.authenticationEntryPoint((AuthenticationEntryPoint) unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(requests -> {
                permitAllUrl.getUrls().forEach(url ->
                    ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl) requests.requestMatchers(new String[] {url})).permitAll()
                );
                ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                    ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                        ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)
                            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl) requests
                                .requestMatchers(new String[] {"/login", "/robotLogin", "/register", "/captchaImage"}))
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, new String[] {"/", "/*.html", "/**.html", "/**.css", "/**.js", "/profile/**"}))
                            .permitAll()
                            .requestMatchers(new String[] {"/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**", "/rest/**", "/ws/**"}))
                        .permitAll()
                        .anyRequest())
                    .authenticated();
            })
            .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler((LogoutSuccessHandler) logoutSuccessHandler))
            .addFilterBefore((Filter) authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore((Filter) corsFilter, JwtAuthenticationTokenFilter.class)
            .addFilterBefore((Filter) corsFilter, LogoutFilter.class)
            .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
