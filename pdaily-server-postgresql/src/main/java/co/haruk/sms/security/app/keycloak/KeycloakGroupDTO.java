package co.haruk.sms.security.app.keycloak;

import co.haruk.sms.subscription.account.app.AccountDTO;

/**
 * @author cristhiank on 15/2/20
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
