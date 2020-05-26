package co.haruk.sms.events.routine.schedule.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.routine.schedule.domain.model.RoutineSchedule;
import co.haruk.sms.events.routine.schedule.domain.model.RoutineScheduleId;
import co.haruk.sms.events.routine.schedule.domain.model.view.RoutineScheduleReadView;
import co.haruk.sms.events.routine.schedule.infrastructure.persistence.RoutineScheduleRepository;

@ApplicationScoped
public class RoutineScheduleAppService {
	@Inject
	RoutineScheduleRepository repository;

	public List<RoutineScheduleReadView> findAllByPatient(String patientId) {
		return repository.findSchedulesByPatient( PatientId.of( patientId ) );
	}

	public RoutineScheduleReadView findById(String id) {
		return repository.findById( RoutineScheduleId.ofNotNull( id ) );
	}

	@Transactional
	public RoutineScheduleDTO saveRoutineSchedule(RoutineScheduleDTO dto) {
		final RoutineSchedule changed = dto.toRoutineSchedule();
		RoutineSchedule saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			saved = repository.update( original );
		} else {
			changed.setId( RoutineScheduleId.generateNew() );
			saved = repository.create( changed );
		}
		return RoutineScheduleDTO.of( saved );
	}

	@Transactional
	public void deleteRoutineSchedule(String id) {
		repository.delete( RoutineScheduleId.ofNotNull( id ) );
	}
}
