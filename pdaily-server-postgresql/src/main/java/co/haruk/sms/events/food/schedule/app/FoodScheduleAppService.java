package co.haruk.sms.events.food.schedule.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.patient.domain.model.PatientId;
import co.haruk.sms.events.food.schedule.domain.model.FoodSchedule;
import co.haruk.sms.events.food.schedule.domain.model.FoodScheduleId;
import co.haruk.sms.events.food.schedule.domain.model.FoodScheduleValidator;
import co.haruk.sms.events.food.schedule.infrastructure.persistence.FoodScheduleRepository;

@ApplicationScoped
public class FoodScheduleAppService {
	@Inject
	FoodScheduleRepository repository;
	@Inject
	FoodScheduleValidator validator;

	public List<FoodScheduleDTO> findAllByPatient(String patientId) {
		final var all = repository.findSchedulesByPatient( PatientId.of( patientId ) );
		return StreamUtils.map( all, FoodScheduleDTO::of );
	}

	public FoodScheduleDTO findById(String id) {
		final var found = repository.findOrFail( FoodScheduleId.ofNotNull( id ) );
		return FoodScheduleDTO.of( found );
	}

	@Transactional
	public FoodScheduleDTO saveFoodSchedule(FoodScheduleDTO dto) {
		final FoodSchedule changed = dto.toFoodSchedule();
		FoodSchedule saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( FoodScheduleId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return FoodScheduleDTO.of( saved );
	}

	@Transactional
	public void deleteFoodSchedule(String id) {
		repository.delete( FoodScheduleId.ofNotNull( id ) );
	}

}
