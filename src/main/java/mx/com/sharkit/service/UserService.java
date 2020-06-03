package mx.com.sharkit.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.sharkit.config.Constants;
import mx.com.sharkit.domain.Adjunto;
import mx.com.sharkit.domain.Authority;
import mx.com.sharkit.domain.Empresa;
import mx.com.sharkit.domain.Proveedor;
import mx.com.sharkit.domain.TipoPersona;
import mx.com.sharkit.domain.TipoUsuario;
import mx.com.sharkit.domain.Transportista;
import mx.com.sharkit.domain.User;
import mx.com.sharkit.repository.AdjuntoRepository;
import mx.com.sharkit.repository.AuthorityRepository;
import mx.com.sharkit.repository.ProveedorRepository;
import mx.com.sharkit.repository.TransportistaRepository;
import mx.com.sharkit.repository.UserRepository;
import mx.com.sharkit.repository.UsuarioImagenRepository;
import mx.com.sharkit.security.AuthoritiesConstants;
import mx.com.sharkit.security.SecurityUtils;
import mx.com.sharkit.service.dto.AdjuntoDTO;
import mx.com.sharkit.service.dto.DireccionDTO;
import mx.com.sharkit.service.dto.UserDTO;
import mx.com.sharkit.service.util.RandomUtil;
import mx.com.sharkit.web.rest.errors.EmailAlreadyUsedException;
import mx.com.sharkit.web.rest.errors.InvalidPasswordException;
import mx.com.sharkit.web.rest.errors.LoginAlreadyUsedException;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthorityRepository authorityRepository;

	private final UsuarioImagenRepository usuarioImagenRepository;

	private final AdjuntoRepository adjuntoRepository;

	private final ProveedorRepository proveedorRepository;

	private final DireccionService direccionService;
	
	private final TransportistaRepository transportistaRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthorityRepository authorityRepository, UsuarioImagenRepository usuarioImagenRepository,
			AdjuntoRepository adjuntoRepository, ProveedorRepository proveedorRepository,
			DireccionService direccionService, TransportistaRepository transportistaRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authorityRepository = authorityRepository;
		this.usuarioImagenRepository = usuarioImagenRepository;
		this.adjuntoRepository = adjuntoRepository;
		this.proveedorRepository = proveedorRepository;
		this.direccionService = direccionService;
		this.transportistaRepository = transportistaRepository;
	}

	public Optional<User> activateRegistration(String key) {
		log.debug("Activating user for activation key {}", key);
		return userRepository.findOneByActivationKey(key).map(user -> {
			// activate given user for the registration key.
			user.setActivated(true);
			user.setActivationKey(null);
			log.debug("Activated user: {}", user);
			return user;
		});
	}

	public Optional<User> completePasswordReset(String newPassword, String key) {
		log.debug("Reset user password for reset key {}", key);
		return userRepository.findOneByResetKey(key)
				.filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400))).map(user -> {
					user.setPassword(passwordEncoder.encode(newPassword));
					user.setResetKey(null);
					user.setResetDate(null);
					return user;
				});
	}

	public Optional<User> requestPasswordReset(String mail) {
		return userRepository.findOneByEmailIgnoreCase(mail).filter(User::getActivated).map(user -> {
			user.setResetKey(RandomUtil.generateResetKey());
			user.setResetDate(Instant.now());
			return user;
		});
	}

	public User registerUser(UserDTO userDTO, String password, boolean isActivated, AdjuntoDTO adjunto) {
		userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new LoginAlreadyUsedException();
			}
		});
		userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new EmailAlreadyUsedException();
			}
		});
		User newUser = new User();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(userDTO.getLogin().toLowerCase());
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setMotherLastName(userDTO.getMotherLastName());
		newUser.setTelefono(userDTO.getTelefono());
		newUser.setGenero(userDTO.getGenero());
		newUser.setFechaNacimiento(userDTO.getFechaNacimiento());
		newUser.setEmail(userDTO.getEmail().toLowerCase());
		newUser.setImageUrl(userDTO.getImageUrl());
		newUser.setCreatedDate(Instant.now());

		String langKey = StringUtils.isAllBlank(userDTO.getLangKey()) ? Constants.DEFAULT_LANGUAGE
				: userDTO.getLangKey();
		newUser.setLangKey(langKey);
		
		Long tipoPersonaId = (userDTO.getTipoPersonaId() != null && userDTO.getTipoPersonaId() > 0)
				? userDTO.getTipoPersonaId()
				: TipoPersona.FISICA;
		newUser.setTipoPersonaId(tipoPersonaId);
		
		// new user is not active
		newUser.setActivated(isActivated);
		newUser.setTipoUsuarioId(TipoUsuario.CLIENTE);
		// new user gets registration key
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		Set<Authority> authorities = new HashSet<>();
		authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
		newUser.setAuthorities(authorities);

		if (adjunto != null) {
			Adjunto adj = new Adjunto();
			adj.setContentType(adjunto.getContentType());
			adj.setFileContentType(adjunto.getFileContentType());
			adj.setSize(adjunto.getSize());
			adj.setFileName(adjunto.getFileName());
			adj.setFile(adjunto.getFile());
			adj = adjuntoRepository.save(adj);
			newUser.setAdjuntoId(adj.getId());
		}

		userRepository.save(newUser);

		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	public User registerUserProveedor(UserDTO userDTO, String razonSocial, String password, boolean isActivated, AdjuntoDTO adjunto) {
		userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new LoginAlreadyUsedException();
			}
		});
		userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new EmailAlreadyUsedException();
			}
		});

		LocalDateTime now = LocalDateTime.now();
		User newUser = new User();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(userDTO.getLogin().toLowerCase());
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setMotherLastName(userDTO.getMotherLastName());
		newUser.setTelefono(userDTO.getTelefono());
		newUser.setGenero(userDTO.getGenero());
		newUser.setFechaNacimiento(userDTO.getFechaNacimiento());
		newUser.setEmail(userDTO.getEmail().toLowerCase());
		newUser.setImageUrl(userDTO.getImageUrl());
		newUser.setCreatedDate(Instant.now());

		String langKey = StringUtils.isAllBlank(userDTO.getLangKey()) ? Constants.DEFAULT_LANGUAGE
				: userDTO.getLangKey();
		newUser.setLangKey(langKey);
		// new user is not active
		newUser.setActivated(isActivated);
		newUser.setTipoUsuarioId(TipoUsuario.PROVEEDOR);

		Long tipoPersonaId = (userDTO.getTipoPersonaId() != null && userDTO.getTipoPersonaId() > 0)
				? userDTO.getTipoPersonaId()
				: TipoPersona.FISICA;
		newUser.setTipoPersonaId(tipoPersonaId);

		// new user gets registration key
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		Set<Authority> authorities = new HashSet<>();
		authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
		authorityRepository.findById(AuthoritiesConstants.PROVEEDOR).ifPresent(authorities::add);
		newUser.setAuthorities(authorities);

		if (adjunto != null) {
			Adjunto adj = new Adjunto();
			adj.setContentType(adjunto.getContentType());
			adj.setFileContentType(adjunto.getFileContentType());
			adj.setSize(adjunto.getSize());
			adj.setFileName(adjunto.getFileName());
			adj.setFile(adjunto.getFile());
			adj = adjuntoRepository.save(adj);
			newUser.setAdjuntoId(adj.getId());
		}

		newUser = userRepository.save(newUser);

		// Crear registro en proveedor
		Proveedor proveedor = new Proveedor();
		proveedor.setNombre(razonSocial);
		proveedor.setEmpresaId(Empresa.CENTRAL_ABASTOS);
		proveedor.setFechaAlta(now);
		proveedor.setUsuarioAltaId(newUser.getId());
		proveedor.setUsuarioId(newUser.getId());
		proveedor.setTransportistaId(Transportista.GENERICO);
		proveedor = proveedorRepository.save(proveedor);

		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	public User registerUserTransportista(UserDTO userDTO, String razonSocial, String password, boolean isActivated, AdjuntoDTO adjunto) {
		userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new LoginAlreadyUsedException();
			}
		});
		userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
			boolean removed = removeNonActivatedUser(existingUser);
			if (!removed) {
				throw new EmailAlreadyUsedException();
			}
		});

		LocalDateTime now = LocalDateTime.now();
		User newUser = new User();
		String encryptedPassword = passwordEncoder.encode(password);
		newUser.setLogin(userDTO.getLogin().toLowerCase());
		// new user gets initially a generated password
		newUser.setPassword(encryptedPassword);
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setMotherLastName(userDTO.getMotherLastName());
		newUser.setTelefono(userDTO.getTelefono());
		newUser.setGenero(userDTO.getGenero());
		newUser.setFechaNacimiento(userDTO.getFechaNacimiento());
		newUser.setEmail(userDTO.getEmail().toLowerCase());
		newUser.setImageUrl(userDTO.getImageUrl());
		newUser.setCreatedDate(Instant.now());

		String langKey = StringUtils.isAllBlank(userDTO.getLangKey()) ? Constants.DEFAULT_LANGUAGE
				: userDTO.getLangKey();
		newUser.setLangKey(langKey);
		// new user is not active
		newUser.setActivated(isActivated);
		newUser.setTipoUsuarioId(TipoUsuario.TRANSPORTISTA);

		Long tipoPersonaId = (userDTO.getTipoPersonaId() != null && userDTO.getTipoPersonaId() > 0)
				? userDTO.getTipoPersonaId()
				: TipoPersona.FISICA;
		newUser.setTipoPersonaId(tipoPersonaId);

		// new user gets registration key
		newUser.setActivationKey(RandomUtil.generateActivationKey());
		Set<Authority> authorities = new HashSet<>();
		authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
		authorityRepository.findById(AuthoritiesConstants.TRANSPORTISTA).ifPresent(authorities::add);
		newUser.setAuthorities(authorities);

		if (adjunto != null) {
			Adjunto adj = new Adjunto();
			adj.setContentType(adjunto.getContentType());
			adj.setFileContentType(adjunto.getFileContentType());
			adj.setSize(adjunto.getSize());
			adj.setFileName(adjunto.getFileName());
			adj.setFile(adjunto.getFile());
			adj = adjuntoRepository.save(adj);
			newUser.setAdjuntoId(adj.getId());
		}

		newUser = userRepository.save(newUser);

		// Crear registro en transportista
		Transportista transportista = new Transportista();
		transportista.setNombre(razonSocial);
		transportista.setEmpresaId(Empresa.CENTRAL_ABASTOS);
		transportista.setFechaAlta(now);
		transportista.setUsuarioAltaId(newUser.getId());
		transportista.setUsuarioId(newUser.getId());
		transportista = transportistaRepository.save(transportista);

		log.debug("Created Information for User: {}", newUser);
		return newUser;
	}

	private boolean removeNonActivatedUser(User existingUser) {
		if (existingUser.getActivated()) {
			return false;
		}
		userRepository.delete(existingUser);
		userRepository.flush();
		return true;
	}

	public User createUser(UserDTO userDTO) {
		User user = new User();
		user.setLogin(userDTO.getLogin().toLowerCase());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail().toLowerCase());
		user.setImageUrl(userDTO.getImageUrl());
		if (userDTO.getLangKey() == null) {
			user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
		} else {
			user.setLangKey(userDTO.getLangKey());
		}
		String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
		user.setPassword(encryptedPassword);
		user.setResetKey(RandomUtil.generateResetKey());
		user.setResetDate(Instant.now());
		user.setActivated(true);
		if (userDTO.getAuthorities() != null) {
			Set<Authority> authorities = userDTO.getAuthorities().stream().map(authorityRepository::findById)
					.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
			user.setAuthorities(authorities);
		}
		userRepository.save(user);
		log.debug("Created Information for User: {}", user);
		return user;
	}

	/**
	 * Update basic information (first name, last name, email, language) for the
	 * current user.
	 *
	 * @param firstName first name of user.
	 * @param lastName  last name of user.
	 * @param email     email id of user.
	 * @param langKey   language key.
	 * @param imageUrl  image URL of user.
	 */
	public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email.toLowerCase());
			user.setLangKey(langKey);
			user.setImageUrl(imageUrl);
			log.debug("Changed Information for User: {}", user);
		});
	}

	public Optional<UserDTO> updateUserToken(UserDTO userDTO) {
		log.debug("Datos a actualizar, userDTO: {}", userDTO);

		return Optional.of(userRepository.findById(userDTO.getId())).filter(Optional::isPresent).map(Optional::get)
				.map(user -> {

					if (userDTO.getFirstName() != null) {
						user.setFirstName(userDTO.getFirstName());
					}
					if (userDTO.getLastName() != null) {
						user.setLastName(userDTO.getLastName());
					}
					if (userDTO.getMotherLastName() != null) {
						user.setMotherLastName(userDTO.getMotherLastName());
					}
					if (userDTO.getToken() != null) {
						user.setToken(userDTO.getToken());
					}
					if (userDTO.getTelefono() != null) {
						user.setTelefono(userDTO.getTelefono());
					}
					if (userDTO.getGenero() != null) {
						user.setGenero(userDTO.getGenero());
					}
					if (userDTO.getFechaNacimiento() != null) {
						user.setFechaNacimiento(userDTO.getFechaNacimiento());
					}
					if (userDTO.getTipoPersonaId() != null) {
						user.setTipoPersonaId(userDTO.getTipoPersonaId());
					}

					if (userDTO.getAdjunto() != null) {
						Adjunto adjunto = null;
						if (userDTO.getAdjunto().getId() != null && userDTO.getAdjunto().getId() > 0) {
							adjunto = adjuntoRepository.findById(userDTO.getAdjunto().getId()).orElse(null);
							if (adjunto != null) {
								adjunto.setContentType(userDTO.getAdjunto().getContentType());
								adjunto.setFile(userDTO.getAdjunto().getFile());
								adjunto.setFileContentType(userDTO.getAdjunto().getFileContentType());
								adjunto.setFileName(userDTO.getAdjunto().getFileName());
								adjunto.setSize(userDTO.getAdjunto().getSize());
							}
						}
						if (adjunto == null) {
							adjunto = new Adjunto();
							adjunto.setContentType(userDTO.getAdjunto().getContentType());
							adjunto.setFile(userDTO.getAdjunto().getFile());
							adjunto.setFileContentType(userDTO.getAdjunto().getFileContentType());
							adjunto.setFileName(userDTO.getAdjunto().getFileName());
							adjunto.setSize(userDTO.getAdjunto().getSize());

							adjunto = adjuntoRepository.save(adjunto);
							user.setAdjuntoId(adjunto.getId());
						}
					}
					
					if (user.getTipoUsuarioId() == TipoUsuario.PROVEEDOR) {
						Proveedor proveedor = proveedorRepository.findOneByUsuarioId(userDTO.getId()).orElse(null);

						if (userDTO.getDireccion() != null ) {
							if (proveedor != null) {
								DireccionDTO direccion = direccionService.save(userDTO.getDireccion()); 
								proveedor.setDireccionId(direccion.getId());
							}	
						}
						
						if (userDTO.getRazonSocial() != null) {
							proveedor.setNombre(userDTO.getRazonSocial());
						}
						
					} else if (user.getTipoUsuarioId() == TipoUsuario.TRANSPORTISTA) {
						Transportista transportista = transportistaRepository.findOneByusuarioId(userDTO.getId()).orElse(null);

						if (userDTO.getDireccion() != null ) {
							if (transportista != null) {
								DireccionDTO direccion = direccionService.save(userDTO.getDireccion()); 
								transportista.setDireccionId(direccion.getId());
							}	
						}
						
						if (userDTO.getRazonSocial() != null) {
							transportista.setNombre(userDTO.getRazonSocial());
						}
					}
					

					log.debug("Changed Information for User: {}", user);
					return user;
				}).map(UserDTO::new);

	}

	/**
	 * Update all information for a specific user, and return the modified user.
	 *
	 * @param userDTO user to update.
	 * @return updated user.
	 */
	public Optional<UserDTO> updateUser(UserDTO userDTO) {

		return Optional.of(userRepository.findById(userDTO.getId())).filter(Optional::isPresent).map(Optional::get)
				.map(user -> {
					user.setLogin(userDTO.getLogin().toLowerCase());
					user.setFirstName(userDTO.getFirstName());
					user.setLastName(userDTO.getLastName());
					user.setEmail(userDTO.getEmail().toLowerCase());
					user.setImageUrl(userDTO.getImageUrl());
					user.setActivated(userDTO.isActivated());
					user.setLangKey(userDTO.getLangKey());
					Set<Authority> managedAuthorities = user.getAuthorities();
					managedAuthorities.clear();
					userDTO.getAuthorities().stream().map(authorityRepository::findById).filter(Optional::isPresent)
							.map(Optional::get).forEach(managedAuthorities::add);
					log.debug("Changed Information for User: {}", user);
					return user;
				}).map(UserDTO::new);

	}

	public void deleteUser(String login) {
		userRepository.findOneByLogin(login).ifPresent(user -> {
			userRepository.delete(user);
			log.debug("Deleted User: {}", user);
		});
	}

	public void changePassword(String currentClearTextPassword, String newPassword) {
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
			String currentEncryptedPassword = user.getPassword();
			if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
				throw new InvalidPasswordException();
			}
			String encryptedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encryptedPassword);
			log.debug("Changed password for User: {}", user);
		});
	}

	@Transactional(readOnly = true)
	public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
		return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthoritiesByLogin(String login) {
		return userRepository.findOneWithAuthoritiesByLogin(login);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities(Long id) {
		return userRepository.findOneWithAuthoritiesById(id);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthorities() {
		return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
	}

	/**
	 * Not activated users should be automatically deleted after 3 days.
	 * <p>
	 * This is scheduled to get fired everyday, at 01:00 (am).
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void removeNotActivatedUsers() {
		userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
				Instant.now().minus(3, ChronoUnit.DAYS)).forEach(user -> {
					log.debug("Deleting not activated user {}", user.getLogin());
					userRepository.delete(user);
				});
	}

	/**
	 * Gets a list of all the authorities.
	 * 
	 * @return a list of all the authorities.
	 */
	public List<String> getAuthorities() {
		return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
	}

}
