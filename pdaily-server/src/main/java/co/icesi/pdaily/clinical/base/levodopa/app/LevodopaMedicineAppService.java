package co.icesi.pdaily.clinical.base.levodopa.app;

import co.haruk.core.StreamUtils;
import co.icesi.pdaily.clinical.base.levodopa.domain.model.LevodopaMedicine;
import co.icesi.pdaily.clinical.base.levodopa.domain.model.LevodopaMedicineId;
import co.icesi.pdaily.clinical.base.levodopa.domain.model.LevodopaMedicineValidator;
import co.icesi.pdaily.clinical.base.levodopa.infrastructure.persistence.LevodopaMedicineRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class LevodopaMedicineAppService {
    @Inject
    LevodopaMedicineValidator validator;
    @Inject
    LevodopaMedicineRepository repository;

    public List<LevodopaMedicineDTO> findAll() {
        List<LevodopaMedicine> all = repository.findAll();
        return StreamUtils.map( all, LevodopaMedicineDTO::of );
    }

    @Transactional
    public LevodopaMedicineDTO saveLevodopaMedicine(LevodopaMedicineDTO dto) {
        final LevodopaMedicine changed = dto.toLevodopaMedicine();
        LevodopaMedicine saved;
        if ( changed.isPersistent() ) {
            final var original = repository.findOrFail( changed.id() );
            original.updateFrom( changed );
            validator.validate( original );
            saved = repository.update( original );
        } else {
            changed.setId( LevodopaMedicineId.generateNew() );
            validator.validate( changed );
            saved = repository.create( changed );
        }
        return LevodopaMedicineDTO.of( saved );
    }

    @Transactional
    public void deleteLevodopaMedicine(String id) {
        repository.delete( LevodopaMedicineId.ofNotNull( id ) );
    }

    public LevodopaMedicineDTO findById(String id) {
        final var found = repository.findOrFail( LevodopaMedicineId.ofNotNull( id ) );
        return LevodopaMedicineDTO.of( found );
    }
}
