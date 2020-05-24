package co.haruk.sms.business.structure.businessunit.domain.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EffectiveActivityThreshold implements Serializable {
	private static final int DEFAULT_EFFECTIVE_THRESHOLD = 24;

	@Column
	private int hours;

	protected EffectiveActivityThreshold() {
	}

	private EffectiveActivityThreshold(int hours) {
		setHours( hours );
	}

	public static EffectiveActivityThreshold of(int hours) {
		return new EffectiveActivityThreshold( hours );
	}

	public static EffectiveActivityThreshold generateDefaultEffectiveThreshold() {
		return new EffectiveActivityThreshold( DEFAULT_EFFECTIVE_THRESHOLD );
	}

	private void setHours(int hours) {
		if ( hours < 0 ) {
			throw new IllegalArgumentException( "El tiempo para actividad una efectiva debe ser positivo y mayor a cero" );
		} else if ( hours == 0 ) {
			this.hours = DEFAULT_EFFECTIVE_THRESHOLD;
		} else {
			this.hours = hours;
		}
	}

	public int hours() {
		return this.hours;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		EffectiveActivityThreshold that = (EffectiveActivityThreshold) o;
		return hours == that.hours;
	}

	@Override
	public int hashCode() {
		return Objects.hash( hours );
	}
}
