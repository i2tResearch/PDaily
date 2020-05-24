package co.haruk.sms.clinical.base.animic.domain.model;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.haruk.sms.clinical.base.animic.infrastructure.persistence.AnimicTypeRepository;

@Dependent
public class AnimicTypeValidator {
	@Inject
	AnimicTypeRepository repository;

	public void validate(AnimicType animicType) {
		failIfDuplicatedByName( animicType );
	}

	private void failIfDuplicatedByName(AnimicType animicType) {
		final Optional<AnimicType> found = repository.findByLabel( animicType.label() );
		if ( found.isPresent() ) {
			final var existent = found.get();
			final var mustFail = !animicType.isPersistent() || !existent.equals( animicType );
			if ( mustFail ) {
				throw new DuplicatedRecordException( animicType.label() );
			}
		}
	}
}
