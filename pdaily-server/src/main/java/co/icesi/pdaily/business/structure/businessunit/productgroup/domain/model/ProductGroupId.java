package co.icesi.pdaily.business.structure.businessunit.productgroup.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class ProductGroupId extends Identity {
	protected ProductGroupId() {

	}

	private ProductGroupId(String id) {
		super( id );
	}

	private ProductGroupId(UUID id) {
		super( id );
	}

	public static ProductGroupId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new ProductGroupId( id );
	}

	public static ProductGroupId ofNotNull(String id) {
		return new ProductGroupId( id );
	}

	public static ProductGroupId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new ProductGroupId( id );
	}

	public static ProductGroupId generateNew() {
		return of( UUID.randomUUID() );
	}

}
