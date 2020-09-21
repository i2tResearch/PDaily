package co.icesi.pdaily.business.structure.geography.domain.model;

import static co.haruk.core.domain.model.guards.Guards.require;
import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

/**
 * @author andres2508 on 2/12/19
 **/
@Embeddable
public class CountryIsoCode implements Serializable {
	private String code;

	protected CountryIsoCode() {
	}

	private CountryIsoCode(String code) {
		setCode( code );
	}

	public static CountryIsoCode of(String code) {
		return new CountryIsoCode( code );
	}

	public String text() {
		return code;
	}

	public void setCode(String code) {
		final var finalCode = requireNonNull( code, "El codigo del pais es requerido" ).trim();
		require( finalCode.length() == 2, "El codigo del pais debe ser de dos digitos" );
		this.code = finalCode.toUpperCase();
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		CountryIsoCode that = (CountryIsoCode) o;
		return code.equalsIgnoreCase( that.code );
	}

	@Override
	public int hashCode() {
		return Objects.hash( code );
	}

	@Override
	public String toString() {
		return code;
	}
}
