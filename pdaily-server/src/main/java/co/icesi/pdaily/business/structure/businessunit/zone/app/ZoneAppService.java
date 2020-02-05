package co.icesi.pdaily.business.structure.businessunit.zone.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.business.structure.businessunit.domain.model.BusinessUnitId;
import co.icesi.pdaily.business.structure.businessunit.zone.domain.model.Zone;
import co.icesi.pdaily.business.structure.businessunit.zone.domain.model.ZoneId;
import co.icesi.pdaily.business.structure.businessunit.zone.domain.model.ZoneValidator;
import co.icesi.pdaily.business.structure.businessunit.zone.infrastructure.persistence.ZoneRepository;

/**
 * @author cristhiank on 24/11/19
 **/
@ApplicationScoped
public class ZoneAppService {
	@Inject
	ZoneRepository repository;
	@Inject
	ZoneValidator validator;

	public List<ZoneDTO> findAll() {
		var all = repository.findAll();
		return StreamUtils.map( all, ZoneDTO::of );
	}

	public ZoneDTO findZoneById(String id) {
		var zone = repository.findOrFail( ZoneId.ofNotNull( id ) );
		return ZoneDTO.of( zone );
	}

	@Transactional
	public ZoneDTO saveZone(ZoneDTO dto) {
		var changed = dto.toZone();
		Zone saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( ZoneId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return ZoneDTO.of( saved );
	}

	@Transactional
	public void deleteZone(String id) {
		final ZoneId zoneId = ZoneId.ofNotNull( id );
		validator.checkBeforeDelete( zoneId );
		repository.delete( zoneId );
	}

	public List<ZoneDTO> findForBusinessUnit(String businessUnitId) {
		List<Zone> found = repository.findForBusinessUnit( BusinessUnitId.ofNotNull( businessUnitId ) );
		return StreamUtils.map( found, ZoneDTO::of );
	}
}
