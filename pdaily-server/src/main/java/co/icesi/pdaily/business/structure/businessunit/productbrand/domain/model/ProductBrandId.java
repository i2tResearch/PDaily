package co.icesi.pdaily.business.structure.businessunit.productbrand.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class ProductBrandId extends Identity {

	protected ProductBrandId() {
	}

	private ProductBrandId(String id) {
		super( id );
	}

	private ProductBrandId(UUID id) {
		super( id );
	}

	public static ProductBrandId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new ProductBrandId( id );
	}

	public static ProductBrandId ofNotNull(String id) {
		return new ProductBrandId( id );
	}

	public static ProductBrandId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new ProductBrandId( id );
	}

	public static ProductBrandId generateNew() {
		return of( UUID.randomUUID() );
	}
}
