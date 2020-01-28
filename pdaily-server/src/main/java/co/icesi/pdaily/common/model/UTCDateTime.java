package co.icesi.pdaily.common.model;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import co.icesi.pdaily.common.infrastructure.jpa.InstantConverter;

@Embeddable
public class UTCDateTime implements Serializable {
	@Column
	@Convert(converter = InstantConverter.class)
	private Instant date;

	protected UTCDateTime() {
	}

	private UTCDateTime(Long date) {
		setDate( date );
	}

	public static UTCDateTime of(Long date) {
		return new UTCDateTime( date );
	}

	public static UTCDateTime ofNullable(Long date) {
		if ( date == null ) {
			return null;
		}

		return new UTCDateTime( date );
	}

	private void setDate(Long date) {
		try {
			this.date = Instant.ofEpochMilli( date );
		} catch (DateTimeException exception) {
			throw new DateTimeException( "La fecha no cumple con la longitud estandar del Instant." );
		}
	}

	public Instant date() {
		return date;
	}

	public Long dateAsLong() {
		return date.toEpochMilli();
	}

	public static UTCDateTime actualDate() {
		return new UTCDateTime( Instant.now().toEpochMilli() );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		UTCDateTime that = (UTCDateTime) o;
		return date.equals( date );
	}

	@Override
	public int hashCode() {
		return Objects.hash( date );
	}

	@Override
	public String toString() {
		return date.toString();
	}
}
