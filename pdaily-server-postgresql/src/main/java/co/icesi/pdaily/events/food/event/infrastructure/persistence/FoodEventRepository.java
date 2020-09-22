package co.icesi.pdaily.events.food.event.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.patient.domain.model.PatientId;
import co.icesi.pdaily.events.food.event.domain.model.FoodEvent;

@ApplicationScoped
public class FoodEventRepository extends JPARepository<FoodEvent> {
	public List<FoodEvent> findByPatient(PatientId patientId) {
		requireNonNull( patientId );
		return findWithNamedQuery(
				FoodEvent.findByPatient,
				QueryParameter.with( "patientId", patientId ).parameters()
		);
	}
}
