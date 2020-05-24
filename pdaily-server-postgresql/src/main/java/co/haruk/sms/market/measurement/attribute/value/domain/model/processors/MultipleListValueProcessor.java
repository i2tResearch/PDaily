package co.haruk.sms.market.measurement.attribute.value.domain.model.processors;

import static co.haruk.core.domain.model.text.StringOps.removeSurrounding;
import static co.haruk.core.domain.model.text.StringOps.trimAndCleanSpaces;

import java.io.StringReader;
import java.util.List;

import javax.json.Json;
import javax.json.JsonException;

import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeContent;

/**
 * @author andres2508 on 9/5/20
 **/
public class MultipleListValueProcessor {
	private List<String> content;

	private MultipleListValueProcessor(List<String> content) {
		this.content = content;
	}

	public static MultipleListValueProcessor of(AttributeContent content) {
		try {
			final var reader = Json.createReader( new StringReader( content.value() ) );
			final var list = reader.readArray().getValuesAs( it -> sanitizeId( it.toString() ) );
			return new MultipleListValueProcessor( list );
		} catch (JsonException ex) {
			throw new IllegalArgumentException( "El valor JSON de la lista es invalido" );
		}
	}

	public static String sanitizeId(String jsonValue) {
		return trimAndCleanSpaces( removeSurrounding( jsonValue, "\"" ) );
	}

	public void removeValue(String value) {
		content.removeIf( it -> isEqualString( it, value ) );
	}

	private boolean isEqualString(String jsonValue, String other) {
		return sanitizeId( jsonValue ).equalsIgnoreCase( sanitizeId( other ) );
	}

	public boolean isEmpty() {
		return content.isEmpty();
	}

	public List<String> content() {
		return content;
	}

	public AttributeContent asContent() {
		final String value = Json.createArrayBuilder( content ).build().toString();
		return AttributeContent.of( value );
	}
}
