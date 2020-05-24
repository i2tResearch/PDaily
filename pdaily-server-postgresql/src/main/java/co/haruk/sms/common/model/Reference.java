package co.haruk.sms.common.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;
import static co.haruk.core.domain.model.text.StringOps.isNullOrEmpty;
import static co.haruk.core.domain.model.text.StringOps.trimAndCleanSpaces;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Reference implements Serializable {
	@Column(name = "reference")
	private String text;

	protected Reference() {
	}

	protected Reference(String reference) {
		this.setReference( reference );
	}

	public static Reference of(String reference) {
		if ( isNullOrEmpty( reference ) ) {
			return null;
		}
		return new Reference( reference );
	}

	private void setReference(String reference) {
		final var finalRef = requireNonNull( reference, "Debe indicar la referencia" );
		this.text = trimAndCleanSpaces( finalRef );
	}

	public String text() {
		return this.text;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Reference that = (Reference) o;
		return text.equalsIgnoreCase( that.text );
	}

	@Override
	public int hashCode() {
		return Objects.hash( text );
	}

	public String toString() {
		return this.text;
	}
}
