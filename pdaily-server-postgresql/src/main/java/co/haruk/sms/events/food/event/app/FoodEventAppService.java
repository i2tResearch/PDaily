package co.haruk.sms.events.food.event.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.food.event.domain.model.FoodEvent;
import co.haruk.sms.events.food.event.domain.model.FoodEventId;
import co.haruk.sms.events.food.event.infrastructure.persistence.FoodEventRepository;

@ApplicationScoped
public class FoodEventAppService {
	@Inject
	FoodEventRepository repository;

	@Transactional
	public FoodEventDTO saveFoodEvent(FoodEventDTO dto) {
		final var changed = dto.toFoodEvent();
		FoodEvent saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.setDate( changed.date() );
			saved = repository.update( original );
		} else {
			changed.setId( FoodEventId.generateNew() );
			saved = repository.create( changed );
		}
		return FoodEventDTO.of( saved );
	}

	public FoodEventDTO findOrFail(String id) {
		final var found = repository.findOrFail( FoodEventId.ofNotNull( id ) );
		return FoodEventDTO.of( found );
	}

	public List<FoodEventDTO> findByPatient(String patientId) {
		final var all = repository.findByPatient( PatientId.ofNotNull( patientId ) );
		return StreamUtils.map( all, FoodEventDTO::of );
	}

	@Transactional
	public void deleteFoodEvent(String id) {
		final var brandId = FoodEventId.ofNotNull( id );
		repository.delete( brandId );
	}
}
