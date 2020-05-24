package co.haruk.sms.events.physical.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.physical.domain.model.PhysicalEvent;
import co.haruk.sms.events.physical.domain.model.PhysicalEventId;
import co.haruk.sms.events.physical.domain.model.detail.BodyPartDetail;
import co.haruk.sms.events.physical.domain.model.view.BodyPartDetailReadView;
import co.haruk.sms.events.physical.domain.model.view.PhysicalEventReadView;

@ApplicationScoped
public class PhysicalEventRepository extends JPARepository<PhysicalEvent> {

	public List<BodyPartDetailReadView> findDetailsAsReadView(PhysicalEventId eventId) {
		requireNonNull( eventId );
		return findOtherWithNamedQuery(
				BodyPartDetailReadView.class,
				BodyPartDetail.findByEventAsReadView,
				QueryParameter.with( "eventId", eventId ).parameters()
		);
	}

	public List<PhysicalEventReadView> findByPatientAsReadView(PatientId patientId) {
		requireNonNull( patientId );
		return findOtherWithNamedQuery(
				PhysicalEventReadView.class,
				PhysicalEvent.findByPatientAsReadView,
				QueryParameter.with( "patientId", patientId ).parameters()
		);
	}
}
