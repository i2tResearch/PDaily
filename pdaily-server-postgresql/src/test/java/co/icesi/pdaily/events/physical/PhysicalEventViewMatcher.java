package co.icesi.pdaily.events.physical;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.JsonbBuilder;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.events.physical.app.BodyPartDetailDTO;
import co.icesi.pdaily.events.physical.app.PhysicalEventDTO;
import co.icesi.pdaily.events.physical.domain.model.view.BodyPartDetailReadView;
import co.icesi.pdaily.events.physical.domain.model.view.PhysicalEventReadView;

public class PhysicalEventViewMatcher extends BaseMatcher<String> {

	private final PhysicalEventDTO request;
	private final List<String> errors = new ArrayList<>();

	private PhysicalEventViewMatcher(PhysicalEventDTO dto) {
		request = dto;
	}

	public static PhysicalEventViewMatcher of(PhysicalEventDTO dto) {
		return new PhysicalEventViewMatcher( dto );
	}

	@Override
	public boolean matches(Object actual) {
		final var readView = JsonbBuilder.create().fromJson( actual.toString(), PhysicalEventReadView.class );
		boolean result = readView.injuryTypeId.equalsIgnoreCase( request.injuryTypeId )
				&& readView.intensity == request.intensity && readView.initialDate.equals( request.initialDate ) &&
				readView.finalDate.equals( request.finalDate );

		addErrorIfFalse( result, "Basic data didn't match" );

		result = result && detailsValidate( request.bodyDetails, readView.bodyDetails );
		addErrorIfFalse( result, "Detail data didn't match" );

		return result;
	}

	private boolean detailsValidate(List<BodyPartDetailDTO> detailRequest,
			List<BodyPartDetailReadView> detailReadView) {
		boolean result = true;
		if ( detailRequest != null ) {
			result = result && detailRequest.size() == detailReadView.size();
			for ( BodyPartDetailDTO campaign : detailRequest ) {
				BodyPartDetailReadView campaignRDV = StreamUtils
						.find( detailReadView, it -> it.bodyPartId.equalsIgnoreCase( campaign.bodyPartId ) ).get();
			}
		} else if ( detailReadView != null && !detailReadView.isEmpty() ) {
			result = false;
		}
		return result;
	}

	private void addErrorIfFalse(boolean result, String message) {
		if ( !result ) {
			errors.add( message );
		}
	}

	@Override
	public void describeTo(Description description) {

	}
}
