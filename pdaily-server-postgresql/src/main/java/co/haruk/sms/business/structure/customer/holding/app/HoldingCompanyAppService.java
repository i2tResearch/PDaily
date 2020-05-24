package co.haruk.sms.business.structure.customer.holding.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.customer.holding.domain.model.HoldingCompany;
import co.haruk.sms.business.structure.customer.holding.domain.model.HoldingCompanyId;
import co.haruk.sms.business.structure.customer.holding.domain.model.HoldingCompanyValidator;
import co.haruk.sms.business.structure.customer.holding.infrastructure.persistence.HoldingCompanyRepository;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;

/**
 * @author andres2508 on 1/12/19
 **/
@ApplicationScoped
public class HoldingCompanyAppService {
	@Inject
	HoldingCompanyRepository repository;
	@Inject
	HoldingCompanyValidator validator;

	public List<HoldingCompanyDTO> findForSubsidiary(String subsidiaryId) {
		final var all = repository.findForSubsidiary( SubsidiaryId.ofNotNull( subsidiaryId ) );
		return StreamUtils.map( all, HoldingCompanyDTO::of );
	}

	@Transactional
	public HoldingCompanyDTO saveHolding(HoldingCompanyDTO dto) {
		final HoldingCompany changes = dto.toHoldingCompany();
		HoldingCompany saved;
		if ( changes.isPersistent() ) {
			final var original = repository.findOrFail( changes.id() );
			original.updateFrom( changes );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changes.setId( HoldingCompanyId.generateNew() );
			validator.validate( changes );
			saved = repository.create( changes );
		}
		return HoldingCompanyDTO.of( saved );
	}

	public HoldingCompanyDTO findById(String id) {
		final var found = repository.findOrFail( HoldingCompanyId.ofNotNull( id ) );
		return HoldingCompanyDTO.of( found );
	}

	@Transactional
	public void deleteHolding(String id) {
		repository.delete( HoldingCompanyId.ofNotNull( id ) );
	}
}
