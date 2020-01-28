package co.icesi.pdaily.business.structure.businessunit.zone.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.guards.Guards;
import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.businessunit.zone.infrastructure.persistence.ZoneRepository;
import co.icesi.pdaily.business.structure.customer.infrastructure.persistence.CustomerRepository;

/**
 * @author cristhiank on 24/11/19
 **/
@Dependent
public class ZoneValidator {
	@Inject
	ZoneRepository repository;
	@Inject
	CustomerRepository customerRepository;

	public void validate(Zone zone) {
		failIfDuplicatedName( zone );
		if ( zone.reference() != null ) {
			failIfDuplicatedReference( zone );
		}
	}

	private void failIfDuplicatedReference(Zone zone) {
		Optional<Zone> found = repository.findByReference( zone.businessUnitId(), zone.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !zone.isPersistent() || !existent.equals( zone );
			if ( mustFail ) {
				throw new DuplicatedRecordException( zone.reference() );
			}
		}
	}

	private void failIfDuplicatedName(Zone zone) {
		Optional<Zone> found = repository.findByName( zone.businessUnitId(), zone.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !zone.isPersistent() || !existent.equals( zone );
			if ( mustFail ) {
				throw new DuplicatedRecordException( zone.name() );
			}
		}
	}

	public void checkBeforeDelete(ZoneId zoneId) {
		final boolean hasCustomers = customerRepository.existsAnyForZone( zoneId );
		Guards.check( !hasCustomers, "No se puede eliminar la zona, tiene clientes asignados." );
	}
}
