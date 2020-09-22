package co.icesi.pdaily.events.levodopa.schedule.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.patient.domain.model.PatientId;
import co.icesi.pdaily.common.model.Schedule;
import co.icesi.pdaily.events.levodopa.schedule.domain.model.LevodopaSchedule;
import co.icesi.pdaily.events.levodopa.schedule.domain.model.LevodopaScheduleId;
import co.icesi.pdaily.events.levodopa.schedule.domain.model.view.LevodopaScheduleReadView;

@ApplicationScoped
public class LevodopaScheduleRepository extends JPARepository<LevodopaSchedule> {
	public List<LevodopaScheduleReadView> findSchedulesByPatient(PatientId patientId) {
		requireNonNull( patientId );
		return findOtherWithNamedQuery(
				LevodopaScheduleReadView.class,
				LevodopaSchedule.findByPatientAsReadView,
				QueryParameter.with( "patientId", patientId ).parameters()
		);
	}

	public LevodopaScheduleReadView findSchedulesById(LevodopaScheduleId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				LevodopaScheduleReadView.class,
				LevodopaSchedule.findByIdAsReadView,
				QueryParameter.with( "id", id ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}

	public Optional<LevodopaSchedule> findBySchedule(Schedule schedule) {
		requireNonNull( schedule );
		return findSingleWithNamedQuery(
				LevodopaSchedule.findScheduleOccurences,
				QueryParameter.with( "schedule", schedule.dateExpression() ).parameters()
		);
	}
}
