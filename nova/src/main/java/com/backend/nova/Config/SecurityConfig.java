package com.backend.nova.Config;

import com.backend.nova.Config.Security.JwtAccessDenied;
import com.backend.nova.Config.Security.JwtAuthenticationFilter;
import com.backend.nova.Config.Security.JwtEntryPoint;
import com.backend.nova.Service.UsuarioService.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;



import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig
{
    @Autowired
    private IUsuarioService userService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtEntryPoint unauthorizedHandler;

    @Autowired
    private JwtAccessDenied accessDeniedHandler;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userService.loadUserByUsername(username);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8089"));
        configuration.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(accessDeniedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.dispatcherTypeMatchers(FORWARD, ERROR).permitAll()
                                .requestMatchers("/auth/**").permitAll()
                            //    .requestMatchers(HttpMethod.DELETE,"/reparacion/reparaciones/").hasRole(RoleType.GERENTE.name())
                            //    .requestMatchers(HttpMethod.POST,"/reparacion/reparaciones").hasRole(RoleType.GERENTE.name())
                            //    .requestMatchers(HttpMethod.PUT,"/consulta/comprarPiezas").hasRole(RoleType.ENCARGADO.name())
                            //    .requestMatchers(HttpMethod.GET,"/consulta/cochesReparaMecanico/{id}").hasAnyRole(RoleType.GERENTE.name(),RoleType.ENCARGADO.name(),RoleType.EMPLEADO.name())
                             //   .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers("/carrito/**").permitAll()
                                .requestMatchers("/cliente/**").permitAll()
                                .requestMatchers("/distribuidor/**").permitAll()
                                .requestMatchers("/producto/**").permitAll()
                                .requestMatchers("/proveedor/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
