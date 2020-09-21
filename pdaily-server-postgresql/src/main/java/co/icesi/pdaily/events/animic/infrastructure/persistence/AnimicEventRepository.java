package co.icesi.pdaily.events.animic.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.business.structure.patient.domain.model.PatientId;
import co.icesi.pdaily.events.animic.domain.model.AnimicEvent;
import co.icesi.pdaily.events.animic.domain.model.AnimicEventId;
import co.icesi.pdaily.events.animic.domain.model.view.AnimicEventReadView;

@ApplicationScoped
public class AnimicEventRepository extends JPARepository<AnimicEvent> {
	public List<AnimicEventReadView> findByPatientAsReadView(PatientId patientId) {
		requireNonNull( patientId );
		return findOtherWithNamedQuery(
				AnimicEventReadView.class,
				AnimicEvent.findByPatientAsReadView,
				QueryParameter.with( "id", patientId ).parameters()
		);
	}

	public AnimicEventReadView findByIdAsReadView(AnimicEventId id) {
		requireNonNull( id );
		return findOtherSingleWithNamedQuery(
				AnimicEventReadView.class,
				AnimicEvent.findByIdAsReadView,
				QueryParameter.with( "id", id ).parameters()
		).orElseThrow( EntityNotFoundException::new );
	}
}
