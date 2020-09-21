package co.icesi.pdaily.business.structure.patient.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.json.bind.JsonbBuilder;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import co.icesi.pdaily.business.structure.address.domain.model.AddressRequest;
import co.icesi.pdaily.business.structure.address.domain.model.view.AddressReadView;
import co.icesi.pdaily.business.structure.patient.contact.app.ContactRequestDTO;
import co.icesi.pdaily.business.structure.patient.contact.domain.model.view.ContactReadView;

public class ContactReadViewMatcher extends BaseMatcher<String> {

	private final ContactRequestDTO request;
	private final List<String> errors = new ArrayList<>();

	private ContactReadViewMatcher(ContactRequestDTO dto) {
		this.request = dto;
	}

	public static ContactReadViewMatcher of(ContactRequestDTO dto) {
		return new ContactReadViewMatcher( dto );
	}

	@Override
	public boolean matches(Object o) {
		final var readView = JsonbBuilder.create().fromJson( o.toString(), ContactReadView.class );
		// Checks for basic consistency
		boolean result = readView.name.equals( request.name ) &&
				Objects.equals( readView.email, request.email ) &&
				Objects.equals( readView.mobilePhone, request.mobilePhone ) &&
				Objects.equals( readView.landlinePhone, request.landlinePhone );
		if ( request.id != null ) {
			result = result && request.id.equalsIgnoreCase( readView.id );
		}
		addErrorIfFalse( result, "Basic data didn't match" );
		// Checks for role consistency
		if ( request.roleId != null ) {
			final var roleMatches = request.roleId.equalsIgnoreCase( readView.roleId ) &&
					readView.roleName != null;
			addErrorIfFalse( roleMatches, "Contact Role data didn't match" );
			result = result && roleMatches;
		}
		// Checks for address consistency
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

	private String getErrorsJoined() {
		return String.join( ",", errors );
	}

	@Override
	public void describeTo(Description description) {
		description.appendText( getErrorsJoined() );
	}
}
