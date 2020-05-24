package co.haruk.sms.events.levodopa.schedule.domain.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PillsDose implements Serializable {
	@Column
	private int dose;

	protected PillsDose() {
	}

	private PillsDose(int dose) {
		setDose( dose );
	}

	public static PillsDose of(int dose) {
		return new PillsDose( dose );
	}

	public int dose() {
		return dose;
	}

	private void setDose(int dose) {
		if ( dose <= 0 ) {
			throw new IllegalArgumentException( "La dosis del medicamento tiene que ser superior a cero." );
		}
		this.dose = dose;
	}
}
