package com.attractorschool.imurab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final DataSource dataSource;
    private final AuthenticationSuccessHandler successHandler;

    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, DataSource dataSource,
                             @Qualifier("customAuthenticationFailureHandler") AuthenticationFailureHandler authenticationFailureHandler,
                             AuthenticationSuccessHandler successHandler) {
        this.userDetailsService = userDetailsService;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.dataSource = dataSource;
        this.successHandler = successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers( HttpMethod.GET, "/", "/news/**", "/forum/**", "/queue").permitAll()
                .antMatchers( HttpMethod.GET,  "/api/queue/**","/api/news/**", "/api/forum/**", "/api/discussion-topics").permitAll()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/auth/**", "/error", "/forum/discussion-topic/*", "/img/**","/images/**","/projectImages/**").permitAll()
                .antMatchers(HttpMethod.POST, "/**").fullyAuthenticated()
                .antMatchers(HttpMethod.PUT, "/**").fullyAuthenticated()
                .antMatchers(HttpMethod.DELETE, "/**").fullyAuthenticated()

                .antMatchers("/users", "/users/create").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/users/edit/*").hasAnyRole("ADMIN", "OPERATOR", "FARMER")
                .antMatchers("/fields/review").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/queue/review").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/avp/**", "/api/avp/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/department/**", "/api/department/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/field-crops/**", "/api/field-crops/**").hasAnyRole("ADMIN", "OPERATOR")
                .antMatchers("/forum/discussion-topic/create").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/news/avp/create").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/news/create").hasAnyRole("ADMIN", "MODERATOR")
                .antMatchers("/api/queue/create/*").hasAnyRole("FARMER")
                .antMatchers("/api/queue/skip/*").hasAnyRole("FARMER")
                .antMatchers("/fields/**", "/api/fields/**").hasAnyRole("ADMIN", "OPERATOR", "FARMER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/auth/login").permitAll()
                .loginProcessingUrl("/auth/login")
                .successHandler(successHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/auth/login?logout")
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(persistentTokenRepository());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}
