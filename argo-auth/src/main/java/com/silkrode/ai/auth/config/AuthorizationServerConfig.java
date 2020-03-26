package com.silkrode.ai.auth.config;

import com.silkrode.ai.auth.service.SpringDataUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * Setting authentication methods and endpoints.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SpringDataUserDetailService springDataUserDetailService;
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1.1 配置客戶端信息的讀取模式
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    // 1.2 客戶端信息從資料庫讀取 (若使用資料庫的方式)
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    // 2.1 設置令牌的相關信息
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService); // 客戶端信息的詳情服務
        service.setSupportRefreshToken(true); // 支持刷新令牌
        service.setTokenStore(tokenStore); // 令牌儲存策略
        //使用資料庫取代
        //service.setAccessTokenValiditySeconds(180); // 令牌默認有效期2小時
        //service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默認有效期3天

        //設置 JWT 令牌
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter));
        service.setTokenEnhancer(tokenEnhancerChain);

        return service;
    }

    // 2.2 設置授權模式的授權碼如何存取
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    // 2.3 配置令牌的能訪問端點和產生令牌服務
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager) // 認証管理器（密碼服務需要）
                .userDetailsService(springDataUserDetailService) // 在 refresh_token 時，在檢查帳號是否被禁用
                .authorizationCodeServices(authorizationCodeServices)// 授權碼管理器（授權碼服務需要）
                .tokenServices(tokenService())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
        ;
    }

    //
    /**
     * @apiNote  配置針對令牌訪問端點的安全策略
     *  token驗證 http://url/oauth/token
     *  取得token : client_id, client_secret, username, password, grant_type
     *  刷新token : client_id, client_secret, grant_type, refresh_toke
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
                .tokenKeyAccess("permitAll()") // /oauth/token_key 是公開
                .checkTokenAccess("permitAll()") // /oauth/check_token 是公開
                .allowFormAuthenticationForClients() // 允許 post 方式申請令牌
        ;
    }
}