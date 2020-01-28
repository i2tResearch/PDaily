package co.icesi.pdaily.business.structure.geography.domain.model.view;

import java.util.UUID;

/**
 * @author cristhiank on 7/12/19
 **/
public final class CityTextView {
	public final String cityId;
	public final String city;
	public final String stateId;
	public final String state;
	public final String countryId;
	public final String country;

	public CityTextView(UUID cityId, String city, UUID stateId, String state, UUID countryId, String country) {
		this.cityId = cityId.toString();
		this.city = city;
		this.stateId = stateId.toString();
		this.state = state;
		this.countryId = countryId.toString();
		this.country = country;
	}
}
