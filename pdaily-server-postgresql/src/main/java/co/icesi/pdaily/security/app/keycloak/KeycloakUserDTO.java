package co.icesi.pdaily.security.app.keycloak;

import co.icesi.pdaily.security.user.app.UserDTO;

public final class KeycloakUserDTO {
	public String id;
	public String name;
	public String lastName;
	public String email;
	public KeycloakCredentialDTO credential;

	protected KeycloakUserDTO() {

	}

	private KeycloakUserDTO(
			String id,
			String name,
			String lastName,
			String email,
			KeycloakCredentialDTO credential) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.credential = credential;
	}

	public static KeycloakUserDTO of(UserDTO user) {
		KeycloakCredentialDTO credential = KeycloakCredentialDTO.of( user.username, user.password );
		return of(
				user.id,
				user.givenName,
				user.lastName,
				user.email,
				credential
		);
	}

	public static KeycloakUserDTO of(
			String id,
			String name,
			String lastName,
			String email,
			KeycloakCredentialDTO credential) {
		return new KeycloakUserDTO( id, name, lastName, email, credential );
	}
}