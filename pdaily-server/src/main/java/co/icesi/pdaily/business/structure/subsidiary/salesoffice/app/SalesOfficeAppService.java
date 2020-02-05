package co.icesi.pdaily.business.structure.subsidiary.salesoffice.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.icesi.pdaily.business.structure.subsidiary.salesoffice.domain.model.SalesOffice;
import co.icesi.pdaily.business.structure.subsidiary.salesoffice.domain.model.SalesOfficeId;
import co.icesi.pdaily.business.structure.subsidiary.salesoffice.domain.model.SalesOfficeValidator;
import co.icesi.pdaily.business.structure.subsidiary.salesoffice.infrastructure.persistence.SalesOfficeRepository;

/**
 * @author cristhiank on 21/11/19
 **/
@ApplicationScoped
public class SalesOfficeAppService {
	@Inject
	SalesOfficeRepository repository;
	@Inject
	SalesOfficeValidator validator;

	public List<SalesOfficeDTO> findAll() {
		var all = repository.findAll();
		return StreamUtils.map( all, SalesOfficeDTO::of );
	}

	public SalesOfficeDTO findById(String id) {
		var office = repository.findOrFail( SalesOfficeId.ofNotNull( id ) );
		return SalesOfficeDTO.of( office );
	}

	@Transactional
	public void deleteSalesOffice(String id) {
		repository.delete( SalesOfficeId.ofNotNull( id ) );
	}

	@Transactional
	public SalesOfficeDTO saveSalesOffice(SalesOfficeDTO dto) {
		SalesOffice changed = dto.toSalesOffice();
		SalesOffice saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( SalesOfficeId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return SalesOfficeDTO.of( saved );
	}

	public List<SalesOfficeDTO> findForSubsidiary(String subsidiaryId) {
		List<SalesOffice> all = repository.findForSubsidiary( SubsidiaryId.ofNotNull( subsidiaryId ) );
		return StreamUtils.map( all, SalesOfficeDTO::of );
	}
}
