package co.icesi.pdaily.business.structure.businessunit.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnit;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitValidator;
import co.icesi.pdaily.business.structure.businessunit.domain.model.EffectiveActivityThreshold;
import co.icesi.pdaily.business.structure.businessunit.domain.model.view.BusinessUnitReadView;
import co.icesi.pdaily.business.structure.businessunit.domain.model.view.BusinessUnitReadViewBuilder;
import co.icesi.pdaily.business.structure.businessunit.infrastructure.persistence.BusinessUnitRepository;

@ApplicationScoped
public class BusinessUnitAppService {
	@Inject
	BusinessUnitRepository repository;
	@Inject
	BusinessUnitValidator validator;
	@Inject
	BusinessUnitReadViewBuilder builder;

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

	public BusinessUnitReadView findOrFail(String id) {
		return builder.buildBusinessUnit( BusinessUnitId.ofNotNull( id ) );
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

	@Transactional
	public BusinessUnitDTO changeEffectiveThreshold(String id, int hours) {
		final var found = repository.findOrFail( BusinessUnitId.of( id ) );
		found.changeEffectiveThreshold( EffectiveActivityThreshold.of( hours ) );
		repository.update( found );
		return BusinessUnitDTO.of( found );
	}
}
