package co.haruk.sms.business.structure.businessunit.productline.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.productline.domain.model.ProductLine;
import co.haruk.sms.business.structure.businessunit.productline.domain.model.ProductLineId;
import co.haruk.sms.business.structure.businessunit.productline.domain.model.ProductLineValidator;
import co.haruk.sms.business.structure.businessunit.productline.infrastructure.persistence.ProductLineRepository;

@ApplicationScoped
public class ProductLineAppService {
	@Inject
	ProductLineRepository repository;
	@Inject
	ProductLineValidator validator;

	@Transactional
	public ProductLineDTO saveProductLine(ProductLineDTO dto) {
		final var changed = dto.toProductLine();
		ProductLine saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( ProductLineId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return ProductLineDTO.of( saved );
	}

	public ProductLineDTO findOrFail(String id) {
		final var found = repository.findOrFail( ProductLineId.ofNotNull( id ) );
		return ProductLineDTO.of( found );
	}

	public List<ProductLineDTO> findAll() {
		final var all = repository.findAll();
		return StreamUtils.map( all, ProductLineDTO::of );
	}

	public List<ProductLineDTO> findForBusiness(String businessUnit) {
		final var all = repository.findForBusinessUnit( BusinessUnitId.ofNotNull( businessUnit ) );
		return StreamUtils.map( all, ProductLineDTO::of );
	}

	@Transactional
	public void deleteProductLine(String id) {
		final var lineId = ProductLineId.ofNotNull( id );
		validator.checkBeforeDelete( lineId );
		repository.delete( lineId );
	}
}
