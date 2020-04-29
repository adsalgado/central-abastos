package mx.com.sharkit.web.rest;

import java.util.Optional;

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
import mx.com.sharkit.service.UserService;
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

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

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
        return new ResponseEntity<>(new JWTToken(jwt, loginVM.getUsername(), user.getTipoUsuarioId()), httpHeaders, HttpStatus.OK);
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        
        private String login;

        private Long tipoUsuarioId;

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
        
        
        
    }
}
