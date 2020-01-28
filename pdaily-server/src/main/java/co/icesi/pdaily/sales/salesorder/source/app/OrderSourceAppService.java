package co.icesi.pdaily.sales.salesorder.source.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.sales.salesorder.source.domain.model.OrderSource;
import co.icesi.pdaily.sales.salesorder.source.domain.model.OrderSourceId;
import co.icesi.pdaily.sales.salesorder.source.domain.model.OrderSourceValidator;
import co.icesi.pdaily.sales.salesorder.source.infrastructure.persistence.OrderSourceRepository;

/**
 * @author cristhiank on 23/12/19
 **/
@ApplicationScoped
public class OrderSourceAppService {

	@Inject
	OrderSourceRepository repository;
	@Inject
	OrderSourceValidator validator;

	public List<OrderSourceDTO> findAll() {
		List<OrderSource> all = repository.findAll();
		return StreamUtils.map( all, OrderSourceDTO::of );
	}

	@Transactional
	public OrderSourceDTO saveSource(OrderSourceDTO dto) {
		final OrderSource changed = dto.toOrderSource();
		OrderSource saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.setName( changed.name() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( OrderSourceId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return OrderSourceDTO.of( saved );
	}

	@Transactional
	public void deleteSource(String id) {
		validator.checkBeforeDelete( OrderSourceId.ofNotNull( id ) );
		repository.delete( OrderSourceId.ofNotNull( id ) );
	}

	public OrderSourceDTO findById(String id) {
		final var found = repository.findOrFail( OrderSourceId.ofNotNull( id ) );
		return OrderSourceDTO.of( found );
	}
}
