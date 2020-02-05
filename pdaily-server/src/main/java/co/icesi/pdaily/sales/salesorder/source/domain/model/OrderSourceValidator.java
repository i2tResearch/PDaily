package co.icesi.pdaily.sales.salesorder.source.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.sales.salesorder.source.infrastructure.persistence.OrderSourceRepository;

/**
 * @author cristhiank on 23/12/19
 **/
@Dependent
public class OrderSourceValidator {
	@Inject
	OrderSourceRepository repository;

	public void validate(OrderSource source) {
		failIfDuplicatedByName( source );
	}

	private void failIfDuplicatedByName(OrderSource source) {
		final Optional<OrderSource> found = repository.findByName( source.name() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !source.isPersistent() || !existent.equals( source );
			if ( mustFail ) {
				throw new DuplicatedRecordException( source.name() );
			}
		}
	}

	public void checkBeforeDelete(OrderSourceId sourceId) {
		// TODO: implement this when activity aggregate is created
	}
}
