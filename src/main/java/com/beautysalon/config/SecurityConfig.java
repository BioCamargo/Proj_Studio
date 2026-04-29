package com.beautysalon.config;

import com.beautysalon.Implementacao.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

 /*   @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth //"/home/login"
                        .requestMatchers( "/","home/", "/login", "/register"
                                , "/css/**","/api/**",
                                "/uploads/**","/js/**", "/images/**" ,"/favicon.ico"
                                 , "/roles/**", "/usuarios/**").permitAll()
                        .requestMatchers("/usuarios/**").authenticated()
                        .requestMatchers("/clientes/**").authenticated()
                        .requestMatchers("/agendamentos/**").authenticated()
                        .requestMatchers("/servicos/**").authenticated()

                        .requestMatchers("/roles/**").hasRole("ADMIN") // era so admin
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home") //true removed, antes era /client
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                ).csrf(csrf-> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/api**"))
                .headers(headers-> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                );

                //.csrf(csrf -> csrf.disable());// Desabilite CSRF temporariamente para testes

        return http.build();
    }*/

    @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                // 1. RECURSOS ESTÁTICOS E PÁGINAS PÚBLICAS
                .requestMatchers(
                        "/", 
                        "/login", 
                        "/register", 
                        "/css/**", 
                        "/js/**", 
                        "/images/**", 
                        "/uploads/**", 
                        "/favicon.ico"
                ).permitAll()
                
                // 2. REGRAS ESPECÍFICAS (Sempre do mais restrito para o mais genérico)
                .requestMatchers("/roles/**").hasRole("ADMIN")
                .requestMatchers("/api/**").permitAll() // Ou configure conforme sua necessidade
                
                // 3. PÁGINAS QUE EXIGEM LOGIN
                .requestMatchers(
                        "/home/**", 
                        "/usuarios/**", 
                        "/clientes/**", 
                        "/agendamentos/**", 
                        "/servicos/**"
                ).authenticated()

                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true) 
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                )
                .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers("/api/**", "/h2-console/**")
                )
                .headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                );

        return http.build();
        }




    //@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers("/css/**", "/js/**", "/images/**", "/uploads/**", "/favicon.ico");
    }
}
