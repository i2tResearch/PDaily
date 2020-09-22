package co.icesi.pdaily.common.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MedicineConcentration {
	// Hace referencia que todos los valores que se ingresen seran en miligramgos
	public final static String TYPE_DOSE = "mg";

	@Column
	private int value;

	protected MedicineConcentration() {
	}

	private MedicineConcentration(int value) {
		setValue( value );
	}

	public static MedicineConcentration of(int value) {
		return new MedicineConcentration( value );
	}

	private void setValue(int value) {
		if ( value <= 0 ) {
			throw new IllegalArgumentException( "El valor de la dosis tiene que ser mayor que cero." );
		}
		this.value = value;
	}

	public String valueInFormat() {
		return value + " " + TYPE_DOSE;
	}

	public int value() {
		return value;
	}
}
