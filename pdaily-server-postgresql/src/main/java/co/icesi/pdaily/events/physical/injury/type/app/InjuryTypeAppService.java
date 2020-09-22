package co.icesi.pdaily.events.physical.injury.type.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.events.physical.injury.type.domain.model.InjuryType;
import co.icesi.pdaily.events.physical.injury.type.domain.model.InjuryTypeId;
import co.icesi.pdaily.events.physical.injury.type.domain.model.InjuryTypeValidator;
import co.icesi.pdaily.events.physical.injury.type.infrastructure.persistence.InjuryTypeRepository;

@ApplicationScoped
public class InjuryTypeAppService {
	@Inject
	InjuryTypeValidator validator;
	@Inject
	InjuryTypeRepository repository;

	public List<InjuryTypeDTO> findAll() {
		List<InjuryType> all = repository.findAll();
		return StreamUtils.map( all, InjuryTypeDTO::of );
	}

	@Transactional
	public InjuryTypeDTO saveInjuryType(InjuryTypeDTO dto) {
		final InjuryType changed = dto.toInjuryType();
		InjuryType saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.setName( changed.name() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( InjuryTypeId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return InjuryTypeDTO.of( saved );
	}

	@Transactional
	public void deleteInjuryType(String id) {
		repository.delete( InjuryTypeId.ofNotNull( id ) );
	}

	public InjuryTypeDTO findById(String id) {
		final var found = repository.findOrFail( InjuryTypeId.ofNotNull( id ) );
		return InjuryTypeDTO.of( found );
	}
}
