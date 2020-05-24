package co.haruk.sms.business.structure.businessunit.productline.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.business.structure.businessunit.product.infrastructure.persistence.ProductRepository;
import co.haruk.sms.business.structure.businessunit.productline.infrastructure.persistence.ProductLineRepository;

@Dependent
public class ProductLineValidator {
	@Inject
	ProductLineRepository productLineRepository;
	@Inject
	ProductRepository productRepository;

	public void validate(ProductLine productLine) {
		failIfDuplicatedName( productLine );
		if ( productLine.reference() != null ) {
			failIfDuplicatedReference( productLine );
		}
	}

	private void failIfDuplicatedReference(ProductLine productLine) {
		var found = productLineRepository.findByReference( productLine.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !productLine.isPersistent() || !existent.equals( productLine );
			if ( mustFail ) {
				throw new DuplicatedRecordException( productLine.reference() );
			}
		}
	}

	private void failIfDuplicatedName(ProductLine productLine) {
		var found = productLineRepository.findByName( productLine.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !productLine.isPersistent() || !existent.equals( productLine );
			if ( mustFail ) {
				throw new DuplicatedRecordException( productLine.name() );
			}
		}
	}

	public void checkBeforeDelete(ProductLineId lineId) {
		final boolean hasProducts = productRepository.existsForLine( lineId );
		check( !hasProducts, "No puede eliminar la linea de producto, tiene productos asignados" );
	}
}
