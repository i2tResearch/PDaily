package co.icesi.pdaily.security.app.keycloak;

public final class KeycloakCredentialDTO {
	public String username;
	public String rawPassword;

	protected KeycloakCredentialDTO() {
	}

	private KeycloakCredentialDTO(String username, String rawPassword) {
		this.username = username;
		this.rawPassword = rawPassword;
	}

	public static KeycloakCredentialDTO of(String username, String rawPassword) {
		return new KeycloakCredentialDTO( username, rawPassword );
	}
}
