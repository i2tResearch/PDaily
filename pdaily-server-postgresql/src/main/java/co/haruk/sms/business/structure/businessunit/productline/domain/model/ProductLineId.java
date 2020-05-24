package co.haruk.sms.business.structure.businessunit.productline.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class ProductLineId extends Identity {

	protected ProductLineId() {
	}

	private ProductLineId(String id) {
		super( id );
	}

	private ProductLineId(UUID id) {
		super( id );
	}

	public static ProductLineId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new ProductLineId( id );
	}

	public static ProductLineId ofNotNull(String id) {
		return new ProductLineId( id );
	}

	public static ProductLineId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new ProductLineId( id );
	}

	public static ProductLineId generateNew() {
		return of( UUID.randomUUID() );
	}
}
