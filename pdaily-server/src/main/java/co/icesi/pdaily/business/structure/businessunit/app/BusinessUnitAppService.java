package co.icesi.pdaily.business.structure.businessunit.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnit;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitValidator;
import co.icesi.pdaily.business.structure.businessunit.infrastructure.persistence.BusinessUnitRepository;

@ApplicationScoped
public class BusinessUnitAppService {
	@Inject
	BusinessUnitRepository repository;
	@Inject
	BusinessUnitValidator validator;

	@Transactional
	public BusinessUnitDTO saveBusinessUnit(BusinessUnitDTO dto) {
		final var changed = dto.toBusinessUnit();
		BusinessUnit saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( BusinessUnitId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return BusinessUnitDTO.of( saved );
	}

	public BusinessUnitDTO findOrFail(String id) {
		final var found = repository.findOrFail( BusinessUnitId.ofNotNull( id ) );
		return BusinessUnitDTO.of( found );
	}

	public List<BusinessUnitDTO> findAll() {
		final var all = repository.findAll();
		return StreamUtils.map( all, BusinessUnitDTO::of );
	}

	@Transactional
	public void deleteBusinessUnit(String id) {
		final var unitId = BusinessUnitId.ofNotNull( id );
		validator.checkBeforeDelete( unitId );
		repository.delete( unitId );
	}
}
