package co.icesi.pdaily.events.physical.domain.model.view;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.List;

import javax.inject.Inject;

import co.icesi.pdaily.events.physical.domain.model.PhysicalEvent;
import co.icesi.pdaily.events.physical.domain.model.PhysicalEventId;
import co.icesi.pdaily.events.physical.infrastructure.persistence.PhysicalEventRepository;
import co.icesi.pdaily.events.physical.injury.type.domain.model.InjuryType;
import co.icesi.pdaily.events.physical.injury.type.infrastructure.persistence.InjuryTypeRepository;

public class PhysicalEventViewBuilder {
	@Inject
	PhysicalEventRepository repository;
	@Inject
	InjuryTypeRepository injuryRepository;

	public PhysicalEventReadView buildPhysicalEvent(PhysicalEventId id) {
		requireNonNull( id );
		PhysicalEvent found = repository.findOrFail( id );
		List<BodyPartDetailReadView> details = repository.findDetailsAsReadView( id );
		InjuryType injuryType = injuryRepository.findOrFail( found.injuryType() );

		PhysicalEventReadView readView = new PhysicalEventReadView(
				id.uuidValue(),
				injuryType.id().uuidValue(),
				injuryType.name().text(),
				found.initialDate().date(),
				found.finalDate().date(),
				found.intensity().intensity()
		);

		readView.bodyDetails = details;

		return readView;
	}
}
