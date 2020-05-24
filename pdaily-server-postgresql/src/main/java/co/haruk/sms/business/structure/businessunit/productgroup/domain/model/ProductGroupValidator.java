package co.haruk.sms.business.structure.businessunit.productgroup.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.business.structure.businessunit.product.infrastructure.persistence.ProductRepository;
import co.haruk.sms.business.structure.businessunit.productgroup.infrastructure.persistence.ProductGroupRepository;

@Dependent
public class ProductGroupValidator {
	@Inject
	ProductGroupRepository repository;
	@Inject
	ProductRepository productRepository;

	public void validate(ProductGroup productGroup) {
		failIfDuplicatedName( productGroup );
		if ( productGroup.reference() != null ) {
			failIfDuplicatedReference( productGroup );
		}
	}

	private void failIfDuplicatedReference(ProductGroup productGroup) {
		var found = repository.findByReference( productGroup.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !productGroup.isPersistent() || !existent.equals( productGroup );
			if ( mustFail ) {
				throw new DuplicatedRecordException( productGroup.reference() );
			}
		}
	}

	private void failIfDuplicatedName(ProductGroup productGroup) {
		var found = repository.findByName( productGroup.getName() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !productGroup.isPersistent() || !existent.equals( productGroup );
			if ( mustFail ) {
				throw new DuplicatedRecordException( productGroup.getName() );
			}
		}
	}

	public void checkBeforeDelete(ProductGroupId groupId) {
		final boolean hasProducts = productRepository.existsForGroup( groupId );
		check( !hasProducts, "No puede eliminar el grupo de producto, tiene productos asignados" );
	}
}
