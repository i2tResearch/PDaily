package co.icesi.pdaily.business.structure.businessunit.productbrand.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.productbrand.domain.model.ProductBrand;
import co.icesi.pdaily.business.structure.businessunit.productbrand.domain.model.ProductBrandId;
import co.icesi.pdaily.business.structure.businessunit.productbrand.domain.model.ProductBrandValidator;
import co.icesi.pdaily.business.structure.businessunit.productbrand.infrastructure.persistence.ProductBrandRepository;

@ApplicationScoped
public class ProductBrandAppService {
	@Inject
	ProductBrandRepository repository;
	@Inject
	ProductBrandValidator validator;

	@Transactional
	public ProductBrandDTO saveProductBrand(ProductBrandDTO dto) {
		final var changed = dto.toProductBrand();
		ProductBrand saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( ProductBrandId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return ProductBrandDTO.of( saved );
	}

	public ProductBrandDTO findOrFail(String id) {
		final var found = repository.findOrFail( ProductBrandId.ofNotNull( id ) );
		return ProductBrandDTO.of( found );
	}

	public List<ProductBrandDTO> findAll() {
		final var all = repository.findAll();
		return StreamUtils.map( all, ProductBrandDTO::of );
	}

	public List<ProductBrandDTO> findForBusiness(String businessUnit) {
		final var all = repository.findForBusinessUnit( BusinessUnitId.ofNotNull( businessUnit ) );
		return StreamUtils.map( all, ProductBrandDTO::of );
	}

	@Transactional
	public void deleteProductBrand(String id) {
		final var brandId = ProductBrandId.ofNotNull( id );
		validator.checkBeforeDelete( brandId );
		repository.delete( brandId );
	}
}
