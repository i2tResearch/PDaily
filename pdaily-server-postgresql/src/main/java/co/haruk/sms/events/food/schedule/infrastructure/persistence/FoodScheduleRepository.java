package co.haruk.sms.events.food.schedule.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.common.model.Schedule;
import co.haruk.sms.events.food.schedule.domain.model.FoodSchedule;

@ApplicationScoped
public class FoodScheduleRepository extends JPARepository<FoodSchedule> {

	public List<FoodSchedule> findSchedulesByPatient(PatientId patientId) {
		requireNonNull( patientId );
		return findWithNamedQuery(
				FoodSchedule.findByPatient,
				QueryParameter.with( "patient", patientId ).parameters()
		);
	}

	public Optional<FoodSchedule> findBySchedule(Schedule schedule) {
		requireNonNull( schedule );
		return findSingleWithNamedQuery(
				FoodSchedule.findScheduleOccurences,
				QueryParameter.with( "schedule", schedule.dateExpression() ).parameters()
		);
	}
}
