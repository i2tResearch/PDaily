package co.icesi.pdaily.subscription.account.user.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.guards.Guards;
import co.icesi.pdaily.common.model.EmailAddress;
import co.icesi.pdaily.common.model.PdailyEntity;
import co.icesi.pdaily.subscription.account.domain.model.AccountId;
import co.icesi.pdaily.subscription.license.domain.model.LicenseId;

/**
 * @author cristhiank on 15/11/19
 **/
@Entity
@Table(name = "subs_users")
@NamedQuery(name = User.findByAccount, query = "SELECT p FROM User p WHERE p.accountId = :accountId")
@NamedQuery(name = User.findByUserName, query = "SELECT p FROM User p WHERE p.username = :username and p.accountId = :accountId")
@NamedQuery(name = User.findByEmailAndAccount, query = "SELECT p FROM User p WHERE p.email = :email and p.accountId = :accountId")
@NamedQuery(name = User.existsEmail, query = "SELECT COUNT(p.id) FROM User p WHERE p.email = :email")
@NamedQuery(name = User.existsUsername, query = "SELECT COUNT(p.id) FROM User p WHERE p.username = :username")
public class User extends PdailyEntity<UserId> {
	private static final String PREFIX = "User.";
	public static final String findByAccount = PREFIX + "findByAccount";
	public static final String findByUserName = PREFIX + "findByUserName";
	public static final String findByEmailAndAccount = PREFIX + "findByEmailAndAccount";
	public static final String existsEmail = PREFIX + "existsEmail";
	public static final String existsUsername = PREFIX + "existsUsername";

	@EmbeddedId
	private UserId id;
	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "given_name"))
	private PlainName givenName;
	@Embedded
	@AttributeOverride(name = "name", column = @Column(name = "last_name"))
	private PlainName lastName;
	@Embedded
	private EmailAddress email;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "username"))
	private UserName username;
	@Embedded
	@AttributeOverride(name = "text", column = @Column(name = "password"))
	private Password password;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "account_id", updatable = false, nullable = false))
	private AccountId accountId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "license_id"))
	private LicenseId licenseId;

	protected User() {
	}

	private User(
			UserId id,
			PlainName givenName,
			PlainName lastName,
			EmailAddress emailAddress,
			UserName userName,
			AccountId accountId,
			LicenseId licenseId) {
		setId( id );
		setGivenName( givenName );
		setLastName( lastName );
		setEmail( emailAddress );
		setUsername( userName );
		setAccountId( accountId );
		setLicenseId( licenseId );
	}

	public static User of(
			UserId id,
			PlainName givenName,
			PlainName lastName,
			EmailAddress emailAddress,
			UserName userName,
			AccountId accountId,
			LicenseId licenseId) {
		return new User( id, givenName, lastName, emailAddress, userName, accountId, licenseId );
	}

	@Override
	public UserId id() {
		return id;
	}

	public PlainName givenName() {
		return givenName;
	}

	public PlainName lastName() {
		return lastName;
	}

	public EmailAddress email() {
		return email;
	}

	public UserName username() {
		return username;
	}

	public Password password() {
		return password;
	}

	public AccountId accountId() {
		return accountId;
	}

	public LicenseId licenseId() {
		return licenseId;
	}

	@Override
	public void setId(UserId id) {
		this.id = id;
	}

	public void setGivenName(PlainName givenName) {
		this.givenName = requireNonNull( givenName, "El nombre es requerido" );
	}

	public void setLastName(PlainName lastName) {
		this.lastName = requireNonNull( lastName, "El apellido es requerido" );
	}

	public void setEmail(EmailAddress email) {
		this.email = requireNonNull( email, "El email es requerido" );
	}

	public void setUsername(UserName username) {
		this.username = requireNonNull( username, "El usuario es requerido" );
	}

	public void setPassword(Password password) {
		this.password = Guards.requireNonNull( password, "La contraseña es requerida" );
	}

	public void setAccountId(AccountId accountId) {
		this.accountId = requireNonNull( accountId, "La cuenta es requerida" );
	}

	public void setLicenseId(LicenseId licenseId) {
		this.licenseId = licenseId;
	}

	public void updateFrom(User changed) {
		setLicenseId( changed.licenseId );
		setUsername( changed.username );
		setEmail( changed.email );
		setLastName( changed.lastName );
		setGivenName( changed.givenName );
	}

	public PlainName fullName() {
		return PlainName.of( givenName.text() + " " + lastName.text() );
	}
}
