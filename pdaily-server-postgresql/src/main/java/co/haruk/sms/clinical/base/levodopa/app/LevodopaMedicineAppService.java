package co.haruk.sms.clinical.base.levodopa.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.sms.clinical.base.levodopa.domain.model.LevodopaMedicine;
import co.haruk.sms.clinical.base.levodopa.domain.model.LevodopaMedicineId;
import co.haruk.sms.clinical.base.levodopa.domain.model.LevodopaMedicineValidator;
import co.haruk.sms.clinical.base.levodopa.domain.model.view.LevodopaMedicineReadView;
import co.haruk.sms.clinical.base.levodopa.infrastructure.persistence.LevodopaMedicineRepository;

@ApplicationScoped
public class LevodopaMedicineAppService {
	@Inject
	LevodopaMedicineValidator validator;
	@Inject
	LevodopaMedicineRepository repository;

	public List<LevodopaMedicineReadView> findAll() {
		return repository.findAllAsReadView();
	}

	@Transactional
	public LevodopaMedicineDTO saveLevodopaMedicine(LevodopaMedicineDTO dto) {
		final LevodopaMedicine changed = dto.toLevodopaMedicine();
		LevodopaMedicine saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( LevodopaMedicineId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return LevodopaMedicineDTO.of( saved );
	}

	@Transactional
	public void deleteLevodopaMedicine(String id) {
		repository.delete( LevodopaMedicineId.ofNotNull( id ) );
	}

	public LevodopaMedicineReadView findById(String id) {
		return repository.findByIdAsReadView( LevodopaMedicineId.ofNotNull( id ) );
	}
}
