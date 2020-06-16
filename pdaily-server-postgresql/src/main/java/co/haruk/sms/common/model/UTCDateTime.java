package co.haruk.sms.common.model;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
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

	public static UTCDateTime of(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd-MM-yyyy HH:mm:ss" );

		TemporalAccessor temporalAccessor = formatter.parse( date );
		LocalDateTime localDateTime = LocalDateTime.from( temporalAccessor );
		ZonedDateTime zonedDateTime = ZonedDateTime.of( localDateTime, ZoneId.systemDefault() );
		Instant result = Instant.from( zonedDateTime );
		return new UTCDateTime( result );
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

	public int dayOfWeek() {
		return date.atZone( ZoneId.systemDefault() ).getDayOfWeek().getValue();
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
