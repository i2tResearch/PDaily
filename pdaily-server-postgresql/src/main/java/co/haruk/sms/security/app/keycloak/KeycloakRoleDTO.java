package co.haruk.sms.security.app.keycloak;

public final class KeycloakRoleDTO {
	public String id;
	public String roleName;

	private KeycloakRoleDTO(String id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}

	public static KeycloakRoleDTO of(String id, String roleName) {
		return new KeycloakRoleDTO( id, roleName );
	}
}