package co.haruk.sms.events.levodopa.schedule.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.levodopa.schedule.domain.model.LevodopaSchedule;
import co.haruk.sms.events.levodopa.schedule.domain.model.LevodopaScheduleId;
import co.haruk.sms.events.levodopa.schedule.domain.model.LevodopaScheduleValidator;
import co.haruk.sms.events.levodopa.schedule.domain.model.view.LevodopaScheduleReadView;
import co.haruk.sms.events.levodopa.schedule.infrastructure.persistence.LevodopaScheduleRepository;

@ApplicationScoped
public class LevodopaScheduleAppService {
	@Inject
	LevodopaScheduleRepository repository;
	@Inject
	LevodopaScheduleValidator validator;

	public List<LevodopaScheduleReadView> findAllByPatient(String patientId) {
		return repository.findSchedulesByPatient( PatientId.of( patientId ) );
	}

	public LevodopaScheduleReadView findById(String id) {
		return repository.findSchedulesById( LevodopaScheduleId.ofNotNull( id ) );
	}

	@Transactional
	public LevodopaScheduleDTO saveLevodopaSchedule(LevodopaScheduleDTO dto) {
		final LevodopaSchedule changed = dto.toLevodopaSchedule();
		LevodopaSchedule saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( LevodopaScheduleId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return LevodopaScheduleDTO.of( saved );
	}

	@Transactional
	public void deleteLevodopaSchedule(String id) {
		repository.delete( LevodopaScheduleId.ofNotNull( id ) );
	}
}
