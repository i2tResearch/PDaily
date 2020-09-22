package co.icesi.pdaily.clinical.base.routines.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.clinical.base.routines.domain.model.RoutineType;
import co.icesi.pdaily.clinical.base.routines.domain.model.RoutineTypeId;
import co.icesi.pdaily.clinical.base.routines.domain.model.RoutineTypeValidator;
import co.icesi.pdaily.clinical.base.routines.infrastructure.persistence.RoutineTypeRepository;

@ApplicationScoped
public class RoutineTypeAppService {
	@Inject
	RoutineTypeRepository repository;
	@Inject
	RoutineTypeValidator validator;

	public List<RoutineTypeDTO> findAll() {
		List<RoutineType> all = repository.findAll();
		return StreamUtils.map( all, RoutineTypeDTO::of );
	}

	@Transactional
	public RoutineTypeDTO saveRoutineType(RoutineTypeDTO dto) {
		final RoutineType changed = dto.toRoutineType();
		RoutineType saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.setLabel( changed.label() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( RoutineTypeId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return RoutineTypeDTO.of( saved );
	}

	@Transactional
	public void deleteRoutineType(String id) {
		repository.delete( RoutineTypeId.ofNotNull( id ) );
	}

	public RoutineTypeDTO findById(String id) {
		final var found = repository.findOrFail( RoutineTypeId.ofNotNull( id ) );
		return RoutineTypeDTO.of( found );
	}
}
