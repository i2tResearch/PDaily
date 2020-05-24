package co.haruk.sms.common.infrastructure.jpa;

import java.time.Instant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class InstantConverter implements AttributeConverter<Instant, Long> {

	@Override
	public Long convertToDatabaseColumn(Instant date) {
		return date.toEpochMilli();
	}

	@Override
	public Instant convertToEntityAttribute(Long date) {
		return Instant.ofEpochMilli( date );
	}
}
