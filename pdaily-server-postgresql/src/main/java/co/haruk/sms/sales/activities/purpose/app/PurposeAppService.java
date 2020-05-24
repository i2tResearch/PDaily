package co.haruk.sms.sales.activities.purpose.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.sms.sales.activities.purpose.domain.model.Purpose;
import co.haruk.sms.sales.activities.purpose.domain.model.PurposeId;
import co.haruk.sms.sales.activities.purpose.domain.model.PurposeValidator;
import co.haruk.sms.sales.activities.purpose.infrastructure.persistence.PurposeRepository;

@ApplicationScoped
public class PurposeAppService {
	@Inject
	PurposeValidator validator;
	@Inject
	PurposeRepository repository;

	public List<PurposeDTO> findAll() {
		List<Purpose> all = repository.findAll();
		return StreamUtils.map( all, PurposeDTO::of );
	}

	@Transactional
	public PurposeDTO savePurpose(PurposeDTO dto) {
		final Purpose changed = dto.toPurpose();
		Purpose saved;
		if ( changed.isPersistent() ) {
			final var original = repository.findOrFail( changed.id() );
			original.setName( changed.name() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( PurposeId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return PurposeDTO.of( saved );
	}

	@Transactional
	public void deletePurpose(String id) {
		PurposeId purposeId = PurposeId.of( id );
		validator.checkBeforeDelete( purposeId );
		repository.delete( purposeId );
	}

	public PurposeDTO findById(String id) {
		final var found = repository.findOrFail( PurposeId.ofNotNull( id ) );
		return PurposeDTO.of( found );
	}
}
