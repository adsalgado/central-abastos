package mx.com.sharkit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import mx.com.sharkit.security.AuthoritiesConstants;
import mx.com.sharkit.security.jwt.JWTConfigurer;
import mx.com.sharkit.security.jwt.TokenProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final TokenProvider tokenProvider;

	private final CorsFilter corsFilter;
	private final SecurityProblemSupport problemSupport;

	public SecurityConfiguration(TokenProvider tokenProvider, CorsFilter corsFilter,
			SecurityProblemSupport problemSupport) {
		this.tokenProvider = tokenProvider;
		this.corsFilter = corsFilter;
		this.problemSupport = problemSupport;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
			.antMatchers(HttpMethod.OPTIONS, "/**")
			.antMatchers("/app/**/*.{js,html}")
			.antMatchers("/i18n/**")
			.antMatchers("/content/**")
			.antMatchers("/swagger-ui/index.html")
			.antMatchers("/test/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().authenticationEntryPoint(problemSupport).accessDeniedHandler(problemSupport)
//				.and()
//				.headers()
//				.contentSecurityPolicy(
//						"default-src 'self' *.sharktech.com.mx; script-src 'self' *.sharktech.com.mx 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' https://fonts.googleapis.com 'unsafe-inline'; img-src 'self' data:; font-src 'self' https://fonts.gstatic.com data:")
//				.and().referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN).and()
//				.featurePolicy(
//						"geolocation 'self' https://dev-cabasto.sharktech.com.mx/; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
//				.and().frameOptions().deny()
				.and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/adjuntos/**").permitAll()
				.antMatchers("/api/productos/**").permitAll()
				.antMatchers("/api/tipo-articulos/**").permitAll()
				.antMatchers("/api/promociones/**").permitAll()
				.antMatchers("/api/proveedor-productos/**").permitAll()
				.antMatchers("/api/tipo-direcciones/**").permitAll()
				.antMatchers("/api/proveedores/**").permitAll()
				.antMatchers("/api/google/**").permitAll()
				.antMatchers("/api/files/**").permitAll()
				.antMatchers("/api/authenticate").permitAll()
				.antMatchers("/api/register").permitAll()
				.antMatchers("/api/register/**").permitAll()
				.antMatchers("/api/activate").permitAll()
				.antMatchers("/api/account/reset-password/init").permitAll()
				.antMatchers("/api/account/reset-password/finish").permitAll()
				.antMatchers("/api/**").authenticated()
				.antMatchers("/secured/**/**", "/secured/**/**/**", "/secured/socket", "/secured/success").authenticated()
				.antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/websocket/**")
				.permitAll().antMatchers("/management/health").permitAll()
				.antMatchers("/management/info").permitAll()
				.antMatchers("/management/prometheus").permitAll()
				.antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
				.and().httpBasic()
				.and().apply(securityConfigurerAdapter());
		
	}

	private JWTConfigurer securityConfigurerAdapter() {
		return new JWTConfigurer(tokenProvider);
	}
}
