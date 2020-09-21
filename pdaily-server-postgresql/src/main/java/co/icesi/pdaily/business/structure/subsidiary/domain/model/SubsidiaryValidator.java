package co.icesi.pdaily.business.structure.subsidiary.domain.model;

import static co.haruk.core.domain.model.guards.Guards.check;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.business.structure.subsidiary.doctor.infrastructure.persistence.DoctorRepository;
import co.icesi.pdaily.business.structure.subsidiary.infrastructure.persistence.SubsidiaryRepository;

/**
 * @author andres2508 on 19/11/19
 **/
@Dependent
public class SubsidiaryValidator {
	@Inject
	SubsidiaryRepository repository;
	@Inject
	DoctorRepository doctorRepository;

	public void validate(Subsidiary subsidiary) {
		failIfDuplicatedName( subsidiary );
		if ( subsidiary.reference() != null ) {
			failIfDuplicatedReference( subsidiary );
		}
	}

	private void failIfDuplicatedReference(Subsidiary subsidiary) {
		Optional<Subsidiary> found = repository.findByReference( subsidiary.reference() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !subsidiary.isPersistent() || !existent.equals( subsidiary );
			if ( mustFail ) {
				throw new DuplicatedRecordException( subsidiary.reference().text() );
			}
		}
	}

	private void failIfDuplicatedName(Subsidiary subsidiary) {
		Optional<Subsidiary> found = repository.findByName( subsidiary.name() );
		if ( found.isPresent() ) {
			var existent = found.get();
			var mustFail = !subsidiary.isPersistent() || !existent.equals( subsidiary );
			if ( mustFail ) {
				throw new DuplicatedRecordException( subsidiary.name().text() );
			}
		}
	}

	public void checkBeforeDelete(SubsidiaryId subsidiaryId) {
		boolean hasDoctors = doctorRepository.existsAnyForSubsidiary( subsidiaryId );
		check( !hasDoctors, "No se puede eliminar la filial, tiene medicos asignados" );
	}
}
