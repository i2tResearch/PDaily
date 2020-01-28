package co.icesi.pdaily.business.structure.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.json.bind.JsonbBuilder;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jetbrains.annotations.NotNull;

import co.icesi.pdaily.business.structure.address.domain.model.AddressRequest;
import co.icesi.pdaily.business.structure.address.domain.model.view.AddressReadView;
import co.icesi.pdaily.business.structure.customer.app.CustomerRequestDTO;
import co.icesi.pdaily.business.structure.customer.domain.model.view.CustomerReadView;

/**
 * @author cristhiank on 10/12/19
 **/
public class CustomerReadViewMatcher extends BaseMatcher<String> {

	private final CustomerRequestDTO request;
	private final List<String> errors = new ArrayList<>();

	private CustomerReadViewMatcher(CustomerRequestDTO dto) {
		this.request = dto;
	}

	public static CustomerReadViewMatcher of(CustomerRequestDTO dto) {
		return new CustomerReadViewMatcher( dto );
	}

	@Override
	public boolean matches(Object o) {
		final var readView = JsonbBuilder.create().fromJson( o.toString(), CustomerReadView.class );
		// Checks for basic consistency
		boolean result = readView.name.equals( request.name ) &&
				Objects.equals( readView.taxID, request.taxID ) &&
				readView.subsidiaryId.equalsIgnoreCase( request.subsidiaryId ) &&
				readView.subsidiaryName != null &&
				Objects.equals( readView.mainEmailAddress, request.mainEmailAddress ) &&
				Objects.equals( readView.reference, request.reference ) &&
				readView.type.equalsIgnoreCase( request.type );
		if ( request.id != null ) {
			result = result && request.id.equalsIgnoreCase( readView.id );
		}
		addErrorIfFalse( result, "Basic data didn't match" );
		// Checks for holding consistency
		if ( request.holdingId != null ) {
			final var holdingMatches = request.holdingId.equalsIgnoreCase( readView.holdingId ) &&
					readView.holdingName != null;
			addErrorIfFalse( holdingMatches, "Holding data didn't match" );
			result = result && holdingMatches;
		}
		// Checks for holding consistency
		final AddressRequest mainAddress = request.mainAddress;
		if ( mainAddress != null ) {
			final AddressReadView addressReadView = readView.mainAddress;
			final var addressMatches = addressReadView.id != null &&
					addressReadView.city != null &&
					addressReadView.state != null &&
					addressReadView.country != null &&
					addressReadView.cityId.equalsIgnoreCase( mainAddress.cityId ) &&
					Objects.equals( mainAddress.line1, addressReadView.line1 ) &&
					Objects.equals( mainAddress.line2, addressReadView.line2 ) &&
					Objects.equals( mainAddress.latitude, addressReadView.latitude ) &&
					Objects.equals( mainAddress.longitude, addressReadView.longitude ) &&
					Objects.equals( mainAddress.description, addressReadView.description );
			addErrorIfFalse( addressMatches, "Address data didn't match" );
			result = result && addressMatches;
		}
		return result;
	}

	private void addErrorIfFalse(boolean result, String message) {
		if ( !result ) {
			errors.add( message );
		}
	}

	@NotNull
	private String getErrorsJoined() {
		return String.join( ",", errors );
	}

	@Override
	public void describeTo(Description description) {
		description.appendText( getErrorsJoined() );
	}
}
