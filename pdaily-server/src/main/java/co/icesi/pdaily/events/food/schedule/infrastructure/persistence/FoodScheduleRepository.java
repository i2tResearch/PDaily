package co.icesi.pdaily.events.food.schedule.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.patient.domain.model.PatientId;
import co.icesi.pdaily.events.food.schedule.domain.model.FoodSchedule;

@ApplicationScoped
public class FoodScheduleRepository extends JPARepository<FoodSchedule> {

	public List<FoodSchedule> findSchedulesByPatient(PatientId patientId) {
		requireNonNull( patientId );
		return findWithNamedQuery(
				FoodSchedule.findByPatient,
				QueryParameter.with( "patient", patientId ).parameters()
		);
	}
}
