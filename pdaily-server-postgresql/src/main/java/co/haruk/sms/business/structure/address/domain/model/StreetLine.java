package co.haruk.sms.business.structure.address.domain.model;

import static co.haruk.core.domain.model.guards.Guards.require;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import co.haruk.core.domain.model.text.StringOps;

/**
 * @author cristhiank on 6/12/19
 **/
@Embeddable
public class StreetLine implements Serializable {
	@Column(name = "street_line")
	private String text;

	protected StreetLine() {
	}

	private StreetLine(String text) {
		setText( text );
	}

	public static StreetLine of(String text) {
		return new StreetLine( text );
	}

	public String text() {
		return text;
	}

	private void setText(String text) {
		require( StringOps.isNotNullOrEmpty( text ), "La linea de dirección es requerida" );
		this.text = StringOps.trimAndCleanSpaces( text );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		StreetLine that = (StreetLine) o;
		return text.equalsIgnoreCase( that.text );
	}

	@Override
	public int hashCode() {
		return Objects.hash( text );
	}

	@Override
	public String toString() {
		return this.text;
	}
}
