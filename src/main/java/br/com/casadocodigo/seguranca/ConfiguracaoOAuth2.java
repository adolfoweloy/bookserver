package br.com.casadocodigo.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ConfiguracaoOAuth2 {

	public static final String RESOURCE_ID = "books";

	@EnableResourceServer
	protected static class OAuth2ResourceServer
			extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.resourceId(RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off
            http
                .requestMatchers()
                    .antMatchers("/api/livros/**").and()
                .authorizeRequests()
                    .antMatchers("/api/livros/**").authenticated();
            // @formatter:on
		}

	}

	@EnableAuthorizationServer
	protected static class OAuth2AuthorizationServer
			extends AuthorizationServerConfigurerAdapter {

		@Override
		public void configure(ClientDetailsServiceConfigurer clients)
				throws Exception {

			clients
				.inMemory()
					.withClient("cliente-curl")
					.secret("123456")
					.authorizedGrantTypes("authorization_code")
					.scopes("read", "write")
					.resourceIds(RESOURCE_ID);
		}

	}

}
