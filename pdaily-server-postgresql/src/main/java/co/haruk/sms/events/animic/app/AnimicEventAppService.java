package co.haruk.sms.events.animic.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.animic.domain.model.AnimicEvent;
import co.haruk.sms.events.animic.domain.model.AnimicEventId;
import co.haruk.sms.events.animic.domain.model.view.AnimicEventReadView;
import co.haruk.sms.events.animic.infrastructure.persistence.AnimicEventRepository;

@ApplicationScoped
public class AnimicEventAppService {
	@Inject
	AnimicEventRepository repository;

	public List<AnimicEventReadView> findAllByPatient(String patientId) {
		return repository.findByPatientAsReadView( PatientId.of( patientId ) );
	}

	public AnimicEventReadView findByIdAsReadView(String id) {
		return repository.findByIdAsReadView( AnimicEventId.ofNotNull( id ) );
	}

	@Transactional
	public AnimicEventDTO saveAnimicEvent(AnimicEventDTO dto) {
		final AnimicEvent changed = dto.toAnimicEvent();
		AnimicEvent saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			saved = repository.update( original );
		} else {
			changed.setId( AnimicEventId.generateNew() );
			saved = repository.create( changed );
		}
		return AnimicEventDTO.of( saved );
	}

	@Transactional
	public void deleteAnimicEvent(String id) {
		repository.delete( AnimicEventId.ofNotNull( id ) );
	}
}
