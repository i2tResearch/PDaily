package co.haruk.sms.market.measurement.attribute.domain.model;

import javax.persistence.Column;

import co.haruk.core.domain.model.guards.Guards;
import co.haruk.core.infrastructure.persistence.jpa.converters.BaseDBEnumConverter;

public enum SubjectType implements BaseDBEnumConverter.DBEnumValue {
	CUSTOMER( "CUSTOMER" ),
	CONTACT( "CONTACT" );

	@Column
	private final String dbKey;

	SubjectType(String dbKey) {
		this.dbKey = dbKey;
	}

	public static SubjectType of(String value) {
		try {
			Guards.requireNonNull( value, "El tipo de la entidad es necesaria." );
			return SubjectType.valueOf( value );
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException( String.format( "El tipo de la entidad: %s no esta definido.", value ) );
		}
	}

	@Override
	public String dbKey() {
		return dbKey;
	}
}
