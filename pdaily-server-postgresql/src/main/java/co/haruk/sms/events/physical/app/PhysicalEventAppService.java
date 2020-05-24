package co.haruk.sms.events.physical.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

import co.haruk.core.infrastructure.jta.JTAUtils;
import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.physical.domain.model.PhysicalEvent;
import co.haruk.sms.events.physical.domain.model.PhysicalEventId;
import co.haruk.sms.events.physical.domain.model.view.PhysicalEventReadView;
import co.haruk.sms.events.physical.domain.model.view.PhysicalEventViewBuilder;
import co.haruk.sms.events.physical.infrastructure.persistence.PhysicalEventRepository;

@ApplicationScoped
public class PhysicalEventAppService {
	@Inject
	PhysicalEventRepository repository;
	@Inject
	PhysicalEventViewBuilder builder;
	@Inject
	UserTransaction transaction;

	public List<PhysicalEventReadView> findAllByPatient(String patientId) {
		return repository.findByPatientAsReadView( PatientId.of( patientId ) );
	}

	public PhysicalEventReadView findByIdAsReadView(String physicalEventId) {
		return builder.buildPhysicalEvent( PhysicalEventId.of( physicalEventId ) );
	}

	public PhysicalEventReadView savePhysicalEvent(PhysicalEventDTO dto) {
		var changed = dto.toPhysicalEvent();
		try {
			PhysicalEvent saved;
			transaction.begin();
			if ( changed.isPersistent() ) {
				PhysicalEvent original = repository.findOrFail( changed.id() );
				original.updateFrom( changed );
				saved = repository.update( original );
			} else {
				changed.setId( PhysicalEventId.generateNew() );
				saved = repository.create( changed );
			}
			transaction.commit();
			return builder.buildPhysicalEvent( saved.id() );
		} catch (Exception ex) {
			JTAUtils.rollback( transaction );
			throw new IllegalStateException( ex );
		}
	}

	@Transactional
	public void deletePhysicalEvent(String id) {
		repository.delete( PhysicalEventId.of( id ) );
	}

}
