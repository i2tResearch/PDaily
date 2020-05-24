package co.haruk.sms.business.structure.address.domain.model.view;

import java.io.Serializable;

/**
 * @author andres2508 on 6/12/19
 **/
public final class AddressReadView implements Serializable {
	public String id;
	public String referencedId;
	public String line1;
	public String line2;
	public String cityId;
	public String city;
	public String stateId;
	public String state;
	public String countryId;
	public String country;
	public float latitude;
	public float longitude;
	public boolean isMain;
	public String description;

	protected AddressReadView() {
	}

	public AddressReadView(
			String id,
			String referencedId,
			String description,
			String line1,
			String line2,
			String cityId,
			String city,
			String stateId,
			String state,
			String countryId,
			String country,
			float latitude,
			float longitude,
			boolean isMain) {
		this.id = id;
		this.referencedId = referencedId;
		this.description = description;
		this.line1 = line1;
		this.line2 = line2;
		this.cityId = cityId;
		this.city = city;
		this.stateId = stateId;
		this.state = state;
		this.countryId = countryId;
		this.country = country;
		this.latitude = latitude;
		this.longitude = longitude;
		this.isMain = isMain;
	}

	public static AddressReadView of(
			String id,
			String referencedId,
			String description,
			String line1,
			String line2,
			String cityId,
			String city,
			String stateId,
			String state,
			String countryId,
			String country,
			float latitude,
			float longitude,
			boolean isMain) {
		return new AddressReadView(
				id,
				referencedId,
				description,
				line1,
				line2,
				cityId,
				city,
				stateId,
				state,
				countryId,
				country,
				latitude,
				longitude,
				isMain
		);
	}
}
