package co.icesi.pdaily.events.physical.domain.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Intensity implements Serializable {
	public final int MAX_INTENSITY = 10;

	@Column
	private int intensity;

	protected Intensity() {
	}

	private Intensity(int intensity) {
		setIntensity( intensity );
	}

	public static Intensity of(int intensity) {
		return new Intensity( intensity );
	}

	public int intensity() {
		return intensity;
	}

	private void setIntensity(int intensity) {
		if ( intensity > MAX_INTENSITY ) {
			throw new IllegalArgumentException( "La intensidad sobrepasa el nivel maximo permitido" );
		} else if ( intensity < 0 ) {
			throw new IllegalArgumentException( "La intensidad no supera el nivel minimo permitido" );
		}

		this.intensity = intensity;
	}

	@Override
	public int hashCode() {
		return Objects.hash( intensity );
	}
}
