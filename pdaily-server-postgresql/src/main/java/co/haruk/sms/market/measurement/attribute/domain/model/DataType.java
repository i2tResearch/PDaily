package co.haruk.sms.market.measurement.attribute.domain.model;

import co.haruk.core.domain.model.guards.Guards;
import co.haruk.core.infrastructure.persistence.jpa.converters.BaseDBEnumConverter;

public enum DataType implements BaseDBEnumConverter.DBEnumValue {
	LIST_SINGLE_ANS( "LIST_SINGLE_ANS" ),
	LIST_MULTIPLE_ANS( "LIST_MULTIPLE_ANS" ),
	BOOLEAN( "BOOLEAN" ),
	NUMBER( "NUMBER" ),
	STRING( "STRING" );

	private final String dbKey;

	DataType(String dbKey) {
		this.dbKey = dbKey;
	}

	public static DataType of(String value) {
		try {
			Guards.requireNonNull( value, "El tipo del dato es necesario." );
			return DataType.valueOf( value );
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException( String.format( "El tipo de dato: %s no esta definido.", value ) );
		}
	}

	public static boolean isListType(DataType type) {
		return type.equals( LIST_MULTIPLE_ANS ) || type.equals( LIST_SINGLE_ANS );
	}

	public static boolean isMultipleList(DataType type) {
		return type.equals( LIST_MULTIPLE_ANS );
	}

	@Override
	public String dbKey() {
		return dbKey;
	}
}
