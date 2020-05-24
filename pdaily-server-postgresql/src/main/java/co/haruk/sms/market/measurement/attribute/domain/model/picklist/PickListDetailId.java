package co.haruk.sms.market.measurement.attribute.domain.model.picklist;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class PickListDetailId extends Identity {

	protected PickListDetailId() {
	}

	private PickListDetailId(String id) {
		super( id );
	}

	private PickListDetailId(UUID id) {
		super( id );
	}

	public static PickListDetailId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new PickListDetailId( id );
	}

	public static PickListDetailId ofNotNull(String id) {
		return new PickListDetailId( id );
	}

	public static PickListDetailId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new PickListDetailId( id );
	}

	public static PickListDetailId generateNew() {
		return of( UUID.randomUUID() );
	}
}
