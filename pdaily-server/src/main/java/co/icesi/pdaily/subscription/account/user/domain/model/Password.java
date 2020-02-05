package co.icesi.pdaily.subscription.account.user.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Objects;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

import co.haruk.core.infrastructure.persistence.jpa.converters.CryptoConverter;

/**
 * @author cristhiank on 15/11/19
 **/
@Embeddable
public class Password {
	@Convert(converter = CryptoConverter.class)
	private String text;

	protected Password() {
	}

	private Password(String password) {
		setText( password );
	}

	public static Password of(String password) {
		return new Password( password );
	}

	public String text() {
		return text;
	}

	private void setText(String password) {
		this.text = requireNonNull( password, "La contraseña es requerida" );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Password password1 = (Password) o;
		return text.equals( password1.text );
	}

	@Override
	public int hashCode() {
		return Objects.hash( text );
	}
}
