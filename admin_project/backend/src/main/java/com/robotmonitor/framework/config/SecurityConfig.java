/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.servlet.Filter
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.http.HttpMethod
 *  org.springframework.security.authentication.AuthenticationManager
 *  org.springframework.security.authentication.AuthenticationProvider
 *  org.springframework.security.authentication.ProviderManager
 *  org.springframework.security.authentication.dao.DaoAuthenticationProvider
 *  org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
 *  org.springframework.security.config.annotation.web.builders.HttpSecurity
 *  org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer$AuthorizedUrl
 *  org.springframework.security.config.http.SessionCreationPolicy
 *  org.springframework.security.core.userdetails.UserDetailsService
 *  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 *  org.springframework.security.crypto.password.PasswordEncoder
 *  org.springframework.security.web.AuthenticationEntryPoint
 *  org.springframework.security.web.SecurityFilterChain
 *  org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 *  org.springframework.security.web.authentication.logout.LogoutFilter
 *  org.springframework.security.web.authentication.logout.LogoutSuccessHandler
 *  org.springframework.security.web.csrf.CookieCsrfTokenRepository
 *  org.springframework.security.web.csrf.CsrfTokenRepository
 *  org.springframework.web.filter.CorsFilter
 */
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

@EnableMethodSecurity(prePostEnabled=true, securedEnabled=true)
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
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder((PasswordEncoder)this.bCryptPasswordEncoder());
        return new ProviderManager(new AuthenticationProvider[]{daoAuthenticationProvider});
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return (SecurityFilterChain)httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers(new String[]{"/rest/**", "/ws/**", "/ai/**", "/tool/**", "/common/**", "/camera/**", "/config/**", "/deepglint/**", "/flight/**", "/food/**", "/monitor/**", "/system/**", "/getInfo", "/error", "/login", "/robotLogin", "/getRouters", "/register", "/captchaImage", "/logout"}).csrfTokenRepository((CsrfTokenRepository)CookieCsrfTokenRepository.withHttpOnlyFalse())).headers(headersCustomizer -> headersCustomizer.cacheControl(cache -> cache.disable()).frameOptions(options -> options.sameOrigin())).exceptionHandling(exception -> exception.authenticationEntryPoint((AuthenticationEntryPoint)this.unauthorizedHandler)).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(requests -> {
            this.permitAllUrl.getUrls().forEach(url -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.requestMatchers(new String[]{url})).permitAll());
            ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)requests.requestMatchers(new String[]{"/login", "/robotLogin", "/register", "/captchaImage"})).permitAll().requestMatchers(HttpMethod.GET, new String[]{"/", "/*.html", "/**.html", "/**.css", "/**.js", "/profile/**"})).permitAll().requestMatchers(new String[]{"/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**", "/rest/**", "/ws/**"})).permitAll().anyRequest()).authenticated();
        }).logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler((LogoutSuccessHandler)this.logoutSuccessHandler)).addFilterBefore((Filter)this.authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class).addFilterBefore((Filter)this.corsFilter, JwtAuthenticationTokenFilter.class).addFilterBefore((Filter)this.corsFilter, LogoutFilter.class).build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
