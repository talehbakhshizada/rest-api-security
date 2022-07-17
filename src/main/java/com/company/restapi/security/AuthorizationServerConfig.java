package com.company.restapi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    private TokenStore tokenStore;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                     PasswordEncoder passwordEncoder,
                                     JwtAccessTokenConverter jwtAccessTokenConverter,
                                     TokenStore tokenStore){
        this.authenticationManager=authenticationManager;
        this.passwordEncoder=passwordEncoder;
        this.jwtAccessTokenConverter=jwtAccessTokenConverter;
        this.tokenStore=tokenStore;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.
                inMemory()
                .withClient("client")
                .authorizedGrantTypes("client_credentials","password")
                .authorities("USER", "ADMIN")
                .scopes("read", "write")
                .resourceIds("user_rest_api")
            //    .accessTokenValiditySeconds(5000)
            //    .refreshTokenValiditySeconds(50000)
                .secret(passwordEncoder.encode("secret"));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter));

        endpoints
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(enhancerChain)
                .authenticationManager(authenticationManager);
    }





}
