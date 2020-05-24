package co.haruk.sms.business.structure.businessunit.product.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.sms.business.structure.businessunit.domain.model.BusinessUnitId;
import co.haruk.sms.business.structure.businessunit.product.domain.model.Product;
import co.haruk.sms.business.structure.businessunit.product.domain.model.ProductId;
import co.haruk.sms.business.structure.businessunit.product.domain.model.ProductValidator;
import co.haruk.sms.business.structure.businessunit.product.domain.model.view.ProductReadView;
import co.haruk.sms.business.structure.businessunit.product.infrastructure.persistence.ProductRepository;

@ApplicationScoped
public class ProductAppService {
	@Inject
	ProductRepository repository;
	@Inject
	ProductValidator validator;

	@Transactional
	public ProductRequestDTO saveForProduct(ProductRequestDTO productData) {
		final Product changed = productData.toProduct();
		Product saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( ProductId.generateNew() );
			validator.validate( changed );
			saved = repository.update( changed );
		}
		return ProductRequestDTO.of( saved );
	}

	public ProductReadView findByIdAsReadView(String id) {
		return repository.findByIdAsReadView( ProductId.ofNotNull( id ) );
	}

	public List<ProductReadView> findAllAsReadView() {
		return repository.findAllAsReadView();
	}

	@Transactional
	public void deleteProduct(String id) {
		repository.delete( ProductId.ofNotNull( id ) );
	}

	public List<ProductReadView> findByBUnitAsReadView(String businessUnit) {
		return repository.findByBUnitAsReadView( BusinessUnitId.of( businessUnit ) );
	}
}
