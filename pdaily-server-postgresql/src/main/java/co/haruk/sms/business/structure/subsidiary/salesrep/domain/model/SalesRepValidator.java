package co.haruk.sms.business.structure.subsidiary.salesrep.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.business.structure.subsidiary.salesrep.infrastructure.persistence.SalesRepRepository;

/**
 * @author cristhiank on 25/11/19
 **/
@Dependent
public class SalesRepValidator {
	@Inject
	SalesRepRepository repository;

	public void validate(SalesRep rep) {
		if ( rep.reference() != null ) {
			failIfDuplicatedReference( rep );
		}
	}

	private void failIfDuplicatedReference(SalesRep rep) {
		Optional<SalesRep> found = repository.findByReference( rep.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !rep.isPersistent() || !existent.equals( rep );
			if ( mustFail ) {
				throw new DuplicatedRecordException( rep.reference() );
			}
		}
	}
}
