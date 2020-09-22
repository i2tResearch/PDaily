package co.icesi.pdaily.business.structure.address.domain.model.view;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.icesi.pdaily.business.structure.address.domain.model.Address;
import co.icesi.pdaily.business.structure.geography.domain.model.GeographyService;
import co.icesi.pdaily.business.structure.geography.domain.model.view.CityTextView;

/**
 * @author andres2508 on 7/12/19
 **/
@Dependent
public class AddressReadViewBuilder {
	@Inject
	GeographyService geographyService;

	public AddressReadView buildFor(Address address) {
		final CityTextView cityView = geographyService.findCityTextView( address.cityId() );
		final var line2String = address.line2() != null ? address.line2().text() : null;
		final String description = address.description() != null ? address.description().text() : null;
		return AddressReadView.of(
				address.id().text(),
				address.referencedId().text(),
				description,
				address.line1().text(),
				line2String,
				cityView.cityId,
				cityView.city,
				cityView.stateId,
				cityView.state,
				cityView.countryId,
				cityView.country,
				address.geolocation().latitude(),
				address.geolocation().longitude(),
				address.isMain()
		);
	}
}
