package co.icesi.pdaily.business.structure.patient.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.business.structure.patient.domain.model.Patient;
import co.icesi.pdaily.business.structure.patient.domain.model.PatientId;
import co.icesi.pdaily.business.structure.patient.infrastructure.persistence.PatientRepository;

@ApplicationScoped
public class PatientAppService {
	@Inject
	PatientRepository repository;

	@Transactional
	public PatientDTO savePatient(PatientDTO dto) {
		final var changed = dto.toPatient();
		Patient saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.setFullName( changed.fullName() );
			saved = repository.update( original );
		} else {
			changed.setId( PatientId.generateNew() );
			saved = repository.create( changed );
		}
		return PatientDTO.of( saved );
	}

	public PatientDTO findOrFail(String id) {
		final var found = repository.findOrFail( PatientId.ofNotNull( id ) );
		return PatientDTO.of( found );
	}

	public List<PatientDTO> findAll() {
		final var all = repository.findAll();
		return StreamUtils.map( all, PatientDTO::of );
	}

	@Transactional
	public void deletePatient(String id) {
		final var brandId = PatientId.ofNotNull( id );
		repository.delete( brandId );
	}
}
