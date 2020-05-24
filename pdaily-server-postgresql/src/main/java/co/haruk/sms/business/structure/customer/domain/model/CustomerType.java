package co.haruk.sms.business.structure.customer.domain.model;

import co.haruk.core.domain.model.guards.Guards;
import co.haruk.core.infrastructure.persistence.jpa.converters.BaseDBEnumConverter;

public enum CustomerType implements BaseDBEnumConverter.DBEnumValue {
	SUPPLIER( "S" ),
	BUYER( "B" );

	private final String dbKey;

	CustomerType(String dbKey) {
		this.dbKey = dbKey;
	}

	public static CustomerType of(String value) {
		try {
			Guards.requireNonNull( value, "El tipo del cliente no puede ser nulo" );
			return CustomerType.valueOf( value );
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException( String.format( "El tipo de cliente: %s no esta definido.", value ) );
		}
	}

	@Override
	public String dbKey() {
		return this.dbKey;
	}
}
