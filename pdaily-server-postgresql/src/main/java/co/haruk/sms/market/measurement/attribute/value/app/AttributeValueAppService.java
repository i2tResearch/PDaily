package co.haruk.sms.market.measurement.attribute.value.app;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import co.haruk.core.StreamUtils;
import co.haruk.core.domain.model.persistence.EntityNotFoundException;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeValue;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeValueId;
import co.haruk.sms.market.measurement.attribute.value.domain.model.AttributeValueValidator;
import co.haruk.sms.market.measurement.attribute.value.domain.model.SubjectId;
import co.haruk.sms.market.measurement.attribute.value.infrastructure.persistence.AttributeValueRepository;

@ApplicationScoped
public class AttributeValueAppService {
	@Inject
	AttributeValueRepository repository;
	@Inject
	AttributeValueValidator validator;

	@Transactional
	public AttributeValueDTO saveAttributeValue(AttributeValueDTO dto) {
		final var changed = dto.toAttributeValue();
		AttributeValue saved;
		if ( changed.isPersistent() ) {
			var original = repository.findOrFail( changed.id() );
			original.setValue( changed.value() );
			validator.validate( original );
			saved = repository.update( original );
		} else {
			changed.setId( AttributeValueId.generateNew() );
			validator.validate( changed );
			saved = repository.create( changed );
		}
		return AttributeValueDTO.of( saved );
	}

	public List<AttributeValueDTO> findBySubjectId(String subjectId) {
		final var list = repository.findBySubjectId( SubjectId.ofNotNull( subjectId ) );
		return StreamUtils.map( list, AttributeValueDTO::of );
	}

	public AttributeValueDTO findOrFail(String id) {
		final var found = repository.findOrFail( AttributeValueId.ofNotNull( id ) );
		if ( found.isDeleted() ) {
			throw new EntityNotFoundException( id );
		}
		return AttributeValueDTO.of( found );
	}

	@Transactional
	public void deleteAttributeValue(String id) {
		final var found = repository.findOrFail( AttributeValueId.ofNotNull( id ) );
		found.markAsDeleted();
		repository.update( found );
	}
}
