package co.haruk.sms.common.model;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UTCDateTime implements Serializable, Comparable<UTCDateTime> {
	@Column
	private Instant date;

	protected UTCDateTime() {
	}

	private UTCDateTime(Long date) {
		setDate( date );
	}

	private UTCDateTime(Instant date) {
		this.date = date;
	}

	public static UTCDateTime of(Long date) {
		return new UTCDateTime( date );
	}

	public static UTCDateTime now() {
		return new UTCDateTime( Instant.now() );
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

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		UTCDateTime that = (UTCDateTime) o;
		return date.equals( that.date );
	}

	@Override
	public int hashCode() {
		return Objects.hash( date );
	}

	@Override
	public String toString() {
		return date.toString();
	}

	@Override
	public int compareTo(UTCDateTime o) {
		return this.date().compareTo( o.date() );
	}

	public UTCDateTime plusHours(int hours) {
		return new UTCDateTime( this.date.plus( hours, ChronoUnit.HOURS ) );
	}

	public UTCDateTime minusHours(int hours) {
		return new UTCDateTime( this.date.minus( hours, ChronoUnit.HOURS ) );
	}
}
