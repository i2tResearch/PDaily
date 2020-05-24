package co.haruk.sms.security.user.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.sms.common.model.EmailAddress;
import co.haruk.sms.common.model.PdailyEntity;
import co.haruk.sms.subscription.account.domain.model.AccountId;
import co.haruk.sms.subscription.license.domain.model.LicenseId;

/**
 * @author cristhiank on 15/11/19
 **/
@Entity
@Table(name = "security_users")
@NamedQuery(name = User.findAll, query = "SELECT p FROM User p")
@NamedQuery(name = User.searchByTerm, query = "SELECT p FROM User p WHERE UPPER(p.email.email) LIKE UPPER(:term) OR UPPER(CONCAT(p.givenName.name,p.lastName.name)) LIKE UPPER(:term) OR UPPER(p.username.text) LIKE UPPER(:term)")
@NamedQuery(name = User.countAll, query = "SELECT COUNT(p.id) FROM User p")
@NamedQuery(name = User.findByAccount, query = "SELECT p FROM User p WHERE p.accountId = :accountId")
@NamedQuery(name = User.findByUserName, query = "SELECT p FROM User p WHERE p.username = :username")
@NamedQuery(name = User.findByEmail, query = "SELECT p FROM User p WHERE p.email = :email")
@NamedQuery(name = User.existsEmail, query = "SELECT COUNT(p.id) FROM User p WHERE p.email = :email")
@NamedQuery(name = User.existsUsername, query = "SELECT COUNT(p.id) FROM User p WHERE p.username = :username")
public class User extends PdailyEntity<UserId> {
	public static final String ADMIN = "admin";
	private static final String PREFIX = "User.";
	public static final String findByAccount = PREFIX + "findByAccount";
	public static final String findAll = PREFIX + "findAll";
	public static final String countAll = PREFIX + "countAll";
	public static final String searchByTerm = PREFIX + "searchByTerm";
	public static final String findByUserName = PREFIX + "findByUserName";
	public static final String findByEmail = PREFIX + "findByEmail";
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
	@AttributeOverride(name = "id", column = @Column(name = "account_id", updatable = false))
	private AccountId accountId;
	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "license_id"))
	private LicenseId licenseId;
	@Convert(converter = UserTypeConverter.class)
	private UserType type;

	protected User() {
	}

	private User(
			UserId id,
			PlainName givenName,
			PlainName lastName,
			EmailAddress emailAddress,
			UserName userName,
			UserType type,
			AccountId accountId,
			LicenseId licenseId) {
		setId( id );
		setGivenName( givenName );
		setLastName( lastName );
		setEmail( emailAddress );
		setUsername( userName );
		setAccountId( type, accountId );
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
		return new User( id, givenName, lastName, emailAddress, userName, UserType.NORMAL, accountId, licenseId );
	}

	public static User defaultAdmin() {
		return new User(
				UserId.generateNew(),
				PlainName.of( ADMIN ),
				PlainName.of( ADMIN ),
				EmailAddress.of( "admin@haruk.co" ),
				UserName.of( ADMIN ),
				UserType.SERVICE,
				null,
				null
		);
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

	public void setPassword(String rawPassword, IEncryptionProvider encryptionProvider) {
		final var finalPass = requireNonNull( rawPassword, "La contraseña es requerida" );
		this.password = encryptionProvider.encrypt( finalPass );
	}

	public void setAccountId(UserType type, AccountId accountId) {
		this.type = type;
		if ( this.type == UserType.SERVICE ) {
			this.accountId = null;
		} else if ( this.type == UserType.NORMAL ) {
			this.accountId = requireNonNull( accountId, "La cuenta es requerida" );
		}
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
