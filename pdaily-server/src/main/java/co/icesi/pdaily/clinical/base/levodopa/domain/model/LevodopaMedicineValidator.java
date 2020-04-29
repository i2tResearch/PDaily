package co.icesi.pdaily.clinical.base.levodopa.domain.model;

import co.haruk.core.domain.model.persistence.DuplicatedRecordException;
import co.icesi.pdaily.clinical.base.levodopa.infrastructure.persistence.LevodopaMedicineRepository;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Optional;

@Dependent
public class LevodopaMedicineValidator {
    @Inject
    LevodopaMedicineRepository repository;

    public void validate(LevodopaMedicine levodopa) {
        failIfDuplicatedByName( levodopa );
    }

    private void failIfDuplicatedByName(LevodopaMedicine levodopa) {
        final Optional<LevodopaMedicine> found = repository.findByName( levodopa.name() );
        if ( found.isPresent() ) {
            final var existent = found.get();
            final var mustFail = !levodopa.isPersistent() || !existent.equals( levodopa );
            if ( mustFail ) {
                throw new DuplicatedRecordException( levodopa.name() );
            }
        }
    }
}
