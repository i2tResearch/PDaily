package co.icesi.pdaily.security.user.domain.model;

import co.haruk.core.infrastructure.persistence.jpa.converters.BaseDBEnumConverter;

/**
 * @author andres2508 on 26/2/20
 **/
public enum UserType implements BaseDBEnumConverter.DBEnumValue {
	SERVICE( "S" ),
	NORMAL( "N" );

	private final String dbKey;

	UserType(String dbKey) {
		this.dbKey = dbKey;
	}

	@Override
	public String dbKey() {
		return this.dbKey;
	}
}
