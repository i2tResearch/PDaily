package co.icesi.pdaily.events.physical.body.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.events.physical.body.domain.model.BodyPart;
import co.icesi.pdaily.events.physical.body.domain.model.BodyPartId;
import co.icesi.pdaily.events.physical.body.domain.model.BodyPartValidator;
import co.icesi.pdaily.events.physical.body.infrastructure.repository.BodyPartRepository;

@ApplicationScoped
public class BodyPartAppService {
	@Inject
	BodyPartValidator validator;
	@Inject
	BodyPartRepository repository;

	public List<BodyPartDTO> findAll() {
		List<BodyPart> all = repository.findAll();
		return StreamUtils.map( all, BodyPartDTO::of );
	}

	@Transactional
	public BodyPartDTO saveBodyPart(BodyPartDTO dto) {
		final BodyPart changed = dto.toBodyPart();
		BodyPart saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.setName( changed.name() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( BodyPartId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return BodyPartDTO.of( saved );
	}

	@Transactional
	public void deleteBodyPart(String id) {
		repository.delete( BodyPartId.ofNotNull( id ) );
	}

	public BodyPartDTO findById(String id) {
		final var found = repository.findOrFail( BodyPartId.ofNotNull( id ) );
		return BodyPartDTO.of( found );
	}
}
