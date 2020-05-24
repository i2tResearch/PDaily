package co.haruk.sms.common.model;

import co.haruk.core.infrastructure.persistence.jpa.converters.BaseDBEnumConverter;

/**
 * @author andres2508 on 10/12/19
 **/
public enum ActiveInactiveState implements BaseDBEnumConverter.DBEnumValue {
	ACTIVE( "A" ),
	INACTIVE( "I" );

	private final String dbKey;

	ActiveInactiveState(String dbKey) {
		this.dbKey = dbKey;
	}

	@Override
	public String dbKey() {
		return this.dbKey;
	}
}
