package co.icesi.pdaily.business.structure.subsidiary.doctor.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.subsidiary.doctor.infrastructure.persistence.DoctorRepository;

/**
 * @author andres2508 on 25/11/19
 **/
@Dependent
public class DoctorValidator {
	@Inject
	DoctorRepository repository;

	public void validate(Doctor rep) {
		if ( rep.reference() != null ) {
			failIfDuplicatedReference( rep );
		}
	}

	private void failIfDuplicatedReference(Doctor rep) {
		Optional<Doctor> found = repository.findByReference( rep.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !rep.isPersistent() || !existent.equals( rep );
			if ( mustFail ) {
				throw new DuplicatedRecordException( rep.reference() );
			}
		}
	}
}
