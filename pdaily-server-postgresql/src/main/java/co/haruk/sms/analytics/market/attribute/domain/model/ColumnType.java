package co.haruk.sms.analytics.market.attribute.domain.model;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.function.Function;

import javax.json.Json;
import javax.json.JsonValue;

/**
 * @author cristhiank on 20/5/20
 **/
public enum ColumnType {
	STRING( Json::createValue ),
	BOOLEAN( (o) -> Boolean.parseBoolean( o ) ? JsonValue.TRUE : JsonValue.FALSE ),
	NUMBER( (o) -> Json.createValue( Double.parseDouble( o ) ) );

	final Function<String, JsonValue> converter;

	ColumnType(Function<String, JsonValue> converter) {
		this.converter = converter;
	}

	public static ColumnType ofString(String name) {
		requireNonNull( name, "Invalid column type" );
		for ( ColumnType value : ColumnType.values() ) {
			if ( value.name().equalsIgnoreCase( name ) ) {
				return value;
			}
		}
		// String as default for unknown types
		return ColumnType.STRING;
	}

	public JsonValue toJSON(String value) {
		if ( value == null ) {
			return JsonValue.NULL;
		}
		return converter.apply( value );
	}
}
