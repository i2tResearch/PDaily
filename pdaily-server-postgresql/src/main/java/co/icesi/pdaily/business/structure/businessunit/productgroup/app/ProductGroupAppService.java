package co.icesi.pdaily.business.structure.businessunit.productgroup.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.productgroup.domain.model.ProductGroup;
import co.icesi.pdaily.business.structure.businessunit.productgroup.domain.model.ProductGroupId;
import co.icesi.pdaily.business.structure.businessunit.productgroup.domain.model.ProductGroupValidator;
import co.icesi.pdaily.business.structure.businessunit.productgroup.infrastructure.persistence.ProductGroupRepository;

@ApplicationScoped
public class ProductGroupAppService {
	@Inject
	ProductGroupRepository repository;
	@Inject
	ProductGroupValidator validator;

	@Transactional
	public ProductGroupDTO saveProductGroup(ProductGroupDTO dto) {
		final var changed = dto.toProductGroup();
		ProductGroup saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( ProductGroupId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return ProductGroupDTO.of( saved );
	}

	public ProductGroupDTO findOrFail(String id) {
		final var found = repository.findOrFail( ProductGroupId.ofNotNull( id ) );
		return ProductGroupDTO.of( found );
	}

	public List<ProductGroupDTO> findAll() {
		final var all = repository.findAll();
		return StreamUtils.map( all, ProductGroupDTO::of );
	}

	public List<ProductGroupDTO> findForBusiness(String businessUnit) {
		final var all = repository.findForBusinessUnit( BusinessUnitId.ofNotNull( businessUnit ) );
		return StreamUtils.map( all, ProductGroupDTO::of );
	}

	@Transactional
	public void deleteProductGroup(String id) {
		final var groupId = ProductGroupId.ofNotNull( id );
		validator.checkBeforeDelete( groupId );
		repository.delete( groupId );
	}
}
