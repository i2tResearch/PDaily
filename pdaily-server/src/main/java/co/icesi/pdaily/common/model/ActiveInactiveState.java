package co.icesi.pdaily.common.model;

import co.haruk.core.infrastructure.persistence.jpa.converters.BaseDBEnumConverter;

/**
 * @author cristhiank on 10/12/19
 **/
public enum ActiveInactiveState implements BaseDBEnumConverter.DBEnumValue {
	ACTIVE( "A" ),
	INACTIVE( "I" );

	private String dbKey;

	ActiveInactiveState(String dbKey) {
		this.dbKey = dbKey;
	}

	@Override
	public String dbKey() {
		return this.dbKey;
	}
}
