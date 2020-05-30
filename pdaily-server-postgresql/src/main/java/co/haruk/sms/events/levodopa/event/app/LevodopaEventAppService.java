package co.haruk.sms.events.levodopa.event.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.levodopa.event.domain.model.LevodopaEvent;
import co.haruk.sms.events.levodopa.event.domain.model.LevodopaEventId;
import co.haruk.sms.events.levodopa.event.infrastructure.persistence.LevodopaEventRepository;

@ApplicationScoped
public class LevodopaEventAppService {
	@Inject
	LevodopaEventRepository repository;

	@Transactional
	public LevodopaEventDTO saveLevodopaEvent(LevodopaEventDTO dto) {
		final var changed = dto.toLevodopaEvent();
		LevodopaEvent saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			saved = repository.update( original );
		} else {
			changed.setId( LevodopaEventId.generateNew() );
			saved = repository.create( changed );
		}
		return LevodopaEventDTO.of( saved );
	}

	public LevodopaEventDTO findOrFail(String id) {
		final var found = repository.findOrFail( LevodopaEventId.ofNotNull( id ) );
		return LevodopaEventDTO.of( found );
	}

	public List<LevodopaEventDTO> findByPatient(String patientId) {
		final var all = repository.findByPatient( PatientId.ofNotNull( patientId ) );
		return StreamUtils.map( all, LevodopaEventDTO::of );
	}

	@Transactional
	public void deleteLevodopaEvent(String id) {
		final var brandId = LevodopaEventId.ofNotNull( id );
		repository.delete( brandId );
	}
}
