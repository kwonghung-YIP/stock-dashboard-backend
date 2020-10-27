package demo.stockdashboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
@EnableWebFluxSecurity
public class WebFluxConfig implements WebFluxConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
		  .addMapping("/company")
		  .allowedOrigins("*")
		  .allowedMethods("GET");
		registry
		  .addMapping("/quote-demo")
		  .allowedOrigins("*")
		  .allowedMethods("GET");
		registry
		  .addMapping("/intraday-init-demo")
		  .allowedOrigins("*")
		  .allowedMethods("GET");
		registry
		  .addMapping("/intraday-delta-demo")
		  .allowedOrigins("*")
		  .allowedMethods("GET");
		registry
		  .addMapping("/previous")
		  .allowedOrigins("*")
		  .allowedMethods("GET");		
		registry
		  .addMapping("/intraday-demo")
		  .allowedOrigins("*")
		  .allowedMethods("GET");
	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange().anyExchange().permitAll();
		return http.build();
	}	

}
