package co.haruk.sms.common.model;

import static co.haruk.core.domain.model.guards.Guards.require;
import static co.haruk.core.domain.model.guards.Guards.requireNonNull;
import static co.haruk.core.domain.model.text.StringOps.isNullOrEmpty;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author andres2508 on 15/11/19
 **/
@Embeddable
public class EmailAddress implements Serializable {
	// Regex from https://emailregex.com/
	private static final String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final Pattern pattern = Pattern.compile( regex );
	@Column
	private String email;

	protected EmailAddress() {
	}

	private EmailAddress(String email) {
		setEmail( email );
	}

	public static EmailAddress of(String email) {
		return new EmailAddress( email );
	}

	public static boolean isValid(String emailString) {
		return pattern.matcher( emailString ).matches();
	}

	public static EmailAddress ofNullable(String address) {
		if ( isNullOrEmpty( address ) ) {
			return null;
		}
		return new EmailAddress( address );
	}

	public String text() {
		return email;
	}

	private void setEmail(String email) {
		final var finalEmail = requireNonNull( email, "El email es requerido" ).trim().toLowerCase();
		require( isValid( finalEmail ), String.format( "El email %s es invalido", email ) );
		this.email = finalEmail;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		EmailAddress that = (EmailAddress) o;
		return email.equalsIgnoreCase( that.email );
	}

	@Override
	public int hashCode() {
		return Objects.hash( email );
	}

	@Override
	public String toString() {
		return email;
	}
}
