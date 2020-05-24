package co.haruk.sms.security.user.domain.model;

import static co.haruk.core.domain.model.guards.Guards.require;
import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author cristhiank on 15/11/19
 **/
@Embeddable
public class UserName implements Serializable {
	private static final String regex = "^[A-Za-z0-9]+(?:[._-][A-Za-z0-9]+)*$";
	private static final Pattern pattern = Pattern.compile( regex );

	@Column
	private String text;

	protected UserName() {
	}

	private UserName(String username) {
		setText( username );
	}

	public static UserName of(String username) {
		return new UserName( username );
	}

	public static boolean isValid(String username) {
		return pattern.matcher( username ).matches();
	}

	public String text() {
		return text;
	}

	private void setText(String text) {
		final String finalUser = requireNonNull( text ).trim().toLowerCase();
		require( isValid( finalUser ), String.format( "El usuario %s es invalido", finalUser ) );
		this.text = finalUser;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		UserName other = (UserName) o;
		return text.equalsIgnoreCase( other.text );
	}

	@Override
	public int hashCode() {
		return Objects.hash( text );
	}

	@Override
	public String toString() {
		return text;
	}
}
