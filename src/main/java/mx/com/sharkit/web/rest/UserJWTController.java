package mx.com.sharkit.web.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import mx.com.sharkit.domain.User;
import mx.com.sharkit.security.jwt.JWTFilter;
import mx.com.sharkit.security.jwt.TokenProvider;
import mx.com.sharkit.service.ParametrosAplicacionService;
import mx.com.sharkit.service.UserService;
import mx.com.sharkit.service.dto.ParametrosAplicacionDTO;
import mx.com.sharkit.web.rest.vm.LoginVM;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

	private final TokenProvider tokenProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final UserService userService;

	private final ParametrosAplicacionService parametrosAplicacionService;

	public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
			UserService userService, ParametrosAplicacionService parametrosAplicacionService) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.userService = userService;
		this.parametrosAplicacionService = parametrosAplicacionService;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVM.getUsername(), loginVM.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
		String jwt = tokenProvider.createToken(authentication, rememberMe);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

		Optional<User> optUserDTO = userService.getUserWithAuthorities();
		User user = new User();
		if (optUserDTO.isPresent()) {
			user = optUserDTO.get();
		}

		JWTToken jwtToken = new JWTToken(jwt, loginVM.getUsername(), user.getTipoUsuarioId());
		jwtToken.setNombre(String.format("%s %s %s", user.getFirstName(), user.getLastName(), user.getMotherLastName()));
		jwtToken.setEmail(user.getEmail());
		jwtToken.setParams(parametrosAplicacionService.findAll().stream()
				.collect(Collectors.toMap(ParametrosAplicacionDTO::getClave, ParametrosAplicacionDTO::getDescripcion)));
		return new ResponseEntity<>(jwtToken, httpHeaders, HttpStatus.OK);
	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		private String idToken;

		private String login;

		private Long tipoUsuarioId;

		private String email;

		private String nombre;

		private Map<String, Object> params = new HashMap<>();

		JWTToken(String idToken) {
			this.idToken = idToken;
		}

		JWTToken(String idToken, String login, Long tipoUsuarioId) {
			this.idToken = idToken;
			this.login = login;
			this.tipoUsuarioId = tipoUsuarioId;
		}

		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("username")
		String getLogin() {
			return login;
		}

		void setLogin(String login) {
			this.login = login;
		}

		@JsonProperty("tipo_usuario")
		public Long getTipoUsuarioId() {
			return tipoUsuarioId;
		}

		public void setTipoUsuarioId(Long tipoUsuarioId) {
			this.tipoUsuarioId = tipoUsuarioId;
		}

		@JsonProperty("parametros")
		public Map<String, Object> getParams() {
			return params;
		}

		public void setParams(Map<String, Object> params) {
			this.params = params;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

	}
}
