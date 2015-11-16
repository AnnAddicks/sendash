package com.addicks.sendash.admin;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.addicks.sendash.admin.service.CustomUserDetailsService;

@Configuration
public class OAuth2ServerConfiguration {

  private static final String RESOURCE_ID = "restservice";

  @Configuration
  @EnableResourceServer
  protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
      resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests().antMatchers("/github").permitAll().and().authorizeRequests()
          .antMatchers("/api/admin").hasRole("ADMIN");
    }

  }

  @Configuration
  @EnableAuthorizationServer
  protected static class AuthorizationServerConfiguration
      extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public JdbcTokenStore tokenStore() {
      return new JdbcTokenStore(dataSource);
    }

    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
      return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
      security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.authorizationCodeServices(authorizationCodeServices())
          .authenticationManager(authenticationManager).tokenStore(tokenStore())
          .userDetailsService(userDetailsService).approvalStoreDisabled();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

      clients.jdbc(dataSource).passwordEncoder(passwordEncoder).withClient("sendashWebApp")
          .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
          .authorities("USER").scopes("read", "write").resourceIds(RESOURCE_ID)
          .accessTokenValiditySeconds(10800).secret("GoGoVCHeckProApp");

    }

  }

}
