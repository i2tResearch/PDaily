package co.icesi.pdaily.security.user.app;

import co.haruk.core.domain.model.entity.PlainName;
import co.icesi.pdaily.common.model.EmailAddress;
import co.icesi.pdaily.security.user.domain.model.User;
import co.icesi.pdaily.security.user.domain.model.UserId;
import co.icesi.pdaily.security.user.domain.model.UserName;
import co.icesi.pdaily.subscription.account.domain.model.AccountId;
import co.icesi.pdaily.subscription.license.domain.model.LicenseId;

/**
 * @author andres2508 on 15/11/19
 **/
public final class UserDTO {

	public String id;
	public String givenName;
	public String lastName;
	public String email;
	public String username;
	public String password;
	public String accountId;
	public String licenseId;

	protected UserDTO() {
	}

	private UserDTO(
			String id,
			String givenName,
			String lastName,
			String email,
			String username,
			String accountId,
			String licenseId) {
		this.id = id;
		this.givenName = givenName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.accountId = accountId;
		this.licenseId = licenseId;
	}

	public static UserDTO of(
			String id,
			String givenName,
			String lastName,
			String email,
			String username,
			String password,
			String accountId,
			String licenseId) {
		var dto = new UserDTO( id, givenName, lastName, email, username, accountId, licenseId );
		dto.password = password;
		return dto;
	}

	public static UserDTO of(User user) {
		final var accountId = user.accountId() != null ? user.accountId().text() : null;
		var dto = of(
				user.id().text(),
				user.givenName().text(),
				user.lastName().text(),
				user.email().text(),
				user.username().text(),
				null,
				accountId,
				null
		);
		dto.licenseId = user.licenseId() != null ? user.licenseId().text() : null;
		return dto;
	}

	public User toUser() {
		return User.of(
				UserId.of( id ),
				PlainName.of( givenName ),
				PlainName.of( lastName ),
				EmailAddress.of( email ),
				UserName.of( username ),
				AccountId.of( accountId ),
				LicenseId.of( licenseId )
		);
	}

	public String fullName() {
		return givenName + ' ' + lastName;
	}
}
