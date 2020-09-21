package co.icesi.pdaily.business.structure.businessunit.product.domain.model;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.businessunit.product.infrastructure.persistence.ProductRepository;

@Dependent
public class ProductValidator {
	@Inject
	ProductRepository repository;

	public void validate(Product product) {
		failIfDuplicatedName( product );
		if ( product.reference() != null ) {
			failIfDuplicatedReference( product );
		}
	}

	private void failIfDuplicatedReference(Product product) {
		var found = repository.findByReference( product.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !product.isPersistent() || !existent.equals( product );
			if ( mustFail ) {
				throw new DuplicatedRecordException( product.reference() );
			}
		}
	}

	private void failIfDuplicatedName(Product product) {
		var found = repository.findByName( product.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !product.isPersistent() || !existent.equals( product );
			if ( mustFail ) {
				throw new DuplicatedRecordException( product.name() );
			}
		}
	}
}
