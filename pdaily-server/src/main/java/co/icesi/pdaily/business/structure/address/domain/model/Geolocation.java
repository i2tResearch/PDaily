package co.icesi.pdaily.business.structure.address.domain.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.guards.Guards;

/**
 * @author cristhiank on 6/12/19
 **/
@Embeddable
public class Geolocation implements Serializable {
	private float latitude;
	private float longitude;

	protected Geolocation() {
	}

	private Geolocation(float latitude, float longitude) {
		setLatitude( latitude );
		setLongitude( longitude );
	}

	public static Geolocation of(float latitude, float longitude) {
		return new Geolocation( latitude, longitude );
	}

	public float latitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		Guards.require( latitude >= -90 && latitude <= 90, "La latitud es invalida" );
		this.latitude = latitude;
	}

	public float longitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		Guards.require( longitude >= -180 && longitude <= 180, "La longitud es invalida" );
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o )
			return true;
		if ( o == null || getClass() != o.getClass() )
			return false;
		Geolocation that = (Geolocation) o;
		return Float.compare( that.latitude, latitude ) == 0 &&
				Float.compare( that.longitude, longitude ) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash( latitude, longitude );
	}

	@Override
	public String toString() {
		return "Geolocation{" + latitude + ", " + longitude + '}';
	}
}
