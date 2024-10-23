package carrotmoa.carrotmoa.config.sequrity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SequrityConfig {
    CustomUserDetailService detailService;

    public SequrityConfig(CustomUserDetailService detailService) {
        this.detailService = detailService;
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/service/**").authenticated()//로그인해야 접근 가능한 url
                        .requestMatchers("/admin/**").hasRole("ADMIN")//어드민이라는 권한이 있는지 검사
                        .anyRequest().permitAll()//그외 나머지는 인증없이 접근 가능한 경로

                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                )

                .formLogin(formLogin -> formLogin
                        .usernameParameter("login-email")
                        .passwordParameter("login-password")
                        //로그인 페이지
                        .loginPage("/user/login-page")
                        //로그인 요청 처리 url
                        .loginProcessingUrl("/user/login")
                        //로그인시 이동할 페이지
                        .defaultSuccessUrl("/user/login")
                        .permitAll()
                )
                .logout((logoutConfig) -> logoutConfig
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/user/login-page")
                        .invalidateHttpSession(true)
                )
                .userDetailsService(detailService);
        return http.build();
    }
}
