package co.icesi.pdaily.business.structure.businessunit.product.domain.model;

import java.util.UUID;

import javax.persistence.Embeddable;

import co.haruk.core.domain.model.entity.Identity;

@Embeddable
public class ProductId extends Identity {

	protected ProductId() {
	}

	private ProductId(String id) {
		super( id );
	}

	private ProductId(UUID id) {
		super( id );
	}

	public static ProductId of(String id) {
		if ( id == null ) {
			return null;
		}
		return new ProductId( id );
	}

	public static ProductId ofNotNull(String id) {
		return new ProductId( id );
	}

	public static ProductId of(UUID id) {
		if ( id == null ) {
			return null;
		}
		return new ProductId( id );
	}

	public static ProductId generateNew() {
		return of( UUID.randomUUID() );
	}
}
