package com.smile.petpat.config;

import com.smile.petpat.jwt.JwtAuthenticationFilter;
import com.smile.petpat.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;

    public WebSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**"
                , "/favicon.ico"
                , "/error");
    }

//    @Bean
//    @Order(1)
//    public SecurityFilterChain exceptionSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .requestMatchers((matchers) -> matchers.antMatchers("/h2-console/**"
//                        , "/favicon.ico"
//                        , "/error"))
//                .authorizeHttpRequests((authorize) -> authorize.anyRequest().permitAll())
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .requestCache().disable()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .cors().disable()
//                .csrf().disable()
//                ;
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .requestCache().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .csrf().disable()
        ;

        http
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/user/**","h2-console/**"
                        ,"/swagger-ui/**","/v3/api-docs/**")
                .permitAll()

                .antMatchers(HttpMethod.GET,"/api/v1/rehoming/public/**").hasRole("GUEST")
                .antMatchers("/api/v1/rehoming/**").hasRole("USER")

                .antMatchers("/api/v1/guest").hasRole("GUEST")
                .antMatchers("/api/v1/user").hasRole("USER")
                .antMatchers("/api/v1/admin").hasRole("ADMIN")

                .anyRequest().authenticated();

        return http.build();
    }

    //    cors 해결
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource(){
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:3000"); // local 테스트 시
//        configuration.addAllowedOrigin("http://121.173.199.79:3000");
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedMethod("GET");
//        configuration.addAllowedMethod("POST");
//        configuration.addAllowedMethod("PUT");
//        configuration.addAllowedMethod("PATCH");
//        configuration.addAllowedHeader("*");
//        configuration.addExposedHeader("Authorization");
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000"); // local 테스트 시
        corsConfiguration.addAllowedOrigin("http://121.173.199.79:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addExposedHeader("RefreshToken");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }





}