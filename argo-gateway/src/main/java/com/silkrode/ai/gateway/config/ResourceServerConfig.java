package com.silkrode.ai.gateway.config;

import com.netflix.discovery.EurekaClient;
import com.silkrode.ai.common.utils.RSAUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.interfaces.RSAPublicKey;

/**
 * Setting the endpoints which that the user must be authenticated before connecting.
 */
@EnableWebFluxSecurity
public class ResourceServerConfig {

    private EurekaClient eurekaClient;
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                // 放行 認證端點
                .pathMatchers("/auth-server/**").permitAll()
                // 放行 notification-service
                .pathMatchers("/notification-service/**").permitAll()
                // 測試環境的 Swagger
                .pathMatchers("/**").permitAll()
                .anyExchange().authenticated()
                .and()
                // OAuth2 JWT
                .oauth2ResourceServer().jwt();
        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() throws Exception {
        String PUBLIC_KEY =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn9/h4cYCbwKm1RlvOzJg\n" +
                        "df2Rs8fbUDT+kFWV6DK/LYwh7Ogvgn9gKQA1rZ6p1phdQbk/ThbgK53kKVHM4doE\n" +
                        "161THEE8AFuX4AS9IX4cLS0YWlqTaovVysDLRL2EgP7pntiGA+LuJAjJw+8xXC1V\n" +
                        "1fj2COh3GeZpdyxOERtjG8XevH3J90u5Vk0J8tRkctJeHw6rtV94uMRZnYXqugVy\n" +
                        "GXIANeGsb8TbhNb8l2sIHfZWgOJYS/EyB49nmroAaCPor9DreLzxyzBdNAOdfe2I\n" +
                        "ePt5ABpvbng4c757suc7A7omsO78quRbBfNQJzXOpJjuuBfqA1XgWkYQugFl5oQu\n" +
                        "PQIDAQAB\n";
        return new NimbusReactiveJwtDecoder((RSAPublicKey) RSAUtils.getPublicKey(PUBLIC_KEY));
    }
}
