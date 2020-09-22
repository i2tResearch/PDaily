package co.icesi.pdaily.security.app.keycloak;

import co.icesi.pdaily.subscription.account.app.AccountDTO;

/**
 * @author andres2508 on 15/2/20
 **/
public class KeycloakGroupDTO {
	public String id;
	public String name;
	public String companyCode;

	protected KeycloakGroupDTO() {
	}

	private KeycloakGroupDTO(String id, String name, String companyCode) {
		this.id = id;
		this.name = name;
		this.companyCode = companyCode;
	}

	public static KeycloakGroupDTO of(AccountDTO account) {
		return new KeycloakGroupDTO( account.id, account.name, account.name );
	}
}
