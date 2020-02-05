package co.icesi.pdaily.business.structure.businessunit.productbrand.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.businessunit.product.infrastructure.persistence.ProductRepository;
import co.icesi.pdaily.business.structure.businessunit.productbrand.infrastructure.persistence.ProductBrandRepository;

@Dependent
public class ProductBrandValidator {
	@Inject
	ProductBrandRepository productBrandRepository;
	@Inject
	ProductRepository productRepository;

	public void validate(ProductBrand productBrand) {
		failIfDuplicatedName( productBrand );
	}

	private void failIfDuplicatedName(ProductBrand productBrand) {
		var found = productBrandRepository.findByName( productBrand.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !productBrand.isPersistent() || !existent.equals( productBrand );
			if ( mustFail ) {
				throw new DuplicatedRecordException( productBrand.name() );
			}
		}
	}

	public void checkBeforeDelete(ProductBrandId brandId) {
		final boolean hasProducts = productRepository.existsForBrand( brandId );
		check( !hasProducts, "No puede eliminar la marca del producto, tiene productos asignados" );
	}
}
