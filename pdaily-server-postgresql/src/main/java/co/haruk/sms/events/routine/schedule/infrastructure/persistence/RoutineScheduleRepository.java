package co.haruk.sms.events.routine.schedule.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.routine.schedule.domain.model.RoutineSchedule;
import co.haruk.sms.events.routine.schedule.domain.model.RoutineScheduleId;
import co.haruk.sms.events.routine.schedule.domain.model.view.RoutineScheduleReadView;

@ApplicationScoped
public class RoutineScheduleRepository extends JPARepository<RoutineSchedule> {

	public List<RoutineScheduleReadView> findSchedulesByPatient(PatientId patientId) {
		requireNonNull( patientId );
		return findOtherWithNamedQuery(
				RoutineScheduleReadView.class,
				RoutineSchedule.findByPatientAsReadView,
				QueryParameter.with( "patientId", patientId ).parameters()
		);
	}

	public RoutineScheduleReadView findById(RoutineScheduleId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				RoutineScheduleReadView.class,
				RoutineSchedule.findByIdAsReadView,
				QueryParameter.with( "id", id ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}
}
