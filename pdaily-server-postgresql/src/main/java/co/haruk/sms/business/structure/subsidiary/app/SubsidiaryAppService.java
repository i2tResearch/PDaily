package co.haruk.sms.business.structure.subsidiary.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.business.structure.subsidiary.domain.model.Subsidiary;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryId;
import co.haruk.sms.business.structure.subsidiary.domain.model.SubsidiaryValidator;
import co.haruk.sms.business.structure.subsidiary.infrastructure.persistence.SubsidiaryRepository;

/**
 * @author cristhiank on 19/11/19
 **/
@ApplicationScoped
public class SubsidiaryAppService {

	@Inject
	SubsidiaryRepository repository;
	@Inject
	SubsidiaryValidator validator;

	public List<SubsidiaryDTO> findAll() {
		var all = repository.findAll();
		return StreamUtils.map( all, SubsidiaryDTO::of );
	}

	@Transactional
	public SubsidiaryDTO saveSubsidiary(SubsidiaryDTO dto) {
		Subsidiary changed = dto.toSubsidiary();
		Subsidiary saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.updateFrom( changed );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( SubsidiaryId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return SubsidiaryDTO.of( saved );
	}

	public SubsidiaryDTO findById(String id) {
		var found = repository.findOrFail( SubsidiaryId.ofNotNull( id ) );
		return SubsidiaryDTO.of( found );
	}

	@Transactional
	public void deleteById(String id) {
		final var subsidiaryId = SubsidiaryId.ofNotNull( id );
		validator.checkBeforeDelete( subsidiaryId );
		repository.delete( subsidiaryId );
	}
}
