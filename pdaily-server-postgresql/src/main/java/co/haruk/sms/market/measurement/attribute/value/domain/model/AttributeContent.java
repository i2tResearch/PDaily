package co.haruk.sms.market.measurement.attribute.value.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;
import static co.haruk.core.domain.model.text.StringOps.isNullOrEmpty;
import static co.haruk.core.domain.model.text.StringOps.removeSurrounding;
import static co.haruk.core.domain.model.text.StringOps.trimAndCleanSpaces;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class AttributeContent implements Serializable {

	@Type(type = "jsonb")
	@Column(name = "value")
	private String value;

	protected AttributeContent() {
	}

	protected AttributeContent(String value) {
		this.setValue( value );
	}

	public static AttributeContent of(String value) {
		if ( isNullOrEmpty( value ) ) {
			throw new IllegalArgumentException( "El contenido del atributo no puede ser nulo o estar vacio." );
		}
		return new AttributeContent( value );
	}

	private void setValue(String value) {
		final var finalRef = trimAndCleanSpaces( requireNonNull( value, "Debe indicar el valor" ) );
		if ( isBoolean( finalRef ) ) {
			this.value = finalRef.toLowerCase(); // Lower boolean is always valid for JSON
		} else {
			this.value = finalRef;
		}
	}

	private boolean isBoolean(String value) {
		return value.equalsIgnoreCase( "true" ) || value.equalsIgnoreCase( "false" );
	}

	public String value() {
		return this.value;
	}

	public String asSingleString() {
		return removeSurrounding( this.value, "\"" );
	}

	public boolean contains(Identity value) {
		return value != null && (this.value.toUpperCase().contains( value.text().toUpperCase() ));
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		AttributeContent that = (AttributeContent) o;
		return value.equalsIgnoreCase( that.value );
	}

	@Override
	public int hashCode() {
		return Objects.hash( value );
	}

	public String toString() {
		return this.value;
	}
}
