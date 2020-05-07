package mx.com.sharkit.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String PROVEEDOR = "ROLE_PROVEEDOR";

    public static final String TRANSPORTISTA = "ROLE_TRANSPORTISTA";

    public static final String RECOLECTOR = "ROLE_RECOLECTOR";


    private AuthoritiesConstants() {
    }
}
