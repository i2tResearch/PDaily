package co.icesi.pdaily.clinical.base.levodopa.type.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.clinical.base.levodopa.type.domain.model.LevodopaType;
import co.icesi.pdaily.clinical.base.levodopa.type.domain.model.LevodopaTypeId;
import co.icesi.pdaily.clinical.base.levodopa.type.domain.model.LevodopaTypeValidator;
import co.icesi.pdaily.clinical.base.levodopa.type.infrastructure.persistence.LevodopaTypeRepository;

@ApplicationScoped
public class LevodopaTypeAppService {
	@Inject
	LevodopaTypeValidator validator;
	@Inject
	LevodopaTypeRepository repository;

	public List<LevodopaTypeDTO> findAll() {
		List<LevodopaType> all = repository.findAll();
		return StreamUtils.map( all, LevodopaTypeDTO::of );
	}

	@Transactional
	public LevodopaTypeDTO saveLevodopaType(LevodopaTypeDTO dto) {
		final LevodopaType changed = dto.toLevodopaType();
		LevodopaType saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.setLabel( changed.label() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( LevodopaTypeId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return LevodopaTypeDTO.of( saved );
	}

	@Transactional
	public void deleteLevodopaType(String id) {
		repository.delete( LevodopaTypeId.ofNotNull( id ) );
	}

	public LevodopaTypeDTO findById(String id) {
		final var found = repository.findOrFail( LevodopaTypeId.ofNotNull( id ) );
		return LevodopaTypeDTO.of( found );
	}
}
