package co.haruk.sms.events.levodopa.event.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.levodopa.event.domain.model.LevodopaEvent;

@ApplicationScoped
public class LevodopaEventRepository extends JPARepository<LevodopaEvent> {
	public List<LevodopaEvent> findByPatient(PatientId patientId) {
		requireNonNull( patientId );
		return findWithNamedQuery(
				LevodopaEvent.findByPatient,
				QueryParameter.with( "patientId", patientId ).parameters()
		);
	}
}
