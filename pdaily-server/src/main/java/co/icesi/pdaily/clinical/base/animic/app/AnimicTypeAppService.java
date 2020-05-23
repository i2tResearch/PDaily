package co.icesi.pdaily.clinical.base.animic.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.clinical.base.animic.domain.model.AnimicType;
import co.icesi.pdaily.clinical.base.animic.domain.model.AnimicTypeId;
import co.icesi.pdaily.clinical.base.animic.domain.model.AnimicTypeValidator;
import co.icesi.pdaily.clinical.base.animic.infrastructure.persistence.AnimicTypeRepository;

@ApplicationScoped
public class AnimicTypeAppService {
	@Inject
	AnimicTypeRepository repository;
	@Inject
	AnimicTypeValidator validator;

	public List<AnimicTypeDTO> findAll() {
		List<AnimicType> all = repository.findAll();
		return StreamUtils.map( all, AnimicTypeDTO::of );
	}

	@Transactional
	public AnimicTypeDTO saveAnimicType(AnimicTypeDTO dto) {
		final AnimicType changed = dto.toAnimicType();
		AnimicType saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.setLabel( changed.label() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( AnimicTypeId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return AnimicTypeDTO.of( saved );
	}

	@Transactional
	public void deleteAnimicType(String id) {
		repository.delete( AnimicTypeId.ofNotNull( id ) );
	}

	public AnimicTypeDTO findById(String id) {
		final var found = repository.findOrFail( AnimicTypeId.ofNotNull( id ) );
		return AnimicTypeDTO.of( found );
	}
}
