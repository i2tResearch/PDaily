package co.haruk.sms.common.model;

import static co.haruk.core.domain.model.guards.Guards.require;
import static co.haruk.core.domain.model.text.StringOps.isNullOrEmpty;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PhoneNumber implements Serializable {
	private static final Pattern pattern = Pattern.compile( "(^\\d{7}$|^\\d{10}$)" );

	@Column
	private String number;

	protected PhoneNumber() {
	}

	private PhoneNumber(String number) {
		setNumber( number );
	}

	public static PhoneNumber of(String numberString) {
		return new PhoneNumber( numberString );
	}

	public static boolean isValid(String numberString) {
		return pattern.matcher( numberString ).matches();
	}

	public static PhoneNumber ofNullable(String number) {
		if ( isNullOrEmpty( number ) ) {
			return null;
		}

		return new PhoneNumber( number );
	}

	public String text() {
		return number;
	}

	private void setNumber(String number) {
		require( isValid( number ), String.format( "El telefono %s es invalido", number ) );
		this.number = number;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		PhoneNumber that = (PhoneNumber) o;
		return number.equalsIgnoreCase( that.number );
	}

	@Override
	public int hashCode() {
		return Objects.hash( number );
	}

	@Override
	public String toString() {
		return number;
	}
}
