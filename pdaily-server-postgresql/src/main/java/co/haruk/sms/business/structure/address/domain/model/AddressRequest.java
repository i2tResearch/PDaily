package co.haruk.sms.business.structure.address.domain.model;

import static co.haruk.core.domain.model.guards.Guards.require;

import co.haruk.core.domain.model.text.StringOps;
import co.haruk.sms.business.structure.address.domain.model.view.AddressReadView;
import co.haruk.sms.business.structure.geography.domain.model.CityId;
import co.haruk.sms.common.model.Reference;

/**
 * @author cristhiank on 10/12/19
 **/
public final class AddressRequest {
	public String id;
	public String description;
	public String referencedId;
	public String line1;
	public String line2;
	public String cityId;
	public float latitude;
	public float longitude;
	public boolean isMain;

	protected AddressRequest() {
	}

	private AddressRequest(
			String id,
			String referencedId,
			String description,
			String line1,
			String line2,
			String cityId,
			float latitude,
			float longitude,
			boolean main) {
		this.id = id;
		this.referencedId = referencedId;
		this.description = description;
		this.line1 = line1;
		this.line2 = line2;
		this.cityId = cityId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isMain = main;
	}

	public static AddressRequest of(
			String id,
			String referencedId,
			String description,
			String line1,
			String line2,
			String cityId,
			float latitude,
			float longitude,
			boolean main) {
		return new AddressRequest(
				id, referencedId, description, line1, line2, cityId, latitude, longitude, main
		);
	}

	public static AddressRequest of(
			String id,
			String referencedId,
			String line1,
			String cityId) {
		return new AddressRequest( id, referencedId, null, line1, null, cityId, 0, 0, false );
	}

	public static AddressRequest of(AddressReadView addressView) {
		return new AddressRequest(
				addressView.id,
				addressView.referencedId,
				addressView.description,
				addressView.line1,
				addressView.line2,
				addressView.cityId,
				addressView.latitude,
				addressView.longitude,
				addressView.isMain
		);
	}

	Address toAddress() {
		require( isValid(), "La dirección es invalida" );
		final var streetLine2 = StringOps.isNotNullOrEmpty( line2 ) ? StreetLine.of( line2 ) : null;
		return Address.of(
				AddressId.of( id ),
				ReferencedId.ofNotNull( referencedId ),
				Reference.of( description ),
				StreetLine.of( line1 ),
				streetLine2,
				CityId.ofNotNull( cityId ),
				Geolocation.of( latitude, longitude ),
				isMain
		);
	}

	private boolean isValid() {
		return cityId != null && line1 != null;
	}
}
